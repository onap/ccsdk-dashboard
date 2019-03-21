appDS2.controller('nodeTableController', function($scope, $log, $modal, NodeHealthService) {

	'use strict';

	// this object holds all app data and functions
	$scope.ecdapp = {};
	// models for controls on screen
	$scope.ecdapp.tableData = [];
	$scope.ecdapp.currentPageNum = 1;
	$scope.ecdapp.viewPerPage = 10;
	// other
	$scope.ecdapp.errMsg = null;
	$scope.ecdapp.isDataLoading = false;
	$scope.ecdapp.isRequestFailed = false;
	$scope.ecdapp.isDcLoaded = false;
	$scope.ecdapp.activeImg = "static/fusion/images/active.png";
	$scope.ecdapp.inactiveImg = "static/fusion/images/inactive.png";
	$scope.ecdapp.datacenter = "";
	$scope.ecdapp.datacenters = [];
	var getDataCenters = function() {
		NodeHealthService.getDatacentersHealth().then(
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
						$scope.ecdapp.errMsg = null;
						$scope.ecdapp.datacenters = jsonObj.items;
						$scope.ecdapp.datacenter = $scope.ecdapp.datacenters[0].name;
						$scope.ecdapp.isDcLoaded = true;
						$scope.ecdapp.isRequestFailed = false;
						$scope.pageChangeHandler(1);
					}

				},
				function(error) {
					$log.error("datacentersController.loadTable failed: "
							+ error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = error;
					$scope.ecdapp.tableData = [];
				});
	};
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
		if ($scope.ecdapp.datacenter != 'Select Datacenter') {
		$scope.ecdapp.isDataLoading = true;
		NodeHealthService.getNodesHealth($scope.ecdapp.currentPageNum,
				$scope.ecdapp.viewPerPage, $scope.ecdapp.datacenter).then(
				function(jsonObj) {
					if (jsonObj.error) {
						$log.error("nodeHealthController.loadTable failed: "
								+ jsonObj.error);
						$scope.ecdapp.isRequestFailed = true;
						$scope.ecdapp.errMsg = jsonObj.error;
						$scope.ecdapp.tableData = [];
					} else {
						// $log.debug("nodeHealthController.loadTable succeeded,
						// size " + jsonObj.data.length);
						$scope.ecdapp.isRequestFailed = false;
						$scope.ecdapp.errMsg = null;
						$scope.ecdapp.totalPages = jsonObj.totalPages;
						$scope.ecdapp.tableData = jsonObj.items;
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error("nodeHealthController.loadTable failed: "
							+ error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = error;
					$scope.ecdapp.tableData = [];
					$scope.ecdapp.isDataLoading = false;
				});
		}
	};

	/**
	 * Invoked at first page load AND when user clicks on the B2B pagination
	 * control.
	 */
	$scope.pageChangeHandler = function(page) {
		// console.log('pageChangeHandler: current is ' +
		// $scope.ecdapp.currentPageNum + ' new is ' + page);
		$scope.ecdapp.currentPageNum = page;
		if ($scope.ecdapp.isDcLoaded) {
			$scope.ecdapp.loadTable();
		}
	}


	/**
	 * Shows a modal pop-up with services on this node.
	 */
	$scope.ecdapp.viewServicesModalPopup = function(nodeInfo, dc) {
		nodeInfo.dc = dc;
		var modalInstance = $modal.open({
			templateUrl : 'node_services_popup.html',
			controller : 'nodeServicesCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-jumbo',
			resolve : {
				message : function() {
					return nodeInfo ;
				}
			}
		});
		modalInstance.result.then(function(response) {
			// No response expected.
		});
	};

	// Populate the table on load. Note that the b2b selector code
	// sets the page-number value, and the change event calls load table.
	// Do not call this here to avoid double load:
	// $scope.ecdapp.loadTable();

	getDataCenters();
});
