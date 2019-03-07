appDS2.controller('inventoryBlueprintTableController', function(
		$rootScope, $scope, $log, $modal, modalService, InventoryBlueprintService, InventoryDeploymentService) {
	
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
	// sorting
	$scope.ecdapp.sortBy = null;
	// searching
	$scope.ecdapp.searchBy = null;
	
	$scope.ecdapp.trackBpRowIndex = function(indx) {
		$scope.ecdapp.tableData[indx].expanded = !$scope.ecdapp.tableData[indx].expanded;
	};
	
	$scope.ecdapp.updateTable = function() {
		$scope.ecdapp.isSrvcDataLoading = true;
		var srvcTypIds = [];
		var srvcIds = [];
		var bpDepls =[];
		var cloneGrid = $scope.ecdapp.tableData;	
		angular.forEach($scope.ecdapp.tableData, function(item, index) {
			  angular.forEach(item, function(value, key) {
				  if (key === "typeId") {
					  srvcTypIds.push(value); 
				  }	  
			  });
	  });
		
		InventoryBlueprintService.getDeploymentForBp(srvcTypIds)
		.then(function(jsonObj) {
		if (jsonObj.error) {
			$log.error("inventoryBlueprintController.updateTable failed: " + jsonObj.error);
		} else {
			bpDepls = jsonObj;
			for (var typIndx = 0; typIndx < bpDepls.length; typIndx++) {
				srvcIds.push(bpDepls[typIndx].serviceRefList);
			}
			angular.forEach(cloneGrid, function(item, index) {
				  item.deployments = srvcIds[index];
				  item.expanded = false;
		});
		$scope.ecdapp.tableData = cloneGrid;
		}
		$scope.ecdapp.isSrvcDataLoading = false;
	}, function(error) {
		$log.error("inventoryBlueprintController.updateTable failed: " + error);
		bpDepls = [];
		$scope.ecdapp.isSrvcDataLoading = false;
	});

	}
	
	$scope.ecdapp.JSONToCSVConverter = function(blueprint) 
	{
      var array = typeof blueprint != 'object' ? JSON.parse(blueprint) : blueprint;
      var str = '';
      for (var i = 0; i < array.length; i++) {
          var line = '';

          for (var index in array[i]) {
            line += array[i][index] + ',';
          }

          line.slice(0, line.Length - 1);

          str += line + '\r\n';
      } 

		var uri = 'data:text/csv;charset=utf-8,' + escape(str);
		var fileName = 'exported';
		
		var link = document.createElement("a");
		link.href= uri;
		
		link.style = "visibility:hidden";
		link.download = fileName + ".csv";
		
		document.body.appendChild(link);
		link.click();
		document.body.removeChild(link);
	};
	
	$scope.ecdapp.createcsv = function(blueprint)
	{
		var bpArr =[];
		bpArr.push(blueprint);
		$scope.ecdapp.JSONToCSVConverter(bpArr);
	};
	
	$scope.ecdapp.exportJson = function(blueprint)
	{
		var bpExportObj = {};
		bpExportObj.application = blueprint.application;
		bpExportObj.component = blueprint.component;
		bpExportObj.typeName = blueprint.typeName;
		bpExportObj.typeVersion = blueprint.typeVersion;
		bpExportObj.blueprintTemplate = blueprint.blueprintTemplate;

		var bpStr = JSON.stringify(bpExportObj);
		var uri = 'data:text/json;charset=utf-8,' + escape(bpStr);
		var fileName = blueprint.application + '_' + blueprint.component + 
			'_' + blueprint.typeName + '_' + blueprint.typeVersion;
		
		var link = document.createElement("a");
		link.href= uri;
		
		link.style = "visibility:hidden";
		link.download = fileName + ".json";
		
		document.body.appendChild(link);
		link.click();
		document.body.removeChild(link);
	};
	/**
	 * Loads the table. Interprets the remote controller's response and copies 
	 * to scope variables. The response is either list to be assigned to 
	 * tableData, or an error to be shown.
	 */
	$scope.ecdapp.loadTable = function(sortBy, searchBy) {
		$scope.ecdapp.isDataLoading = true;
		$scope.ecdapp.sortBy = sortBy;
		$scope.ecdapp.searchBy = searchBy;
		InventoryBlueprintService.getBlueprints($scope.ecdapp.currentPageNum, $scope.ecdapp.viewPerPage, sortBy, searchBy)
			.then(function(jsonObj) {
			if (jsonObj.error) {
				$log.error("inventoryBlueprintController.loadTable failed: " + jsonObj.error);
				$scope.ecdapp.isRequestFailed = true;
				$scope.ecdapp.errMsg = jsonObj.error;
				$scope.ecdapp.tableData = [];
			} else {
				//if (debug)
					//$log.debug("inventoryBlueprintController.loadTable succeeded, size " + jsonObj.data.length); 
				$scope.ecdapp.isRequestFailed = false;
				$scope.ecdapp.errMsg = null;
				$scope.ecdapp.totalPages = jsonObj.totalPages;
				$scope.ecdapp.tableData = jsonObj.items;
				$scope.ecdapp.updateTable();
			}			
			$scope.ecdapp.isDataLoading = false;
		}, function(error) {
			$log.error("inventoryBlueprintController.loadTable failed: " + error);
			$scope.ecdapp.isRequestFailed = true;
			$scope.ecdapp.errMsg = error;
			$scope.ecdapp.tableData = [];
			$scope.ecdapp.isDataLoading = false;
		});
	};
	
	/**
	 * Loads the table. Interprets the remote controller's response and copies 
	 * to scope variables. The response is either list to be assigned to 
	 * tableData, or an error to be shown.
	 */
	$scope.ecdapp.sortTable = function(sortBy) {
		$scope.ecdapp.isDataLoading = true;
		$scope.ecdapp.sortBy = sortBy;
		InventoryBlueprintService.getBlueprints($scope.ecdapp.currentPageNum, $scope.ecdapp.viewPerPage, sortBy, $scope.ecdapp.searchBy)
			.then(function(jsonObj) {
			if (jsonObj.error) {
				$log.error("inventoryBlueprintController.loadTable failed: " + jsonObj.error);
				$scope.ecdapp.isRequestFailed = true;
				$scope.ecdapp.errMsg = jsonObj.error;
				$scope.ecdapp.tableData = [];
			} else {
				if (debug)
					$log.debug("inventoryBlueprintController.loadTable succeeded, size " + jsonObj.data.length); 
				$scope.ecdapp.isRequestFailed = false;
				$scope.ecdapp.errMsg = null;
				$scope.ecdapp.totalPages = jsonObj.totalPages;
				$scope.ecdapp.tableData = jsonObj.items;
				$scope.ecdapp.updateTable();
			}			
			$scope.ecdapp.isDataLoading = false;
		}, function(error) {
			$log.error("inventoryBlueprintController.loadTable failed: " + error);
			$scope.ecdapp.isRequestFailed = true;
			$scope.ecdapp.errMsg = error;
			$scope.ecdapp.tableData = [];
			$scope.ecdapp.isDataLoading = false;
		});
	};
	
	/**
	 * Loads the table. Interprets the remote controller's response and copies 
	 * to scope variables. The response is either list to be assigned to 
	 * tableData, or an error to be shown.
	 */
	$scope.ecdapp.searchTable = function(searchBy) {
		$scope.ecdapp.isDataLoading = true;
		$scope.ecdapp.searchBy = searchBy;
		InventoryBlueprintService.getBlueprints($scope.ecdapp.currentPageNum, $scope.ecdapp.viewPerPage, $scope.ecdapp.sortBy, searchBy)
			.then(function(jsonObj) {
			if (jsonObj.error) {
				$log.error("inventoryBlueprintController.loadTable failed: " + jsonObj.error);
				$scope.ecdapp.isRequestFailed = true;
				$scope.ecdapp.errMsg = jsonObj.error;
				$scope.ecdapp.tableData = [];
			} else {
				if (debug)
					$log.debug("inventoryBlueprintController.loadTable succeeded, size " + jsonObj.data.length); 
				$scope.ecdapp.isRequestFailed = false;
				$scope.ecdapp.errMsg = null;
				$scope.ecdapp.totalPages = jsonObj.totalPages;
				$scope.ecdapp.tableData = jsonObj.items;
				$scope.ecdapp.updateTable();
			}			
			$scope.ecdapp.isDataLoading = false;
		}, function(error) {
			$log.error("inventoryBlueprintController.loadTable failed: " + error);
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
		$scope.ecdapp.loadTable($scope.ecdapp.sortBy, $scope.ecdapp.searchBy);
	}
		
	/**
	 * Shows a modal pop-up to update a blueprint.
	 * Passes data in via an object named "message". 
	 * On success, updates the table.
	 */
	$scope.ecdapp.updateBlueprintModalPopup = function(blueprint) {
		$scope.ecdapp.editBlueprint = null;
		var modalInstance = $modal.open({
			templateUrl : 'inventory_blueprint_update_popup.html',
			controller : 'inventoryBlueprintUpdateCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-jumbo',
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
				$log.debug('updateBlueprintModalPopup: response: ' + JSON.stringify(response));
			if (response == null) {
				if (debug)
					$log.debug('user closed dialog');
			}
			else {
				if (response.error != null) {
					$log.error('updateBlueprintModalPopup failed: ' + response.error);
					alert('Failed to update blueprint:\n' + response.error);
				}
				else {
					// success, get the updated list.
					$scope.ecdapp.loadTable()
				}
			}
		});
	};
	
	/**
	 * Shows a modal pop-up with blueprint content.
	 * Passes data in via an object named "message". 
	 */
	$scope.ecdapp.viewBlueprintModalPopup = function(blueprint) {
		$scope.ecdapp.editBlueprint = null;
		var modalInstance = $modal.open({
			templateUrl : 'inventory_blueprint_view_popup.html',
			controller : 'inventoryBlueprintViewCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-jumbo',
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
			templateUrl : 'inventory_blueprint_upload_popup.html',
			controller : 'inventoryBlueprintUploadCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-jumbo',
			resolve : {
				message : function() {
					var dataForPopup = {
						blueprint : $scope.ecdapp.editBlueprint,
						blueprintList : $scope.ecdapp.tableData,
					};
					return dataForPopup;
				}
			}
		});
		modalInstance.result.then(function(response) {
			if (debug)
				$log.debug('uploadBlueprintModalPopup: response: ' + JSON.stringify(response));
			if (response == null) {
				if (debug)
					$log.debug('user closed dialog');
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
			templateUrl : 'inventory_blueprint_deploy_popup.html',
			controller : 'inventoryBlueprintDeployCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-jumbo',
			resolve : {
				message : function() {
					var dataForPopup = {
						blueprint : blueprint,
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
		modalService.popupConfirmWin("Confirm", "Delete blueprint with name '"
				+ blueprint.typeName + "'?", function() {
			InventoryBlueprintService.deleteBlueprint(blueprint.typeId).then(
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
						$log.error('InventoryBlueprintService.deleteBlueprint failed: ' + error);
						alert('Service failed to delete blueprint:\n' + error);
					});
		})
	};

});
/*************************************************************************/

appDS2.controller('inventoryBlueprintUpdateCtrl', function(
		$scope, $log, $modalInstance, message, InventoryBlueprintService) {

	'use strict';
	
	// Controls logging in this controller
	var debug = false;

	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.label = 'Update Blueprint';
	$scope.ecdapp.updateInProgress = false;
	// Create a ServiceTypeRequest object for edit
	$scope.ecdapp.serviceType = angular.copy(message.blueprint);

	/**
	 * Validates content of user-editable fields.
	 * Returns null if all is well, 
	 * a descriptive error message otherwise.
	 */
	$scope.ecdapp.validateRequest = function(serviceType) {
		if (serviceType == null)
			return 'No data found.\nPlease enter some values.';
		if (serviceType.blueprintTemplate == null || serviceType.blueprintTemplate.trim() == '')
			return 'Blueprint Template is required.\nPlease enter a value.';
		let blueprintTemplate = {};
		try {
			blueprintTemplate = YAML.parse(serviceType.blueprintTemplate);
		}
		catch (ex) {
			return ('Blueprint template is not in YAML format:\n' + ex);
		}
		return null;
	}
	
	$scope.ecdapp.updateBlueprint = function(serviceType) {
		$scope.ecdapp.updateInProgress = true;
		if (debug)
			$log.debug('updateBlueprint: serviceType is ' + JSON.stringify($scope.ecdapp.serviceType));
		var validateMsg = $scope.ecdapp.validateRequest(serviceType);
		if (validateMsg != null) {
			alert('Invalid Request:\n' + validateMsg);
			return;			
		}

		InventoryBlueprintService.updateBlueprint(serviceType)
			.then(function(response) {
				if (debug)
					$log.debug('inventoryBlueprintUpdateCtrl.updateBlueprint: ' + JSON.stringify(response));
				if (response && response.error) {
					$log.error('InventoryBlueprintService.updateBlueprint failed: ' + response.error);
					alert('Failed to update blueprint:\n' + response.error);
				}
				else {
					// Delete service returns null on success.
					$modalInstance.close("success");
				}
				$scope.ecdapp.updateInProgress = false;
			},
			function(error) {
				$log.error('InventoryBlueprintService.updateBlueprint failed: ' + error);
				$scope.ecdapp.updateInProgress = false;
				alert('Service failed to update blueprint:\n' + error);
			});

	};

});


appDS2.controller('inventoryBlueprintUploadCtrl', function(
		$scope, $log, $modalInstance, message, InventoryBlueprintService) {

	'use strict';
	
	// Controls logging in this controller
	var debug = false;

	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.label = 'Upload Blueprint';
	$scope.ecdapp.uploadInProgress = false;
	$scope.ecdapp.serviceTypeRequest = 
		{
			asdcResourceId : '',
			asdcServiceId : '',
			asdcServiceURL : '',
			blueprintTemplate : '',
			owner : $scope.userId,
			serviceIds : '',
			serviceLocations : '',
			typeName : '',
			typeVersion : '',
			vnfTypes : '',
			application: '',
			component: ''
		};
	$scope.ecdapp.writeRole = false;
	$scope.ecdapp.newCompName = "";
	$scope.ecdapp.newCompDisplayName = "";
	$scope.ecdapp.newCompId = null;
	$scope.ecdapp.enableAppForm = false;
	$scope.ecdapp.apps = [];
	$scope.ecdapp.comps = [];
	$scope.ecdapp.validAppl = false;
	$scope.ecdapp.validComp = false;
	$scope.ecdapp.isImport = false;
	
	/**
	 * Handler for file-read event reads file, parses JSON, validates content.
	 */
	var importFileReader = new FileReader();
	importFileReader.onload = function(event) {
		let jsonString = importFileReader.result;
		if (debug)
			$log.debug('fileReader.onload: read: ' + jsonString);
		let ydict = {};
		try {
			ydict = JSON.parse(jsonString);
		}
		catch (ex) {
			alert('Failed to parse file as JSON:\n' + ex);
		}
		// Process the file
		for (var ykey in ydict) {
			let yval = ydict[ykey];
			if (debug)
				$log.debug('importFileReader.onload: typeof ' + ykey + ' is ' + typeof ykey);
			
			if (ykey === "application") {
				$scope.ecdapp.serviceTypeRequest.application = yval;
				$scope.ecdapp.validAppl = true;
			} else if (ykey === "component") {
				$scope.ecdapp.serviceTypeRequest.component = yval;
				$scope.ecdapp.validComp = true;
			} else if (ykey === "typeName") {
				$scope.ecdapp.serviceTypeRequest.typeName = yval;
			} else if (ykey === "typeVersion") {
				$scope.ecdapp.serviceTypeRequest.typeVersion = yval;
			} else if (ykey === "blueprintTemplate") {
				$scope.ecdapp.serviceTypeRequest.blueprintTemplate = yval;
			}
		}
	}
	
	$scope.ecdapp.handleImportCb = function($event) {
		  var checkbox = $event.target;
		  var action = (checkbox.checked ? 'import' : 'regular');
		  if (action === 'import') {
			  $scope.ecdapp.serviceTypeRequest.application = '';
			  $scope.ecdapp.serviceTypeRequest.component = '';
			  $scope.ecdapp.isImport = true;
		  }	  
		  if (action === 'regular') {
			  $scope.ecdapp.serviceTypeRequest.application = 'Select Application';
			  $scope.ecdapp.serviceTypeRequest.component = 'Select Component';
			  $scope.ecdapp.isImport = false;
		  }
	}
	// Handler for file-select event
	$scope.ecdapp.handleImportFile = function() {
		if (debug)
			$log.debug('handleFileSelect: $scope.ecdapp.fileModel.name is ' + $scope.ecdapp.serviceTypeRequest.fileModel.name);
		importFileReader.readAsText($scope.ecdapp.serviceTypeRequest.fileModel);
	};
	
	/**
	 * Get the components from database
	 * 
	 */
	InventoryBlueprintService.getComponents()
		.then(function(jsonObj) {
		if (jsonObj.error) {
			$log.error("inventoryBlueprintController.loadComponents failed: " + jsonObj.error);
			$scope.ecdapp.components = [];
		} else {
			if (debug)
				$log.debug("inventoryBlueprintController.loadComponents succeeded, size " + jsonObj.data.length);
			$scope.ecdapp.isRequestFailed = false;
			$scope.ecdapp.errMsg = null;
			$scope.ecdapp.components = jsonObj;
			if (Array.isArray($scope.ecdapp.components) ) {
				  angular.forEach($scope.ecdapp.components, function(item, index) {
					  angular.forEach(item, function(value, key) {
						  if (key === "app") {
							  $scope.ecdapp.apps.push(value); 
						  }	  
					  });
			  });
			}
		}			
		$scope.ecdapp.isDataLoading = false;
	}, function(error) {
		$log.error("inventoryBlueprintController.loadComponents failed: " + error);
		$scope.ecdapp.components = [];
	});

	//$scope.ecdapp.components = [{"ECOMPC":[{"compId":1,"cname":"controller","dname":"CONTROLLER"},{"compId":2,"cname":"mso","dname":"MSO"},{"compId":3,"cname":"appc","dname":"APP-C"},{"compId":4,"cname":"clamp","dname":"CLAMP"},{"compId":5,"cname":"ecompscheduler","dname":"ECOMP SCHEDULER"},{"compId":6,"cname":"policy","dname":"POLICY"},{"compId":7,"cname":"eipam","dname":"EIPAM"},{"compId":181,"cname":"pdasms","dname":"PDAS-MS"},{"cname":"true"}]},
	//	 {"DCAE": [{"compId":8,"cname":"dcae","dname":"DCAE"}]}];
	$scope.ecdapp.selectAppComp = function(appName) {
        if(appName === "Select Application"){
            $scope.ecdapp.validAppl = false;
        } else {
        	$scope.ecdapp.validAppl = true;
			for (var appIndx = 0; appIndx < $scope.ecdapp.components.length; appIndx++) {
				if ($scope.ecdapp.components[appIndx].app === appName) {
					$scope.ecdapp.comps = $scope.ecdapp.components[appIndx].comps;
					break;
				}
			}
        }
	}
	$scope.ecdapp.validateComp = function(appName) {
        if($scope.ecdapp.serviceTypeRequest.component === "Select Component"){
            $scope.ecdapp.validComp = false;
        } else {
        	$scope.ecdapp.validComp = true;
        }
	}
	var fileReader = new FileReader();
	fileReader.onload = function(event) {
		let yamlString = fileReader.result;
		$scope.ecdapp.serviceTypeRequest.blueprintTemplate = yamlString;
	}
	$scope.ecdapp.enableAddApp = function() {
		if ($scope.ecdapp.enableAppForm) {
			$scope.ecdapp.enableAppForm = false;
		} else {
			$scope.ecdapp.enableAppForm = true;
		}
	}
	$scope.ecdapp.addApplication = function(appName, compId, compName, compDisplayName) {
		// save new application to database, reload components list
        if (compName.trim().length < 1 || compDisplayName.trim().length < 1) {
            alert("Must enter value for Component name and display name");
            return;
        }
		//console.log("Saving " + applicationName + " to components list");
		InventoryBlueprintService.insertComponent(compName, compDisplayName.toUpperCase()).then(function(jsonObj) {
            if (jsonObj.error) {
                $log.error("inventoryBlueprintController.loadComponents failed: " + jsonObj.error);
            } else {
                if (debug)
                    $log.debug("inventoryBlueprintController.loadComponents succeeded, size " + jsonObj.data.length);
                //console.log(jsonObj);
                InventoryBlueprintService.getComponents().then(function(jsonObj) {
                	/*
                    $scope.ecdapp.newApplicationName = "";
                    $scope.ecdapp.newApplicationDisplayName = "";
                    $scope.ecdapp.selectedOption = $scope.ecdapp.components[0];
                    */
                    $scope.ecdapp.components = jsonObj;
                    $scope.ecdapp.enableAppForm = false;
                    $scope.ecdapp.selectAppComp(appName);
				});
            }
		}, function(error) {
            $log.error("inventoryBlueprintController.addApplication failed: " + error);
        });
	};

	// Handler for file-select event
	$scope.handleFileSelect = function() {
		if (debug)
			$log.debug('handleFileSelect: $scope.ecdapp.fileModel.name is ' + $scope.ecdapp.serviceTypeRequest.fileModel.name);
		fileReader.readAsText($scope.ecdapp.serviceTypeRequest.fileModel);
	};
	// Convert serviceIds, serviceLocations, and vnfTypes to JSON Array
	$scope.ecdapp.convertStringsToArrays = function(serviceTypeRequest) {
		if (serviceTypeRequest.serviceIds || serviceTypeRequest.serviceIds.trim() != '') {
			try {
				serviceTypeRequest.serviceIds = angular.fromJson(serviceTypeRequest.serviceIds.split(","));
			} catch (error) {
				return 'Service Ids is not in the correct format.';
			}
		} else {
			serviceTypeRequest.serviceIds = [];
		}
		
		if (serviceTypeRequest.serviceLocations || serviceTypeRequest.serviceLocations.trim() != '') {
			try {
				serviceTypeRequest.serviceLocations = angular.fromJson(serviceTypeRequest.serviceLocations.split(","));
			} catch (error) {
				return 'Service Locations is not in the correct format.';
			}
		} else {
			serviceTypeRequest.serviceLocations = [];
		}
		
		if (serviceTypeRequest.vnfTypes || serviceTypeRequest.vnfTypes.trim() != '') {
			try {
				serviceTypeRequest.vnfTypes = angular.fromJson(serviceTypeRequest.vnfTypes.split(","));
			} catch (error) {
				return 'VNF Types is not in the correct format.';
			}
		} else {
			serviceTypeRequest.vnfTypes = [];
		}
		
		try {
			if (isNaN(serviceTypeRequest.typeVersion)) {
			serviceTypeRequest.typeVersion = serviceTypeRequest.typeVersion.replace(/\./gi, "");
			serviceTypeRequest.typeVersion = angular.fromJson(serviceTypeRequest.typeVersion);
			}
		} catch (error) {
			return 'typeVersion is not in the correct format.';
		}
		
		return null;
	}
	
	/**
	 * Validates content of user-editable fields.
	 * Returns null if all is well, 
	 * a descriptive error message otherwise.
	 */
	$scope.ecdapp.validateRequest = function(serviceTypeRequest) {
		if (!serviceTypeRequest)
			return 'No data found.\nPlease enter some values.';
		if (!serviceTypeRequest.owner || serviceTypeRequest.owner.trim() == '') {
			return 'Application/Owner is required.\nPlease enter a value.';
		}
		if (!serviceTypeRequest.typeName || serviceTypeRequest.typeName.trim() == '') {
			return 'Type Name is required.\nPlease enter a value.';
		}
		if (!serviceTypeRequest.typeVersion ) {
				//|| serviceTypeRequest.typeVersion.trim() == '') {
			return 'Type Version is required.\nPlease enter a value.';
		}
		if (!serviceTypeRequest.blueprintTemplate || serviceTypeRequest.blueprintTemplate.trim() == '')
			return 'Blueprint Template is required.\nPlease enter a value.';

		return null;
	}
	
	$scope.ecdapp.uploadBlueprint = function(serviceTypeRequest) {	
		if (debug)
			$log.debug('uploadBlueprint: serviceType is ' + JSON.stringify(serviceTypeRequest));
		$scope.ecdapp.uploadInProgress = true;
		var validateMsg = $scope.ecdapp.validateRequest(serviceTypeRequest);
		if (validateMsg != null) {
			editServiceType = angular.copy(serviceTypeRequest);
			alert('Invalid Request:\n' + validateMsg);
			$scope.ecdapp.uploadInProgress = false;
			return;			
		}
		//var authUser = $scope.userId;
		//serviceTypeRequest.owner = serviceTypeRequest.owner + ':' + authUser;
		// Create a editServiceTypeRequest object for edit
		var editServiceType = angular.copy(serviceTypeRequest);
		var convertMsg = $scope.ecdapp.convertStringsToArrays(editServiceType);
		if (convertMsg != null) {
			editServiceType = angular.copy(serviceTypeRequest);
			alert('Invalid Request:\n' + convertMsg);
			return;			
		}

		InventoryBlueprintService.uploadBlueprint(editServiceType)
			.then(function(response) {
				if (debug)
					$log.debug('inventoryBlueprintUploadCtrl.uploadBlueprint: ' + JSON.stringify(response));
				if (response && response.error) {
					$log.error('InventoryBlueprintService.uploadBlueprint failed: ' + response.error);
					alert('Failed to upload blueprint:\n' + response.error);
				}
				else {
					// Upload service returns null on success.
					$modalInstance.close("success");
				}
				$scope.ecdapp.uploadInProgress = false;
			},
			function(error) {
				$log.error('InventoryBlueprintService.uploadBlueprint failed: ' + error);
				$scope.ecdapp.uploadInProgress = false;
				alert('Service failed to upload blueprint:\n' + error);
			});

	};

});

/*************************************************************************/

appDS2.controller('inventoryBlueprintViewCtrl', function(
		$scope, $log, message, InventoryBlueprintService) {

	'use strict';
	
	var debug = false;
	
	if (debug)
		$log.debug("inventoryBlueprintViewCtrl.message: " + JSON.stringify(message));

	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.blueprintId = message.blueprint.typeId;
	
	$scope.ecdapp.label = 'View Blueprint ' + message.blueprint.typeName;

	// Fetch the blueprint
	$scope.ecdapp.isDataLoading = true;
	InventoryBlueprintService.viewBlueprint(message.blueprint.typeId).then(function(jsonObj) {
		if (debug)
			$log.debug("inventoryBlueprintViewCtrl.viewBlueprint response: " + JSON.stringify(jsonObj));
		if (jsonObj.error) {
			$scope.ecdapp.errMsg = 'Request Failed';
		}
		else {
			$scope.ecdapp.blueprint = jsonObj.blueprintTemplate;
		}
		$scope.ecdapp.isDataLoading = false;
	}, function(error) {
		$scope.ecdapp.isDataLoading = false;
		alert('Failed to get blueprint. Please retry.');
		$log.error("blueprintViewCtrl failed: " + error);
	});
	
});


/*************************************************************************/

appDS2.controller('inventoryBlueprintDeployCtrl', function(
		$scope, $rootScope, $log, $modal, $modalInstance, message, InventoryDeploymentService, InventoryBlueprintService) {

	'use strict';
	
	// Controls logging in this controller
	var debug = false;
	
	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.label = 'Deploy Blueprint';
	$scope.ecdapp.errMsg = '';
	
	// deployment in progress
	$scope.ecdapp.deploymentInProgress = false;
	$scope.ecdapp.isDataLoading = false;
	$scope.ecdapp.tenant = $rootScope.tenantList.tenant;
	$scope.ecdapp.validTenant = false;
	
	// Cache the input parameter names for validation
	if (debug)
		$log.debug('inventoryBlueprintDeployCtrl: inputs: ' + JSON.stringify(message.blueprint.blueprintInputs));
	$scope.ecdapp.inputsDict = message.blueprint.blueprintInputs;

	// Copy the input parameter names and default values
	let inputsAndDefaults = {};
	for (var pkey in message.blueprint.blueprintInputs) {
		if (debug)
			$log.debug('inventoryBlueprintDeployCtrl: checking key ' + pkey);
		let dval = message.blueprint.blueprintInputs[pkey].defaultValue;
		if (  typeof dval === "undefined" )
			dval = '';
		inputsAndDefaults[pkey] = dval;
	}
	if (debug)
		$log.debug('inventoryBlueprintDeployCtrl: inputsAndDefaults: ' + JSON.stringify(inputsAndDefaults));
	
	// Create an object for edit
	//var genDeployId = message.blueprint.component + "_" + message.blueprint.typeName;
	$scope.ecdapp.editRequest = {
		deployment_id : '',
		type_id : message.blueprint.typeId,
		fileModel : null,
		parmFileDict : inputsAndDefaults,
		tenant : '',
		component: message.blueprint.component,
		tag : message.blueprint.typeName
	};
    $scope.ecdapp.validateTenant = function(){
        if($scope.ecdapp.editRequest.tenant === "Select Tenant"){
            $scope.ecdapp.validTenant = false;
        }else{
        	$scope.ecdapp.validTenant = true;
        }
    }
	/**
	 * Handler for file-read event reads file, parses JSON, validates content.
	 */
	var fileReader = new FileReader();
	fileReader.onload = function(event) {
		let jsonString = fileReader.result;
		if (debug)
			$log.debug('fileReader.onload: read: ' + jsonString);
		let ydict = {};
		try {
			ydict = JSON.parse(jsonString);
		}
		catch (ex) {
			alert('Failed to parse file as JSON:\n' + ex);
		}
		// Process the file
		for (var ykey in ydict) {
			let yval = ydict[ykey];
			if (debug)
				$log.debug('fileReader.onload: typeof ' + ykey + ' is ' + typeof ykey);
			// Allow only expected keys with scalar values
			if (! (ykey in $scope.ecdapp.editRequest.parmFileDict))
				alert('Unexpected file content:\nKey not defined by blueprint:\n' + ykey);
			/*
			else if (typeof yval !== 'string' && typeof yval !== 'number')
				alert('Unexpected file content:\nNot a simple key-value pair:\n' + ykey);
			*/
			if (yval.constructor === {}.constructor)
				$scope.ecdapp.editRequest.parmFileDict[ykey] = angular.toJson(yval);
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
		if (editRequest.type_id == null || editRequest.type_id.trim() == '')
			return 'Type ID is required.\nPlease enter a value.';
		if (editRequest.tag == null || editRequest.tag.trim() == '')
			return 'Deployment tag is required.\nPlease enter a value.';
		if (editRequest.tenant == null || editRequest.tenant.trim() == '')
			return 'Tenant name is required.\nPlease enter a value.';
		// Check that every file parameter is defined by blueprint
		for (var pkey in $scope.ecdapp.editRequest.parmFileDict) {
			// Defined in blueprint?
			if (! $scope.ecdapp.inputsDict[pkey]) 
				return 'Unexpected input parameter\n' + pkey;
		return null;
		}
	};

	$scope.ecdapp.viewDeploymentExecutionsModalPopup = function(deployment) {
		var modalInstance = $modal.open({
			templateUrl : 'inventory_execution_view_popup.html',
			controller : 'inventoryDeploymentExecutionsViewCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-jumbo',
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
			// No response.
		});
	};
	$scope.ecdapp.deployBlueprint = function(editRequest) {
		//editRequest.tenant = $rootScope.tenantList.tenant;
		$scope.ecdapp.errMsg = '';
		editRequest.deployment_id = editRequest.component + "_" + editRequest.tag;
		if (debug)
			$log.debug('deployBlueprint: editRequest is ' + JSON.stringify($scope.ecdapp.editRequest));
		var validateMsg = $scope.ecdapp.validateRequest(editRequest);
		if (validateMsg != null) {
			alert('Invalid Request:\n' + validateMsg);
			return;			
		}
		// Create request with key:value parameters dictionary
		let deploymentRequestObject =	{
			deploymentId : editRequest.deployment_id,
			serviceTypeId : editRequest.type_id,
			inputs : {},
			tenant : editRequest.tenant,
			method : "create"
		};
		let deploymentExecObj = {
				deploymentRef : editRequest.deployment_id,
				tenant : editRequest.tenant
		}
		for (var pkey in $scope.ecdapp.editRequest.parmFileDict)
			try {
				deploymentRequestObject.inputs[pkey] = angular.fromJson($scope.ecdapp.editRequest.parmFileDict[pkey]);
			} catch (error) {
				deploymentRequestObject.inputs[pkey] = $scope.ecdapp.editRequest.parmFileDict[pkey];
			}
		if (debug) 
			$log.debug('deployBlueprint: deploymentRequestObject is ' + JSON.stringify(deployRequestObject));

		$scope.ecdapp.deploymentInProgress = true;
		InventoryDeploymentService.deployBlueprint(deploymentRequestObject)
			.then(function(response) {
				$scope.ecdapp.deploymentInProgress = false;
				if (response.error) {
					$scope.ecdapp.errMsg = 'Failed to deploy blueprint: ' + response.error; 
					alert('Failed to deploy blueprint:\n' + response.error);
				}
				else {
					$modalInstance.close(response);
					// launch the view executions modal
					$scope.ecdapp.viewDeploymentExecutionsModalPopup(deploymentExecObj);
				}
			},
			function (error) {
				$log.error('inventoryBlueprintDeployCtrl: error while deploying: ' + error);
				$scope.ecdapp.errMsg = 'Server rejected deployment request: ' + error; 
				alert('Server rejected deployment request:\n' + error);
				$scope.ecdapp.deploymentInProgress = false;
			}
		);
	};
});
