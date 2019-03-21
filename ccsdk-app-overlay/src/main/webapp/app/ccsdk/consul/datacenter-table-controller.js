appDS2.controller('datacenterTableController', function($scope, $log, $modal, DatacenterHealthService) {

	'use strict';

	// this object holds all app data and functions
	$scope.ecdapp = {};
	// models for controls on screen
	$scope.ecdapp.tableData = [];
	$scope.ecdapp.currentPageNum = 1;
	$scope.ecdapp.totalPages = 1;
	$scope.ecdapp.viewPerPage = 100;
	$scope.ecdapp.viewPerPageOptions = [ {
		index : 0,
		value : 50
	}, {
		index : 1,
		value : 100
	}, {
		index : 2,
		value : 500
	}, {
		index : 3,
		value : 1000
	}, {
		index : 4,
		value : 2500
	} ];
	// other
	$scope.ecdapp.errMsg = null;
	$scope.ecdapp.isDataLoading = true;
	$scope.ecdapp.isRequestFailed = false;

	/**
	 * Answers an array of the specified size - makes Angular iteration easy.
	 */
	$scope.ecdapp.buildArraySizeN = function(num) {
		// $log.debug("buildArraySizeN: invoked with " + num);
		return new Array(num);
	}

	/**
	 * Loads the table. Interprets the remote controller's response and copies
	 * to scope variables. The response is either list to be assigned to
	 * tableData, or an error to be shown.
	 */
	$scope.ecdapp.loadTable = function() {
		$scope.ecdapp.isDataLoading = true;
		DatacenterHealthService.getDatacentersHealth(
				$scope.ecdapp.currentPageNum, $scope.ecdapp.viewPerPage).then(
				function(jsonObj) {
					if (jsonObj.error) {
						$log.error("datacentersController.loadTable failed: "
								+ jsonObj.error);
						$scope.ecdapp.isRequestFailed = true;
						$scope.ecdapp.errMsg = jsonObj.error;
						$scope.ecdapp.tableData = [];
					} else {
						// $log.debug("datacentersController.loadTable
						// succeeded, size " + jsonObj.data.length);
						$scope.ecdapp.isRequestFailed = false;
						$scope.ecdapp.errMsg = null;
						$scope.ecdapp.totalPages = jsonObj.totalPages;
						$scope.ecdapp.tableData = jsonObj.items;
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error("datacentersController.loadTable failed: "
							+ error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = error;
					$scope.ecdapp.tableData = [];
					$scope.ecdapp.isDataLoading = false;
				});
	};

	// Populate the table on load. Note that the b2b selector code
	// sets the page-number value, and the change event calls load table.
	// Do not call this here to avoid double load:
	// $scope.ecdapp.loadTable();

});
