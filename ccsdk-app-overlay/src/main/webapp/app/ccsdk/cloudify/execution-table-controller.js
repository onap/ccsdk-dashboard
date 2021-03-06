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
appDS2.controller('executionTableController', function(
		$rootScope, $scope, $log, $modal, modalService, ExecutionService) {

	'use strict';

	// Controls logging in this controller
	var debug = false;

	// this object holds all app data and functions
	$scope.ecdapp = {};
	// models for controls on screen
	$scope.ecdapp.tableData = [];
	$scope.ecdapp.currentPageNum = 1;
	$scope.ecdapp.viewPerPage = 10;
	// other
	$scope.ecdapp.errMsg = null;
	$scope.ecdapp.isDataLoading = true;
	$scope.ecdapp.isRequestFailed = false;

	/**
	 * Loads the table. Interprets the remote controller's response and copies
	 * to scope variables. The response is either a list to be assigned to
	 * tableData, or an error to be shown.
	 */
	$scope.ecdapp.loadTable = function() {
		$scope.ecdapp.isDataLoading = true;
		ExecutionService.getExecutions($scope.ecdapp.currentPageNum,
				$scope.ecdapp.viewPerPage).then(
				function(jsonObj) {
					if (jsonObj.error) {
						$log.error("executionController.loadTable failed: "
								+ jsonObj.error);
						$scope.ecdapp.isRequestFailed = true;
						$scope.ecdapp.errMsg = jsonObj.error;
						$scope.ecdapp.tableData = [];
					} else {
						$scope.ecdapp.isRequestFailed = false;
						$scope.ecdapp.errMsg = null;
						$scope.ecdapp.totalPages = jsonObj.totalPages;
						$scope.ecdapp.tableData = jsonObj.items;
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error("executionController.loadTable failed: "
							+ error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = error;
					$scope.ecdapp.tableData = [];
					$scope.ecdapp.isDataLoading = false;
				});
	};

	/**
	 * Invoked at first page load AND when
	 * user clicks on the B2B pagination control. 
	 */
	$scope.pageChangeHandler = function(page) {
		if (debug)
			console.log('pageChangeHandler: current is ' + $scope.ecdapp.currentPageNum + ' new is ' + page);
		$scope.ecdapp.currentPageNum = page;
		$scope.ecdapp.loadTable();
	}
	
	/**
	 * Shows a modal pop-up with the error. 
	 */
	$scope.ecdapp.viewErrorModalPopup = function(row) {
		modalService.showFailure('Error Details', row.error, function() { } );
	};
	
	/**
	 * Shows a modal pop-up to confirm deletion. 
	 * On successful completion, updates the table.
	 */
	$scope.ecdapp.cancelExecutionModalPopup = function(execution) {
		modalService.popupConfirmWin("Confirm", "Cancel execution with ID '"
				+ execution.id + "'?", function() {
			// TODO: gather action from user
			ExecutionService.cancelExecution(execution.id, execution.deploymentId, "cancel").then(
					function(response) {
						if (debug)
							$log.debug("xeqTblController.cancelExecutionModalPopup: " + JSON.stringify(response));
						if (response && response.error) {
							// $log.error('cancelExectuion failed: ' + response.error);
							alert('Failed to cancel execution:\n' + response.error);
						}
						else {
							// No response body on success.
							$scope.ecdapp.loadTable();
						}
					},
					function(error) {
						$log.error('ExecutionService.cancelExecution failed: ' + error);
						alert('Service failed to cancel execution:\n' + error);
					});
		})
	};

	// Populate the table on load. Note that the b2b selector code
	// sets the page-number value, and the change event calls load table.
	// Do not call this here to avoid double load:
	// $scope.ecdapp.loadTable();

});
