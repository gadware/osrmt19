package com.osrmt.appclient.services;

import com.osframework.appclient.services.BaseService;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.common.ReferenceGroupList;
import com.osrmt.ejb.reqreference.IRequirementReference;
import com.osrmt.ejb.reqreference.RequirementReferenceUtil;;

/**
 * ReferenceServices provides helper functionality to return interfaces
 * to the business entity beans.  
 */
public class ReqReferenceServices extends BaseService {
	
		

    public static ReferenceGroupList getManagedReferenceGroups(boolean systemWizard) {
		try {
			return getReqReference().getManagedReferenceGroups(systemWizard, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("ReqReferenceServices",ex);
			return new ReferenceGroupList();
		}
    }
    
	public static IRequirementReference getReqReference() throws Exception {
		return RequirementReferenceUtil.getRequirementReference();
	}
	
}
