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
appDS2.controller('treeViewController',function(
		$scope, $rootScope, $log, $modal, ExecutionService, ControllerService){
	
	'use strict';

	// Set to true for verbose output
	var debug = false;

	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.isDataLoading = false;
	$scope.ecdapp.isRequestFailed = false;
	$scope.orgChart = true;
	$scope.status = '';
	var selectedItem = {
		name: ''
	};
	// Initial data as eye candy
	$scope.orgChartData = [  { 'blueprint_id': 'Root', 'parent': 'parent' } ]; 
	var controllersList = [];
	
	var getControllers = function(){
		ControllerService.getControllers().then(function(jsonObj) {
			if (debug)
				$log.debug("treeViewController.getControllers succeeded: " + JSON.stringify(jsonObj));
			// Empty
			controllersList.length = 0;
			// Refill
			jsonObj.filter(function(d) {
				controllersList.push(d);
				if (d.selected)
					selectedItem = d; 
				return;
			});
			$scope.ecdapp.loadTable();
		}, function(error) {
			alert('Failed to load controllers. Please retry.');
			$log.error("treeViewController.getControllers failed: " + error);
		});
	}

	/**
	 * Called from the directive when user picks a status value.
	 */
	$scope.ecdapp.loadTable = function(status) {
		$scope.ecdapp.isDataLoading = true;
		$scope.status = status;
		// Empty list and create the root controller item
		$scope.orgChartData.length = 0;
		$scope.orgChartData.push({
			"blueprint_id": selectedItem.name,
			"parent": "parent"
		});
		ExecutionService.getExecutionsByStatus(status).then(
				function(jsonObj) {
					if (jsonObj.error) {
						$log.error("treeViewController.loadTable failed: "
								+ jsonObj.error);
						$scope.ecdapp.isRequestFailed = true;
						$scope.ecdapp.errMsg = jsonObj.error;
					} else {
						$scope.ecdapp.isRequestFailed = false;
						$scope.ecdapp.errMsg = null;
						for (var i=0; i < jsonObj.items.length; i++) {
							$scope.orgChartData.push(jsonObj.items[i]);				
						}
						$scope.$broadcast('listenEvent', {data: $scope.orgChartData} );
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error("treeViewController.loadTable failed: "
							+ error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = error;
					$scope.ecdapp.isDataLoading = false;
				});
	};

	// Listens for change of the selected controller
	$rootScope.$on('controllerChange', function(e, d){
		$scope.orgChartData[0].blueprint_id = d.name;
		$scope.$broadcast('listenEvent', {data: $scope.orgChartData});
	})
	
	// Shows popup with list of controllers
	$scope.ecdapp.showEcompCInstancesModalPopup = function() {
		var modalInstance = $modal.open({
			templateUrl : 'ecompc_instances_popup.html',
			controller : 'selectEcompcController',
			windowClass: 'modal-docked',
			sizeClass: 'modal-medium',
			resolve : {
				message : function() {
					return { items: controllersList }
				}
			}
		});
		modalInstance.result.then(function(response) { 
			// Always reload the table
			// $log.debug('modalInstance: reloading controllers');
			getControllers('active');
		}, 
		function(error) {
			// Should never happen
			$log.error('modalInstance: ERROR!?');
		});
	};
	
	// Populate the table on load.
	getControllers('active');

});
