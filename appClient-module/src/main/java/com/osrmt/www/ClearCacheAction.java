/*
 * $Id: ClearCacheAction.java,v 1.1 2006/08/29 18:49:00 aron-smith Exp $ 
 *
 * Copyright 2000-2005 Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.osrmt.www;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.osframework.framework.logging.Debug;
import com.osrmt.www.common.BaseAction;
import com.osrmt.www.common.WebUser;
import com.osrmt.www.services.LocalSecurityServices;


/**
 * <p>Validate a user logon.</p>
 *
 * @version $Rev: 164858 $ $Date: 2006/08/29 18:49:00 $
 */
public final class ClearCacheAction extends BaseAction {

    // --------------------------------------------------------- Public Methods

    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

    	try {
        ActionMessages errors = new ActionMessages();
        
        WebUser user = getUser(request);
        Debug.LogDebug(this,"Clearing cache");
        LocalSecurityServices.clearSystemCache();

        // Otherwise, return "success"
        	return mapping.findForward("success");
    	} catch (NotLoggedInException l) {
    		return mapping.findForward("NotLoggedInException".toLowerCase());
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		throw ex;
    	}

    }

}
