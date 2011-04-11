package com.runtimecollective.api.vo;

public class Project {
	
	private int mId;
	private int mClientId;
	private int mCreatorUserId;
	private String mName;
	private String mCreationDate;
	
	public int getId() {
		return mId;
	}
	public void setId(int id) {
		mId = id;
	}
	public int getClientId() {
		return mClientId;
	}
	public void setClientId(int clientId) {
		mClientId = clientId;
	}
	public int getCreatorUserId() {
		return mCreatorUserId;
	}
	public void setCreatorUserId(int creatorUserId) {
		mCreatorUserId = creatorUserId;
	}
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}
	
	public String getCreationdate() {
		return mCreationDate;
	}
	public void setCreationDate(String creationDate) {
		mCreationDate= creationDate;
	}

}
