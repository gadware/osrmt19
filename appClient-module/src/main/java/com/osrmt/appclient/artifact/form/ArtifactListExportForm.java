package com.osrmt.appclient.artifact.form;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.common.ControlPanel;
import com.osframework.appclient.ui.common.FormControl;
import com.osframework.appclient.ui.components.PanelOkCancel;
import com.osframework.appclient.ui.components.UICenterSouthDialog;
import com.osframework.appclient.ui.components.UIReferenceSearch;
import com.osframework.appclient.ui.controls.UICheckBox;
import com.osframework.appclient.ui.controls.UIOptionGroup;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.appclient.ui.listeners.UIActionListener;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.common.ReferenceDisplay;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.ModelColumnFramework;
import com.osframework.modellibrary.reference.group.ReferenceGroup;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osrmt.appclient.services.RequirementServices;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reference.group.FormTitleGroup;
import com.osrmt.modellibrary.reqmanager.ArtifactList;

public class ArtifactListExportForm extends UICenterSouthDialog {
	
	private static final long serialVersionUID = 1L;

	private ArtifactList artifactList;
	private JFrame frame;
	private PanelOkCancel okCancel = new PanelOkCancel();
	private ArtifactExportModel exportModel = new ArtifactExportModel();
	private Vector formControls = new Vector();

	public ArtifactListExportForm(JFrame frame, ArtifactList list) {
		super(frame, false);
		this.frame = frame;
		this.artifactList = list;
		initialize();
	}
	
	public void initialize() {
		try {
			initForm();
			addListeners();
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	private void initForm() {
		setTitle(ReferenceServices.getDisplay(FormTitleGroup.ARTIFACTLISTEXPORT));
		getSouthPanel().add(okCancel, BorderLayout.CENTER);
		getCenterPanel().add(new JScrollPane(buildExportPanel()), BorderLayout.CENTER);
		setSize(UIProperties.getWINDOW_SIZE_700_400());
	}
	
	private void addListeners() {
		okCancel.addCancelActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				dispose();
			}
		});
		okCancel.addOkActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				updateModel();
				exportArtifacts();
				dispose();
			}
		});
	}

	private JPanel buildExportPanel() {
		ApplicationControlList list = SecurityServices.getAppControls(ApplicationFramework.get(ApplicationGroup.ARTIFACTLISTEXPORT));
		
		ControlPanel cp = new ControlPanel(frame);
		cp.initialize(list, 1, exportModel);
		formControls = cp.getFormControls();
		return cp.getPanel();
	}
	
	//TODO rewrite this hardcoded form...
	private void updateModel() {
		Enumeration e1= this.formControls.elements();
		while (e1.hasMoreElements()) {
			FormControl fc = (FormControl) e1.nextElement();
			if (fc.getModelColumnRefId() == ModelColumnFramework.ARTIFACTEXPORTMODELSELECTED) {
				if (fc.getComponent() instanceof UIOptionGroup) {
					UIOptionGroup optionGroup = (UIOptionGroup) fc.getComponent();
					if (optionGroup.getSelected() == 0) {
						exportModel.setExportList(true);
					} else {
						exportModel.setExportAllArtifacts(true);
					}
				} else {
					Debug.LogError(this,"control is not UIOptionGroup");
				}				
			} else if (fc.getModelColumnRefId() == ModelColumnFramework.ARTIFACTEXPORTMODELPRIMARYKEY) {
				if (fc.getComponent() instanceof UICheckBox) {
					UICheckBox checkBox = (UICheckBox) fc.getComponent();
					exportModel.setRemovePrimaryKey(checkBox.isSelected());
				} else {
					Debug.LogError(this,"control is not UICheckBox");
				}
			} else if (fc.getModelColumnRefId() == ModelColumnFramework.ARTIFACTEXPORTMODELTREE) {
				if (fc.getComponent() instanceof UICheckBox) {
					UICheckBox checkBox = (UICheckBox) fc.getComponent();
					exportModel.setExportRelationships(checkBox.isSelected());
				} else {
					Debug.LogError(this,"control is not UICheckBox");
				}
			} else if (fc.getModelColumnRefId() == ModelColumnFramework.ARTIFACTEXPORTMODELDEPENDENCIES) {
				if (fc.getComponent() instanceof UICheckBox) {
					UICheckBox checkBox = (UICheckBox) fc.getComponent();
					exportModel.setExportDependencies(checkBox.isSelected());
				} else {
					Debug.LogError(this,"control is not UICheckBox");
				}
			}
		}
	}

	private void exportArtifacts() {
		if (artifactList == null || artifactList.size() == 0) {
			final UIReferenceSearch referenceSearch = new UIReferenceSearch(frame);
			referenceSearch.start(ReferenceGroup.Artifact, ReferenceServices.getMsg(SystemMessageFramework.SELECTARTIFACTTYPESTOEXPORT), false);
			referenceSearch.setSize(UIProperties.getDIALOG_SIZE_450_330());
			referenceSearch.addOkActionListener(new UIActionListener(frame) {
				public void actionExecuted(ActionEvent e) {
					try {
						//TODO should force selection before ok
						ArtifactList list = new ArtifactList(4096);
						if (referenceSearch.getSelectedValues() != null) {
							Enumeration e1 = referenceSearch.getSelectedValues().elements();
							while (e1.hasMoreElements()) {
								ReferenceDisplay artifactType = (ReferenceDisplay) e1.nextElement();
								list.addAll(RequirementServices.getAllItems(artifactType.getRefId()));
							}
							Debug.displayGUIMessage(list.size() + " " + ReferenceServices.getMsg(SystemMessageFramework.ARTIFACTSEXPORTEDTO)
									+ " " + exportModel.getFilePath());
							export(list);
							referenceSearch.dispose();
						}
					} catch (Exception ex) {
						Debug.LogException(this, ex);
					}
				}
			});
			referenceSearch.setVisible(true);

		} else {
			export(artifactList);
		}
	}
	
	private void export(ArtifactList list) {
		RequirementServices.exportArtifacts(list, exportModel);
	}
}
