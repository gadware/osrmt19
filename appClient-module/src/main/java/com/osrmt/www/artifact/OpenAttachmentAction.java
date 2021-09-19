/**    Copyright (C) 2006  PSC (Poland Solution Center)
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.osrmt.www.artifact;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.FileProcess;
import com.osframework.modellibrary.system.RecordFileModel;
import com.osrmt.www.NotLoggedInException;
import com.osrmt.www.common.BaseAction;
import com.osrmt.www.services.LocalSystemServices;
import com.osrmt.www.utilty.WebFileUtil;
/**
 *
 */
public class OpenAttachmentAction extends BaseAction {
    
    /* forward name="success" path="" */
    private final static String SUCCESS = "success";
    
    /**
     * inputs
     */
    static String RECORDFILEID = "recordfileid";
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    public ActionForward execute(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            
            Integer recordfileid = PropertyUtils.getSimpleProperty(form,
            		RECORDFILEID) == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form,
            				RECORDFILEID);
            
            ActionMessages errors = new ActionMessages();
            
            RecordFileModel rfm = LocalSystemServices.getRecordFile(recordfileid, getServiceCall(request));
            
            if(!new File(servlet.getServletContext().getRealPath("")+"/reports/attachments").exists()){
                new File(servlet.getServletContext().getRealPath("")+"/reports/attachments").mkdir();
            }
            WebFileUtil.copyFile(new File(FileProcess.getFilePath(rfm.getStorageDirectory(), rfm.getStorageFileName())), 
            		new File(servlet.getServletContext().getRealPath("")+"/reports/attachments/" + rfm.getStorageFileName()));
            response.sendRedirect("reports/attachments/"+ rfm.getStorageFileName());
            return null;
        } catch (NotLoggedInException l) {
            return mapping.findForward("NotLoggedInException".toLowerCase());
        } catch (Exception ex) {
            Debug.LogException(this, ex);
            throw ex;
        }
    }
    
    
}
