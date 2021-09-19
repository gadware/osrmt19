package com.osrmt.appclient.artifact.form;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.common.ControlPanel;
import com.osframework.appclient.ui.components.UIStandardDialog;
import com.osframework.appclient.ui.listeners.UIActionListener;
import com.osframework.framework.utility.TimedAction;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osrmt.appclient.setting.AuthenticationSetting;
import com.osrmt.appclient.setting.DataFormatSetting;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;

public class SystemOptionForm extends UIStandardDialog {
	
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private SystemOptionModel optionModel = null;
	
	public SystemOptionForm(JFrame frame) {
		super(frame);
		this.frame = frame;
		initialize();
		addListeners();
	}	
	
	public void initialize() {
		optionModel = new SystemOptionModel(SecurityServices.getGlobalSettings());
		setTitle(ReferenceServices.getDisplay(FormTitleFramework.SYSTEMOPTIONMAINTENANCE));
		ApplicationControlList acl = SecurityServices.getAppControls(ApplicationFramework.get(ApplicationGroup.SYSTEMOPTIONFORM));
		JPanel panel = ControlPanel.getPanel(frame, acl, optionModel, 1);
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
				SecurityServices.UpdateApplicationSetting(optionModel);
				TimedAction ta = new TimedAction(1.0) {
					@Override
					public void executeTask() {
						AuthenticationSetting.initialize();
						DataFormatSetting.initialize();
					}
				};
				dispose();
			}
		});
	}
	
}

