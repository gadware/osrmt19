package com.osrmt.www.common;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.common.IHtmlApplicationControl;
import com.osframework.modellibrary.common.IControlModel;
import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.group.ControlTypeFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.ApplicationControlModel;
import com.osframework.modellibrary.reference.security.ApplicationCustomControlModel;
import com.osframework.modellibrary.reference.security.InvalidUserLoginException;
import com.osframework.modellibrary.reference.security.InvalidUserPasswordException;

public class HtmlControlPanel {

	public HtmlControlPanel() {
		super();
	}
	
	public static String buildControls(String hiddenInputHtml, ApplicationControlList controls, IControlModel model, ServiceCall call, boolean readOnly) throws InvalidUserLoginException, InvalidUserPasswordException, Exception {
		StringBuilder sb = new StringBuilder(4096);
		sb.append("<table>");
		Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();
			ApplicationCustomControlModel custom = SecurityServices.getApplicationCustomControl(rm.getApplicationCustomControlId());
			rm.setCustom(custom);
			if (rm.getControlTypeRefId() != ControlTypeFramework.UITAB
					&& rm.getControlTypeRefId() != ControlTypeFramework.UISEPARATOR) {
				sb.append(getHtml(rm, model, readOnly));
			}
		}
		sb.append("</table>");
		sb.append("\r\n");
		return sb.toString();		
	}
	
	
	private static String getHtml(IHtmlApplicationControl fc, IControlModel model, boolean readOnly) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder(64);
		sb.append("<tr>");
		sb.append("<td class=\"controllabel\">");
		//TODO escape html
		sb.append(fc.getLabel());
		sb.append("</td>");
		
		sb.append("<td>");
		if (readOnly || fc.isLocked() || fc.isDisabled()) {
			sb.append(new HtmlControl(fc, model).getValue());
		} else {
			sb.append(new HtmlControl(fc, model).getControl());
		}
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("\r\n");
		return sb.toString();
	}
	
}

