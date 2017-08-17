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
appDS2.controller('selectEcompcController', function(
	$scope, $rootScope, $log, $modalInstance, message, ControllerService) {

	// message brings the table items
	
	'use strict';

	// Set to true for verbose output
	var debug = false;
	
	// this object holds data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.tableData = message.items;

	// Populate the model that drives the radio buttons.
	// Radio buttons require a model with the value of the button
	// that is selected.  The data comes across with a boolean
	// indicator on each row, selected or not, which is useless
	// for driving the radio buttons.  This model uses the URL
	// as the unique value.
	$scope.ecdapp.radiobutton = {
		url : null
	};
	for (var rowNum in message.items) {
		let row = message.items[rowNum];
		if (debug)
			$log.debug('selectEcompcController: row is ' + JSON.stringify(row));
		if (row.selected)
			$scope.ecdapp.radiobutton.url = row.url;
	}
	
	/**
	 * Handles a click on radio button to select a controller.
	 */
	$scope.ecdapp.selectController = function(row) {
		if (debug)
			$log.debug('selectController: row is ' + JSON.stringify(row));
		if (row == null || row.url == null)
			$log.error('selectController invoked with bad argument');
		else
			ControllerService.setControllerSelection(row).then(function(data) {
				$rootScope.$broadcast('controllerChange', {name: row.name})
			},
			function(error) {
				$log.error('selectController failed: ' + error);
			});
	};
	
});
