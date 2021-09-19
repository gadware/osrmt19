package com.osrmt.appclient.artifact.form;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.osframework.appclient.services.Application;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.services.SystemServices;
import com.osframework.appclient.ui.common.ControlPanel;
import com.osframework.appclient.ui.components.UIStandardDialog;
import com.osframework.appclient.ui.listeners.UIActionListener;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.appclient.services.RequirementServices;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reqmanager.BaselineModel;

public class BaselineForm extends UIStandardDialog {
	
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private BaselineModel baseline = new BaselineModel();
	
	public BaselineForm(JFrame frame) {
		super(frame);
		this.frame = frame;
		initialize();
		addListeners();
	}	
	
	public void initialize() {
		setTitle(ReferenceServices.getDisplay(FormTitleFramework.SYSTEMBASELIINE));
		ApplicationControlList acl = SecurityServices.getAppControls(ApplicationFramework.get(ApplicationGroup.BASELINEFORM));
		JPanel panel = ControlPanel.getPanel(frame, acl, baseline, 1);
		getCenterPanel().add(panel, BorderLayout.CENTER);
	}
	
	
	private void addListeners() {
		getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				dispose();
			}
		});
		// OK
		getButtonPanel().getCmdButton1().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				if (baseline.getBaselineName() != null && baseline.getBaselineName().toString().length() > 0) {
					
					if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(frame, SystemServices.calculateBaseline())) {
						ApplicationObject ao = (ApplicationObject) Application.getObject();
						int productRefId = ao.getProductRefId();
						if (productRefId > 0) {
							int baselineId = RequirementServices.createBaseline(baseline.getBaselineName()).getPrimaryKeyId();
							dispose();
						}
					}

				}
			}
		});
	}
	
}

