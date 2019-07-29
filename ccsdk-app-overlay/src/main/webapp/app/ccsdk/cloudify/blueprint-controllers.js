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
appDS2.controller('blueprintTableController', function(
		$rootScope, $scope, $log, $modal, modalService, BlueprintService) {
	
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
	 * to scope variables. The response is either list to be assigned to 
	 * tableData, or an error to be shown.
	 */
	$scope.ecdapp.loadTable = function() {
		$scope.ecdapp.isDataLoading = true;
		BlueprintService.getBlueprints($scope.ecdapp.currentPageNum, $scope.ecdapp.viewPerPage)
			.then(function(jsonObj) {
			if (jsonObj.error) {
				$log.error("blueprintController.loadTable failed: " + jsonObj.error);
				$scope.ecdapp.isRequestFailed = true;
				$scope.ecdapp.errMsg = jsonObj.error;
				$scope.ecdapp.tableData = [];
			} else {
				if (debug)
					$log.debug("bluePrintController.loadTable succeeded, size " + jsonObj.data.length); 
				$scope.ecdapp.isRequestFailed = false;
				$scope.ecdapp.errMsg = null;
				$scope.ecdapp.totalPages = jsonObj.totalPages;
				$scope.ecdapp.tableData = jsonObj.items;
			}			
			$scope.ecdapp.isDataLoading = false;
		}, function(error) {
			$log.error("blueprintController.loadTable failed: " + error);
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
	 * Shows a modal pop-up with blueprint content. 
	 * Passes data in via an object named "message". 
	 */
	$scope.ecdapp.viewBlueprintModalPopup = function(blueprint) {
		$scope.ecdapp.editBlueprint = null;
		var modalInstance = $modal.open({
			templateUrl : 'blueprint_view_popup.html',
			controller : 'blueprintViewCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-medium',
			resolve : {
				message : function() {
					var dataForPopup = {
						blueprint : blueprint
					};
					return dataForPopup;
				}
			}
		});
		modalInstance.result.then(function() {
			// No response.
		});
	};
	
	/**
	 * Shows a modal pop-up to upload a blueprint. 
	 * Passes data in via an object named "message". 
	 * On success, updates the table.
	 */
	$scope.ecdapp.uploadBlueprintModalPopup = function() {
		$scope.ecdapp.editBlueprint = null;
		var modalInstance = $modal.open({
			templateUrl : 'blueprint_upload_popup.html',
			controller : 'blueprintUploadCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-small',
			resolve : {
				message : function() {
					var dataForPopup = {
						blueprint : $scope.ecdapp.editBlueprint,
						blueprintList : $scope.ecdapp.tableData
					};
					return dataForPopup;
				}
			}
		});
		modalInstance.result.then(function(response) {
			if (debug)
				$log.debug('uploadBlueprintModalPopup: response: ' + JSON.stringify(response));
			if (response == null) {
				// $log.debug('user closed dialog');
			}
			else {
				if (response.error != null) {
					$log.error('uploadBlueprintModalPopup failed: ' + response.error);
					alert('Failed to upload blueprint:\n' + response.error);
				}
				else {
					// success, get the updated list.
					$scope.ecdapp.loadTable()
				}
			}
		});
	};
	
    /**
	 * Shows a modal pop-up to create a deployment from a blueprint. 
	 * Passes data in via an object named "message". 
	 */
	$scope.ecdapp.deployBlueprintModalPopup = function(blueprint) {
		var modalInstance = $modal.open({
			templateUrl : 'blueprint_deploy_popup.html',
			controller : 'blueprintDeployCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-medium',
			resolve : {
				message : function() {
					var dataForPopup = {
						blueprint : blueprint
					};
					return dataForPopup;
				}
			}
		});
		modalInstance.result.then(function(response) {
			if (debug)
				$log.debug('deployBlueprintModalPopup: response: ' + JSON.stringify(response));
			if (response == null) {
				if (debug)
					$log.debug('user closed dialog');
			}
			else {
				if (response.error != null) {
					$log.error('deployBlueprintModalPopup failed: ' + response.error);
					alert('Failed to deploy blueprint:\n' + response.error);
					// No need to update THIS table.
					// Must switch to deployments page to see result?  Awkward.
				}
			}
		});
	};

	/**
	 * Shows a modal pop-up to confirm deletion. 
	 * On successful completion, updates the table.
	 */
	$scope.ecdapp.deleteBlueprintModalPopup = function(blueprint) {
		modalService.popupConfirmWin("Confirm", "Delete blueprint with ID '"
				+ blueprint.id + "'?", function() {
			BlueprintService.deleteBlueprint(blueprint.id).then(
					function(response) {
						if (debug)
							$log.debug('deleteBlueprintModalPopup: response: ' + JSON.stringify(response));
						if (response && response.error) {
							// $log.error('deleteBlueprint failed: ' + response.error);
							alert('Failed to delete blueprint:\n' + response.error);
						}
						else {
							// No response body on success.
							$scope.ecdapp.loadTable();
						}
					},
					function(error) {
						$log.error('BlueprintService.deleteBlueprint failed: ' + error);
						alert('Service failed to delete blueprint:\n' + error);
					});
		});
	};

	// Populate the table on load.  Note that the b2b selector code
	// sets the page-number value, and the change event calls load table.
	// Do not call this here to avoid double load: 
	// $scope.ecdapp.loadTable();

});

/*************************************************************************/

appDS2.controller('blueprintUploadCtrl', function(
		$scope, $log, $modalInstance, message, BlueprintService) {

	'use strict';
	
	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.label = 'Upload Blueprint';
	$scope.ecdapp.uploadRequest = 
		{
			blueprint_id : '',
			blueprint_filename : '',
			zip_url : ''
		};

	/**
	 * Validates content of user-editable fields.
	 * Uses the list in message.feedList 
	 * Returns null if all is well, 
	 * a descriptive error message otherwise.
	 */
	$scope.ecdapp.validateRequest = function(uploadRequest) {
		if (uploadRequest == null)
			return "No data found.\nPlease enter some values.";
		if (uploadRequest.blueprint_id == null || uploadRequest.blueprint_id.trim() == '')
			return "ID is required.\nPlease enter a value.";
		if (uploadRequest.blueprint_filename == null || uploadRequest.blueprint_filename.trim() == '')
			return "File name is required.\nPlease enter a value.";
		if (uploadRequest.blueprint_filename.toLowerCase().substr(-4) != 'yaml')
			return "File name must end with YAML.\nPlease use that suffix.";
		if (uploadRequest.zip_url == null || uploadRequest.zip_url.trim() == '')
			return "Zip file URL is required.\nPlease enter a value.";
		return null;
	}
	
	$scope.ecdapp.uploadBlueprint = function(uploadRequest) {
		var validateMsg = $scope.ecdapp.validateRequest(uploadRequest);
		if (validateMsg != null) {
			alert('Invalid upload request:\n' + validateMsg);
			return;			
		}
		BlueprintService.uploadBlueprint(uploadRequest)
			.then(function(response) {
				// $log.debug('blueprintPopupCtrl: response: ' + response);
				if (response.error)
					alert('Failed to upload blueprint:\n' + response.error);
				else	
					$modalInstance.close(response);
			},
			function (error) {
				$log.error('blueprintUploadCtrl: error while uploading: ' + error);
				alert('Server rejected upload request:\n' + error);
			}
		);

	};

});

/*************************************************************************/

appDS2.controller('blueprintViewCtrl', function(
		$scope, $log, message, BlueprintService) {

	'use strict';
	
	var debug = false;
	
	if (debug)
		$log.debug("blueprintViewCtrl.message: " + JSON.stringify(message));

	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.blueprintId = message.blueprint.id;
	
	$scope.ecdapp.label = 'View Blueprint ' + message.blueprint.id;

	// Fetch the blueprint
	$scope.ecdapp.isDataLoading = true;
	BlueprintService.viewBlueprint(message.blueprint.id).then(function(jsonObj) {
		if (debug)
			$log.debug("blueprintViewCtrl.viewBlueprint response: " + JSON.stringify(jsonObj));
		if (jsonObj.error) {
			$scope.ecdapp.errMsg = 'Request Failed';
		}
		else {
			$scope.ecdapp.blueprint = jsonObj.content;
		}
		$scope.ecdapp.isDataLoading = false;
	}, function(error) {
		$scope.ecdapp.isDataLoading = false;
		alert('Failed to get blueprint. Please retry.');
		$log.error("blueprintViewCtrl failed: " + error);
	});
	
});


/*************************************************************************/

appDS2.controller('blueprintDeployCtrl', function(
		$scope, $log, $modalInstance, message, DeploymentService) {

	'use strict';
	
	// Controls logging in this controller
	var debug = false;
	
	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.label = 'Deploy Blueprint';

	// Cache the input parameter names for validation
	if (debug)
		$log.debug('blueprintDeployCtrl: inputs: ' + JSON.stringify(message.blueprint.plan.inputs));
	$scope.ecdapp.inputsDict = message.blueprint.plan.inputs;

	// Copy the input parameter names and default values
	let inputsAndDefaults = {};
	for (var pkey in message.blueprint.plan.inputs) {
		if (debug)
			$log.debug('blueprintDeployCtrl: checking key ' + pkey);
		let dval = message.blueprint.plan.inputs[pkey].default;
		if (! dval)
			dval = '';
		inputsAndDefaults[pkey] = dval;
	}
	if (debug)
		$log.debug('blueprintDeployCtrl: inputsAndDefaults: ' + JSON.stringify(inputsAndDefaults));
	
	// Create an object for edit
	$scope.ecdapp.editRequest = {
		deployment_id : '',
		blueprint_id : message.blueprint.id,
		fileModel : null,
		parmFileDict : inputsAndDefaults
	};

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
		// Process the file
		for (var ykey in ydict) {
			let yval = ydict[ykey];
			if (debug)
				$log.debug('fileReader.onload: typeof ' + ykey + ' is ' + typeof ykey);
			// Allow only expected keys with scalar values
			if (! (ykey in $scope.ecdapp.editRequest.parmFileDict))
				alert('Unexpected file content:\nKey not defined by blueprint:\n' + ykey);
			else if (typeof yval !== 'string' && typeof yval !== 'number')
				alert('Unexpected file content:\nNot a simple key-value pair:\n' + ykey);
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
	$scope.ecdapp.validateRequest = function(editRequest) {
		if (editRequest == null)
			return 'No data found.\nPlease enter some values.';
		if (editRequest.deployment_id == null || editRequest.deployment_id.trim() == '')
			return 'Deployment ID is required.\nPlease enter a value.';
		if (editRequest.blueprint_id == null || editRequest.blueprint_id.trim() == '')
			return 'Blueprint ID is required.\nPlease enter a value.';
		// Check that every file parameter is defined by blueprint
		for (var pkey in $scope.ecdapp.editRequest.parmFileDict) {
			// Defined in blueprint?
			if (! $scope.ecdapp.inputsDict[pkey]) 
				return 'Unexpected input parameter\n' + pkey;
			// Populated?
			let parmVal = $scope.ecdapp.editRequest.parmFileDict[pkey];
			if (parmVal == null || (typeof (parmVal) === 'string' && parmVal.trim().length == 0))
				return 'Missing value for parameter\n' + pkey;
		}
		// Check that a value is supplied for every expected input
		for (var bkey in $scope.ecdapp.inputsDict) {
			if (! $scope.ecdapp.editRequest.parmFileDict[bkey]) 
				return 'Missing input parameter\n' + bkey;
		}
		return null;
	}
	
	$scope.ecdapp.deployBlueprint = function(editRequest) {
		if (debug)
			$log.debug('deployBlueprint: editRequest is ' + JSON.stringify($scope.ecdapp.editRequest));
		var validateMsg = $scope.ecdapp.validateRequest(editRequest);
		if (validateMsg != null) {
			alert('Invalid Request:\n' + validateMsg);
			return;			
		}
		// Create request with key:value parameters dictionary
		let deployRequest =	{
			deployment_id : editRequest.deployment_id, 
			blueprint_id : editRequest.blueprint_id,
			parameters : {}
		};
		for (var pkey in $scope.ecdapp.editRequest.parmFileDict) 
			deployRequest.parameters[pkey] = $scope.ecdapp.editRequest.parmFileDict[pkey];
		if (debug) 
			$log.debug('deployBlueprint: deployRequest is ' + JSON.stringify(deployRequest));
		
		DeploymentService.deployBlueprint(deployRequest)
			.then(function(response) {
				if (response.error)
					alert('Failed to deploy blueprint:\n' + response.error);
				else	
					$modalInstance.close(response);
			},
			function (error) {
				$log.error('blueprintDeployCtrl: error while deploying: ' + error);
				alert('Server rejected deployment request:\n' + error);
			}
		);

	};

});
