package com.osrmt.www.services;
import java.util.ArrayList;
import java.util.Enumeration;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.modellibrary.reference.common.ReferenceList;
import com.osframework.modellibrary.reference.group.ReferenceGroup;

public class LocalReferenceServices extends BaseService {

	public LocalReferenceServices() {
		super();
	}

	public static ArrayList getProducts() {
		authenticateContainer();
		ReferenceList referenceList = ReferenceServices.getActiveReferenceList(ReferenceGroup.Product);
		ArrayList list = new ArrayList();
		Enumeration e1 = referenceList.elements();
		while (e1.hasMoreElements()) {
			list.add(e1.nextElement());
		}
		return list;
	}
	
	public static String getDisplay(int refId) {
		authenticateContainer();
		return ReferenceServices.getDisplay(refId);
	}
}

