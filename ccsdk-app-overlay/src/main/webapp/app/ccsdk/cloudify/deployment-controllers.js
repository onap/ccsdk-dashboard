appDS2.controller('deploymentTableController', function(
		$rootScope, $scope, $log, $modal, $routeParams, DeploymentService, ControllerService,
		InventoryDeploymentService, localStorageService, $interval) {

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
	$scope.ecdapp.useCache = true;
	$scope.ecdapp.groupByTenant = false;
	$scope.ecdapp.filterByUser = true;
  $scope.ecdapp.cache_switch = {
      value: true
  };
  $scope.ecdapp.getTenants = function() {  
    ControllerService.getTenants()
    .then(function(jsonObj) {
      if (jsonObj.error) {
        $log.error("blueprintController.getTenants failed: " + jsonObj.error);
        $scope.ecdapp.isRequestFailed = true;
        $scope.ecdapp.errMsg = jsonObj.error;
        $scope.ecdapp.tableData = [];
      } else {
        var tenants = [];
        for (var tenIndx = 0; tenIndx < jsonObj.items.length; tenIndx++) {
          tenants.push(jsonObj.items[tenIndx].name);
        }
        $scope.ecdapp.availableTenants = tenants;
        if ($scope.ecdapp.availableTenants != undefined) {
          $scope.ecdapp.selectedTenant = $scope.ecdapp.availableTenants[0];
        }
        localStorageService.set('tenants', JSON.stringify(tenants));
      }     
    }, function(error) {
      $log.error("blueprintController.getTenants failed: " + error);
      $scope.ecdapp.isRequestFailed = true;
      $scope.ecdapp.errMsg = error;
    });
  };
  
  $scope.ecdapp.availableTenants = JSON.parse(localStorageService.get('tenants'));
  if ($scope.ecdapp.availableTenants == undefined) {
    $scope.ecdapp.getTenants();
  } else {
    //$scope.ecdapp.selectedTenant = $scope.ecdapp.availableTenants[0];
    $scope.ecdapp.selectedTenant = '';
  }
  // searching
  $scope.ecdapp.searchBy = $routeParams.depId;
  if ($scope.ecdapp.searchBy == undefined) {
    $scope.ecdapp.searchBy = "tenant:" + $scope.ecdapp.selectedTenant + ";" + 
    "cache:" + $scope.ecdapp.useCache + ";" +"owner:" + $scope.userId + ";";
    //+ "user:" + $scope.ecdapp.filterByUser + ";";
  } else {
    if ($scope.ecdapp.searchBy.includes("owner")) {
      if ($scope.ecdapp.searchBy.split(":")[1] === "group") {
        $scope.ecdapp.filterByUser = false;
        $scope.ecdapp.searchBy = "tenant:" + $scope.ecdapp.selectedTenant + ";" + 
        "cache:" + $scope.ecdapp.useCache + ";";
      }
    } else {   
      $scope.ecdapp.selectedTenant = $scope.ecdapp.searchBy.split(";")[0].split(":")[1]
    }
  }

  //$scope.ecdapp.selectedTenant = "default_tenant";
  //if ($scope.ecdapp.availableTenants != undefined) {
   // $scope.ecdapp.selectedTenant = $scope.ecdapp.availableTenants[0];
  //}
  
  // sorting
  $scope.ecdapp.sortBy = null;

  $scope.ecdapp.isDisabled = false;
  $scope.ecdapp.isDetailView = false;
  $scope.ecdapp.isHelmType = false;
  $scope.ecdapp.aafUsernameString;
  $scope.ecdapp.dcaeTargetTypeString;
  $scope.ecdapp.selectedSrvc;
  $scope.ecdapp.usingAafFilter = false;
  $scope.ecdapp.usingDcaeTargetTypeFilter = false;
  $scope.ecdapp.selectedOwner;
  $scope.ecdapp.availableServices; // = JSON.parse(localStorageService.get('deplNames'));
  $scope.ecdapp.selectedServices;
  $scope.ecdapp.bpOwners = JSON.parse(localStorageService.get('bpOwners'));

  $scope.ecdapp.availableStatus = 
    ['pending','started','cancelling','force_cancelling','cancelled','terminated','failed'];
  $scope.ecdapp.selectedStatus;

  $scope.ecdapp.showingMoreFilters = false;

  $scope.ecdapp.toggleMoreFilters = function() {      
    $scope.ecdapp.showingMoreFilters = !$scope.ecdapp.showingMoreFilters;
  };

  
    // Handler for disabling non aaf/dcae target type search fields
    $scope.ecdapp.handleDisableOtherFields = function() {
      //If using aaf filter, disable others
      //and clear the dcae target type field
      if ($scope.ecdapp.aafUsernameString == '' || typeof $scope.ecdapp.aafUsernameString == "undefined")
        $scope.ecdapp.usingAafFilter = false;
      else {
        $scope.ecdapp.usingAafFilter = true;
        $scope.ecdapp.dcaeTargetTypeString = '';
      }
        
      //If using dcae target type filter, disable others
      //and clear the aaf filter field
      if ($scope.ecdapp.dcaeTargetTypeString == '' || typeof $scope.ecdapp.dcaeTargetTypeString == "undefined")
          $scope.ecdapp.usingDcaeTargetTypeFilter = false;
      else {
        $scope.ecdapp.usingDcaeTargetTypeFilter = true;
        $scope.ecdapp.aafUsernameString = '';
      }
    };

    // get the blueprints summary list
    $scope.ecdapp.bp = [];
    $scope.ecdapp.getAllAuthBpList = function() {
      InventoryBlueprintService.getBlueprintsSummary()
      .then(function(jsonObj) {
        if (jsonObj.error) {
          $log.error("inventoryDeploymentUpdateCtrl.loadTable failed: " + jsonObj.error);
          $scope.ecdapp.errMsg = jsonObj.error;
          $scope.ecdapp.bp = [];
        } else {
          $scope.ecdapp.errMsg = null;
          $scope.ecdapp.bp = jsonObj;
        }     
        $scope.ecdapp.isDataLoading = false;
      }, function(error) {
        $log.error("inventoryDeploymentUpdateCtrl.loadTable failed: " + error);
        $scope.ecdapp.errMsg = error;
        $scope.ecdapp.bp = [];
        $scope.ecdapp.isDataLoading = false;
      });
    };
    
  $scope.ecdapp.getDeploymentsList = function() {
    InventoryDeploymentService.getDeploymentList($scope.ecdapp.searchBy).then(
        function(jsonObj) {
          if (jsonObj.error) {
            $log.error("inventoryDeploymentController.getDeploymentList failed: "
                + jsonObj.error);
            $scope.ecdapp.availableServices = [];
          } else {
            //localStorageService.set('deplNames', JSON.stringify(jsonObj));
            $scope.ecdapp.availableServices = jsonObj;
              //JSON.parse(localStorageService.get('deplNames'));
          }
        },
        function(error) {
          $log.error("inventoryDeploymentController.getDeploymentList failed: "
              + error);
          $scope.ecdapp.availableServices = [];
        });
  };
  
  $scope.ecdapp.reloadTable = function() {
    $scope.ecdapp.currentPageNum = 1;
    $scope.ecdapp.searchBy = "tenant:" + $scope.ecdapp.selectedTenant + ";" 
    +"cache:" + $scope.ecdapp.useCache + ";";
    //+ "user:" + $scope.ecdapp.filterByUser + ";"; 
    if ($scope.ecdapp.filterByUser) {
      $scope.ecdapp.searchBy += "owner:" + $scope.userId + ";";
    }
    $scope.ecdapp.searchString = '';
    $scope.ecdapp.resetFilters();
    $scope.ecdapp.loadTable();
  };
  
  $scope.ecdapp.resetFilters = function() {
    $scope.ecdapp.selectedServices = '';
    $scope.ecdapp.selectedTenants = '';
    $scope.ecdapp.selectedStatus = '';
    $scope.ecdapp.selectedOwner = '';
    $scope.ecdapp.isHelmType = false;
    $scope.ecdapp.searchString = '';
    $scope.ecdapp.searchBy = "tenant:" + $scope.ecdapp.selectedTenant + ";"
    +"cache:" + $scope.ecdapp.useCache + ";";
    if ($scope.ecdapp.filterByUser) {
      $scope.ecdapp.searchBy += "owner:" + $scope.userId + ";";
    }
    /*
    $scope.ecdapp.searchBy = "tenant:" + $scope.ecdapp.selectedTenant + ";"
    +"cache:" + $scope.ecdapp.useCache + ";"+ "user:" + $scope.ecdapp.filterByUser + ";";
    */
  };
  $scope.ecdapp.filterBySvc = function() {
    if ( typeof $scope.ecdapp.searchString != "undefined" && 
        $scope.ecdapp.searchString != '') {     
      if ($scope.ecdapp.searchString.includes("serviceRef:") ||
          $scope.ecdapp.searchString.includes("tenant:") ||
          $scope.ecdapp.searchString.includes("status:") ||
          $scope.ecdapp.searchString.includes("helm:")) {
        $scope.ecdapp.searchBy = $scope.ecdapp.searchString +";cache:" + $scope.ecdapp.useCache + ";";
         // + "user:" + $scope.ecdapp.filterByUser + ";";
      } else {
        $scope.ecdapp.searchBy = "tenant:" + $scope.ecdapp.selectedTenant + ";"
        $scope.ecdapp.searchBy += 'serviceRef:' + $scope.ecdapp.searchString +";cache:" + $scope.ecdapp.useCache + ";";
          //+ "user:" + $scope.ecdapp.filterByUser + ";";
      }
      if ($scope.ecdapp.filterByUser) {
        $scope.ecdapp.searchBy += "owner:" + $scope.userId + ";";
      }
      $scope.ecdapp.searchTable();
    }
  };

  $scope.ecdapp.extendedfilterSrch = function() {
    /*
    $scope.ecdapp.searchBy = "tenant:" + $scope.ecdapp.selectedTenant +  ";" +
    "cache:" + $scope.ecdapp.useCache + ";" + "user:" + $scope.ecdapp.filterByUser + ";";
     * 
     */
    $scope.ecdapp.searchBy = "tenant:" + $scope.ecdapp.selectedTenant +  ";" +
    "cache:" + $scope.ecdapp.useCache + ";";
    
    if ( typeof $scope.ecdapp.selectedServices != "undefined" && 
      $scope.ecdapp.selectedServices != '') {
      var svcFilterStr = 'serviceRef:' + $scope.ecdapp.selectedServices.toString();
      $scope.ecdapp.searchBy += svcFilterStr + ";"
    }  
    if ( typeof $scope.ecdapp.selectedOwner != "undefined" &&
        $scope.ecdapp.selectedOwner != '') {
      var ownerFilterStr = 'owner:' + $scope.ecdapp.selectedOwner.toString();
      $scope.ecdapp.searchBy +=  ownerFilterStr + ';'
    }
    if ( typeof $scope.ecdapp.selectedStatus != "undefined" &&
        $scope.ecdapp.selectedStatus != '') {
      var statusFilterStr = 'status:' + $scope.ecdapp.selectedStatus.toString();
      $scope.ecdapp.searchBy +=  statusFilterStr + ';'
    }
    if ( typeof $scope.ecdapp.isHelmType != "undefined" &&
        $scope.ecdapp.isHelmType == true) {
      var helmFilterStr = 'helm:' + $scope.ecdapp.isHelmType;
      $scope.ecdapp.searchBy +=  helmFilterStr + ';'
    }     
  
    if ($scope.ecdapp.filterByUser) {
      $scope.ecdapp.searchBy += "owner:" + $scope.userId + ";";
    }
    //If using AAF username filter, ignore other fields and do filtered search.
    if ($scope.ecdapp.usingAafFilter) {
          var aafFilterStr = 'aafUsername:' + $scope.ecdapp.aafUsernameString.toString();
          $scope.ecdapp.searchBy = $scope.ecdapp.aafUsernameString.toString();
          $scope.ecdapp.searchString = aafFilterStr + ';';
          $scope.ecdapp.searchTableAafFilter();
        }
    else if ($scope.ecdapp.usingDcaeTargetTypeFilter) {
        var dcaeTargetTypeFilterStr = 'dcaeTargetType:' + $scope.ecdapp.dcaeTargetTypeString.toString();
        $scope.ecdapp.searchBy = $scope.ecdapp.dcaeTargetTypeString.toString();
        $scope.ecdapp.searchString = dcaeTargetTypeFilterStr + ';';
        $scope.ecdapp.searchTableDcaeTargetTypeFilter();
      }
    else {
      $scope.ecdapp.searchString = $scope.ecdapp.searchBy;
      $scope.ecdapp.searchTable();
    }  
  };

  $scope.ecdapp.sortTable = function(sortBy) {
    $scope.ecdapp.isDataLoading = true;
    $scope.ecdapp.sortBy = sortBy;
    DeploymentService.getDeployments($scope.ecdapp.currentPageNum,
        $scope.ecdapp.viewPerPage, $scope.ecdapp.sortBy, $scope.ecdapp.searchBy).then(
            function(jsonObj) {
              if (jsonObj.error) {
                $log.error("DeploymentController.sortTable failed: "
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
              $log.error("DeploymentController.sortTable failed: "
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
  $scope.ecdapp.searchTable = function() {
    $scope.ecdapp.isDataLoading = true;
    $scope.ecdapp.showingMoreFilters = false;
    if ($scope.ecdapp.currentPageNum != 1) {
      $scope.ecdapp.currentPageNum = 1;
    } else {
      $scope.ecdapp.stopLoading();
      DeploymentService.getDeployments($scope.ecdapp.currentPageNum,
        $scope.ecdapp.viewPerPage, $scope.ecdapp.sortBy, $scope.ecdapp.searchBy).then(
            function(jsonObj) {
              stop = $interval( function(){ $scope.ecdapp.loadTable(); }, 180000, 100, false);
              $scope.ecdapp.searchString = $scope.ecdapp.searchBy;
              if (jsonObj.error) {
                $log.error("DeploymentController.loadTable failed: "
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
              $scope.ecdapp.searchString = $scope.ecdapp.searchBy;
              $scope.ecdapp.searchBy = '';
              $log.error("DeploymentController.loadTable failed: "
                  + error);
              $scope.ecdapp.isRequestFailed = true;
              $scope.ecdapp.errMsg = error;
              $scope.ecdapp.tableData = [];
              $scope.ecdapp.isDataLoading = false;
            });
    }
  };

  /**
   * Loads the table with AAF Filter. Interprets the remote controller's response and copies
   * to scope variables. The response is either a list to be assigned to
   * tableData, or an error to be shown.
   */
  $scope.ecdapp.searchTableAafFilter = function() {
    $scope.ecdapp.isDataLoading = true;
    $scope.ecdapp.showingMoreFilters = false;
    if ($scope.ecdapp.currentPageNum != 1) {
      $scope.ecdapp.currentPageNum = 1;
    } else {
      $scope.ecdapp.stopLoading();
    InventoryDeploymentService.getDeploymentsAafFilter($scope.ecdapp.currentPageNum,
        $scope.ecdapp.viewPerPage, $scope.ecdapp.searchBy).then(
            function(jsonObj) {
              stop = $interval( function(){ $scope.ecdapp.loadTable(); }, 180000, 100, false);
              $scope.ecdapp.searchString = $scope.ecdapp.searchBy;
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
                //$scope.ecdapp.updateTable();
              }
              $scope.ecdapp.isDataLoading = false;
            },
            function(error) {
              $scope.ecdapp.searchString = $scope.ecdapp.searchBy;
              $scope.ecdapp.searchBy = '';
              $log.error("inventoryDeploymentController.loadTable failed: "
                  + error);
              $scope.ecdapp.isRequestFailed = true;
              $scope.ecdapp.errMsg = error;
              $scope.ecdapp.tableData = [];
              $scope.ecdapp.isDataLoading = false;
            });
    }
  };
  
  /**
   * Loads the table with Dcae target type Filter. Interprets the remote controller's response and copies
   * to scope variables. The response is either a list to be assigned to
   * tableData, or an error to be shown.
   */
  $scope.ecdapp.searchTableDcaeTargetTypeFilter = function() {
    $scope.ecdapp.isDataLoading = true;
    $scope.ecdapp.showingMoreFilters = false;
    if ($scope.ecdapp.currentPageNum != 1) {
      $scope.ecdapp.currentPageNum = 1;
    } else {
      $scope.ecdapp.stopLoading();
    InventoryDeploymentService.getDeploymentsDcaeTargetTypeFilter($scope.ecdapp.currentPageNum,
        $scope.ecdapp.viewPerPage, $scope.ecdapp.searchBy).then(
            function(jsonObj) {
              stop = $interval( function(){ $scope.ecdapp.loadTable(); }, 180000, 100, false);
              $scope.ecdapp.searchString = $scope.ecdapp.searchBy;
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
                //$scope.ecdapp.updateTable();
              }
              $scope.ecdapp.isDataLoading = false;
            },
            function(error) {
              $scope.ecdapp.searchString = $scope.ecdapp.searchBy;
              $scope.ecdapp.searchBy = '';
              $log.error("inventoryDeploymentController.loadTable failed: "
                  + error);
              $scope.ecdapp.isRequestFailed = true;
              $scope.ecdapp.errMsg = error;
              $scope.ecdapp.tableData = [];
              $scope.ecdapp.isDataLoading = false;
            });
    }
  }; 
  $scope.ecdapp.stopLoading = function() {
    if (angular.isDefined(stop)) {
      $interval.cancel(stop);
      stop = undefined;
    }
  };
  
  $scope.ecdapp.toggleRefresh = function() {
    if ($scope.ecdapp.useCache === true) {
        stop = $interval( function(){ $scope.ecdapp.loadTable(); }, 180000, 100, false);
      } else {
        $scope.ecdapp.stopLoading();
      }
  };

  if ($scope.ecdapp.useCache === true) {
    stop = $interval( function(){ $scope.ecdapp.loadTable(); }, 180000, 100, false);
  }
  
  $scope.ecdapp.toggleUserFilt = function() {
    if ($scope.ecdapp.filterByUser === true) {
      $scope.ecdapp.groupByTenant = false;
      $scope.ecdapp.selectedTenant = '';
    } else {
      $scope.ecdapp.searchString = '';
    }
    $scope.ecdapp.reloadTable();
  }
  
  $scope.ecdapp.toggleTenantFilt = function() {
    if ($scope.ecdapp.groupByTenant  === false) {
      $scope.ecdapp.selectedTenant = '';
    }
  }
	/**
	 * Loads the table. Interprets the remote controller's response and copies
	 * to scope variables. The response is either a list to be assigned to
	 * tableData, or an error to be shown.
	 */
	$scope.ecdapp.loadTable = function(sortBy, searchBy) {
		$scope.ecdapp.isDataLoading = true;
    //$scope.ecdapp.searchString = '';
    $scope.ecdapp.sortBy = sortBy;
		DeploymentService.getDeployments($scope.ecdapp.currentPageNum,
				$scope.ecdapp.viewPerPage, $scope.ecdapp.sortBy, $scope.ecdapp.searchBy).then(
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
          var loc = location.pathname;
          var loc1 = loc.replace("/", "");
          var loc2 = loc1.replace("/ecd", "/login.htm");
          alert("Session expired - Sign in again");
          location.replace("/"+loc2);
				});
      $scope.ecdapp.getDeploymentsList();
	};

	/**
	 * Invoked at first page load AND when
	 * user clicks on the B2B pagination control. 
	 */
	$scope.pageChangeHandler = function(page) {
		// console.log('pageChangeHandler: current is ' + $scope.ecdapp.currentPageNum + ' new is ' + page);
		$scope.ecdapp.currentPageNum = page;
		//$scope.ecdapp.searchBy = "tenant:" + $scope.ecdapp.selectedTenant + ";"; 
    $scope.ecdapp.loadTable($scope.ecdapp.sortBy, $scope.ecdapp.searchBy);
	}
	
	$scope.ecdapp.tenantChangeHandler = function() {
	  $scope.ecdapp.currentPageNum = 1;
    $scope.ecdapp.searchBy = "tenant:" + $scope.ecdapp.selectedTenant + ";"
    +"cache:" + $scope.ecdapp.useCache + ";";
    //+ "user:" + $scope.ecdapp.filterByUser + ";"; 
    if ($scope.ecdapp.filterByUser) {
      $scope.ecdapp.searchBy += "owner:" + $scope.userId + ";";
    }
    $scope.ecdapp.searchString = $scope.ecdapp.searchBy;
    $scope.ecdapp.loadTable($scope.ecdapp.sortBy, $scope.ecdapp.searchBy);	  
	}
	
  //$scope.ecdapp.getTenants();
  
  /**
   * modal pop-up for app reconfig operation
   * 
   */
  $scope.ecdapp.reconfigDeploymentModalPopup = function(deployment) {
    var modalInstance = $modal.open({
      templateUrl : 'app_reconfig_view_popup.html',
      controller : 'appReconfigCtrl',
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
   * Shows a modal pop-up to confirm deletion. 
   * On successful completion, updates the table.
   */
  $scope.ecdapp.deleteDeploymentModalPopup = function(deployment) { 
    deployment.onlyLatest = true;
    var modalInstance = $modal.open({
      templateUrl : 'inventory_deployment_delete_popup.html',
      controller : 'deploymentDeleteCtrl',
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
      controller : 'deploymentExecutionsViewCtrl',
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

	
  $scope.ecdapp.viewDeploymentInputsModalPopup = function(deployment) {
    var modalInstance = $modal.open({
      templateUrl : 'inventory_deployment_inputs_view_popup.html',
      controller : 'deploymentInputsViewCtrl',
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
   * Shows a modal pop-up to initiate update blueprint for a deployment
   */
  $scope.ecdapp.updateDeploymentModalPopup = function(deployment) {
    var modalInstance = $modal.open({
      templateUrl: 'inventory_deployment_update_popup.html',
      controller: 'deploymentUpdateCtrl',
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
      if (response != null) {
        if (response.error != null) {
          $log.error('updateDeploymentModalPopup failed: ' + response.error);
          alert('Failed to update deployment:\n' + response.error);
        }
        else {
          $scope.ecdapp.viewDeploymentExecutionsModalPopup(deployment);
        }
      }
    });
  };

  /**
   * Shows a modal pop-up with blueprint content.
   * Passes data in via an object named "message". 
   */
  $scope.ecdapp.getBlueprintDataModal = function(deployment) {
    var modalInstance = $modal.open({
      templateUrl : 'blueprint_content_popup.html',
      controller : 'deployBlueprintContentCtrl',
      windowClass: 'modal-docked',
      sizeClass: 'modal-jumbo',
      resolve : {
        message : function() {
          var dataForPopup = {
              blueprint : deployment,
              tenant_name: $scope.ecdapp.selectedTenant
          };
          return dataForPopup;
        }
      }
    });
    modalInstance.result.then(function(response) {
    });
  };

  
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
						deployment : deployment,
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
	
  $scope.ecdapp.checkHelmStatus = function(deployment) {
    var selTenant = deployment.tenant_name;
    if ( typeof selTenant === "undefined" ) {
      selTenant = "default_tenant";
    }
    deployment.onlyLatest = true;

    // This object holds data for this operation
    $scope.ecdapp.helmStatusRequest = {
        "deployment_id": deployment.id,
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
        alert('Request Failed: ' + jsonObj.error);
      } else {
        console.log('%c POSTED helm status request', 'color: magenta; font-weight: bold;');
        $scope.ecdapp.viewDeploymentExecutionsModalPopup(deployment);
      }
    }, function(error) {
      $log.error('helmStatusFlow failed: ' + error);
      alert('helmStatusFlow failed: ' + error);
    });

  };
  
  /**
   * Shows a modal pop-up to initiate helm upgrade for a deployment
   */
  $scope.ecdapp.upgradeDeploymentModalPopup = function(deployment) {
    //console.log(deployment);
    var modalInstance = $modal.open({
      templateUrl: 'inventory_deployment_upgrade_popup.html',
      controller: 'deploymentUpgradeCtrl',
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
   * Shows a modal pop-up to initiate helm rollback for a deployment
   */
  $scope.ecdapp.rollbackDeploymentModalPopup = function(deployment) {
    var modalInstance = $modal.open({
      templateUrl: 'inventory_deployment_rollback_popup.html',
      controller: 'deploymentRollbackCtrl',
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

  $scope.$on('$destroy', function() {
    // Make sure that the interval is destroyed too
    $scope.ecdapp.stopLoading();
  });
  
});

/*************************************************************************/

appDS2.controller('deploymentDeleteCtrl', function(
    $scope, $rootScope, $log, $modalInstance, message, InventoryDeploymentService) {

  'use strict';

  // Controls logging in this controller
  var debug = false;

  // this object holds all app data and functions
  $scope.ecdapp = {};
  $scope.ecdapp.label = 'Undeploy?';
  $scope.ecdapp.deploymentRef = message.deployment.id;
  var selTenant = message.deployment.tenant_name;
  $scope.ecdapp.ui_tenant = selTenant;
  $scope.ecdapp.tenant = selTenant;

  $scope.ecdapp.deleteDeploymentById = function(){
    $scope.ecdapp.isDisabled = true;
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

appDS2.controller('deploymentExecutionsViewCtrl', function(
    $scope, $rootScope, $interval, $log, $modalInstance, message, modalService, InventoryExecutionService, ExecutionService) {

  'use strict';

  var debug = false;

  if (debug)
    $log.debug("inventoryDeploymentsExecutionsViewCtrl.message: " + JSON.stringify(message));

  // this object holds all app data and functions
  $scope.ecdapp = {};
  // models for controls on screen
  $scope.ecdapp.label = 'Deployment ' + message.deployment.id + ' Executions';
  $scope.ecdapp.tableData = [];
  $scope.ecdapp.logTableData = [];
  $scope.ecdapp.currentPageNum = 1;
  $scope.ecdapp.viewPerPage = 10;
  $scope.ecdapp.currentLogPageNum = 1;
  $scope.ecdapp.selectedRow = null;

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
  var selTenant = message.deployment.tenant_name;

  $scope.ecdapp.ui_tenant = selTenant;
  $scope.ecdapp.tenant = selTenant;
  $scope.ecdapp.execId = "";
  $scope.ecdapp.deplRef = message.deployment.id;
  var stop;
  /**
   * Loads the table. Interprets the remote controller's response and copies
   * to scope variables. The response is either a list to be assigned to
   * tableData, or an error to be shown.
   */
  $scope.ecdapp.loadTable = function() {
    $scope.ecdapp.isDataLoading = true;
    InventoryExecutionService.getExecutionsByDeployment($scope.ecdapp.deplRef, 
        $scope.ecdapp.tenant,
        $scope.ecdapp.currentPageNum,
        $scope.ecdapp.viewPerPage).then(
            function(jsonObj) {
              if (jsonObj.error) {
                $log.error("deploymentExecutionsViewCtrl.loadTable failed: "
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
                      $scope.ecdapp.execId = exec_id;
                      $scope.ecdapp.selectedRow = resultLen-1;
                  if ($scope.ecdapp.isLastExecution) {
                    $scope.ecdapp.tableData = [];
                    $scope.ecdapp.tableData.push(jsonObj.items[resultLen-1]);
                  } else {
                    $scope.ecdapp.tableData = jsonObj.items;
                  }
                  $scope.ecdapp.getExecutionLogs($scope.ecdapp.selectedRow, exec_id, $scope.ecdapp.tenant);
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

    $scope.ecdapp.copyStringToClipboard = function(str) {
       // Create new element
       var el = document.createElement('textarea');
       // Set value (string to be copied)
       el.value = str;
       // Set non-editable to avoid focus and move outside of view
       el.setAttribute('readonly', '');
       el.style = {position: 'absolute', left: '-9999px'};
       document.body.appendChild(el);
       // Select text inside element
       el.select();
       // Copy text to clipboard
       document.execCommand('copy');
       // Remove temporary element
       document.body.removeChild(el);
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
  $scope.pageChangeHandlerEvent = function(page) {
    if (debug)
      console.log('pageChangeHandlerEvent: current is ' + $scope.ecdapp.currentLogPageNum + ' new is ' + page);
      if (page != $scope.ecdapp.currentLogPageNum ) {
      $scope.ecdapp.currentLogPageNum = page;
      $scope.ecdapp.getExecutionLogs($scope.ecdapp.selectedRow, $scope.ecdapp.execId, $scope.ecdapp.tenant);
    }
  }

  $scope.ecdapp.setClickedRow = function(index){  //function that sets the value of selectedRow to current index
     $scope.ecdapp.selectedRow = index;
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

  $scope.ecdapp.getExecutionLogs = function(rowIdx, id, tenant) {
    $scope.ecdapp.setClickedRow(rowIdx);
    $scope.ecdapp.execId = id;
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
appDS2.controller('deploymentInputsViewCtrl', function(
    $scope, $rootScope, $log, $modalInstance, message, InventoryDeploymentService) {

  'use strict';

  // Controls logging in this controller
  var debug = false;

  // this object holds all app data and functions
  $scope.ecdapp = {};
  $scope.ecdapp.label = 'Deployment Inputs';
  $scope.ecdapp.deploymentRef = message.deployment.id;
  $scope.ecdapp.serviceId = message.deployment.serviceId;
  $scope.ecdapp.errMsg = null;
  $scope.ecdapp.isDataLoading = true;
  $scope.ecdapp.isRequestFailed = false;
  var selTenant = message.deployment.tenant_name;
  if ( typeof selTenant === "undefined" ) {
    selTenant = "default_tenant";
  }
  $scope.ecdapp.ui_tenant = selTenant;
  $scope.ecdapp.tenant = selTenant;
  let inputsAndDescriptions = {};
  let inputsAndDefaults = {};
  // Get associated blueprint for inputs info
  InventoryDeploymentService.getBlueprint($scope.ecdapp.deploymentRef, $scope.ecdapp.tenant).then(function(blueprint) {
    if (debug)
      $log.debug("inventoryDeploymentInputsViewCtrl.getBlueprint response: " + JSON.stringify(blueprint));
    if  (!blueprint.error) {
      for (var pkey in blueprint.items[0].plan.inputs) {
        let description = blueprint.items[0].plan.inputs[pkey].description;
        if (  typeof description === "undefined" ) {
          description = '';
        }
        inputsAndDescriptions[pkey] = description;
      }
      $scope.ecdapp.deployment.descriptionDict = inputsAndDescriptions;
    }
  },
  function(error) {
    $log.error('InventoryDeploymentService.getBlueprint failed: ' + JSON.stringify(error));
  }); 
      
  InventoryDeploymentService.getDeployment($scope.ecdapp.deploymentRef, $scope.ecdapp.tenant).then(function(deployment) {
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
      //$scope.ecdapp.deployment = deployment.items[0];
      let ydict = deployment.items[0].inputs; 
      for (var ykey in ydict) { 
        let yval = ydict[ykey]; 
        if (yval.constructor === {}.constructor) 
          inputsAndDefaults[ykey] = JSON.stringify(yval, undefined, 2);
        else 
          inputsAndDefaults[ykey] = yval;
      }
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
  $scope.ecdapp.deployment = {
      parmFileDict : inputsAndDefaults,
      descriptionDict : inputsAndDescriptions
  };
});

/*************************************************************************/

appDS2.controller('deployBlueprintContentCtrl', function(
    $scope, $log, message, DeploymentService) {

  'use strict';

  var debug = false;

  if (debug)
    $log.debug("deployBlueprintContentCtrl.message: " + JSON.stringify(message));

  // this object holds all app data and functions
  $scope.ecdapp = {};
  $scope.ecdapp.label = 'View Blueprint ' + message.blueprint.blueprint_id;
  // Fetch the blueprint
  $scope.ecdapp.isDataLoading = true;
  DeploymentService.getBlueprintContent(message.blueprint.blueprint_id, message.tenant_name).then(function(jsonObj) {
    if (debug)
      $log.debug("deployBlueprintContentCtrl.viewBlueprint response: " + JSON.stringify(jsonObj));
    if (jsonObj.error) {
      $scope.ecdapp.errMsg = 'Request Failed';
    }
    else {
      $scope.ecdapp.typeName = message.blueprint.blueprint_id;
      $scope.ecdapp.blueprint = jsonObj.data;
    }
    $scope.ecdapp.isDataLoading = false;
  }, function(error) {
    $scope.ecdapp.isDataLoading = false;
    alert('Failed to get blueprint. Please retry.');
    $log.error("deployBlueprintContentCtrl failed: " + error);
  });
});


	/*************************************************************************/
	appDS2.controller('deploymentUpdateCtrl', function(
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
	  $scope.ecdapp.label = 'Update ' + message.deployment.id;
	  $scope.ecdapp.deploymentRef = message.deployment.id;
	  var selTenant = message.deployment.tenant_name;
	  if ( typeof selTenant === "undefined" ) {
	    selTenant = "default_tenant";
	  }
	  $scope.ecdapp.tenant = selTenant;
	  $scope.ecdapp.ui_tenant = selTenant;
	  $scope.ecdapp.updateBp = '';
	  $scope.ecdapp.typeName = '';
	  $scope.ecdapp.typeId = '';
	  $scope.ecdapp.inputsDict = {};
	  $scope.ecdapp.nodeInst = [];
	  $scope.ecdapp.selectedNodeInst;
	  $scope.ecdapp.editRequest = {
	      deployment_id : $scope.ecdapp.deploymentRef,
	      type_id : '',
	      fileModel : null,
	      parmFileDict : {},
	      descriptionDict : {},
	      tenant : $scope.ecdapp.tenant
	  };
	  $scope.ecdapp.install_flow = {
	      value: true
	  };
	    
	  $scope.ecdapp.uninstall_flow = {
	      value: true
	  };
	  
	  $scope.ecdapp.reinstall_flow = {
	      value: true
	  };
	  
	  $scope.ecdapp.install_first_flow_flag = {
	      value: false
	  };
	  
	  $scope.ecdapp.force_flag = {
	      value: false
	  };
	  
	  $scope.ecdapp.ignore_failure_flag = {
	      value: false
	  };

	  $scope.ecdapp.skip_install = false;
	  $scope.ecdapp.skip_uninstall = false;
	  $scope.ecdapp.skip_reinstall = false;
	  $scope.ecdapp.install_first_flow = false;
	  $scope.ecdapp.force = false;
	  $scope.ecdapp.ignore_failure = false;
	  
	  $scope.$watch('ecdapp.install_flow["value"]', function(newValue,oldValue,scope) {
	    if (newValue != oldValue) {
	      console.log("toggled install flow: " + newValue);
	      $scope.ecdapp.skip_install = oldValue;
	    }
	  }, true);
	  
	  $scope.$watch('ecdapp.uninstall_flow["value"]', function(newValue,oldValue,scope) {
	    if (newValue != oldValue) {
	      console.log("toggled uninstall flow: " + newValue);
	      $scope.ecdapp.skip_uninstall = oldValue;
	    }
	  }, true);
	  
	  $scope.$watch('ecdapp.reinstall_flow["value"]', function(newValue,oldValue,scope) {
	    if (newValue != oldValue) {
	      console.log("toggled reinstall flow: " + newValue);
	      $scope.ecdapp.skip_reinstall = oldValue;
	    }
	  }, true);
	  
	  $scope.$watch('ecdapp.install_first_flow_flag["value"]', function(newValue,oldValue,scope) {
	    if (newValue != oldValue) {
	      console.log("toggled install first flow: " + newValue);
	      $scope.ecdapp.install_first_flow = newValue;
	    }
	  }, true);
	  
	  $scope.$watch('ecdapp.force_flag["value"]', function(newValue,oldValue,scope) {
	    if (newValue != oldValue) {
	      console.log("toggled force flag: " + newValue);
	      $scope.ecdapp.force = newValue;
	    }
	  }, true);
	  
	  $scope.$watch('ecdapp.ignore_failure_flag["value"]', function(newValue,oldValue,scope) {
	    if (newValue != oldValue) {
	      console.log("toggled ignore_failure flow: " + newValue);
	      $scope.ecdapp.ignore_failure = newValue;
	    }
	  }, true);

	  // deployment node instances
	  InventoryDeploymentService.getNodeInstances($scope.ecdapp.deploymentRef, $scope.ecdapp.tenant)
	  .then(function(jsonObj) {
	    if (jsonObj.error) {
	      $log.error("inventoryDeploymentUpdateCtrl.getNodeInstances failed: " + jsonObj.error);
	      $scope.ecdapp.errMsg = jsonObj.error;
	      $scope.ecdapp.nodeInst = [];
	    } else {
	      $scope.ecdapp.errMsg = null;
	      for (var nodeIndx = 0; nodeIndx < jsonObj.items.length; nodeIndx++) {
	        $scope.ecdapp.nodeInst.push(jsonObj.items[nodeIndx].id);
	      }
	    }     
	  }, function(error) {
	    $log.error("inventoryDeploymentUpdateCtrl.getNodeInstances failed: " + error);
	    $scope.ecdapp.errMsg = error;
	    $scope.ecdapp.nodeInst = [];
	    $scope.ecdapp.isDataLoading = false;
	  });    

	  // current blueprint
/*	  var typeLink = message.deployment.typeLink.href;
	  var n = typeLink.lastIndexOf("/");
	  var typeId = typeLink.substring(n+1);
	  // Fetch the blueprint
	  $scope.ecdapp.isDataLoading = true;
	  InventoryBlueprintService.viewBlueprint(typeId).then(function(jsonObj) {
	    if (debug)
	      $log.debug("inventoryDeploymentUpdateCtrl.viewBlueprint response: " + JSON.stringify(jsonObj));
	    if (jsonObj.error) {
	      $scope.ecdapp.errMsg = 'Request Failed';
	    }
	    else {
	      $scope.ecdapp.typeName = jsonObj.typeName;
	      $scope.ecdapp.typeId = jsonObj.typeId;
	      $scope.ecdapp.bp.push(jsonObj);
	      $scope.ecdapp.getBlueprint();
	    }
	    //$scope.ecdapp.isDataLoading = false;
	  }, function(error) {
	    $scope.ecdapp.isDataLoading = false;
	    alert('Failed to get blueprint. Please retry.');
	    $log.error("blueprintViewCtrl failed: " + error);
	  });*/

	  // get the blueprints from inventory matching deployment reference filter
	  $scope.ecdapp.bp = [];
	  InventoryBlueprintService.getBlueprintsSummary()
	  .then(function(jsonObj) {
	    if (jsonObj.error) {
	      $log.error("inventoryDeploymentUpdateCtrl.loadTable failed: " + jsonObj.error);
	      $scope.ecdapp.errMsg = jsonObj.error;
	      $scope.ecdapp.bp = [];
	    } else {
	      $scope.ecdapp.errMsg = null;
	      $scope.ecdapp.bp = jsonObj;
	    }     
	    $scope.ecdapp.isDataLoading = false;
	  }, function(error) {
	    $log.error("inventoryDeploymentUpdateCtrl.loadTable failed: " + error);
	    $scope.ecdapp.errMsg = error;
	    $scope.ecdapp.bp = [];
	    $scope.ecdapp.isDataLoading = false;
	  });
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
	        InventoryDeploymentService.getDeployment($scope.ecdapp.deploymentRef, $scope.ecdapp.tenant).then(function(deployment) {
	          if (deployment.items.length == 0) {
	            $scope.ecdapp.errMsg = "404 - Deployment " + message.deployment.deploymentRef + " Not Found!";
	            $log.error("InventoryDeploymentSerice.getDeployment failed: "
	                + $scope.ecdapp.errMsg);
	            //$scope.ecdapp.isRequestFailed = true;
	            $scope.ecdapp.serviceTypeComplete = true;
	          } 
	          // Deployment IDs are unique, so this will always return exactly one item!.
	          // retrieve inputs of deployment.
	          else {
	            $scope.ecdapp.errMsg = null;
	            $scope.ecdapp.deployment = deployment.items[0];
	            // Copy the input parameter names and default values
	            let inputsAndDefaults = {};
	            let inputsAndDescriptions = {};
	            let ydict = deployment.items[0].inputs; 
	            for (var ykey in ydict) { 
	              let yval = ydict[ykey]; 
	              if (yval.constructor === {}.constructor) 
	                inputsAndDefaults[ykey] = JSON.stringify(yval, undefined, 2);
	              else 
	                //$scope.ecdapp.deployment.inputs[ykey] = yval;
	                inputsAndDefaults[ykey] = yval;
	            }
	            for (var pkey in $scope.ecdapp.inputsDict) {
	              //$scope.ecdapp.deployment = deployment.items[0];           
	              let description = $scope.ecdapp.inputsDict[pkey].description;
	              if (  typeof description === "undefined" )
	                description = '';
	              inputsAndDescriptions[pkey] = description;            
	            }
	            
	            $scope.ecdapp.editRequest.parmFileDict = inputsAndDefaults;
	            $scope.ecdapp.editRequest.descriptionDict = inputsAndDescriptions;
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
	    let deploymentRequestObject = {
	        deploymentId : editRequest.deployment_id,
	        serviceTypeId : editRequest.type_id,
	        inputs : {},
	        tenant : editRequest.tenant,
	        method : "update",
	        reinstall_list : $scope.ecdapp.selectedNodeInst,
	        skip_install : $scope.ecdapp.skip_install,
	        skip_uninstall : $scope.ecdapp.skip_uninstall,
	        skip_reinstall : $scope.ecdapp.skip_reinstall,
	        force : $scope.ecdapp.force,
	        ignore_failure : $scope.ecdapp.ignore_failure,
	        install_first : $scope.ecdapp.install_first_flow
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
	appDS2.controller('deploymentRollbackCtrl', function(
	    $scope, $rootScope, $log, $modalInstance, message, InventoryDeploymentService) {

	  'use strict';

	  // Controls logging in this controller
	  var debug = false;

	  // this object holds all app data and functions
	  $scope.ecdapp = {};
	  $scope.ecdapp.label = 'Deployment Rollback';
	  $scope.ecdapp.revisions = [];
	  $scope.ecdapp.deploymentRef = message.deployment.id;   
	  $scope.ecdapp.serviceId = message.deployment.serviceId;
	  $scope.ecdapp.errMsg = null;
	  $scope.ecdapp.isDataLoading = true;
	  $scope.ecdapp.isRequestFailed = false;
	  $scope.ecdapp.updatingDeployment = false;
	  var selTenant = message.deployment.tenant_name;
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
  appDS2.controller('appReconfigCtrl', function(
      $scope, $rootScope, $log, $modalInstance, message, InventoryDeploymentService, DeploymentService) {

    'use strict';

    var debug = true;
    // this object holds all app data and functions
    $scope.ecdapp = {};
    $scope.ecdapp.tenant = message.deployment.tenant_name;
    $scope.ecdapp.deploymentId = message.deployment.id;
    $scope.ecdapp.bpId = message.deployment.blueprint_id;
    $scope.ecdapp.errMsg = null;
    $scope.ecdapp.isDataLoading = true;
    $scope.ecdapp.isRequestFailed = false;
    $scope.ecdapp.updatingDeployment = false;
    $scope.ecdapp.parmFileDict = {};
    $scope.ecdapp.parmCurrFileDict = {};
    $scope.ecdapp.app_config_from_bp = {};
    $scope.ecdapp.app_config_new = {};
    $scope.ecdapp.app_config_bakup = {};
    $scope.ecdapp.app_config_inputs = [];
    $scope.ecdapp.app_config_bp_str ='';
    $scope.ecdapp.app_config_latest_str ='';
    $scope.ecdapp.descriptionDict = {};
    $scope.ecdapp.appConfigOverride = {};
    $scope.ecdapp.app_config_input_map = {};

    $scope.ecdapp.reconfigRequest = {
        deployment_id: $scope.ecdapp.deploymentId,
        workflow_id: 'execute_operation',
        parameters: {},
        tenant: $scope.ecdapp.tenant
    };
    $scope.ecdapp.reconfigRequest.parameters.operation = 
      'dcae.interfaces.lifecycle.Reconfiguration';
    $scope.ecdapp.reconfigRequest.parameters.operation_kwargs = {};
    $scope.ecdapp.reconfigRequest.parameters.operation_kwargs.type = 'app_reconfig';
    $scope.ecdapp.reconfigRequest.parameters.operation_kwargs.application_config = {};
    $scope.ecdapp.reconfigRequest.parameters.allow_kwargs_override = true;
    $scope.ecdapp.reconfigRequest.parameters.type_names =
      ['dcae.nodes.DockerContainerForComponents', 'dcae.nodes.DockerContainerForComponentsUsingDmaap',
        'dcae.nodes.ContainerizedServiceComponent', 'dcae.nodes.ContainerizedServiceComponentUsingDmaap'];

    // get the BP object from cloudify
    // get the depl object from cloudify
    // get the node instance data from cloudify
    // parse the application_config from BP node properties
    // parse the inputs from deployment
    // display FORM with current input fields that are referred in the appl
    // config object
    // display application_config on the screen and setup data binding to the
    // input FORM
    // create POST request body for start execution to cloudify
    // invoke service method to send POST request to cloudify
    InventoryDeploymentService.getBlueprint($scope.ecdapp.bpId, 
        $scope.ecdapp.tenant).then(function(blueprint) {
          if (debug)
            $log.debug("appReconfigCtrl.getBlueprint response: " + JSON.stringify(blueprint));
          if  (blueprint.error) {
            $scope.ecdapp.errMsg = 'Request Failed: ' + blueprint.error;
            $scope.ecdapp.updatingDeployment = false;
            $scope.ecdapp.isRequestFailed = true;
          }
          else {
            $scope.ecdapp.isRequestFailed = false;
            if (Array.isArray(blueprint.items) && blueprint.items.length > 0) {
              var foundNode = blueprint.items[0].plan.nodes.find(function checkAppConfig(node) {
                if (Object.keys(node.properties).includes("application_config")) {
                  return true;
                }
              });
              if (foundNode != undefined && foundNode.properties != undefined ) {
                $scope.ecdapp.app_config_from_bp = foundNode.properties.application_config;
                $scope.ecdapp.app_config_bp_str = 
                  JSON.stringify($scope.ecdapp.app_config_from_bp, undefined, 4);

                $scope.ecdapp.checkType = function(key1, value1) {
                  if (value1 instanceof Object && !Array.isArray(value1)) {
                    for (let [key2, value2] of Object.entries(value1)) {
                      if (key2 == "get_input") {
                        $scope.ecdapp.app_config_inputs.push(value2);
                        $scope.ecdapp.app_config_input_map[key1] = value2;
                      } else {
                        $scope.ecdapp.checkType(key2, value2);
                      }
                    }
                  }
                };

                for (let [key1, value1] of 
                    Object.entries($scope.ecdapp.app_config_from_bp)) 
                {
                  $scope.ecdapp.checkType(key1, value1);
                }
              }

              let inputsAndDescriptions = {};
              for (var pkey in blueprint.items[0].plan.inputs) {
                let description = blueprint.items[0].plan.inputs[pkey].description;
                if (  typeof description === "undefined" ) {
                  description = '';
                }
                inputsAndDescriptions[pkey] = description;
              }
              $scope.ecdapp.descriptionDict = inputsAndDescriptions;

              InventoryDeploymentService.getDeployment($scope.ecdapp.deploymentId, 
                  $scope.ecdapp.tenant).then(function(deployment) {
                    if (deployment.items.length == 0) {
                      $scope.ecdapp.errMsg = "404 - Deployment " + $scope.ecdapp.deploymentId + " Not Found!";
                      $log.error("InventoryDeploymentSerice.getDeployment failed: "
                          + $scope.ecdapp.errMsg);
                      $scope.ecdapp.isRequestFailed = true;
                    } 
                    else {
                      $scope.ecdapp.errMsg = null;
                      $scope.ecdapp.deployment = deployment.items[0];
                      $scope.ecdapp.isRequestFailed = false;
                      if (Array.isArray(deployment.items) && deployment.items.length > 0) {
                        var deplInputs = deployment.items[0].inputs;
                        $scope.ecdapp.app_config_inputs.forEach(function findMatch(value, index, array) {
                          for (let [key, val] of Object.entries(deplInputs)) {
                            if (value == key) {
                              $scope.ecdapp.parmFileDict[key] = val;
                            }
                          }
                        });
                      }
                    }
                    $scope.ecdapp.isDataLoading = false;
                  },
                  function(error) {
                    $log.error('InventoryDeploymentService.getDeployment failed: ' + JSON.stringify(error));
                    $scope.ecdapp.isRequestFailed = true;
                    $scope.ecdapp.errMsg = error;
                    $scope.ecdapp.isDataLoading = false;
                  });

              InventoryDeploymentService.getNodeInstanceData($scope.ecdapp.deploymentId, 
                  $scope.ecdapp.tenant).then(function(nodeInstances) {
                    if (Array.isArray(nodeInstances.items) && nodeInstances.items.length > 0) {
                      var foundNode = nodeInstances.items.find(function checkAppConfig(item) {
                        if (Object.keys(item.runtime_properties).includes("application_config")) {
                          return true;
                        }
                      });
                      if (foundNode != undefined) {
                        $scope.ecdapp.app_config_new = foundNode.runtime_properties.application_config;
                        $scope.ecdapp.app_config_latest_str = 
                          JSON.stringify($scope.ecdapp.app_config_new, undefined, 4);
                        for (let [key, value] of 
                            Object.entries(foundNode.runtime_properties.application_config)) 
                        {
                          if (value.constructor === {}.constructor)
                            $scope.ecdapp.appConfigOverride[key] = angular.toJson(value);
                          else
                            $scope.ecdapp.appConfigOverride[key] = value;      
                        }
                        $scope.ecdapp.app_config_inputs.forEach(function findMatch(value, index, array) {
                          for (let [key, val] of Object.entries($scope.ecdapp.appConfigOverride)) {
                            if (value == key) {
                              $scope.ecdapp.parmCurrFileDict[key] = val;
                            }
                          }
                        });             
                      }
                    }
                  },
                  function(error) {
                    $log.error('InventoryDeploymentService.getNodeInstances failed: ' + JSON.stringify(error));
                  });
            }
            $scope.ecdapp.isDataLoading = false;
          }
        },
        function(error) {
          $log.error('InventoryDeploymentService.getBlueprint failed: ' + JSON.stringify(error));
          $scope.ecdapp.isRequestFailed = true;
          $scope.ecdapp.errMsg = error;
          $scope.ecdapp.isDataLoading = false;
        });   

    $scope.ecdapp.refreshAppConfig = function(newInputs) {
      angular.copy($scope.ecdapp.app_config_from_bp, $scope.ecdapp.app_config_bakup);
      for (let [input_key, input_val] of Object.entries(newInputs)) {
        $scope.ecdapp.replaceNewInput(input_key, input_val, $scope.ecdapp.app_config_from_bp);
      }
      angular.copy($scope.ecdapp.app_config_from_bp, $scope.ecdapp.app_config_new);
      angular.copy($scope.ecdapp.app_config_bakup, $scope.ecdapp.app_config_from_bp);

      $scope.ecdapp.app_config_latest_str = 
        JSON.stringify($scope.ecdapp.app_config_new, undefined, 4);
    };

    var fileReader = new FileReader();
    fileReader.onload = function(event) {
      let jsonString = fileReader.result;
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
        // Allow only expected keys with scalar values
        if (ykey in $scope.ecdapp.parmFileDict)
        {
          // alert('Unexpected file content:\nKey not defined by blueprint:\n' +
          // ykey);
          if (yval.constructor === {}.constructor)
            $scope.ecdapp.parmFileDict[ykey] = angular.toJson(yval);
          else
            $scope.ecdapp.parmFileDict[ykey] = yval;
        }
      }
      $scope.ecdapp.refreshAppConfig($scope.ecdapp.parmFileDict);
      $scope.$apply();
    };
    // Handler for file-select event
    $scope.handleFileSelect = function() {
      if (debug)
        $log.debug('handleFileSelect: $scope.ecdapp.fileModel.name is ' + $scope.ecdapp.editRequest.fileModel.name);
      fileReader.readAsText($scope.ecdapp.editRequest.fileModel);
    };
    $scope.ecdapp.replaceNewInput = function(input_key, input_val, src) {
      var itemFound = false;
      for (let [key, value] of Object.entries(src)) {
        if (value instanceof Object && !Array.isArray(value)) {
          for (let [key2, value2] of Object.entries(value)) {
            if (key2 == "get_input" && value2 == input_key) {
              src[key] = input_val;
              itemFound = true;
              break;
            } else {
              $scope.ecdapp.replaceNewInput(input_key, input_val, value2);
            }
          }
        } 
        if (itemFound) {
          break;
        }
      }
    };

    $scope.ecdapp.reconfigDeploymentById = function(newInputs) { 
      $scope.ecdapp.updatingDeployment = true;
      angular.copy($scope.ecdapp.app_config_from_bp, $scope.ecdapp.app_config_bakup);
      for (let [input_key, input_val] of Object.entries(newInputs)) {
        $scope.ecdapp.replaceNewInput(input_key, input_val, 
            $scope.ecdapp.app_config_from_bp);
      }
      angular.copy($scope.ecdapp.app_config_from_bp, $scope.ecdapp.app_config_new);
      angular.copy($scope.ecdapp.app_config_bakup, $scope.ecdapp.app_config_from_bp);
      $scope.ecdapp.app_config_latest_str = 
        JSON.stringify($scope.ecdapp.app_config_new, undefined, 4);

      $scope.ecdapp.reconfigRequest.parameters.operation_kwargs.application_config = 
        $scope.ecdapp.app_config_new;      

      DeploymentService.reconfigFlow($scope.ecdapp.reconfigRequest).then(function(jsonObj) {       
        if (debug)
          $log.debug("appReconfigCtrl.reconfigFlow response: " + JSON.stringify(jsonObj));
        if (jsonObj.error) {
          $scope.ecdapp.errMsg = 'Request Failed: ' + jsonObj.error;
          $scope.ecdapp.updatingDeployment = false;
        }
        else {
          $scope.ecdapp.updatingDeployment = false;
          alert('application reconfig request for ' + $scope.ecdapp.deploymentId + ' successfully went through to Cloudify. Reconfig is now pending');               
          $modalInstance.dismiss('cancel');
        }
      }, function(error) {
        $scope.ecdapp.updatingDeployment = false;
        alert('Failed to perform reconfig for Deployment Id ' + $scope.ecdapp.deploymentId);
        $log.error('appReconfigCtrl.reconfigFlow failed: ' + error);
      });

    };            
  });
  
	/*************************************************************************/
	appDS2.controller('deploymentUpgradeCtrl', function(
	    $scope, $rootScope, $log, $modalInstance, message, InventoryDeploymentService) {

	  'use strict';

	  // Controls logging in this controller
	  var debug = false;

	  // this object holds all app data and functions
	  $scope.ecdapp = {};
	  $scope.ecdapp.label = 'Deployment Upgrade';
	  $scope.ecdapp.deployment = null;
	  $scope.ecdapp.deploymentRef = message.deployment.id;   //THIS IS THE BLUEPRINT ID
	  $scope.ecdapp.bpId = message.deployment.blueprint_id;
	  $scope.ecdapp.serviceId = message.deployment.serviceId;
	  $scope.ecdapp.errMsg = null;
	  $scope.ecdapp.isDataLoading = true;
	  $scope.ecdapp.isRequestFailed = false;
	  $scope.ecdapp.updatingDeployment = false;
	  var selTenant = message.deployment.tenant_name;
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
	  InventoryDeploymentService.getDeployment($scope.ecdapp.deploymentRef, $scope.ecdapp.tenant).then(function(deployment) {
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
	        if (key == 'config-format' || key == 'config-url' || key == 'chart-version' || key == 'chart-repo-url' || key == 'config-set') {
	          $scope.ecdapp.editRequest.resourceDefinitionChanges[key] = $scope.ecdapp.parmFileDict[key];
	        } else {
	          $scope.ecdapp.editRequest.resourceConstants[key] = $scope.ecdapp.parmFileDict[key];
	        }
	        //$scope.ecdapp.editRequest.resourceDefinitionChanges['config_set'] = '';
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
	    if ((!editRequest["config-url"] || editRequest["config-url"].trim() == '') && 
	        (!editRequest["config_set"] || editRequest["config_set"].trim() == '')) {
	      return 'Config URL or Config set is required.\nPlease enter either of these input values';
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
	    InventoryDeploymentService.getBlueprint($scope.ecdapp.bpId, $scope.ecdapp.tenant).then(function(blueprint) {
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
	                alert('Helm Upgrade request for ' + $scope.ecdapp.deploymentRef + ' successfully went through to Cloudiy. Upgrade is now pending');               $modalInstance.dismiss('cancel');
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
	      
	      if (key == 'config-format' || key == 'config-url' || key == 'chart-version' || key == 'chart-repo-url' || key == 'config-set') {
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

