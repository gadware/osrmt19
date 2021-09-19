/*
    //usage

    Copyright (C) 2006  Aron Lancout Smith

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

*/
package com.osrmt.ejb.reqreference;

import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.common.ReferenceGroupList;

public interface IRequirementReference
{
    /**  
     *  Get all reference groups
     */ 
    public ReferenceGroupList getManagedReferenceGroups(boolean systemWizard, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


}
