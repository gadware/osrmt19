package com.osrmt.www.issue;
import com.osframework.modellibrary.common.IControlModel;
import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.InvalidUserLoginException;
import com.osframework.modellibrary.reference.security.InvalidUserPasswordException;
import com.osrmt.modellibrary.issue.IssueModel;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.www.common.HtmlControlPanel;
import com.osrmt.www.services.LocalSecurityServices;

public class IssueDetailController {

	public IssueDetailController() {
		super();
	}
	
	
	public static String buildControls(IssueModel issue, int artifactRefId, IControlModel model, ServiceCall call, boolean readOnly) throws InvalidUserLoginException, InvalidUserPasswordException, Exception{
		String hiddenInput = "<input name=\"issueid\" value=\"" + issue.getIssueId() + "\" type=\"hidden\"/>";
		ApplicationControlList controls = LocalSecurityServices.getAppControlsByUser(artifactRefId, 
				ApplicationFramework.get(ApplicationGroup.HTMLISSUEFORM), call);

		return HtmlControlPanel.buildControls(hiddenInput, controls, model, call, readOnly);
	}
	
	
}

