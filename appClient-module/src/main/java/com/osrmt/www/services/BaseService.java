package com.osrmt.www.services;
import java.security.Principal;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.callback.SecurityAssociationHandler;

import com.osframework.framework.locale.ConvertUtil;
import com.osframework.framework.logging.Debug;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;


public class BaseService {

	public BaseService() {
		super();
	}

	public static void authenticateContainer() {
        SecurityAssociationHandler handler = new SecurityAssociationHandler();
        Principal user = new SimplePrincipal("200");
        handler.setSecurityInfo(user, "j2ee".toCharArray());
        LoginContext loginContext = null;
		try {
			loginContext = new LoginContext("client-login", handler);
	        loginContext.login();
	        Subject subject = loginContext.getSubject();
	        Set principals = subject.getPrincipals();
	        principals.add(user);

		} catch (LoginException e) {
			Debug.LogException("BaseService", e);
			
		}
	}
	
	public static void convertUTF(ArtifactModel artifact) {
		artifact.setArtifactName(ConvertUtil.toUTF8(artifact.getArtifactName()));
		artifact.setDescription(ConvertUtil.toUTF8(artifact.getDescription()));
		artifact.setRationale(ConvertUtil.toUTF8(artifact.getRationale()));
		artifact.setOrigin(ConvertUtil.toUTF8(artifact.getOrigin()));
		artifact.setGoal(ConvertUtil.toUTF8(artifact.getGoal()));
		artifact.setContext(ConvertUtil.toUTF8(artifact.getContext()));
		artifact.setPrecondition(ConvertUtil.toUTF8(artifact.getPrecondition()));
		artifact.setPostcondition(ConvertUtil.toUTF8(artifact.getPostcondition()));
		artifact.setSummary(ConvertUtil.toUTF8(artifact.getSummary()));
		artifact.setExternalReferences(ConvertUtil.toUTF8(artifact.getExternalReferences()));
		artifact.setCustomText1(ConvertUtil.toUTF8(artifact.getCustomText1()));
		artifact.setCustomText2(ConvertUtil.toUTF8(artifact.getCustomText2()));
		artifact.setCustomText3(ConvertUtil.toUTF8(artifact.getCustomText3()));
		artifact.setCustomText4(ConvertUtil.toUTF8(artifact.getCustomText4()));
		artifact.setCustomMemo1(ConvertUtil.toUTF8(artifact.getCustomMemo1()));
		artifact.setCustomMemo2(ConvertUtil.toUTF8(artifact.getCustomMemo2()));
	}
}

