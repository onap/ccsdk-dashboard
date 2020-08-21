appDS2.controller('executionsViewController', 
    function($scope, $rootScope, ControllerService, $modal, ExecutionService, 
        InventoryDeploymentService, DeploymentService, InventoryBlueprintService, 
        InventoryExecutionService, localStorageService, $log, $interval) {
	
  var coll = document.getElementsByClassName("collapsible");
  var i; 
  for (i = 0; i < coll.length; i++) {
    coll[i].addEventListener("click", function() {
      this.classList.toggle("active");
      var content = this.nextElementSibling;
      if (content.style.display === "" || content.style.display === "block") {
        content.style.display = "none";
        content.style.maxHeight = null;
      } else {
        content.style.display = "block";
        content.style.maxHeight = content.scrollHeight + "px";
      }
    });
  }
  var stop;
	$scope.ecdapp = {};
	$scope.ecdapp.isDataLoading = false;
	$scope.ecdapp.appLabel = "";
	$scope.ecdapp.bpCount = 0;
	$scope.ecdapp.depCount = 0;
	$scope.ecdapp.pluginCount = 0;
	$scope.ecdapp.filterByUser = true;
	$scope.ecdapp.searchByBp = 'owner:' + $scope.userId + ';';
	$scope.ecdapp.searchByDep = 'owner:' + $scope.userId + ';';
	$scope.ecdapp.bpObjList = [];
	$scope.ecdapp.depObjList = [];
	$scope.ecdapp.currentBpId = '';
	$scope.ecdapp.currentDep;
	$scope.ecdapp.isBpDataLoading = false;
	$scope.ecdapp.isDepDataLoading = false;
	$scope.ecdapp.isPluginDataLoading = false;
	$scope.ecdapp.isExecDataLoading = false;
	$scope.ecdapp.level_options = {
	    "off":"Group",
	    "on":"User"
	};
	$scope.ecdapp.inv_query_options = {
	      "off":"Off",
	      "on":"On"
	};
	 
  $scope.ecdapp.showingMoreFilters = false;

  $scope.ecdapp.toggleMoreFilters = function() {      
    $scope.ecdapp.showingMoreFilters = !$scope.ecdapp.showingMoreFilters;
  };
  $scope.ecdapp.availableStatus = 
    ['pending','started','cancelling','force_cancelling','cancelled','terminated','failed'];
  $scope.ecdapp.selectedStatus = 'started';
	$scope.ecdapp.level_switch = {
	    value: true
	};
  $scope.ecdapp.inv_load = {
        value: false
  };
 
  $scope.ecdapp.currentPage = 1;  
  $scope.ecdapp.viewPerPage = 10;
  $scope.ecdapp.selectedTenant = '';
  $scope.ecdapp.execId = '';
  
	var debug = false;
	
  $scope.ecdapp.resetFilters = function() {
    $scope.ecdapp.selectedTenant = '';
    $scope.ecdapp.selectedStatus = '';
    $scope.ecdapp.execId = '';
  };
  
  $scope.ecdapp.extendedfilterSrch = function() {
    $scope.ecdapp.showingMoreFilters = !$scope.ecdapp.showingMoreFilters;
    $scope.ecdapp.searchExecTable();   
  };
  
  $scope.ecdapp.searchExecTable = function() {
    $scope.ecdapp.currentPage = 1;  
    $scope.ecdapp.viewPerPage = 10;
    if ($scope.ecdapp.execId != '') {
      getExecutionForId();
    } else {
      if ($scope.ecdapp.selectedTenant == '') {
        getActiveExecutions();
      } else {
        getPerTenantExecutions();
      }
    }
  };

  $scope.ecdapp.toggleUserFilt = function() {
    if ($scope.ecdapp.level_switch.value) {
      $scope.ecdapp.searchByBp = 'owner:' + $scope.userId + ';';
      $scope.ecdapp.searchByDep = 'owner:' + $scope.userId + ';'; 
    } else {
      $scope.ecdapp.searchByBp = null;
      $scope.ecdapp.searchByDep = null;
    }
    getBlueprintsCount();
    getDeploymentsCount();
  };
  
  $scope.$watch('ecdapp.inv_load["value"]', function(newValue,oldValue,scope) {
    if (newValue != oldValue) {
      if (newValue === true) {
        $scope.ecdapp.toggleUserFilt();
        $scope.ecdapp.searchExecTable(); 
        stop = $interval( function(){ $scope.ecdapp.toggleUserFilt(); $scope.ecdapp.searchExecTable(); }, 60000, 100, false);
      } else {
        $scope.ecdapp.stopLoading();
      }
    }
  }, true);
  
  $scope.ecdapp.stopLoading = function() {
    if (angular.isDefined(stop)) {
      $interval.cancel(stop);
      stop = undefined;
    }
  };
  
	var getTenants = function() {  
	  var tenantStr = localStorageService.get('tenants');
	  if (!tenantStr) {
	    ControllerService.getTenants()
			.then(function(jsonObj) {
				if (jsonObj.error) {
					$log.error("executionsViewController.getTenants failed: " + jsonObj.error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = jsonObj.error;
				} else {
				  var tenants = [];
	        for (var tenIndx = 0; tenIndx < jsonObj.items.length; tenIndx++) {
	          tenants.push(jsonObj.items[tenIndx].name);
	        }
	        localStorageService.set('tenants', JSON.stringify(tenants));
	        $scope.ecdapp.availableTenants = tenants;        
				}			
			}, function(error) {
				$log.error("executionsViewController.loadTable failed: " + error);
				$scope.ecdapp.isRequestFailed = true;
				$scope.ecdapp.errMsg = error;
			});
	  } else {
	    $scope.ecdapp.availableTenants = JSON.parse(tenantStr);
    }
	  getActiveExecutions();
		};

	var getAppLabel = function() {
    var label = localStorageService.get('appLabel');
    if (!label) {
      ControllerService.getAppLabel().then(function(jsonObj) {
			if (debug) {
				$log.debug("Controller.getAppLabel succeeded: " + JSON.stringify(jsonObj));
			}
			localStorageService.set('appLabel', JSON.stringify(jsonObj));
			$scope.ecdapp.appLabel = jsonObj;
		}, function(error) {
			$log.error("Controller.getAppLabel failed: " + error);
		});
    } else {
      $scope.ecdapp.appLabel = JSON.parse(label);
    }
	};
  
  var getBlueprintsCount = function() {
    $scope.ecdapp.bpObjList = [];
    $scope.ecdapp.currentBpId = '';
    $scope.ecdapp.isBpDataLoading = true;
    InventoryBlueprintService.getBlueprintIdsList($scope.ecdapp.searchByBp).then(
        function(jsonObj) {
          if (jsonObj.error) {
            $log.error("execViewCtlr.getBlueprintsCount failed: "
                + jsonObj.error);
            $scope.ecdapp.bpCount = 0;
            $scope.ecdapp.isBpDataLoading = false;
            $scope.ecdapp.bpObjList = [];
          } else {
            $scope.ecdapp.bpCount = jsonObj.totalItems;
            $scope.ecdapp.bpObjList = jsonObj.items;
            $scope.ecdapp.isBpDataLoading = false;
          }
        },
        function(error) {
          $log.error("execViewCtlr.getBlueprintsCount failed: "
              + error);
          $scope.ecdapp.bpCount = 0;
          $scope.ecdapp.bpObjList = [];
          $scope.ecdapp.isBpDataLoading = false;
        });
  };
  
  $scope.ecdapp.updateDepl = function() {
    $scope.ecdapp.currentDep = '';
    if ($scope.ecdapp.currentBpId != '') {
      $scope.ecdapp.isDeplDataLoading = true;
      $scope.ecdapp.depObjList = [];
      $scope.ecdapp.execData = [];
      var srvcIds = [];
      var srvcTypIds = [];
      srvcTypIds.push($scope.ecdapp.currentBpId);
      InventoryBlueprintService.getDeploymentForBp(srvcTypIds)
      .then(function(jsonObj) {
        if (jsonObj.error) {
          $scope.ecdapp.depObjList = [];
          $scope.ecdapp.isDeplDataLoading = false;
        } else {
          for (var typIndx = 0; typIndx < jsonObj.length; typIndx++) {
            for (var depIndx = 0; depIndx < jsonObj[typIndx].serviceRefList.items.length; depIndx++) {
              srvcIds.push(jsonObj[typIndx].serviceRefList.items[depIndx]);
            }    
          }
          $scope.ecdapp.depObjList = srvcIds;
          //$scope.ecdapp.currentDep = $scope.ecdapp.depObjList[0];
          $scope.ecdapp.isDeplDataLoading = false;
        }
      }, function(error) {
        $log.error("inventoryBlueprintController.updateTable failed: " + error);
        bpDepls = [];
        $scope.ecdapp.isDeplDataLoading = false;
      });
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

  $scope.ecdapp.updateExec = function() {
    if ($scope.ecdapp.currentDep != '') {
      $scope.ecdapp.isExecDataLoading = true;
      $scope.ecdapp.resetFilters();
      var currentDeplId = $scope.ecdapp.currentDep.split(":")[0];
      var currentDeplTenant = $scope.ecdapp.currentDep.split(":")[1];
      InventoryExecutionService.getExecutionsByDeployment(currentDeplId, 
          currentDeplTenant,
          1,
          1000).then(
              function(jsonObj) {
                if (jsonObj.error) {
                  $scope.ecdapp.execData = [];
                  $scope.ecdapp.isExecDataLoading = false;
                } else {
                  $scope.ecdapp.totalPages = jsonObj.totalPages;
                  $scope.ecdapp.execData = jsonObj.items;
                  $scope.ecdapp.isExecDataLoading = false;
                }
              }, function(error) {
                $log.error("execViewCtlr.updateExec failed: " + error);
                $scope.ecdapp.isExecDataLoading = false;
              });
    }
  };

  var getActiveExecutions = function() {
    $scope.ecdapp.isExecDataLoading = true;
    InventoryExecutionService.getActiveExecutions($scope.ecdapp.currentPage, $scope.ecdapp.viewPerPage)
        .then(
            function(jsonObj) {
              if (jsonObj.error) {
                $scope.ecdapp.execData = [];
                $scope.ecdapp.isExecDataLoading = false;
              } else {
                $scope.ecdapp.totalPages = jsonObj.totalPages;
                $scope.ecdapp.execData = jsonObj.items;
                $scope.ecdapp.isExecDataLoading = false;
              }
            }, function(error) {
              $log.error("execViewCtlr.updateExec failed: " + error);
              $scope.ecdapp.isExecDataLoading = false;
            });
  };
  
  var getPerTenantExecutions = function() {
    $scope.ecdapp.isExecDataLoading = true;
    InventoryExecutionService.getExecutionsByTenant($scope.ecdapp.selectedTenant,
        $scope.ecdapp.selectedStatus, $scope.ecdapp.currentPage, $scope.ecdapp.viewPerPage)
        .then(
            function(jsonObj) {
              if (jsonObj.error) {
                $scope.ecdapp.execData = [];
                $scope.ecdapp.isExecDataLoading = false;
              } else {
                $scope.ecdapp.totalPages = jsonObj.totalPages;
                $scope.ecdapp.execData = jsonObj.items;
                $scope.ecdapp.isExecDataLoading = false;
              }
            }, function(error) {
              $log.error("execViewCtlr.updateExec failed: " + error);
              $scope.ecdapp.isExecDataLoading = false;
            });
  };
  
  var getExecutionForId = function() {
    $scope.ecdapp.isExecDataLoading = true;
    InventoryExecutionService.getExecutionsById($scope.ecdapp.execId, 
        $scope.ecdapp.selectedTenant, $scope.ecdapp.currentPage, $scope.ecdapp.viewPerPage)
        .then(
            function(jsonObj) {
              if (jsonObj.error) {
                $scope.ecdapp.execData = [];
                $scope.ecdapp.isExecDataLoading = false;
              } else {
                $scope.ecdapp.totalPages = jsonObj.totalPages;
                $scope.ecdapp.execData = jsonObj.items;
                $scope.ecdapp.isExecDataLoading = false;
              }
            }, function(error) {
              $log.error("execViewCtlr.getExecutionsById failed: " + error);
              $scope.ecdapp.isExecDataLoading = false;
            });
  };
  /**
   * Invoked at first page load AND when
   * user clicks on the B2B pagination control. 
   */
  $scope.pageChangeHandler = function(page) {
    $scope.ecdapp.currentPage = page;
    if ($scope.ecdapp.selectedTenant != '') {
      getPerTenantExecutions();
    }
  }
  
  var getDeploymentsCount = function() {
    $scope.ecdapp.isDepDataLoading = true;
    $scope.ecdapp.execData = [];
    InventoryDeploymentService.getDeploymentCount($scope.ecdapp.searchByDep).then(
        function(jsonObj) {
          if (jsonObj.error) {
            $log.error("execViewCtlr.getDeploymentscount failed: "
                + jsonObj.error);
            $scope.ecdapp.depCount = 0;
            $scope.ecdapp.depObjList = [];
          } else {
            $scope.ecdapp.depCount = jsonObj.totalItems;
            $scope.ecdapp.depObjList = jsonObj.items;
            $scope.ecdapp.isDepDataLoading = false;
          }
        },
        function(error) {
          $log.error("execViewCtlr.getDeploymentscount failed: "
              + error);
          $scope.ecdapp.isDepDataLoading = false;
          $scope.ecdapp.depCount = 0;
        });
  }; 
  
  var getPluginsCount = function() {
    $scope.ecdapp.isPluginDataLoading = true;
    ControllerService.getPluginsCount().then(
        function(jsonObj) {
          if (jsonObj.error) {
            $log.error("execViewCtlr.getPluginsCount failed: "
                + jsonObj.error);
            $scope.ecdapp.isPluginDataLoading = false;
            $scope.ecdapp.pluginCount = 0;
          } else {
            $scope.ecdapp.pluginCount = jsonObj.totalItems;
            $scope.ecdapp.isPluginDataLoading = false;
          }
        },
        function(error) {
          $log.error("execViewCtlr.getPluginsCount failed: "
              + error);
          $scope.ecdapp.pluginCount = 0;
          $scope.ecdapp.isPluginDataLoading = false;
        });
  }; 
  var getOwners = function() {
    InventoryBlueprintService.getOwnersList().then(
        function(jsonObj) {
          if (jsonObj.error) {
            $log.error("execViewCtlr.getOwnersList failed: "
                + jsonObj.error);
            $scope.ecdapp.bpOwners = [];
          } else {
            $scope.ecdapp.bpOwners = jsonObj;
            localStorageService.set('bpOwners', JSON.stringify(jsonObj));
          }
        },
        function(error) {
          $log.error("execViewCtlr.getOwnersList failed: "
              + error);
          $scope.ecdapp.bpOwners = [];
        });
  };
  
	// Initialize the page
	getTenants();
	getAppLabel();
	getBlueprintsCount();
	getDeploymentsCount();
	getOwners();
	getPluginsCount();
	getActiveExecutions();
  $scope.$on('$destroy', function() {
    $scope.ecdapp.stopLoading();
  });
});
