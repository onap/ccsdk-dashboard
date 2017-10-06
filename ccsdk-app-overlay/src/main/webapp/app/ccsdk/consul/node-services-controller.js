/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2017 AT&T Intellectual Property. All rights reserved.
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
appDS2.controller('nodeServicesCtrl', function($scope, $log, message, NodeHealthService) {

	'use strict';
	
	// Controls logging in this controller
	var debug = false;

	if (debug)
		$log.debug('nodeServicesCtrl: message is ' + JSON.stringify(message));

	// this object holds all app data and functions
	$scope.ecdapp = {};	
	$scope.ecdapp.label = 'Services on Node ' + message.node;
	
	/**
	 * Loads the table of services for the specified node.
	 */
	$scope.ecdapp.loadTable = function(nodeName) {
		$scope.ecdapp.isDataLoading = true;
		if (debug)
			$log.debug('nodeServicesCtrl: loading data for ' + nodeName);
		NodeHealthService.getNodeServicesHealth(nodeName).then(
				function(jsonObj) {
					if (debug)
						$log.debug('nodeServicesCtrl: response is ' + JSON.stringify(jsonObj));
					if (jsonObj.error) {
						$log.error("nodeServicesCtrl.loadTable failed: "
								+ jsonObj.error);
						$scope.ecdapp.isRequestFailed = true;
						$scope.ecdapp.errMsg = jsonObj.error;
						$scope.ecdapp.tableData = [];
					} else {
						// $log.debug("serviceHealthController.loadTable
						// succeeded, size " + jsonObj.data.length);
						$scope.ecdapp.isRequestFailed = false;
						$scope.ecdapp.errMsg = null;
						$scope.ecdapp.tableData = jsonObj;
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error("nodeServicesCtrl.loadTable failed: "
							+ error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = error;
					$scope.ecdapp.tableData = [];
					$scope.ecdapp.isDataLoading = false;
				});
	};

	// Show services for the requested node
	if (debug)
		$log.debug('nodeServicesCtrl: requesting services for node ' + message.node);
	$scope.ecdapp.loadTable(message.node);	
	
});
