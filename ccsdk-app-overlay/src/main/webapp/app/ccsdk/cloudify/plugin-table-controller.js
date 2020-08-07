appDS2.controller('PluginsTableController', function(
		$rootScope, $scope, $log, $modal, modalService, PluginService) {

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
		PluginService.getPlugins($scope.ecdapp.currentPageNum,
				$scope.ecdapp.viewPerPage).then(
				function(jsonObj) {
					if (jsonObj.error) {
						$log.error("PluginsTableController.loadTable failed: "
								+ jsonObj.error);
						$scope.ecdapp.isRequestFailed = true;
						$scope.ecdapp.errMsg = jsonObj.error;
						$scope.ecdapp.tableData = [];
					} else {
						$scope.ecdapp.isRequestFailed = false;
						$scope.ecdapp.errMsg = null;
						$scope.ecdapp.tableData = jsonObj;
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error("PluginsTableController.loadTable failed: "
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

});
