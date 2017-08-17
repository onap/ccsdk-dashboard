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
appDS2.controller('executionsViewController', function($scope, $rootScope, ControllerService, $modal, ExecutionService, $log) {
	
	$scope.parent = { 'blueprint_id': 'Root', 'parent': 'parent' };
	$scope.ecdapp = {};
	$scope.ecdapp.isDataLoading = false;
	$scope.controllersList = [];
	$scope.controllerCall;
	var debug = false;
	
	var getControllers = function(){
		$scope.ecdapp.isDataLoading = true;
		ControllerService.getControllers().then(function(jsonObj) {
			if (debug)
				$log.debug("verticalComponentController.getControllers succeeded: " + JSON.stringify(jsonObj));
			// Empty
			$scope.controllersList.length = 0;
			// Refill
			jsonObj.filter(function(d) {
				$scope.controllersList.push(d);
				if (d.selected){
					$scope.parent.blueprint_id = d.name;
					$scope.controllerCallDone = true;
				}
				return;
			});
			$scope.ecdapp.isDataLoading = false;
		}, function(error) {
			$scope.ecdapp.isDataLoading = false;
			alert('Failed to load controllers. Please retry.');
			$log.error("verticalComponentController.getControllers failed: " + error);
		});
	};
	
	$rootScope.$on('controllerChange', function(e, d){
		$scope.parent.blueprint_id = d.name;
	});
	
	$scope.ecdapp.loadTable = function(status) {
		$scope.ecdapp.isDataLoading = true;
		
		// Empty list and create the root controller item
		$scope.orgChartData = [];
		
		ExecutionService.getExecutionsByStatus(status).then(
			function(jsonObj) {
				
				if (jsonObj.error) {
					$log.error("verticalComponentController.loadTable failed: "
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
					setTimeout(function(){$('.child-item').popover()}, 0);
				}
				$scope.status = status;
				$scope.ecdapp.isDataLoading = false;
			},
			function(error) {
				$log.error("verticalComponentController.loadTable failed: "
						+ error);
				$scope.ecdapp.isRequestFailed = true;
				$scope.ecdapp.errMsg =  error;
				$scope.ecdapp.isDataLoading = false;
			});
	};
	
	$scope.showEcompCInstancesModalPopup = function() {
		var modalInstance = $modal.open({
			templateUrl : 'ecompc_instances_popup.html',
			controller : 'selectEcompcController',
			windowClass: 'modal-docked',
			sizeClass: 'modal-medium',
			resolve : {
				message : function() {
					return { items: $scope.controllersList }
				}
			}
		});
	};

	$scope.showsubDropdown = function(e){
		$('#submenu').toggle();
		e.stopPropagation();
	    e.preventDefault();
	}
	
	$scope.closeSubMenu = function(){
		$('#submenu').css({display:'none'})
	}
	
	// Initialize the page
	getControllers();
	$scope.ecdapp.loadTable('active');
	$scope.showOrgTable = true;
});
