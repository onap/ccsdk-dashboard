/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 *  
 *  Modifications Copyright (C) 2019 IBM.
 * ================================================================================
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ============LICENSE_END=========================================================
 *
 *  ECOMP is a trademark and service mark of AT&T Intellectual Property.
 *******************************************************************************/
package org.onap.ccsdk.dasboard.exceptions;

import static org.junit.Assert.*;

import org.junit.Test;
import org.onap.ccsdk.dashboard.exceptions.DashboardControllerException;

public class DahboardControllerExceptionTest {

	    @Test
	    public void TestException1() {
	       
		   DashboardControllerException sta = new DashboardControllerException();
	       assertEquals("org.onap.ccsdk.dashboard.exceptions.DashboardControllerException",sta.getClass().getName());
	       
	    }

	    @Test
	    public void TestException2() {
	       
	    	DashboardControllerException sta= new DashboardControllerException("org.onap.ccsdk.dashboard.exceptions.DashboardControllerException");
	        assertEquals(sta.getMessage(), "org.onap.ccsdk.dashboard.exceptions.DashboardControllerException");
	        
	    }


	    @Test
	    public void TestException3() {
	        
	    DashboardControllerException sta= new DashboardControllerException(new Throwable());
	    assertEquals("org.onap.ccsdk.dashboard.exceptions.DashboardControllerException", sta.getClass().getName());
	       
	    }

	    

	    @Test
	    public void TestException4() {
	    	
	    DashboardControllerException sta= new DashboardControllerException("org.onap.ccsdk.dashboard.exceptions.DashboardControllerException", new Throwable());
	     assertEquals("org.onap.ccsdk.dashboard.exceptions.DashboardControllerException",sta.getClass().getName());
	        
	    }
}
