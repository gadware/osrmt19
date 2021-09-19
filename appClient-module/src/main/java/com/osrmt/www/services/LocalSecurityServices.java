package com.osrmt.www.services;
import java.util.Enumeration;

import com.osframework.appclient.services.SecurityServices;
import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.ApplicationSettingFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.ApplicationCustomControlModel;
import com.osframework.modellibrary.reference.security.ApplicationSecurityList;
import com.osframework.modellibrary.reference.security.ApplicationSecurityModel;
import com.osframework.modellibrary.reference.security.ApplicationSettingList;
import com.osframework.modellibrary.reference.security.ApplicationUserModel;
import com.osframework.modellibrary.reference.security.InvalidUserLoginException;
import com.osframework.modellibrary.reference.security.InvalidUserPasswordException;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.www.common.ArtifactPrivilege;

public class LocalSecurityServices extends BaseService {
	
	public LocalSecurityServices() {
		super();
	}
	
	public static ApplicationUserModel authenticate(ApplicationUserModel user) throws InvalidUserLoginException, InvalidUserPasswordException, Exception {
		authenticateContainer();
		ApplicationSettingList asl = SecurityServices.getSetting(ApplicationFramework.get(0),
				ApplicationSettingFramework.LDAP_INITIAL_CONTEXT_FACTORY);
		boolean ldap = false;
		if (asl.size() > 0 && asl.getFirst().getValueString() != null) {
			ldap = asl.getFirst().getValueString().equalsIgnoreCase("LDAP");
		}
		return SecurityServices.authenticate(user, false, ldap);
	}

	public static ApplicationControlList getAppControlsByUser(int appTypeRefId, ApplicationFramework application, ServiceCall call) throws InvalidUserLoginException, InvalidUserPasswordException, Exception {
		authenticateContainer();
		return SecurityServices.getAppControlsByUser(appTypeRefId, application, call);
	}

	/**  
	 *  Get the application custom control
	 */ 
	public static ApplicationCustomControlModel getApplicationCustomControl(int id, ServiceCall call) {
		authenticateContainer();
		return getApplicationCustomControl(id, call);
	}
	
	/**  
	 *  Get the application custom control
	 */ 
	public static ApplicationSecurityList getAppSecurity(ApplicationFramework application, ServiceCall call) {
		authenticateContainer();
		return SecurityServices.getAppSecurity(application, call);
	}
	
	/**  
	 *  Get the application custom control
	 */ 
	public static ApplicationSecurityList getAppSecurity(ApplicationFramework application, int appTypeRefId, ServiceCall call) {
		authenticateContainer();
		return SecurityServices.getAppSecurity(appTypeRefId, application, call);
	}
	
	public static void printStats() {
		authenticateContainer();
		SecurityServices.printStats();
	}
	
	public static ArtifactPrivilege getPrivilege(int appTypeRefId, int productRefId, ServiceCall call) {
		ArtifactPrivilege priv = new ArtifactPrivilege();
		
		ApplicationSecurityList asl = getAppSecurity(ApplicationFramework.get(ApplicationGroup.ARTIFACTFORM), appTypeRefId, call);
		Enumeration e1 = asl.elements();
		if (e1.hasMoreElements()) {
			ApplicationSecurityModel acm = (ApplicationSecurityModel) e1.nextElement();
			if (acm.isReadOnly()) {
				priv.setRead(true);
			} else {
				priv.setRead(true);
				priv.setCreate(true);
				priv.setDelete(true);
				priv.setUpdate(true);
			}
		}
		
		return priv;
	}

    public static void clearSystemCache() {
		authenticateContainer();
		SecurityServices.clearCache();
    }
}

