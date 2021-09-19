package com.osrmt.www.common;
import com.osframework.modellibrary.reference.security.ApplicationUserModel;

public class WebUser extends ApplicationUserModel {
	
	private int productRefId = 0;

	public WebUser() {
		super();
	}

	public int getProductRefId() {
		return productRefId;
	}

	public void setProductRefId(int productRefId) {
		this.productRefId = productRefId;
	}

	
}

