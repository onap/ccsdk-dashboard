appDS2.controller('executionsViewController', function($scope, $rootScope, ControllerService, $modal, ExecutionService, $log) {
	
	$scope.parent = { 'blueprint_id': 'Root', 'parent': 'parent' };
	$scope.ecdapp = {};
	$scope.ecdapp.isDataLoading = false;
	$scope.ecdapp.appLabel = "";
	$rootScope.tenantList = {
		tenant: '',
		data: {}
	};
	var debug = false;
	
	var getTenants = function() {
		ControllerService.getTenants()
			.then(function(jsonObj) {
				if (jsonObj.error) {
					$log.error("executionsViewController.getTenants failed: " + jsonObj.error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = jsonObj.error;
					$scope.ecdapp.tableData = [];
				} else {
					$rootScope.tenantList.data = jsonObj.items;
					$rootScope.tenantList.tenant = jsonObj.items[0].name;
				}			
			}, function(error) {
				$log.error("executionsViewController.loadTable failed: " + error);
				$scope.ecdapp.isRequestFailed = true;
				$scope.ecdapp.errMsg = error;
			});
		};

	var getAppLabel = function() {
		ControllerService.getAppLabel().then(function(jsonObj) {
			if (debug)
				$log.debug("Controller.getAppLabel succeeded: " + JSON.stringify(jsonObj));
			$scope.ecdapp.appLabel = jsonObj;
		}, function(error) {
			$log.error("Controller.getAppLabel failed: " + error);
		});
	};
	// Initialize the page
	getAppLabel();
	getTenants();

});
