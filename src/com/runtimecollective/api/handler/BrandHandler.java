package com.runtimecollective.api.handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import com.runtimecollective.api.vo.Brand;

public class BrandHandler extends DefaultHandler {
	
	static final  String ARRAY = "array";
	static final  String VALUE = "value";
	static final  String ID = "id";
	static final  String CLIENT_ID = "clientId";
	static final  String PROJECT_ID = "projectId";
	static final  String NAME = "name";
	static final  String CREATOR_ID = "creationDate";
	private List<Brand> mBrands;
	private Brand currentBrand;
	private StringBuilder mBuilder;
	private int arrayLevel = 0;

	
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        mBuilder.append(ch, start, length);
    }

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		mBrands = new ArrayList<Brand>();
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
		if (name.equalsIgnoreCase(ARRAY)) {
			if (attributes.getValue(VALUE).equalsIgnoreCase("com.runtimecollective.search.domainobject.Brand")) {
	            this.currentBrand = new Brand();
			}
			arrayLevel ++;
	    }
	}

	/**
	 * Add project xml attributes to current project 
	 */
	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		super.endElement(uri, localName, name);
		if (this.currentBrand != null) {
            if (localName.equalsIgnoreCase(ID)) {
                currentBrand.setId(Integer.parseInt(mBuilder.toString().trim()));
            } 
            else if (localName.equalsIgnoreCase(NAME)){
                currentBrand.setName(mBuilder.toString().trim());
            }
            /*else if (localName.equalsIgnoreCase(CLIENT_ID)){
                currentBrand.setClientId(Integer.parseInt(mBuilder.toString().trim()));
            } 
            else if (localName.equalsIgnoreCase(CREATION_DATE)){
                currentBrand.setCreationDate(mBuilder.toString().trim());
            } */
            else if (localName.equalsIgnoreCase(ARRAY)){
            	if (arrayLevel == 1)
            	{
            		mBrands.add(currentBrand);
            	}
            	arrayLevel--;
            }
            mBuilder.setLength(0);    
        }
	}
	
	/**
	 * return the parsed list of projects
	 * @return Project list
	 */
	public List<Brand> getBrands(){
        return mBrands;
    }

}
