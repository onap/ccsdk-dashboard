appDS2.controller('executionsViewController', function($scope, $rootScope, ControllerService, $modal, ExecutionService, $log) {
	
	$scope.parent = { 'blueprint_id': 'Root', 'parent': 'parent' };
	$scope.ecdapp = {};
	$scope.ecdapp.isDataLoading = false;
	$scope.ecdapp.appLabel = "";
	$scope.controllersList = [];
	$scope.controllerCall;
	$rootScope.tenantList = {
		tenant: '',
		data: {}
	};
	$rootScope.cardData = [
        {
            "cardHeading": "Applications",
            "cardContent": "Applications managed by ECOMP Controller",
            "cardFooterFlyout": "Some Flyout Content",
            "flyout":"false"
        },
        {
            "cardHeading": "Tenants",
            "cardContent": "Tenants where applications are deployed ",
            "cardFooterFlyout": "Some Flyout Content",
            "flyout":"false"
        },
        {
            "cardHeading": "Blueprints",
            "cardContent": "Blueprints data from ECOMPC Inventory",
            "cardFooterFlyout": "Some Flyout Content",
            "flyout":"false"
        },
        {
            "cardHeading": "Deployments",
            "cardContent": "Deployments done using the blueprints",
            "cardFooterFlyout": "Some Flyout Content",
            "flyout":"false"
        }
    ];
  /* events fired on the drop targets */
  document.addEventListener("dragover", function( event ) {
      // prevent default to allow drop
      event.preventDefault();
  }, false);

  
        
    $scope.horizontalPositions = [
        { value: 'center', text: 'Center' },
        { value: 'left', text: 'Left' },
        { value: 'right', text: 'Right' },
    ];
    $scope.horizontalPosition = $scope.horizontalPositions[0];
    $scope.verticalPositions = [
        { value: 'below', text: 'Below' },
        { value: 'above', text: 'Above' },
    ];
    $scope.ddOption1 = [
        { value: '', text: 'Actions' },
        { value: '1', text: 'Option 1' },
        { value: '2', text: 'Option 2' },
        { value: '3', text: 'Option 3' }
    ];

    $scope.ddOptionValue1 = {};
    $scope.ddOptionValue1.value = $scope.ddOption1[0].value;

    /* This showcases having an expander default expanded */
    $scope.testmpc = true;
    $scope.testmpc6 = true;
    $scope.testmpc14 = true;
    $scope.verticalPosition = $scope.verticalPositions[0];

    $scope.concurrent = [10, 20, 30];
    $scope.concurrentValue = $scope.concurrent[0];

    $scope.flyoutContent = {};
    $scope.flyoutContent.contentUpdated = false;
    $scope.flyoutContent.enableSave = false;
    $scope.flyoutContent.concurrentCalls = 0;
    $scope.enableSaveFun = function () {
        if ($scope.flyoutContent.concurrentCalls !== 0 || $scope.concurrentValue != $scope.concurrent[0]) {
            $scope.flyoutContent.enableSave = true;
            $scope.flyoutContent.contentUpdated = true;
        } else {
            $scope.flyoutContent.enableSave = false;
            $scope.flyoutContent.contentUpdated = true;
        }
    };
    
	$scope.ecdapp.opsUrls = [];
	$rootScope.gTabs = [];
	$rootScope.menuKeys = [];
	$rootScope.opsMenu = [
        {
            title: 'Cloudify',
            id: 'cfy',
            uniqueId: 'uniqueTab1x',
            tabPanelId: 'threetab1x',
            url: ''
        }, {
	        title: 'Grafana',
	        id: 'grf',
	        uniqueId: 'uniqueTab2x',
	        tabPanelId: 'threetab2x',
	        url: ''
        }, {
	        title: 'Consul',
	        id: 'cnsl',
	        uniqueId: 'uniqueTab3x',
	        tabPanelId: 'threetab3x',
	        url: ''
        }, {
	        title: 'Kubernetes',
	        id: 'k8s',
	        uniqueId: 'uniqueTab4x',
	        tabPanelId: 'threetab4x',
	        url: ''
        }, {
        	title: 'Prometheus',
	        id: 'prom',
	        uniqueId: 'uniqueTab5x',
	        tabPanelId: 'threetab5x',
	        url: ''
        }, {
        	title: 'DBCL',
	        id: 'dbcl',
	        uniqueId: '',
	        tabPanelId: '',
	        url: ''
        }
    ];

	$rootScope.tenant_cluster_map =
	[
		{
		 tenant: 'dyh1b1902',
		 cluster: ['platform', 'component']
		},
		{
		 tenant: 'DCAE-24256-D-04Central',
		 cluster: ['component']
		},
		{
		 tenant: 'DCAE-24256-D-04Edge',
		 cluster: ['component']
		}
	];

	$rootScope.site_tenant_cluster_list =
		[
			{site: 'dyh1b1902',
			 cfy_url: 'https://ecompc-orcl-dev-s1.ecomp.idns.cip.att.com/console/login',
			 cnsl_url: 'https://http://ecompc-cnsl-dev-s5.ecomp.idns.cip.att.com:8500/ui/',
			 tenant_cluster_map: 
					[
						{
							 tenant: 'dyh1b1902',
							 k8_ip: '32.68.214.62',
							 cluster: 'platform',
							 k8_url: 'https://32.68.214.62:6443/ui',
							 prom_url: 'https://32.68.214.62:30102/graph',
							 grf_url: 'https://32.68.214.62:30101/login'				
						},
						{
							 tenant: 'dyh1b1902',
							 k8_ip: '32.68.214.62',
							 cluster: 'component',
							 k8_url: 'https://32.68.214.62:6443/ui',
							 prom_url: 'https://32.68.214.62:30102/graph',
							 grf_url: 'https://32.68.214.62:30101/login'				
						},
						{
							 tenant: 'DCAE-24256-D-04Central',
							 k8_ip: '32.68.192.81',
							 cluster: 'component',
							 k8_url: 'https://32.68.192.81:6443/ui',
							 prom_url: 'https://32.68.192.81:30102/graph',
							 grf_url: 'https://32.68.192.81:30101/login'				
						},
						{
							 tenant: 'DCAE-24256-D-04Edge',
							 k8_ip: '32.68.194.92',
							 cluster: 'component',
							 k8_url: 'https://32.68.194.92:6443/ui',
							 prom_url: 'https://32.68.194.92:30102/graph',
							 grf_url: 'https://32.68.194.92:30101/login'				
						}
					]
			},
			{site: 'mtn23b',
			 cfy_url: 'https://ecompc-orcl-dev-s2.ecomp.idns.cip.att.com/console/login',
			 cnsl_url: 'https://ecompc-cnsl-dev-s2.ecomp.idns.cip.att.com:8500/ui/',
			 tenant_cluster_map: 
				[
					{
						 tenant: 'mtn23b',
						 k8_ip: '32.68.214.62',
						 cluster: 'platform',
						 k8: 'https://32.68.214.62:6443/ui',
						 prom: 'https://32.68.214.62:30102/graph',
						 grf: 'https://32.68.214.62:30101/login'				
					},
					{
						 tenant: 'mtn23b',
						 k8_ip: '32.68.214.62',
						 cluster: 'component',
						 k8: 'https://32.68.214.62:6443/ui',
						 prom: 'https://32.68.214.62:30102/graph',
						 grf: 'https://32.68.214.62:30101/login'				
					},
					{
						 tenant: 'DCAE-24256-D-04Central',
						 k8_ip: '32.68.192.81',
						 cluster: 'component',
						 k8: 'https://32.68.192.81:6443/ui',
						 prom: 'https://32.68.192.81:30102/graph',
						 grf: 'https://32.68.192.81:30101/login'				
					},
					{
						 tenant: 'DCAE-24256-D-04Edge',
						 k8_ip: '32.68.194.92',
						 cluster: 'component',
						 k8: 'https://32.68.194.92:6443/ui',
						 prom: 'https://32.68.194.92:30102/graph',
						 grf: 'https://32.68.194.92:30101/login'				
					}
				]
			}
		];
		
	$rootScope.tenant_cluster_apps_map =
		[
			{
				 tenant: 'dyh1b1902',
				 k8_ip: '32.68.214.62',
				 cluster: 'platform',
				 k8: 'https://32.68.214.62:6443/ui',
				 prom: 'https://32.68.214.62:30102/graph',
				 grf: 'https://32.68.214.62:30101/login'				
			},
			{
				 tenant: 'dyh1b1902',
				 k8_ip: '32.68.214.62',
				 cluster: 'component',
				 k8: 'https://32.68.214.62:6443/ui',
				 prom: 'https://32.68.214.62:30102/graph',
				 grf: 'https://32.68.214.62:30101/login'				
			},
			{
				 tenant: 'DCAE-24256-D-04Central',
				 k8_ip: '32.68.192.81',
				 cluster: 'component',
				 k8: 'https://32.68.192.81:6443/ui',
				 prom: 'https://32.68.192.81:30102/graph',
				 grf: 'https://32.68.192.81:30101/login'				
			},
			{
				 tenant: 'DCAE-24256-D-04Edge',
				 k8_ip: '32.68.194.92',
				 cluster: 'component',
				 k8: 'https://32.68.194.92:6443/ui',
				 prom: 'https://32.68.194.92:30102/graph',
				 grf: 'https://32.68.194.92:30101/login'				
			}
		];
	
	$rootScope.site_cfy_cnsl_map =
	[
	{site: 'dyh1b1902',
	 cfy: 'https://ecompc-orcl-dev-s1.ecomp.idns.cip.att.com/console/login',
	 cnsl: 'https://http://ecompc-cnsl-dev-s5.ecomp.idns.cip.att.com:8500/ui/'
	},
	{site: 'mtn23b',
	 cfy: 'https://ecompc-orcl-dev-s2.ecomp.idns.cip.att.com/console/login',
	 cnsl: 'https://ecompc-cnsl-dev-s2.ecomp.idns.cip.att.com:8500/ui/'
	}
	]

	//$rootScope.cfyUrl = $rootScope.site_cfy_map[1].cfy;
	
	$rootScope.site_tenant_map =
	[
	{site: 'dyh1b1902',
	 tenant: ['dyh1b1902','DCAE-24256-D-04Central','DCAE-24256-D-04Edge']
	},
	{site: 'mtn23b',
	tenant: ['MTN23b-ECOMP-DEV-S1','DYH1b-FTL3F-DCAE-24256-D-03Central','DYH1b-FTL3F-DCAE-24256-D-03Edge']
	}
	]
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

	var getControllers = function(){
		$scope.ecdapp.isDataLoading = true;
		ControllerService.getControllers().then(function(jsonObj) {
			if (debug)
				$log.debug("verticalComponentController.getControllers succeeded: " + JSON.stringify(jsonObj));
			// Empty
			$scope.controllersList.length = 0;
			// Refill
			jsonObj.filter(function(d) {
				$scope.controllersList.push(d);
				if (d.selected){
					$scope.parent.blueprint_id = d.name;
					$scope.controllerCallDone = true;
				}
				return;
			});
			$scope.ecdapp.isDataLoading = false;
		}, function(error) {
			$scope.ecdapp.isDataLoading = false;
			alert('Failed to load controllers. Please retry.');
			$log.error("verticalComponentController.getControllers failed: " + error);
		});
	};
	
	$rootScope.$on('controllerChange', function(e, d){
		$scope.parent.blueprint_id = d.name;
	});
	
	$scope.ecdapp.loadTable = function(status) {
		$scope.ecdapp.isDataLoading = true;
		
		// Empty list and create the root controller item
		$scope.orgChartData = [];
		
		ExecutionService.getExecutionsByStatus(status).then(
			function(jsonObj) {			
				if (jsonObj.error) {
					$log.error("verticalComponentController.loadTable failed: "
							+ jsonObj.error);
					$scope.ecdapp.isRequestFailed = true;
					$scope.ecdapp.errMsg = jsonObj.error;
				} else {
					$scope.ecdapp.isRequestFailed = false;
					$scope.ecdapp.errMsg = null;
					for (var i=0; i < jsonObj.items.length; i++) {
						$scope.orgChartData.push(jsonObj.items[i]);				
					}
					$scope.$broadcast('listenEvent', {data: $scope.orgChartData} );
					setTimeout(function(){$('.child-item').popover()}, 0);
				}
				$scope.status = status;
				$scope.ecdapp.isDataLoading = false;
			},
			function(error) {
				$log.error("verticalComponentController.loadTable failed: "
						+ error);
				$scope.ecdapp.isRequestFailed = true;
				$scope.ecdapp.errMsg =  error;
				$scope.ecdapp.isDataLoading = false;
			});
	};
	
	$scope.showEcompCInstancesModalPopup = function() {
		var modalInstance = $modal.open({
			templateUrl : 'ecompc_instances_popup.html',
			controller : 'selectEcompcController',
			windowClass: 'modal-docked',
			sizeClass: 'modal-medium',
			resolve : {
				message : function() {
					return { items: $scope.controllersList }
				}
			}
		});
	};

	$scope.showsubDropdown = function(e){
		$('#submenu').toggle();
		e.stopPropagation();
	    e.preventDefault();
	}
	
	$scope.closeSubMenu = function(){
		$('#submenu').css({display:'none'})
	}
	
	var getOpsUrls = function() {
		ControllerService.getOpsUrls().then(function(jsonObj) {
			if (jsonObj.error) {
				$log.error("executionsViewController.getOpsUrls failed: " + jsonObj.error);
			} else {
				//$scope.ecdapp.opsUrls = jsonObj;
				angular.forEach(jsonObj, function(value, key) {
					var found = false;
					angular.forEach($rootScope.opsMenu, function(val2, key2, item) {
						if (!found) {
							if (val2.id === value.id) {
								val2.url = value.url;
								found = true;
							} 
						}
					});
				});
			}			
		}, function(error) {
			$log.error("executionsViewController.getOpsUrls failed: " + error);
		});
		/*
		$scope.ecdapp.opsUrls = [
			{id: 'cfy', url: 'https://ecompc-orcl-dev-s1.ecomp.idns.cip.att.com/console/login'},
			{id: 'grf', url: 'http://ecompc-grfn-dev-s1.ecomp.idns.cip.att.com:30101/login'},
			{id: 'cnsl', url: 'http://ecompc-cnsl-dev-s1.ecomp.idns.cip.att.com:8500/ui/'},
			{id: 'k8s', url: 'https://zldcmtn23aecc1kpma00.809a89.mtn23a.tci.att.com:6443/ui'},
			{id: 'prom', url: 'http://zldcmtn23aecc1kpma00.809a89.mtn23a.tci.att.com:30102/graph'},
			{id: 'dbcl', url: 'http://zldcdyh1bdcc2dokr04.707507.dyh1b.tci.att.com:8080/dmaap-bc-app/login.htm'}];
		 */
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
	//getControllers();
	getOpsUrls();
	//$scope.ecdapp.loadTable('active');
	//$scope.showOrgTable = true;
	//$rootScope.activeTabsId = 'cfy';

});
