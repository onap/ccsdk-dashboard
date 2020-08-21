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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.onap.ccsdk.dashboard.core.MockitoTestSuite;
import org.springframework.web.servlet.ModelAndView;

public class ECDSingleSignOnControllerTest extends MockitoTestSuite {

    @InjectMocks
    ECDSingleSignOnController subject;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testSingleSignOnLogin() throws Exception {

        mockedRequest.addParameter("forwardURL", "ecd#");
        ModelAndView actualResult = subject.singleSignOnLogin(mockedRequest, mockedResponse);
        assertTrue(actualResult.getViewName().contains("login"));

    }

}
