appDS2.controller('inventoryDeploymentTableController', function(
		$rootScope, $scope, $interval, $log, $modal, modalService, InventoryDeploymentService, InventoryBlueprintService) {

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
	$scope.ecdapp.selectedRow = null;  // initialize our variable to null
	$scope.ecdapp.setClickedRow = function(index){  //function that sets the value of selectedRow to current index
		$scope.ecdapp.selectedRow = index;
	}
        
	$scope.ecdapp.updateTable = function() {			
		$scope.ecdapp.isSrvcDataLoading = true;
		var srvcIds = [];
		var cloneGrid = $scope.ecdapp.tableData;	
		angular.forEach($scope.ecdapp.tableData, function(item, index) {
			  angular.forEach(item, function(value, key) {
				  if (key === "deploymentRef") {
					  srvcIds.push(value); 
				  }	  
			  });
	  });
		
		InventoryDeploymentService.getDeploymentStatus(srvcIds)
		.then(function(jsonObj) {
		if (jsonObj.error) {
			$log.error("inventoryDeploymentTableController.updateTable failed: " + jsonObj.error);
		} else {
			for (var indx = 0; indx < jsonObj.length; indx++) {
				if (jsonObj[indx].status === "terminated") {
					jsonObj[indx].status = "completed";
					jsonObj[indx].statusImg = "static/fusion/images/active.png";
				} else {
					jsonObj[indx].statusImg = "static/fusion/images/inactive.png";
				}
				cloneGrid[indx].statusInfo = jsonObj[indx];
			}
			$scope.ecdapp.tableData = cloneGrid;
		}
		$scope.ecdapp.isSrvcDataLoading = false;
	}, function(error) {
		$log.error("inventoryBlueprintController.updateTable failed: " + error);
		$scope.ecdapp.isSrvcDataLoading = false;
	});
	}
	/**
	 * Loads the table. Interprets the remote controller's response and copies
	 * to scope variables. The response is either a list to be assigned to
	 * tableData, or an error to be shown.
	 */
	$scope.ecdapp.loadTable = function(sortBy, searchBy) {
		$scope.ecdapp.isDataLoading = true;
		$scope.ecdapp.sortBy = sortBy;
		$scope.ecdapp.searchBy = searchBy;
		InventoryDeploymentService.getDeployments($scope.ecdapp.currentPageNum,
				$scope.ecdapp.viewPerPage, sortBy, searchBy).then(
				function(jsonObj) {
					if (jsonObj.error) {
						$log.error("inventoryDeploymentController.loadTable failed: "
								+ jsonObj.error);
						$scope.ecdapp.isRequestFailed = true;
						$scope.ecdapp.errMsg = jsonObj.error;
						$scope.ecdapp.tableData = [];
					} else {
						$scope.ecdapp.isRequestFailed = false;
						$scope.ecdapp.errMsg = null;
						$scope.ecdapp.totalPages = jsonObj.totalPages;
						$scope.ecdapp.tableData = jsonObj.items;
						$scope.ecdapp.updateTable();
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error("inventoryDeploymentController.loadTable failed: "
							+ error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = error;
					$scope.ecdapp.tableData = [];
					$scope.ecdapp.isDataLoading = false;
				});
	};
	
	/**
	 * Loads the table. Interprets the remote controller's response and copies
	 * to scope variables. The response is either a list to be assigned to
	 * tableData, or an error to be shown.
	 */
	$scope.ecdapp.sortTable = function(sortBy) {
		$scope.ecdapp.isDataLoading = true;
		$scope.ecdapp.sortBy = sortBy;
		InventoryDeploymentService.getDeployments($scope.ecdapp.currentPageNum,
				$scope.ecdapp.viewPerPage, sortBy, $scope.ecdapp.searchBy).then(
				function(jsonObj) {
					if (jsonObj.error) {
						$log.error("inventoryDeploymentController.loadTable failed: "
								+ jsonObj.error);
						$scope.ecdapp.isRequestFailed = true;
						$scope.ecdapp.errMsg = jsonObj.error;
						$scope.ecdapp.tableData = [];
					} else {
						$scope.ecdapp.isRequestFailed = false;
						$scope.ecdapp.errMsg = null;
						$scope.ecdapp.totalPages = jsonObj.totalPages;
						$scope.ecdapp.tableData = jsonObj.items;
						$scope.ecdapp.updateTable();
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error("inventoryDeploymentController.loadTable failed: "
							+ error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = error;
					$scope.ecdapp.tableData = [];
					$scope.ecdapp.isDataLoading = false;
				});
	};
	
	/**
	 * Loads the table. Interprets the remote controller's response and copies
	 * to scope variables. The response is either a list to be assigned to
	 * tableData, or an error to be shown.
	 */
	$scope.ecdapp.searchTable = function(searchBy) {
		$scope.ecdapp.isDataLoading = true;
		$scope.ecdapp.searchBy = searchBy;
		InventoryDeploymentService.getDeployments($scope.ecdapp.currentPageNum,
				$scope.ecdapp.viewPerPage, $scope.ecdapp.sortBy, searchBy).then(
				function(jsonObj) {
					if (jsonObj.error) {
						$log.error("inventoryDeploymentController.loadTable failed: "
								+ jsonObj.error);
						$scope.ecdapp.isRequestFailed = true;
						$scope.ecdapp.errMsg = jsonObj.error;
						$scope.ecdapp.tableData = [];
					} else {
						$scope.ecdapp.isRequestFailed = false;
						$scope.ecdapp.errMsg = null;
						$scope.ecdapp.totalPages = jsonObj.totalPages;
						$scope.ecdapp.tableData = jsonObj.items;
						$scope.ecdapp.updateTable();
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error("inventoryDeploymentController.loadTable failed: "
							+ error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = error;
					$scope.ecdapp.tableData = [];
					$scope.ecdapp.isDataLoading = false;
				});
	};
	
	$scope.ecdapp.checkHelmStatus = function(deployment) {
		var selTenant = deployment.statusInfo.tenant_name;
		if ( typeof selTenant === "undefined" ) {
			selTenant = "default_tenant";
		}
		deployment.onlyLatest = true;
		
		// This object holds data for this operation
		$scope.ecdapp.helmStatusRequest = {
				"deployment_id": deployment.deploymentRef,
				"workflow_name": "status",
				"tenant": selTenant
		};
		InventoryDeploymentService.helmStatusFlow($scope.ecdapp.helmStatusRequest).then(function(jsonObj) {
			if (debug)
	 			$log.debug("checkHelmStatus response: " + JSON.stringify(jsonObj));
	 		if (jsonObj.error) {
	 			$scope.ecdapp.errMsg = 'Request Failed: ' + jsonObj.error;
	 			$scope.ecdapp.updatingDeployment = false;
	 			$scope.ecdapp.isDataLoading = false;
	 		} else {
	 			console.log('%c POSTED helm status request', 'color: magenta; font-weight: bold;');
	 		}
		}, function(error) {
	 				$log.error('helmStatusFlow failed: ' + error);
	 			});
		$scope.ecdapp.viewDeploymentExecutionsModalPopup(deployment);
	};
	/**
	 * Shows a modal pop-up with blueprint content.
	 * Passes data in via an object named "message". 
	 */
	$scope.ecdapp.viewBlueprintDataModal = function(deployment) {
		var modalInstance = $modal.open({
			templateUrl : 'blueprint_data_view_popup.html',
			controller : 'deployBlueprintViewCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-jumbo',
			resolve : {
				message : function() {
					var dataForPopup = {
						blueprint : deployment
					};
					return dataForPopup;
				}
			}
		});
		modalInstance.result.then(function(response) {
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
	 * Shows a modal pop-up to confirm deletion. 
	 * On successful completion, updates the table.
	 */
	$scope.ecdapp.deleteDeploymentModalPopup = function(deployment) {	
		deployment.onlyLatest = true;
		var modalInstance = $modal.open({
			templateUrl : 'inventory_deployment_delete_popup.html',
			controller : 'inventoryDeploymentDeleteCtrl',
			sizeClass: 'modal-small',
			resolve : {
				message : function() {
					var dataForPopup = {
						deployment : deployment,
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
					$scope.ecdapp.viewDeploymentExecutionsModalPopup(deployment);
				}
			}
		});
	};

	/**
	 * Shows a modal pop-up with executions for a deployment.
	 * Passes data in via an object named "deployment".
	 */
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
	
	/**
	 * Shows a modal pop-up with executions for a deployment.
	 * Passes data in via an object named "deployment".
	 */
	$scope.ecdapp.viewDeploymentInputsModalPopup = function(deployment) {
		var modalInstance = $modal.open({
			templateUrl : 'inventory_deployment_inputs_view_popup.html',
			controller : 'inventoryDeploymentInputsViewCtrl',
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

	/**
	 * Shows a modal pop-up to initiate helm upgrade for a deployment
	 */
	$scope.ecdapp.upgradeDeploymentModalPopup = function(deployment) {
		//console.log(deployment);
		var modalInstance = $modal.open({
			templateUrl: 'inventory_deployment_upgrade_popup.html',
			controller: 'inventoryDeploymentUpgradeCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-jumbo',
			resolve: {
				message: function() {
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
	
	/**
	 * Shows a modal pop-up to initiate helm rollback for a deployment
	 */
	$scope.ecdapp.rollbackDeploymentModalPopup = function(deployment) {
		var modalInstance = $modal.open({
			templateUrl: 'inventory_deployment_rollback_popup.html',
			controller: 'inventoryDeploymentRollbackCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-jumbo',
			resolve: {
				message: function() {
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
	
	/**
	 * Shows a modal pop-up to initiate update blueprint for a deployment
	 */
	$scope.ecdapp.updateDeploymentModalPopup = function(deployment) {
		var modalInstance = $modal.open({
			templateUrl: 'inventory_deployment_update_popup.html',
			controller: 'inventoryDeploymentUpdateCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-jumbo',
			resolve: {
				message: function() {
					var dataForPopup = {
						deployment : deployment
					};
					return dataForPopup;
				}
			}
		});		
	};
	
	/**
	 * Shows a modal pop-up to confirm service deletion. 
	 * On successful completion, updates the table.
	 */
	$scope.ecdapp.deleteServiceModalPopup = function(service) {
		modalService.popupConfirmWin("Confirm", "Delete Service with ID '"
				+ service.serviceId + "'?", function() {
			InventoryDeploymentService.deleteService(service.serviceId).then(
					function(response) {
						if (debug)
							$log.debug('deleteServiceModalPopup: response: ' + JSON.stringify(response));
						if (response && response.error) {
							alert('Failed to delete service:\n' + response.error);
						}
						else {
							// No response body on success.
							$scope.ecdapp.loadTable();
						}
					},
					function(error) {
						$log.error('InventoryDeploymentService.deleteService failed: ' + error);
						alert('Service failed to delete service:\n' + error);
					});
		})
	};
});

/*************************************************************************/

appDS2.controller('inventoryDeploymentDeleteCtrl', function(
		$scope, $rootScope, $log, $modalInstance, message, InventoryDeploymentService) {

	'use strict';
	
	// Controls logging in this controller
	var debug = false;

	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.label = 'Undeploy?';
	$scope.ecdapp.deploymentRef = message.deployment.deploymentRef;
	var selTenant = message.deployment.statusInfo.tenant_name;
	$scope.ecdapp.ui_tenant = selTenant;
	$scope.ecdapp.tenant = selTenant;

	$scope.ecdapp.deleteDeploymentById = function(){
		InventoryDeploymentService.deleteDeployment($scope.ecdapp.deploymentRef, $scope.ecdapp.tenant).then(
			function(response) {
				if (debug)
					$log.debug('inventoryDeploymentDeleteCtrl.deleteDeployment: ' + JSON.stringify(response));
				if (response && response.error) {
					$log.error('InventoryDeploymentService.deleteDeployment failed: ' + response.error);
					alert('Failed to delete deployment:\n' + response.error);
				}
				else {
					// Delete service returns null on success.
					$modalInstance.close("success");
				}
			},
			function(error) {
				$log.error('InventoryDeploymentService.deleteDeployment failed: ' + error);
				alert('Service failed to delete deployment:\n' + error);
			});
	}

});

/*************************************************************************/

appDS2.controller('inventoryDeploymentExecutionsViewCtrl', function(
		$scope, $rootScope, $interval, $log, $modalInstance, message, modalService, InventoryExecutionService, ExecutionService) {

	'use strict';

	var debug = false;

	if (debug)
		$log.debug("inventoryDeploymentsExecutionsViewCtrl.message: " + JSON.stringify(message));

	// this object holds all app data and functions
	$scope.ecdapp = {};
	// models for controls on screen
	$scope.ecdapp.label = 'Deployment Executions';
	$scope.ecdapp.tableData = [];
	$scope.ecdapp.logTableData = [];
	$scope.ecdapp.currentPageNum = 1;
	$scope.ecdapp.viewPerPage = 50;
	$scope.ecdapp.currentLogPageNum = 1;
	
	// other
	$scope.ecdapp.errMsg = null;
	$scope.ecdapp.isDataLoading = true;
	$scope.ecdapp.isEventLogQuery = false;
	$scope.ecdapp.isRequestFailed = false;
	$scope.ecdapp.isLastExecution = message.deployment.onlyLatest;
	$scope.ecdapp.isLogType = true;
    $scope.ecdapp.refresh_switch = {
            value: true
        };
    $scope.ecdapp.options = {
            "on":"On",
            "off":"Off"
        }   
	var selTenant = 'default_tenant';
	
	if (typeof message.deployment.statusInfo === "undefined") {
		selTenant = message.deployment.tenant;
	} else {
		selTenant = message.deployment.statusInfo.tenant_name;
	}

	$scope.ecdapp.ui_tenant = selTenant;
	$scope.ecdapp.tenant = selTenant;
	$scope.ecdapp.deplRef = message.deployment.deploymentRef;
    var stop;
	/**
	 * Loads the table. Interprets the remote controller's response and copies
	 * to scope variables. The response is either a list to be assigned to
	 * tableData, or an error to be shown.
	 */
	$scope.ecdapp.loadTable = function() {
		$scope.ecdapp.isDataLoading = true;
		InventoryExecutionService.getExecutionsByDeployment(message.deployment.deploymentRef, 
				$scope.ecdapp.tenant,
				$scope.ecdapp.currentPageNum,
				$scope.ecdapp.viewPerPage).then(
				function(jsonObj) {
					if (jsonObj.error) {
						$log.error("inventoryDeploymentExecutionsViewCtrl.loadTable failed: "
								+ jsonObj.error);
						$scope.ecdapp.isRequestFailed = true;
						if (jsonObj.error.includes("404")) {
							$scope.ecdapp.errMsg = "404 - Deployment " + message.deployment.deploymentRef + " Not Found!";
						}
						$scope.ecdapp.tableData = [];
				        $scope.ecdapp.stopLoading();
					} else {
						$scope.ecdapp.isRequestFailed = false;
						$scope.ecdapp.errMsg = null;
						$scope.ecdapp.totalPages = jsonObj.totalPages;
						var resultLen = jsonObj.items.length;
						if (resultLen != undefined && resultLen > 0) {
							var exec_id = jsonObj.items[resultLen-1].id;
							if ($scope.ecdapp.isLastExecution) {
								$scope.ecdapp.tableData = [];
								$scope.ecdapp.tableData.push(jsonObj.items[resultLen-1]);
							} else {
								$scope.ecdapp.tableData = jsonObj.items;
							}
							$scope.ecdapp.getExecutionLogs(exec_id, $scope.ecdapp.tenant);
						}
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error("inventoryDeploymentExecutionsViewCtrl.loadTable failed: "
							+ error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = error;
					$scope.ecdapp.tableData = [];
					$scope.ecdapp.isDataLoading = false;
			        $scope.ecdapp.stopLoading();
				});
	};
    $scope.$watch('ecdapp.refresh_switch["value"]', function(newValue,oldValue,scope) {
    	if (newValue != oldValue) {
    		if (newValue === true) {
    			$scope.ecdapp.loadTable();
    			stop = $interval( function(){ $scope.ecdapp.loadTable(); }, 30000, 100, false);
    		} else {
    			$scope.ecdapp.stopLoading();
    		}
    	}
    }, true);

    if ($scope.ecdapp.refresh_switch.value === true) {
	stop = $interval( function(){ $scope.ecdapp.loadTable(); }, 30000, 100, false);
    }
    
    $scope.ecdapp.stopLoading = function() {
        if (angular.isDefined(stop)) {
          $interval.cancel(stop);
          stop = undefined;
        }
      };
    
  	$scope.ecdapp.cancelExecutionModalPopup = function(execution, tenant) {
		modalService.popupConfirmWin("Confirm", "Cancel execution with ID '"
				+ execution.id + "'?", function() {
			$scope.ecdapp.isCancelOn = true;
			// TODO: gather action from user
			InventoryExecutionService.cancelExecution(execution.id, execution.deployment_id, "force-cancel", tenant).then(
					function(response) {
						if (debug)
							$log.debug("Controller.cancelExecutionModalPopup: " + JSON.stringify(response));
						if (response && response.error) {
							// $log.error('cancelExectuion failed: ' + response.error);
							alert('Failed to cancel execution:\n' + response.error);
							$scope.ecdapp.isCancelOn = false;
						}
						else {
							// No response body on success.
							$scope.ecdapp.isCancelOn = false;
							$scope.ecdapp.loadTable();
						}
					},
					function(error) {
						$scope.ecdapp.isCancelOn = false;
						$log.error('ExecutionService.cancelExecution failed: ' + error);
						alert('Service failed to cancel execution:\n' + error);
					});
		})
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
	
	$scope.ecdapp.selected = false;
	$scope.ecdapp.toggleStatusDefinitions = function() {
		$scope.ecdapp.selected = $scope.ecdapp.selected ? false :true;
	}

	/**
	 * Shows a modal pop-up with the error.
	 */
	$scope.ecdapp.viewErrorModalPopup = function(row) {
		$modalInstance.dismiss('cancel');
		modalService.showFailure('Error Details', row.error, function() { } );
	};
	
	$scope.ecdapp.getExecutionLogs = function(id, tenant) {
		$scope.ecdapp.executionId = id;
		$scope.ecdapp.isEventLogQuery = false;
		InventoryExecutionService.getEventsByExecution(id , $scope.ecdapp.isLogType, tenant,
				$scope.ecdapp.currentLogPageNum, $scope.ecdapp.viewPerPage ).then(
				function(jsonObj) {
					if (jsonObj.error) {
						$log.error("inventoryDeploymentExecutionsViewCtrl.getExecutionLogs failed: "
								+ jsonObj.error);
						$scope.ecdapp.isEventLogQuery = false;
						$scope.ecdapp.evtErrMsg = jsonObj.error;
						$scope.ecdapp.logTableData = [];
					} else {
						$scope.ecdapp.isEventLogQuery = true;
						$scope.ecdapp.evtErrMsg = null;
						$scope.ecdapp.totalLogPages = jsonObj.totalPages;
						$scope.ecdapp.logTableData = jsonObj.items;
						/*
						if ($scope.ecdapp.isLogType) {
							$scope.ecdapp.logTableData = jsonObj.items;
						} else {
							$scope.ecdapp.logTableData = [];
							angular.forEach(jsonObj.items, function(item, index) {
								  angular.forEach(item, function(value, key) {
									  if (key === "type" && value != "cloudify_log") {
										  $scope.ecdapp.logTableData.push(item); 
									  }	  
								  });
							});
						}
						*/
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error("inventoryDeploymentExecutionsViewCtrl.getExecutionLogs failed: "
							+ error);
					$scope.ecdapp.evtErrMsg = error;
					$scope.ecdapp.logTableData = [];
					$scope.ecdapp.isDataLoading = false;
				});
	}

    $scope.$on('$destroy', function() {
        // Make sure that the interval is destroyed too
        $scope.ecdapp.stopLoading();
      });
});

/*************************************************************************/
appDS2.controller('inventoryDeploymentInputsViewCtrl', function(
		$scope, $rootScope, $log, $modalInstance, message, InventoryDeploymentService) {

	'use strict';
	
	// Controls logging in this controller
	var debug = false;
	
	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.label = 'Deployment Inputs';
	$scope.ecdapp.deployment = null;
	$scope.ecdapp.deploymentRef = message.deployment.deploymentRef;
	$scope.ecdapp.serviceId = message.deployment.serviceId;
	$scope.ecdapp.errMsg = null;
	$scope.ecdapp.isDataLoading = true;
	$scope.ecdapp.isRequestFailed = false;
	var selTenant = message.deployment.statusInfo.tenant_name;
	if ( typeof selTenant === "undefined" ) {
		selTenant = "default_tenant";
	}
	$scope.ecdapp.ui_tenant = selTenant;
	$scope.ecdapp.tenant = selTenant;

	InventoryDeploymentService.getDeployment(message.deployment.deploymentRef, $scope.ecdapp.tenant).then(function(deployment) {
		if (deployment.items.length == 0) {
			$scope.ecdapp.errMsg = "404 - Deployment " + message.deployment.deploymentRef + " Not Found!";
			$log.error("InventoryDeploymentSerice.getDeployment failed: "
					+ $scope.ecdapp.errMsg);
			$scope.ecdapp.isRequestFailed = true;
		} 
		// Deployment IDs are unique, so this will always return exactly one item!
		// retrieve blueprintId and inputs of deployment.
		else {
			$scope.ecdapp.errMsg = null;
			$scope.ecdapp.deployment = deployment.items[0];
			$scope.ecdapp.isRequestFailed = false;
		}
		$scope.ecdapp.isDataLoading = false;
	},
	function(error) {
		$log.error('InventoryDeploymentService.getDeployment failed: ' + JSON.stringify(error));
		$scope.ecdapp.isRequestFailed = true;
		$scope.ecdapp.errMsg = error;
		$scope.ecdapp.isDataLoading = false;
	});	
});

/*************************************************************************/
appDS2.controller('inventoryDeploymentUpdateCtrl', function(
		$scope, $log, $modalInstance, message, InventoryBlueprintService, InventoryDeploymentService) {

	'use strict';
	
	var debug = false;
	if (debug)
		$log.debug("inventoryDeploymentUpdateCtrl.message: " + JSON.stringify(message));

	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.deploymentInProgress = false;
	$scope.ecdapp.serviceTypeComplete = false;
	$scope.ecdapp.isDataLoading = true;
	$scope.ecdapp.errMsg = null;
	$scope.ecdapp.label = 'Update Deployment:  ' + message.deployment.deploymentRef;
	$scope.ecdapp.deploymentRef = message.deployment.deploymentRef;
	var selTenant = message.deployment.statusInfo.tenant_name;
	if ( typeof selTenant === "undefined" ) {
		selTenant = "default_tenant";
	}
	$scope.ecdapp.tenant = selTenant;
	$scope.ecdapp.ui_tenant = selTenant;
	$scope.ecdapp.typeName = '';
	$scope.ecdapp.typeId = '';
	$scope.ecdapp.inputsDict = {};
	$scope.ecdapp.editRequest = {
			deployment_id : message.deployment.deploymentRef,
			type_id : '',
			fileModel : null,
			parmFileDict : {},
			tenant : $scope.ecdapp.tenant
		};

	// get the blueprints from inventory matching deployment reference filter
	$scope.ecdapp.bp = [];
	var sortBy = '';
	var searchBy = message.deployment.deploymentRef.split("_", 1);
	searchBy = searchBy[0];
	InventoryBlueprintService.getBlueprints(1, 100, sortBy, searchBy)
		.then(function(jsonObj) {
		if (jsonObj.error) {
			$log.error("inventoryDeploymentUpdateCtrl.loadTable failed: " + jsonObj.error);
			$scope.ecdapp.errMsg = jsonObj.error;
			$scope.ecdapp.bp = [];
		} else {
			$scope.ecdapp.errMsg = null;
			$scope.ecdapp.bp = jsonObj.items;	
			if (Array.isArray($scope.ecdapp.bp) ) {
				  angular.forEach($scope.ecdapp.bp, function(item, index) {
				  item.checked = false;
			  });
			}
		}			
		$scope.ecdapp.isDataLoading = false;
	}, function(error) {
		$log.error("inventoryDeploymentUpdateCtrl.loadTable failed: " + error);
		$scope.ecdapp.errMsg = error;
		$scope.ecdapp.bp = [];
		$scope.ecdapp.isDataLoading = false;
	});
	$scope.ecdapp.updateSelection = function(position) {
		$scope.ecdapp.typeId = position;
		$scope.ecdapp.editRequest.type_id = position;
		angular.forEach($scope.ecdapp.bp, function(item, index) {
	          if (position != index+1) 
	        	  item.checked = false;
	        });
	}
	$scope.ecdapp.getBlueprint = function() {
		$scope.ecdapp.isDataLoading = true;
		InventoryBlueprintService.viewBlueprint($scope.ecdapp.typeId).then(function(jsonObj) {
			if (debug)
				$log.debug("inventoryDeploymentUpdateCtrl.viewBlueprint response: " + JSON.stringify(jsonObj));
			if (jsonObj.error) {
				$scope.ecdapp.errMsg = 'Request Failed';
				$scope.ecdapp.serviceTypeComplete = false;
				$scope.ecdapp.isDataLoading = false;
			}
			else {
				$scope.ecdapp.typeName = jsonObj.typeName;
				$scope.ecdapp.typeVersion = jsonObj.typeVersion;
				$scope.ecdapp.inputsDict = jsonObj.blueprintInputs;
				// query the current deployment inputs
				InventoryDeploymentService.getDeployment(message.deployment.deploymentRef, $scope.ecdapp.tenant).then(function(deployment) {
					if (deployment.items.length == 0) {
						$scope.ecdapp.errMsg = "404 - Deployment " + message.deployment.deploymentRef + " Not Found!";
						$log.error("InventoryDeploymentSerice.getDeployment failed: "
								+ $scope.ecdapp.errMsg);
						//$scope.ecdapp.isRequestFailed = true;
						$scope.ecdapp.serviceTypeComplete = true;
					} 
					// Deployment IDs are unique, so this will always return exactly one item!
					// retrieve inputs of deployment.
					else {
						$scope.ecdapp.errMsg = null;
						$scope.ecdapp.deployment = deployment.items[0];
						// Copy the input parameter names and default values
						let inputsAndDefaults = {};
						for (var pkey in $scope.ecdapp.inputsDict) {
							if (debug)
								$log.debug('inventoryDeploymentUpdateCtrl: checking key ' + pkey);
							let dval = $scope.ecdapp.deployment.inputs[pkey];
								//$scope.ecdapp.inputsDict[pkey].defaultValue;
							if (dval === undefined || dval === null) {
								dval = '';
							}
							inputsAndDefaults[pkey] = dval;
						}
						$scope.ecdapp.editRequest.parmFileDict = inputsAndDefaults;
						$scope.ecdapp.editRequest.type_id = $scope.ecdapp.typeId;
						if (debug)
							$log.debug('inventoryBlueprintDeployCtrl: inputsAndDefaults: ' + JSON.stringify(inputsAndDefaults));

						$scope.ecdapp.serviceTypeComplete = true;
						//$scope.$apply();
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error('InventoryDeploymentService.getDeployment failed: ' + JSON.stringify(error));
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = error;
					$scope.ecdapp.isDataLoading = false;
				});	
			}
			$scope.ecdapp.isDataLoading = false;
		}, function(error) {
			$scope.ecdapp.isDataLoading = false;
			$scope.ecdapp.serviceTypeComplete = false;
			alert('Failed to get blueprint. Please retry with valid blueprint ID.');
			$log.error("inventoryDeploymentUpdateCtrl failed: " + error);
		});
	};

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
		
		$scope.ecdapp.validateRequest = function(editRequest) {
			if (editRequest == null)
				return 'No data found.\nPlease enter some values.';
			if (editRequest.deployment_id == null || editRequest.deployment_id.trim() == '')
				return 'Deployment ID is required.\nPlease enter a value.';
			if (editRequest.type_id == null || editRequest.type_id.trim() == '')
				return 'Type ID is required.\nPlease enter a value.';
			// Check that every file parameter is defined by blueprint
			for (var pkey in $scope.ecdapp.editRequest.parmFileDict) {
				// Defined in blueprint?
				if (! $scope.ecdapp.inputsDict[pkey]) 
					return 'Unexpected input parameter\n' + pkey;
			}
			return null;
		}
		
		$scope.ecdapp.updateDeployment = function(editRequest) {
			if (debug)
				$log.debug('deployBlueprint: editRequest is ' + JSON.stringify($scope.ecdapp.editRequest));
			var validateMsg = $scope.ecdapp.validateRequest(editRequest);
			if (validateMsg != null) {
				alert('Invalid Request:\n' + validateMsg);
				$scope.ecdapp.errMsg = validateMsg;
				return;			
			}
			// Create request with key:value parameters dictionary
			let deploymentRequestObject =	{
				deploymentId : editRequest.deployment_id,
				serviceTypeId : editRequest.type_id,
				inputs : {},
				tenant : editRequest.tenant,
				method : "update"
			};
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
						alert('Failed to deploy blueprint:\n' + response.error);
						$scope.ecdapp.errMsg = response.error;
					} else {
						alert('Deployment update request sent successfully, query the execution status for final outcome');
						$modalInstance.close(response);
					}
				},
				function (error) {
					$log.error('inventoryBlueprintDeployCtrl: error while deploying: ' + error);
					alert('Server rejected deployment request:\n' + error);
					$scope.ecdapp.deploymentInProgress = false;
				}
			);
		};

});


/*************************************************************************/

appDS2.controller('deployBlueprintViewCtrl', function(
		$scope, $log, message, InventoryBlueprintService) {

	'use strict';
	
	var debug = false;
	
	if (debug)
		$log.debug("deployBlueprintViewCtrl.message: " + JSON.stringify(message));

	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.label = 'View Blueprint ' + message.blueprint.deploymentRef;

	var typeLink = message.blueprint.typeLink.href;
    var n = typeLink.lastIndexOf("/");
    var typeId = typeLink.substring(n+1);
	// Fetch the blueprint
	$scope.ecdapp.isDataLoading = true;
	InventoryBlueprintService.viewBlueprint(typeId).then(function(jsonObj) {
		if (debug)
			$log.debug("deployBlueprintViewCtrl.viewBlueprint response: " + JSON.stringify(jsonObj));
		if (jsonObj.error) {
			$scope.ecdapp.errMsg = 'Request Failed';
		}
		else {
			$scope.ecdapp.typeName = jsonObj.typeName;
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
appDS2.controller('inventoryDeploymentRollbackCtrl', function(
		$scope, $rootScope, $log, $modalInstance, message, InventoryDeploymentService) {

	'use strict';
	
	// Controls logging in this controller
	var debug = false;
	
	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.label = 'Deployment Rollback';
	$scope.ecdapp.revisions = [];
	$scope.ecdapp.deploymentRef = message.deployment.deploymentRef;		
	$scope.ecdapp.serviceId = message.deployment.serviceId;
	$scope.ecdapp.errMsg = null;
	$scope.ecdapp.isDataLoading = true;
	$scope.ecdapp.isRequestFailed = false;
	$scope.ecdapp.updatingDeployment = false;
	var selTenant = message.deployment.statusInfo.tenant_name;
	if ( typeof selTenant === "undefined" ) {
		selTenant = "default_tenant";
	}
	$scope.ecdapp.tenant = selTenant;
	$scope.ecdapp.ui_tenant = selTenant;
	$scope.ecdapp.local_revisions = [];
	// This object holds data for this operation
	$scope.ecdapp.rollbackRequest = {
			"deployment_id": message.deployment.deploymentRef,
			"workflow_name": "rollback",
			"tenant": selTenant,
			"revision": 1
	};
	
	InventoryDeploymentService.getNodeInstanceVersions($scope.ecdapp.deploymentRef, $scope.ecdapp.tenant).then(function(nodeRunTime) {
		if (nodeRunTime == null) {
			$scope.ecdapp.errMsg = "Failed to retrieve Node instance runtime information";
			$log.error("InventoryDeploymentSerice.getNodeInstanceVersions failed: "
					+ $scope.ecdapp.errMsg);
			$scope.ecdapp.isRequestFailed = true;			
		} else {
			$scope.ecdapp.errMsg = null;
			$scope.ecdapp.revisions = nodeRunTime.items[0].runtime_properties['helm-history'];
			  if (Array.isArray($scope.ecdapp.revisions) ) {
				  var dLen = $scope.ecdapp.revisions.length;

				  for (var i = 1; i < dLen; i++) {
				    var str = $scope.ecdapp.revisions[i].replace(/\s+/g, ' ');
				    var itemStrArr = str.split(" ");
				    var itemLen = itemStrArr.length;
				    var revObj = {};
				    revObj.revision = itemStrArr[0].trim();
				    revObj.updated = itemStrArr.slice(1,5).toString().replace(/,/g, ' ');	
				    revObj.status = itemStrArr[6].trim();
				    revObj.chart = itemStrArr[7].trim();
				    revObj.description = itemStrArr.slice(8,itemLen).toString().replace(/,/g, ' ');
				    revObj.name = itemStrArr[0].trim();
				    revObj.checked = false;
				    $scope.ecdapp.local_revisions.push(revObj);
				  }
			  }
			console.log($scope.ecdapp.local_revisions);
			$scope.ecdapp.isRequestFailed = false;
		}
		$scope.ecdapp.isDataLoading = false;
	},
	function(error) {
		$log.error('InventoryDeploymentService.getNodeInstanceVersions failed: ' + JSON.stringify(error));
		$scope.ecdapp.isRequestFailed = true;
		$scope.ecdapp.errMsg = error;
		$scope.ecdapp.isDataLoading = false;
	});
	$scope.ecdapp.updateSelection = function(position) {
		$scope.ecdapp.rollbackRequest.revision = position;
		angular.forEach($scope.ecdapp.local_revisions, function(item, index) {
	          if (position != index+1) 
	        	  item.checked = false;
	        });
	}
	/**
	 * rollback deployment based on parameters user enters/adjusts in modal popup
	 * First retrieves the node-id using the blueprintId
	 * Using the node-id and deploymentId, retrieves the node-instance-id
	 * Calls the update resource API, passing object with deploymentId, and changed parameters
	 */
	 $scope.ecdapp.rollbackWorkflow = function(revision) {
	 	$scope.ecdapp.updatingDeployment = true;
	 	$scope.ecdapp.isDataLoading = true;
		InventoryDeploymentService.rollbackFlow($scope.ecdapp.rollbackRequest).then(function(jsonObj) {
			if (debug)
	 			$log.debug("inventoryDeploymentRollbackCtrl.rollbackWorkflow response: " + JSON.stringify(jsonObj));
	 		if (jsonObj.error) {
	 			$scope.ecdapp.errMsg = 'Request Failed: ' + jsonObj.error;
	 			$scope.ecdapp.updatingDeployment = false;
	 			$scope.ecdapp.isDataLoading = false;
	 		} else {
	 			console.log('%c ROLLBACK RESOURCES COMPLETED', 'color: magenta; font-weight: bold;');
	 			alert('Rollback request for ' + $scope.ecdapp.deploymentRef + ' successfully went through to Cloudiy. Rollback is now pending');
	 			$scope.ecdapp.updatingDeployment = false;
	 			$scope.ecdapp.isDataLoading = false;
	 		}
	 		}, function(error) {
	 				$scope.ecdapp.updatingDeployment = false;
	 				$log.error('inventoryDeploymentRollbackCtrl failed: ' + error);
	 				alert('Failed to rollback Deployment ' + $scope.ecdapp.deploymentRef + '. Please retry.');
	 				$scope.ecdapp.isDataLoading = false;
	 			});
	 	}
});

/*************************************************************************/
appDS2.controller('inventoryDeploymentUpgradeCtrl', function(
		$scope, $rootScope, $log, $modalInstance, message, InventoryDeploymentService) {

	'use strict';
	
	// Controls logging in this controller
	var debug = false;
	
	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.label = 'Deployment Upgrade';
	$scope.ecdapp.deployment = null;
	$scope.ecdapp.deploymentRef = message.deployment.deploymentRef;		//THIS IS THE BLUEPRINT ID
	$scope.ecdapp.serviceId = message.deployment.serviceId;
	$scope.ecdapp.errMsg = null;
	$scope.ecdapp.isDataLoading = true;
	$scope.ecdapp.isRequestFailed = false;
	$scope.ecdapp.updatingDeployment = false;
	var selTenant = message.deployment.statusInfo.tenant_name;
	if ( typeof selTenant === "undefined" ) {
		selTenant = "default_tenant";
	}
	$scope.ecdapp.tenant = selTenant;
	$scope.ecdapp.ui_tenant = selTenant;
	$scope.ecdapp.parmFileDict = {};
	
	// This object holds data for editing the input parameters
	$scope.ecdapp.editRequest = {
		deployment_id: '',
		type_id: '',
		fileModel: null,
		resourceConstants: {},
		resourceDefinitionChanges: {}
	};
	//First get the blueprintId associated with the deployment, along with the inputs of the deployment
	InventoryDeploymentService.getDeployment(message.deployment.deploymentRef, $scope.ecdapp.tenant).then(function(deployment) {
		if (deployment.items.length == 0) {
			$scope.ecdapp.errMsg = "404 - Deployment " + message.deployment.deploymentRef + " Not Found!";
			$log.error("InventoryDeploymentSerice.getDeployment failed: "
					+ $scope.ecdapp.errMsg);
			$scope.ecdapp.isRequestFailed = true;
		} 
		// Deployment IDs are unique, so this will always return exactly one item!
		else {
			$scope.ecdapp.errMsg = null;
			$scope.ecdapp.deployment = deployment.items[0];
			$scope.ecdapp.isRequestFailed = false;
			$scope.ecdapp.editRequest.type_id = deployment.items[0].blueprint_id;
			$scope.ecdapp.parmFileDict = deployment.items[0].inputs;
			Object.keys($scope.ecdapp.parmFileDict).map(function(key, index) {
				if (key == 'config-format' || key == 'config-url' || key == 'chart-version' || key == 'chart-repo-url') {
					$scope.ecdapp.editRequest.resourceDefinitionChanges[key] = $scope.ecdapp.parmFileDict[key];
				} else {
					$scope.ecdapp.editRequest.resourceConstants[key] = $scope.ecdapp.parmFileDict[key];
				}
			});
		}
		$scope.ecdapp.isDataLoading = false;
	},
	function(error) {
		$log.error('InventoryDeploymentService.getDeployment failed: ' + JSON.stringify(error));
		$scope.ecdapp.isRequestFailed = true;
		$scope.ecdapp.errMsg = error;
		$scope.ecdapp.isDataLoading = false;
	});

	/**
	 * Validates content of user-editable fields.
	 * Returns null if all is well, 
	 * a descriptive error message otherwise.
	 */
	$scope.ecdapp.validateRequest = function(editRequest) {
		if (editRequest == null)
			return 'No data found.\nPlease enter some values.';
		if (editRequest["chart-version"] == null || editRequest["chart-version"].trim() == '')
			return 'Chart version is required.\nPlease enter a value.';
		if (!editRequest["config-format"] || editRequest["config-format"].trim() == '') {
			return 'config format is required.\nPlease enter a value.';
		}
		if (!editRequest["config-url"] || editRequest["config-url"].trim() == '') {
			return 'Config URL is required.\nPlease enter a value.';
		}
		return null;
	};
	
	/**
	 * Helm upgrade for deployment based on parameters user enters in modal popup
	 * First retrieves the node-id using the blueprintId
	 * Using the node-id and deploymentId, retrieves the node-instance-id
	 * Calls the start execution API, passing object with deploymentId, and changed parameters
	 */

	 $scope.ecdapp.upgradeWorkflow = function(resourceDefinitionChanges) {
	 	$scope.ecdapp.updatingDeployment = true;
	 	let nodeId = '';

	 	// validate request
	 	var validateMsg = $scope.ecdapp.validateRequest(resourceDefinitionChanges);
		if (validateMsg != null) {
			alert('Invalid Request:\n' + validateMsg);
			$scope.ecdapp.updatingDeployment = false;
			return;			
		}
	 	//get node id from blueprint
	 	InventoryDeploymentService.getBlueprint($scope.ecdapp.deploymentRef, $scope.ecdapp.tenant).then(function(blueprint) {
	 		if (debug)
				$log.debug("inventoryDeploymentUpgradeCtrl.getBlueprint response: " + JSON.stringify(blueprint));
			if  (blueprint.error) {
				$scope.ecdapp.errMsg = 'Request Failed: ' + blueprint.error;
				$scope.ecdapp.updatingDeployment = false;
			}
			else {
	 			//console.log('returned blueprint:' + blueprint);
	 			let count = 0;
	 			//console.log("number of node objects in array: " + blueprint.items[0].plan.nodes.length);
	 			//console.log(JSON.stringify(blueprint));
	 			blueprint.items[0].plan.nodes.map(function(node) {
	 				if (node.type == 'onap.nodes.component') {
	 					//want to get FIRST node with type 'cloudify.kubernetes.resources.Deployment' so only set nodeID for first matching type
	 					if (count < 1) {
	 						nodeId = node.id;
	 					}
	 					count = count + 1;
	 				}
	 			});
	 			//if no node has type 'cloudify.kubernetes.resources.Deployment', return message saying no deployment exists and exit (ie nodeId still is '')
	 			if (nodeId == '') {
	 				alert('Failed to retrieve Node Id. No matching deployment found (no deployment exists)');
	 				$scope.ecdapp.updatingDeployment = false;
	 				return;
	 			}
	 			//found node id. now need to retrieve node-instance-id
	 			console.log('%c RETRIEVED NODE ID: ' + nodeId, 'color: orange; font-weight: bold;');
	 			let nodeInstanceId = '';
	 			InventoryDeploymentService.getNodeInstanceId($scope.ecdapp.deploymentRef, nodeId, $scope.ecdapp.tenant).then(function(jsonObj) {
	 				if (debug)
	 					$log.debug("inventoryDeploymentUpgradeCtrl.getNodeInstanceId response: " + JSON.stringify(jsonObj));
	 				if (jsonObj.error) {
	 					$scope.ecdapp.errMsg = 'Request Failed: ' + jsonObj.error;
	 					$scope.ecdapp.updatingDeployment = false;
	 				}
	 				else {
	 					//if nodeInstanceId is still '' then it wasn't found, stop flow)
	 					if (jsonObj.items[0].id == '') {
	 						alert('Failed to retrieve Node Instance Id for Node Id' + nodeId);
	 						$scope.ecdapp.updatingDeployment = false;
	 						return;
	 					}
	 					//found node-instance-id. now need to update resources
	 					nodeInstanceId = jsonObj.items[0].id;
	 					console.log('%c RETRIEVED NODE INSTANCE ID:' + nodeInstanceId, 'color: green; font-weight: bold;');
	 					//console.log(resourceDefinitionChanges);
	 					InventoryDeploymentService.upgradeFlow($scope.ecdapp.deploymentRef, nodeInstanceId, resourceDefinitionChanges, $scope.ecdapp.tenant).then(function(jsonObj) {
	 						if (debug)
	 							$log.debug("inventoryDeploymentUpgradeCtrl.updateResources response: " + JSON.stringify(jsonObj));
	 						if (jsonObj.error) {
	 							$scope.ecdapp.errMsg = 'Request Failed: ' + jsonObj.error;
	 							$scope.ecdapp.updatingDeployment = false;
	 						}
	 						else {
	 							console.log('%c UPDATE RESOURCES COMPLETED', 'color: magenta; font-weight: bold;');
	 							//console.log(jsonObj);
	 							$scope.ecdapp.updatingDeployment = false;
	 							alert('Helm Upgrade request for ' + $scope.ecdapp.deploymentRef + ' successfully went through to Cloudiy. Upgrade is now pending');	 							$modalInstance.dismiss('cancel');
	 						}
	 					}, function(error) {
	 						$scope.ecdapp.updatingDeployment = false;
	 						alert('Failed to perform upgrade for Deployment Id ' + $scope.ecdapp.deploymentRef + ' and Node Instance Id ' + nodeInstanceId + '. Please retry.');
	 						$log.error('inventoryDeploymentUpgradeCtrl failed: ' + error);
	 					});
	 				}
	 			}, function(error) {
	 				$scope.ecdapp.updatingDeployment = false;
	 				alert('Failed to get Node Instance Id for deploymentId ' + $scope.ecdapp.deploymentRef + ' and Node Id ' + nodeId + '. Please retry.');
	 				$log.error('inventoryDeploymentUpgradeCtrl failed: ' + error);
	 			});

	 		}
	 	}, function(error) {
	 		$scope.ecdapp.updatingDeployment = false;
	 		alert('Failed to get blueprint for blueprintId ' + $scope.ecdapp.deploymentRef + '. Please retry.');
	 		$log.error('inventoryDeploymentUpgradeCtrl failed: ' + error);
	 	});
	 };

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
			if (! (ykey in $scope.ecdapp.parmFileDict))
				alert('Unexpected file content:\nKey not defined by blueprint:\n' + ykey);
			if (yval.constructor === {}.constructor)
				$scope.ecdapp.parmFileDict[ykey] = angular.toJson(yval);
			else
				$scope.ecdapp.parmFileDict[ykey] = yval;
		}
		if (debug)
			$log.debug('fileReader.onload: parmFileDict: ' + JSON.stringify($scope.ecdapp.parmFileDict));

		// Update table in all cases 
		//$scope.ecdapp.setResourceDefinitionChanges($scope.ecdapp.editRequest.resourceDefinitionChanges, $scope.ecdapp.editRequest.parmFileDict);
		Object.keys($scope.ecdapp.parmFileDict).map(function(key, index) {
			if (key == 'config-format' || key == 'config-url' || key == 'chart-version') {
				$scope.ecdapp.editRequest.resourceDefinitionChanges[key] = $scope.ecdapp.parmFileDict[key];
			} else {
				$scope.ecdapp.editRequest.resourceConstants[key] = $scope.ecdapp.parmFileDict[key];
			}
		});
		//$scope.$apply();
	};

	// Handler for file-select event
	$scope.handleFileSelect = function() {
		if (debug)
			$log.debug('handleFileSelect: $scope.ecdapp.fileModel.name is ' + $scope.ecdapp.editRequest.fileModel.name);
		fileReader.readAsText($scope.ecdapp.editRequest.fileModel);
	};

});