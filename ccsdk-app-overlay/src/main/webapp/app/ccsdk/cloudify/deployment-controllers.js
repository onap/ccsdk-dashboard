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
appDS2.controller('deploymentTableController', function(
		$rootScope, $scope, $log, $modal, DeploymentService) {

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
		DeploymentService.getDeployments($scope.ecdapp.currentPageNum,
				$scope.ecdapp.viewPerPage).then(
				function(jsonObj) {
					if (jsonObj.error) {
						$log.error("deploymentController.loadTable failed: "
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
					$log.error("deploymentController.loadTable failed: "
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
		// console.log('pageChangeHandler: current is ' + $scope.ecdapp.currentPageNum + ' new is ' + page);
		$scope.ecdapp.currentPageNum = page;
		$scope.ecdapp.loadTable();
	}
	
	/**
	 * Shows a modal pop-up to create an execution from a deployment. 
	 * Passes data in via an object named "message". 
	 * On success, updates the table.
	 */
	$scope.ecdapp.createExecutionModalPopup = function(deployment) {
		var modalInstance = $modal.open({
			templateUrl : 'deployment_execute_popup.html',
			controller : 'deploymentExecuteCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-large',
			resolve : {
				message : function() {
					var dataForPopup = {
						deployment : deployment
					};
					return dataForPopup;
				}
			}
		});
		modalInstance.result.then(function(response) {
			if (debug)
				$log.debug('createExecutionModalPopup: response: ' + JSON.stringify(response));
			if (response == null) {
				// $log.debug('user closed dialog');
			}
			else {
				if (response.error != null) {
					$log.error('createExecutionModalPopup: failed to delete: ' + error);
					alert('Failed to create execution:\n' + response.error);
					// No need to update THIS table.
					// Must switch to executions page to see result?  Awkward.
				}
			}
		});
	};
	
	/**
	 * Shows a modal pop-up to confirm deletion. 
	 * On successful completion, updates the table.
	 */
	$scope.ecdapp.deleteDeploymentModalPopup = function(deployment) {		
		var modalInstance = $modal.open({
			templateUrl : 'deployment_delete_popup.html',
			controller : 'deploymentDeleteCtrl',
			sizeClass: 'modal-small',
			resolve : {
				message : function() {
					var dataForPopup = {
						deployment : deployment
					};
					return dataForPopup;
				}
			}
		});
		modalInstance.result.then(function(response) {
			if (debug)
				$log.debug('deleteDeploymentPopup: response: ' + JSON.stringify(response));
			if (response == null) {
				// $log.debug('user closed dialog');
			}
			else {
				if (response.error != null) {
					$log.error('deleteDeploymentModalPopup failed: ' + response.error);
					alert('Failed to delete deployment:\n' + response.error);
				}
				else {
					// success, get the updated list.
					$scope.ecdapp.loadTable()
				}
			}
		});
	};

});

/*************************************************************************/

appDS2.controller('deploymentDeleteCtrl', function(
		$scope, $log, $modalInstance, message, DeploymentService) {

	'use strict';
	
	// Controls logging in this controller
	var debug = false;

	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.label = 'Delete Deployment';
	$scope.ecdapp.deploymentId = message.deployment.id;
	$scope.ecdapp.ignoreLiveNodes = false;

	$scope.ecdapp.deleteDeploymentById = function(){
		DeploymentService.deleteDeployment($scope.ecdapp.deploymentId, $scope.ecdapp.ignoreLiveNodes).then(
			function(response) {
				if (debug)
					$log.debug('deploymentDeleteCtrl.deleteDeployment: ' + JSON.stringify(response));
				if (response && response.error) {
					$log.error('DeploymentService.deleteDeployment failed: ' + response.error);
					alert('Failed to delete deployment:\n' + response.error);
				}
				else {
					// Delete service returns null on success.
					$modalInstance.close("success");
				}
			},
			function(error) {
				$log.error('DeploymentService.deleteDeployment failed: ' + error);
				alert('Service failed to delete deployment:\n' + error);
			});
	}

});

/*************************************************************************/

appDS2.controller('deploymentExecuteCtrl', function(
		$scope, $log, $modalInstance, message, ExecutionService) {

	'use strict';
	
	// Controls logging in this controller
	var debug = false;

	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.label = 'Execute Deployment';
		
	// Cache the input parameter names for validation
	$scope.ecdapp.inputsDict = {};
	if (debug)
		$log.debug('DeploymentXeqCtrl: inputsDict is ' + JSON.stringify($scope.ecdapp.inputsDict));

	// Copy the input names and values
	// (different structure than blueprint)
	/*for (var pkey in message.deployment.inputs) {
		let dval = message.deployment.inputs[pkey];
		inputsAndValues[pkey] = dval;
	}*/

	// Gather workflow names as a simple list
	var workflowList = [];
	for (var i = 0; i < message.deployment.workflows.length; i++)
		workflowList.push(message.deployment.workflows[i].name);
	if (debug)
		$log.debug('DeploymentXeqCtrl: workflowList is ' + JSON.stringify(workflowList));

	// Create an object for edit.
	$scope.ecdapp.editRequest = {
		deployment_id : message.deployment.id,
		allow_custom_parameter : false,
		force : false,
		// Has title and value objects
		workflow_name: { value : '' },
		workflow_list : workflowList,
		fileModel : null,
		parmFileDict : {}
	};
	
	$scope.selectWorkflowName = function(){
		var workflows = message.deployment.workflows;
		let inputsAndValues = {};
		var res = workflows.filter(function(d){ 
			if(d.name == $scope.ecdapp.editRequest.workflow_name.value){ 
				return d 
				} 
			});
		for(var key in res[0].parameters){
			inputsAndValues[key] = typeof (res[0].parameters[key].default) === 'string' ? res[0].parameters[key].default : null;
		}
		$scope.ecdapp.inputsDict = inputsAndValues;
		$scope.ecdapp.editRequest.parmFileDict = inputsAndValues;
	};
		
	if (debug)
		$log.debug('DeploymentXeqCtrl: editRequest is ' + JSON.stringify($scope.ecdapp.editRequest));

	/**
	 * Handler for file-read event reads file, parses YAML, validates content.
	 */
	var fileReader = new FileReader();
	fileReader.onload = function(event) {
		let yamlString = fileReader.result;
		if (debug)
			$log.debug('fileReader.onload: read: ' + yamlString);
		let ydict = {};
		try {
			ydict = YAML.parse(yamlString);
		}
		catch (ex) {
			alert('Failed to parse file as YAML:\n' + ex);
		}
		for (var ykey in ydict) {
			let yval = ydict[ykey];
			if (debug)
				$log.debug('fileReader.onload: typeof ' + ykey + ' is ' + typeof ykey);
			// Only accept valid, expected key-value pairs
			if (typeof yval !== 'string' && typeof yval!=='number')
				alert('Unexpected file content:\nNot a simple key-value pair:\n' + ykey);
			else if (! (ykey in $scope.ecdapp.editRequest.parmFileDict))
				alert('Unexpected file content:\nKey not defined by deployment:\n' + ykey);
			else
				$scope.ecdapp.editRequest.parmFileDict[ykey] = yval;
		}
		if (debug)
			$log.debug('fileReader.onload: parmFileDict: ' + JSON.stringify($scope.ecdapp.editRequest.parmFileDict));

		// Update table in all cases 
		$scope.$apply();
	}

	// Handler for file-select event
	$scope.handleFileSelect = function() {
		if (debug)
			$log.debug('handleFileSelect: $scope.ecdapp.fileModel.name is ' + $scope.ecdapp.editRequest.fileModel.name);
		fileReader.readAsText($scope.ecdapp.editRequest.fileModel);
	};

	/**
	 * Validates content of user-editable fields.
	 * Returns null if all is well, 
	 * a descriptive error message otherwise.
	 */
	var validateRequest = function(editRequest) {
		if (debug)
			$log.debug('validateRequest: editRequest is ' + JSON.stringify(editRequest));
		if (editRequest == null)
			return "No data found.\nPlease enter some values.";
		if (editRequest.deployment_id == null || editRequest.deployment_id.trim() == '')
			return "Deployment ID is required.\nPlease enter a value.";
		if (editRequest.workflow_name.value == null || editRequest.workflow_name.value.trim() == '')
			return "Workflow name is required.\nPlease select a workflow.";
		// Check that every file parameter is defined by blueprint
		for (var pkey in $scope.ecdapp.editRequest.parmFileDict) {
			// Defined in blueprint?
			if (! $scope.ecdapp.inputsDict[pkey]) 
				return 'Unexpected input parameter\n' + pkey;
			// Populated?
			let parmVal = $scope.ecdapp.editRequest.parmFileDict[pkey];
			if (parmVal.trim().length == 0)
				return 'Missing value for parameter ' + pkey;
		}
		// Check that a value is supplied for every expected input
		for (var bkey in $scope.ecdapp.inputsDict) {
			if (! $scope.ecdapp.editRequest.parmFileDict[bkey]) 
				return 'Missing input parameter\n' + bkey;
		}
		return null;
	}
	
	$scope.ecdapp.executeDeployment = function(editRequest) {
		var validateMsg = validateRequest(editRequest);
		if (validateMsg != null) {
			alert('Invalid execution request:\n' + validateMsg);
			return;			
		}

		// Create request with key:value parameters dictionary
		let executeRequest =	{
			deployment_id : editRequest.deployment_id, 
			workflow_name : editRequest.workflow_name.value,
			allow_custom_parameter : editRequest.allow_custom_parameter,
			force : editRequest.force,
			parameters : {}
		};
		for (var pkey in $scope.ecdapp.editRequest.parmFileDict) 
			executeRequest.parameters[pkey] = $scope.ecdapp.editRequest.parmFileDict[pkey];
		if (debug) 
			$log.debug('executeDeployment: executeRequest is ' + JSON.stringify(executeRequest));

		ExecutionService.executeDeployment(executeRequest)
			.then(function(response) {
				if (response.error)
					alert('Failed to create execution:\n' + response.error);
				else	
					$modalInstance.close(response);
			},
			function (error) {
				$log.error('deploymentExecuteCtrl: error while creating execution: ' + error);
				alert('Server rejected execute request:\n' + error);
			}
		);

	};

});

