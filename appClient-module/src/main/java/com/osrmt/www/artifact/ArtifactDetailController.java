package com.osrmt.www.artifact;
import com.osframework.modellibrary.common.IControlModel;
import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.InvalidUserLoginException;
import com.osframework.modellibrary.reference.security.InvalidUserPasswordException;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;
import com.osrmt.www.common.HtmlControlPanel;
import com.osrmt.www.services.LocalSecurityServices;

public class ArtifactDetailController {

	public ArtifactDetailController() {
		super();
	}
	
	public static String buildControls(ArtifactModel artifact, IControlModel model, ServiceCall call, boolean readOnly) throws InvalidUserLoginException, InvalidUserPasswordException, Exception{
		String hiddenInput = "<input name=\"artifactid\" value=\"" + artifact.getArtifactId() + "\" type=\"hidden\"/>";
		ApplicationControlList controls = LocalSecurityServices.getAppControlsByUser(artifact.getArtifactRefId(), 
				ApplicationFramework.get(ApplicationGroup.HTMLARTIFACTFORM), call);

		return HtmlControlPanel.buildControls(hiddenInput, controls, model, call, readOnly);
	}
}

