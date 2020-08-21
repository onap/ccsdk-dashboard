/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 *
 * ECOMP is a trademark and service mark of AT&T Intellectual Property.
 *******************************************************************************/

package org.onap.ccsdk.dashboard.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.core.MockitoTestSuite;
import org.onap.ccsdk.dashboard.model.HealthStatus;
import org.onap.portalsdk.core.util.SystemProperties;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
@PrepareForTest({SystemProperties.class})
public class HealthCheckControllerTest extends MockitoTestSuite {

    @InjectMocks
    HealthCheckController subject;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockedRequest = getMockedRequest();
        mockedResponse = getMockedResponse();
        PowerMockito.mockStatic(SystemProperties.class);
        when(SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME))
            .thenReturn("oom-dash");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testHealthCheck() {
        String messg = "oom-dash health check passed";
        HealthStatus actualResult = subject.healthCheck(mockedRequest, mockedResponse);
        assertTrue(actualResult.statusCode == 200);
    }

}
