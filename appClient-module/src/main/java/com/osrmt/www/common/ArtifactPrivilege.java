package com.osrmt.www.common;

public class ArtifactPrivilege {
	
	private boolean create = false;
	private boolean read = false;
	private boolean update = false;
	private boolean delete = false;

	public ArtifactPrivilege() {
		super();
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}
	
	

}

