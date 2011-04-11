package com.runtimecollective.api.vo;

public class Brand {

	private int mId;
	private int mClientId;
	private int mCreatorId;
	private int mProjectId;
	private boolean mEnabled;
	private String mName;
	
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
	public int getCreatorId() {
		return mCreatorId;
	}
	public void setCreatorId(int creatorId) {
		mCreatorId = creatorId;
	}
	public int getProjectId() {
		return mProjectId;
	}
	public void setProjectId(int projectId) {
		mProjectId = projectId;
	}
	public boolean isEnabled() {
		return mEnabled;
	}
	public void setEnabled(boolean enabled) {
		this.mEnabled = enabled;
	}
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}
	
}
