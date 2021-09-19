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

package com.osrmt.www.services;

import java.rmi.RemoteException;

import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.ejb.system.ISystem;
import com.osframework.ejb.system.SystemUtil;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.system.RecordFileModel;

/**
 *
 * @author zborowskil
 */
public class LocalSystemServices extends BaseService {
    
    /** Creates a new instance of LocalReportWriterServices */
    public LocalSystemServices() {
        super();
    }
    
    private static ISystem getSystemRef() throws Exception {
        return SystemUtil.getSystem();
    }
    
    public static RecordFileModel getRecordFile(Integer recordFileId, ServiceCall call) throws RemoteException, DataAccessException, Exception {
        try {
        	RecordFileModel record = getSystemRef().getRecordFile(recordFileId, call);
            return record;
        } catch (Exception e) {
            Debug.LogException("SystemServices", e);
            return null;
        }
    }
    
}
