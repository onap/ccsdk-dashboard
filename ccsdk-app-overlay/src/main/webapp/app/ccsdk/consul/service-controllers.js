appDS2.controller('serviceHealthTableController', function($scope, $log, $modal, modalService, ServiceHealthService) {

	'use strict';

	// this object holds all app data and functions
	$scope.ecdapp = {};
	// models for controls on screen
	$scope.ecdapp.tableData = [];
	$scope.ecdapp.currentPageNum = 1;
	$scope.ecdapp.viewPerPage = 100;
	// other
	$scope.ecdapp.errMsg = null;
	$scope.ecdapp.isDataLoading = false;
	$scope.ecdapp.isDcLoaded = false;
	$scope.ecdapp.isDcAvail = false;
	$scope.ecdapp.isRequestFailed = false;
	$scope.ecdapp.activeImg = "static/fusion/images/active.png";
	$scope.ecdapp.inactiveImg = "static/fusion/images/inactive.png";
	$scope.ecdapp.datacenter = "";
	$scope.ecdapp.datacenters = [];
	
	var getDataCenters = function() {
		ServiceHealthService.getDatacentersHealth().then(
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
						//$scope.ecdapp.totalPages = jsonObj.totalPages;
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
	 * Loads the table. Interprets the remote controller's response and copies
	 * to scope variables. The response is either list to be assigned to
	 * tableData, or an error to be shown.
	 */
	$scope.ecdapp.loadTable = function() {
		if ($scope.ecdapp.datacenter != 'Select Datacenter') {
		$scope.ecdapp.isDataLoading = true;
		ServiceHealthService.getServicesHealth($scope.ecdapp.currentPageNum,
				$scope.ecdapp.viewPerPage, $scope.ecdapp.datacenter).then(
				function(jsonObj) {
					if (jsonObj.error) {
						$log.error("serviceHealthTableController.loadTable failed: "
								+ jsonObj.error);
						$scope.ecdapp.isRequestFailed = true;
						$scope.ecdapp.errMsg = jsonObj.error;
						$scope.ecdapp.tableData = [];
					} else {
						// $log.debug("serviceHealthController.loadTable
						// succeeded, size " + jsonObj.data.length);
						$scope.ecdapp.isRequestFailed = false;
						$scope.ecdapp.errMsg = null;
						$scope.ecdapp.totalPages = jsonObj.totalPages;
						$scope.ecdapp.tableData = jsonObj.items;
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error("serviceHealthTableController.loadTable failed: "
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
	 * Shows a modal pop-up to register a service. 
	 */
	$scope.ecdapp.registerServiceModalPopup = function() {
		var modalInstance = $modal.open({
			templateUrl : 'service_register_popup.html',
			controller : 'serviceRegisterCtrl',
			windowClass: 'modal-docked',
			sizeClass: 'modal-medium',
			resolve : {
				message : function() {
					return { /* no data */ } ;
				}
			}
		});
		modalInstance.result.then(function(response) {
			// $log.debug('addFeedModalPopup: response: ' + JSON.stringify(response));
			if (response == null) {
				// $log.debug('user closed dialog');
			}
			else {
				if (response.error != null) {
					$log.error('registerServiceModalPopup failed: ' + response.error);
					alert('Failed to register service:\n' + response.error);
				}
				else {
					// success, get the updated list.
					$scope.ecdapp.loadTable()
				}
			}
		});
	};
	
	/**
	 * Shows a modal pop-up with service health history. 
	 */
	$scope.ecdapp.viewHealthHistoryModalPopup = function(service) {
		var modalInstance = $modal.open({
			templateUrl : 'service_history_popup.html',
			controller : 'serviceHistoryCtlr',
			windowClass: 'modal-docked',
			sizeClass: 'modal-large',
			resolve : {
				message : function() {
					return service;
				}
			}
		});
		modalInstance.result.then(function(response) {
			// $log.debug('addFeedModalPopup: response: ' + JSON.stringify(response));
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
	 * Shows a modal pop-up to confirm deregistration. 
	 * On successful completion, updates the table.
	 */
	$scope.ecdapp.deregisterServiceModalPopup = function(service) {
		modalService.popupConfirmWin("Confirm", "Deregister service '"
				+ service.serviceName + "'?", function() {
			ServiceHealthService.deregisterService(service.serviceName).then(
					function(response) {
						if (response && response.error) {
							// $log.error('deleteBlueprint failed: ' + response.error);
							alert('Failed to deregister service:\n' + response.error);
						}
						else {
							// No response body on success.
							$scope.ecdapp.loadTable();
						}
					},
					function(error) {
						$log.error('ServiceHealthService.deregisterService failed: ' + error);
						alert('Failed to deregister service:\n' + error);
					});
		})
	};

	getDataCenters();
	// Populate the table on load. Note that the b2b selector code
	// sets the page-number value, and the change event calls load table.
	// Do not call this here to avoid double load:
	// $scope.ecdapp.loadTable();

});


appDS2.controller('serviceRegisterCtrl', function($scope, $log, $modalInstance, message, ServiceHealthService) {

	'use strict';
	
	// this object holds all app data and functions
	$scope.ecdapp = {};
	$scope.ecdapp.uploadInProgress = false;
	$scope.ecdapp.label = 'Register Service';
	// Model the data simply here.  
	// Build the complex request later.
	$scope.ecdapp.registerRequest = 
		{
			service_id : '',
			service_name : '',
			service_address : '',
			service_port : '',
			check_endpoint: '',
			check_interval: '',
			check_description: '',
			check_name: ''
		};

	/**
	 * Validates content of user-editable fields.
	 * Uses the list in message.feedList 
	 * Returns null if all is well, 
	 * a descriptive error message otherwise.
	 */
	$scope.ecdapp.validateRequest = function(request) {
		if (request == null)
			return "No data found.\nPlease enter some values.";
		if (request.service_id == null || request.service_id.trim() == '')
			return "Service ID is required.\nPlease enter a value.";
		if (request.service_name == null || request.service_name.trim() == '')
			return "Service name is required.\nPlease enter a value.";
		if (request.service_address == null || request.service_address.trim() == '')
			return "Service address is required.\nPlease enter a value.";
		if (request.service_port == null || request.service_port.trim() == '')
			return "Service port is required.\nPlease enter a value.";
		if (request.check_endpoint == null || request.check_endpoint.trim() == '')
			return "Check endpoint URL is required.\nPlease enter a value.";
		if (request.check_interval == null || request.check_interval.trim() == '')
			return "Check interval is required.\nPlease enter a value.";
		// description and name are optional (I think)
		return null;
	}
	
	$scope.ecdapp.registerService = function(edit_req) {
		$scope.ecdapp.uploadInProgress = true;
		var validateMsg = $scope.ecdapp.validateRequest(edit_req);
		if (validateMsg != null) {
			alert('Invalid registration request:\n' + validateMsg);
			return;			
		}
		// Build the complex request
		let request = {
			services : [
			    {
			    	id 		: edit_req.service_id,
			    	name 	: edit_req.service_name,
			    	address	: edit_req.service_address,
			    	port	: edit_req.service_port,
			    	tags: [ ],				        
			    	checks : [
			    		{
			    			endpoint	: edit_req.check_endpoint,
			    			interval	: edit_req.check_interval,
			    			description : edit_req.check_description,
			    			name		: edit_req.check_name
			    		}
			    	]
			    }
			]
		};
		
		ServiceHealthService.registerService(request)
			.then(function(response) {
				if (response && response.error) {
					alert('Failed to register service:\n' + response.error);
				} else {
					$modalInstance.close("success");
				}
				$scope.ecdapp.uploadInProgress = false;
			},
			function (error) {
				$log.error('serviceRegisterCtrl: error while registering: ' + error);
				$scope.ecdapp.uploadInProgress = false;
				alert('Server rejected registration request:\n' + error);
			}
		);

	};

});


appDS2.controller('serviceHistoryCtlr', function($scope, $log, $modal, message, ServiceHealthService) {

	'use strict';

	// Controls logging in this controller
	var debug = false;
	if (debug)
		$log.debug('serviceHistoryCtlr: message: ' + JSON.stringify(message));

	// this object holds all app data and functions
	$scope.ecdapp = {};
	// For convenience
	$scope.ecdapp.serviceName = message.serviceName;
	$scope.ecdapp.label = 'History for Service ' + $scope.ecdapp.serviceName;

	// models for controls on screen
	$scope.ecdapp.startDateTime = '';
	$scope.ecdapp.endDateTime = '';
	// data fetched from backend
	$scope.ecdapp.tableData = [];
	// progress and error handling
	$scope.ecdapp.isDataLoading = false;
	$scope.ecdapp.isRequestFailed = false;
	$scope.ecdapp.errMsg = null;

	/**
	 * Loads the table. Interprets the remote controller's response and copies
	 * to scope variables. The response is either list to be assigned to
	 * tableData, or an error to be shown.
	 */
	$scope.ecdapp.showHistory = function() {
		// Validate the entries in the date fields.
		// If user types, it's a STRING (not a Date).
		if ($scope.ecdapp.startDateTime == null) {
			alert("Please enter a start date.");
			return;
		}
		let startDT = Date.parse($scope.ecdapp.startDateTime); 
		if (isNaN(startDT)) {
			alert("Failed to parse start date.");
			return;
		}
		else {
			// Use the parsed version.
			$scope.ecdapp.startDateTime = new Date(startDT);
		}
		if ($scope.ecdapp.endDateTime == null) {
			alert("Please enter an end date.");
			return;
		}
		let endDT = Date.parse($scope.ecdapp.endDateTime);
		if (isNaN(endDT)) {
			alert("Failed to parse end date.");
			return;
		}
		else {
			$scope.ecdapp.endDateTime = new Date(endDT);
		}
		if ($scope.ecdapp.startDateTime.getTime() >= $scope.ecdapp.endDateTime.getTime()) {
			alert("The start date must be before the end date, please correct.");
			return;
		}
		$scope.ecdapp.isDataLoading = true;
		ServiceHealthService.getServiceHealthHistory(
			$scope.ecdapp.serviceName, 
			$scope.ecdapp.startDateTime.toISOString(), 
			$scope.ecdapp.endDateTime.toISOString() ).then(
				function(jsonObj) {
					if (jsonObj.error) {
						$log.error("serviceHistoryCtlr.getServiceHealthHistory reported error: "
								+ jsonObj.error);
						$scope.ecdapp.isRequestFailed = true;
						$scope.ecdapp.errMsg = jsonObj.error;
						$scope.ecdapp.tableData = [];
					} else {
						if (debug)
							$log.debug("serviceHistoryCtlr.getServiceHealthHistory succeeded, size " + jsonObj.length);
						$scope.ecdapp.isRequestFailed = false;
						$scope.ecdapp.errMsg = null;
						$scope.ecdapp.tableData = jsonObj;
					}
					$scope.ecdapp.isDataLoading = false;
				},
				function(error) {
					$log.error("serviceHistoryCtlr.getServiceHealthHistory failed: "
							+ error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = error;
					$scope.ecdapp.tableData = [];
					$scope.ecdapp.isDataLoading = false;
				});
	};


});
