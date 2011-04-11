package com.runtimecollective.api;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xml.sax.SAXException;

import com.runtimecollective.api.handler.BrandHandler;
import com.runtimecollective.api.handler.ProjectHandler;
import com.runtimecollective.api.vo.Brand;
import com.runtimecollective.api.vo.Project;


public class Model {

	private String baseUrl;
	
	private static Model mModel;
	private SAXParserFactory saxParserFactory;
	private SAXParser parser;
	private ClientConnectionManager connectionManager;
	private HttpClient httpClient;
	private int mCurrentBrandId;
	private SchemeRegistry schemeRegistry;
	
	protected Model() {
		schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http",
		PlainSocketFactory.getSocketFactory(), 80));
		connectionManager = new ThreadSafeClientConnManager(schemeRegistry);
		httpClient = new DefaultHttpClient(connectionManager);
		 
		 
		saxParserFactory = SAXParserFactory.newInstance();
		try {
			parser = saxParserFactory.newSAXParser();
		}
		 catch (Exception e){
			 e.printStackTrace();
		 }
		 // Exists only to defeat instantiation.
	}
	
	/**
	 * Get the singleton
	 * @return
	 */
	public static Model getInstance() {
		if (mModel == null) {
			mModel = new Model();
		}
		return mModel;
	}
	
	public HttpResponse Login(String username, String password) throws UnsupportedEncodingException, IOException {
	    HttpPost httpPost = new HttpPost(baseUrl + "j_acegi_security_check");
	    
	    
	    List<NameValuePair> nvps = new ArrayList <NameValuePair>();
	    nvps.add(new BasicNameValuePair("j_username", username));
	    nvps.add(new BasicNameValuePair("j_password", password));
	    httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
	    
	    //get cookie from logging in
    	return httpClient.execute(httpPost);
	}

	/**
	 * Make an http request for the users available projects 
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 */
	public List<Project> getProjects() throws IOException, SAXException {
		 
		HttpGet projectNamesGet = new HttpGet(baseUrl + "projects/?output=xml&time=1295005820518");
		
		
		HttpResponse response = httpClient.execute(projectNamesGet);
		ProjectHandler handler =  new ProjectHandler();
		parser.parse(response.getEntity().getContent(), handler);
		return handler.getProjects();
	}
	
	/**
	 * Make an http request for the users available projects 
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 */
	public List<Brand> getBrands(int projectId) throws IOException, SAXException {
		 
		HttpGet projectNamesGet = new HttpGet(String.format(baseUrl + "brands/?projectId=%d&output=xml", projectId));
		System.out.println(EntityUtils.toString(httpClient.execute(projectNamesGet).getEntity()));
		BrandHandler handler =  new BrandHandler();
		parser.parse(httpClient.execute(projectNamesGet).getEntity().getContent(), handler);
		return handler.getBrands();
	}

	public int getCurrentBrandId() {
		return mCurrentBrandId;
	}

	public void setCurrentBrandId(int currentBrandId) {
		mCurrentBrandId = currentBrandId;
	}
	
	public void setBaseUrl(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}
}
