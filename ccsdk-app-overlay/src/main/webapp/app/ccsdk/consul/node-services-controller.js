appDS2.controller('nodeServicesCtrl', function($scope, $log, message, NodeHealthService) {

	'use strict';
	
	// Controls logging in this controller
	var debug = false;

	if (debug)
		$log.debug('nodeServicesCtrl: message is ' + JSON.stringify(message));

	// this object holds all app data and functions
	$scope.ecdapp = {};	
	$scope.ecdapp.label = 'Services on Node ' + message.node;
	$scope.ecdapp.activeImg = "static/fusion/images/active.png";
	$scope.ecdapp.inactiveImg = "static/fusion/images/inactive.png";
	/**
	 * Loads the table of services for the specified node.
	 */
	$scope.ecdapp.loadTable = function(nodeName, dc) {
		$scope.ecdapp.isDataLoading = true;
		if (debug)
			$log.debug('nodeServicesCtrl: loading data for ' + nodeName);
		NodeHealthService.getNodeServicesHealth(nodeName, dc).then(
				function(jsonObj) {
					if (debug)
						$log.debug('nodeServicesCtrl: response is ' + JSON.stringify(jsonObj));
					if (jsonObj.error) {
						$log.error("nodeServicesCtrl.loadTable failed: "
								+ jsonObj.error);
						$scope.ecdapp.isRequestFailed = true;
						$scope.ecdapp.errMsg = jsonObj.error;
						$scope.ecdapp.tableData = [];
					} else {
						// $log.debug("serviceHealthController.loadTable
						// succeeded, size " + jsonObj.data.length);
						$scope.ecdapp.isRequestFailed = false;
						$scope.ecdapp.errMsg = null;
						$scope.ecdapp.tableData = jsonObj;
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error("nodeServicesCtrl.loadTable failed: "
							+ error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = error;
					$scope.ecdapp.tableData = [];
					$scope.ecdapp.isDataLoading = false;
				});
	};

	// Show services for the requested node
	if (debug)
		$log.debug('nodeServicesCtrl: requesting services for node ' + message.node);
	$scope.ecdapp.loadTable(message.node, message.dc);

	
});
