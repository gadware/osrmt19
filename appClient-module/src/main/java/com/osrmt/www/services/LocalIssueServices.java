package com.osrmt.www.services;
import com.osframework.datalibrary.common.UpdateResult;
import com.osframework.modellibrary.common.ServiceCall;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.appclient.services.IssueServices;
import com.osrmt.modellibrary.issue.IssueList;
import com.osrmt.modellibrary.issue.IssueModel;
import com.osrmt.modellibrary.reference.group.IssueTypeGroup;

public class LocalIssueServices extends BaseService {

	public LocalIssueServices() {
		super();
	}

	public static IssueModel getIssue(Integer issueId, ServiceCall serviceCall) {
		authenticateContainer();
		IssueModel issue = IssueServices.getIssue(issueId.intValue(), serviceCall);
		return issue;
	}
	
	public static IssueList getIssuesByArtifact(int artifactId, IssueTypeGroup issueType, ServiceCall call) { 
		authenticateContainer();
		IssueList issues = IssueServices.getIssuesByArtifact(artifactId, issueType, call);
		return issues;
	}
	
	public static UpdateResult updateIssue(IssueModel issue, ServiceCall serviceCall) {
		authenticateContainer();
		return IssueServices.UpdateIssue(issue, IssueTypeGroup.get(IssueTypeGroup.FEEDBACK), serviceCall);
	}
	
	
	private static void setApplicationObject(int productRefId, ServiceCall call) {
		ApplicationObject ao = new ApplicationObject();
		ao.setProductRefId(productRefId, LocalReferenceServices.getDisplay(productRefId));
		call.setApplication(ao);
	}
	
	public static IssueList getIssuesBySubmitUser(int userId, IssueTypeGroup issueType, ServiceCall call) { 
		authenticateContainer();
		IssueList issues = IssueServices.getIssuesBySubmitUser(userId, issueType, call);
		return issues;
	}
	

}

