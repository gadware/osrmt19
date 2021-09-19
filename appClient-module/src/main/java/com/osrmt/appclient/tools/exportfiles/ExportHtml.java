package com.osrmt.appclient.tools.exportfiles;
import java.util.Enumeration;

import com.osframework.appclient.services.Application;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.framework.utility.ControlScript;
import com.osframework.framework.utility.FileSystemUtil;
import com.osframework.modellibrary.reference.common.ReferenceList;
import com.osframework.modellibrary.reference.common.ReferenceModel;
import com.osframework.modellibrary.reference.group.ReferenceGroup;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.appclient.services.RequirementServices;
import com.osrmt.modellibrary.reference.group.RelationGroup;
import com.osrmt.modellibrary.reqmanager.ArtifactList;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;

public class ExportHtml {

	public ExportHtml() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static String getUsage() {
		return "usage:\n\tExportArtifacts <ALL|artifact ref id> <filename script filename> <content script filename>"
			+ "\n\t\te.g. ExportArtifacts 132 filename.js content.js";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length != 3) {
				System.err.println(getUsage());
			} else {
				int productId = ReferenceServices.getActiveReferenceList(ReferenceGroup.Product).getFirst().getRefId();
				ApplicationObject ao = new ApplicationObject();
				ao.setProductRefId(productId,"");
				Application.setObject(ao);
				ArtifactList artifacts = new ArtifactList();
				if (args[0].equalsIgnoreCase("ALL")) {
					ReferenceList refs = ReferenceServices.getActiveReferenceList(ReferenceGroup.Artifact);
					Enumeration r1 = refs.elements();
					while (r1.hasMoreElements()) {
						ReferenceModel rm = (ReferenceModel) r1.nextElement();
						ArtifactList sublist = RequirementServices.getAllItems(rm.getRefId());
						Enumeration a1 = sublist.elements();
						while (a1.hasMoreElements()) {
							ArtifactModel am = (ArtifactModel) a1.nextElement();
							artifacts.add(am);
							
						}
					}
				} else {
					RequirementServices.getAllItems(Integer.parseInt(args[0]));
				}
				String filenameScript = FileSystemUtil.getTextContents(args[1]);
				String contentScript = FileSystemUtil.getTextContents(args[2]);
				int count = artifacts.size();
				int prevCountDown = 10;
				Enumeration e1 = artifacts.elements();
				while (e1.hasMoreElements()) {
					ArtifactModel am = (ArtifactModel) e1.nextElement();
					process(am, filenameScript, contentScript);
					count--;
					int countDown = (10*count/artifacts.size())+1;
					if (countDown < prevCountDown) {
						System.out.print(countDown + "..");
						prevCountDown = countDown;
					}					
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void process(ArtifactModel am, String filenameScript, String contentScript) throws Exception  {
		ControlScript script1 = new ControlScript();
		script1.addReference(am, "artifact");
		String filename = (String) script1.execute(filenameScript, String.class, true);
		
		ControlScript script2 = new ControlScript();
		ArtifactList parents = RequirementServices.getParentArtifacts(am);
		ArtifactList children = RequirementServices.getChildren(am.getArtifactRefId(), am.getArtifactId(), RelationGroup.get(RelationGroup.RELATED));
		ArtifactList traceto = RequirementServices.getDependentItems(am.getArtifactId());
		ArtifactList tracefrom = RequirementServices.getParentDependentItems(am.getArtifactId());
		script2.addReference(parents, "parents");
		script2.addReference(children, "children");
		script2.addReference(traceto, "traceto");
		script2.addReference(tracefrom, "tracefrom");
		script2.addReference(am, "artifact");
		String contents = (String) script2.execute(contentScript, String.class, true);
		
//		System.out.println(am.getArtifactName() + " > " + filename);
//		System.out.println(contents);
		FileSystemUtil.createFile(filename, contents);
	}

}

