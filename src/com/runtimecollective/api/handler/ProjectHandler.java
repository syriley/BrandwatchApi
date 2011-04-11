package com.runtimecollective.api.handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.runtimecollective.api.vo.Project;

public class ProjectHandler extends DefaultHandler {
	
	static final  String ARRAY = "array";
	static final  String VALUE = "value";
	static final  String ID = "id";
	static final  String CLIENT_ID = "clientId";
	static final  String NAME = "name";
	static final  String CREATION_DATE = "creationDate";
	private List<Project> mProjects;
	private Project currentProject;
	private StringBuilder mBuilder;
	

	
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        mBuilder.append(ch, start, length);
    }

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		mProjects = new ArrayList<Project>();
		mBuilder = new StringBuilder();
	}
	
	/**
	 * make a new project object for each project in xml array
	 */
	@Override
	public void startElement(String uri, String localName, String name,
		Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, name, attributes);
		if (localName.equalsIgnoreCase(ARRAY) && attributes.getValue(VALUE).equalsIgnoreCase("com.runtimecollective.crawler.domainobject.Project")) {
	            this.currentProject = new Project();
	    }
	}

	/**
	 * Add project xml attributes to current project 
	 */
	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		super.endElement(uri, localName, name);
		if (this.currentProject != null) {
            if (localName.equalsIgnoreCase(ID)) {
                currentProject.setId(Integer.parseInt(mBuilder.toString().trim()));
            } 
            else if (localName.equalsIgnoreCase(NAME)){
                currentProject.setName(mBuilder.toString().trim());
            }
            /*else if (localName.equalsIgnoreCase(CLIENT_ID)){
                currentProject.setClientId(Integer.parseInt(mBuilder.toString().trim()));
            } 
            else if (localName.equalsIgnoreCase(CREATION_DATE)){
                currentProject.setCreationDate(mBuilder.toString().trim());
            } */
            else if (localName.equalsIgnoreCase(ARRAY)){
                mProjects.add(currentProject);
            }
            mBuilder.setLength(0);    
        }
	}
	
	/**
	 * return the parsed list of projects
	 * @return Project list
	 */
	public List<Project> getProjects(){
        return mProjects;
    }

}
