/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 * 
 * Modifications Copyright (C) 2019 IBM.
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

package org.onap.ccsdk.dashboard.exceptions.inventory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BlueprintParseExceptionTest {

    @Test
    public void TestException3() {

        BlueprintParseException mae = new BlueprintParseException(new Throwable());
        assertEquals("org.onap.ccsdk.dashboard.exceptions.inventory.BlueprintParseException",
            mae.getClass().getName());

    }

}
