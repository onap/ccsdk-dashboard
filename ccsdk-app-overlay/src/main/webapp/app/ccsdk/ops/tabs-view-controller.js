appDS2.controller('tabsController', function ($rootScope, $scope, $interval, b2bDOMHelper, $timeout, $route) {
	'use strict';
	$scope.ecdapp = {};
	$scope.ecdapp.opsItem = $route.current.$$route.item;
	$scope.ecdapp.activeTabsId = $scope.ecdapp.opsItem ;
	$scope.ecdapp.activeTabUrl = '';
	$scope.ecdapp.isInit = false;
    $scope.ecdapp.cfy = {};
    $scope.ecdapp.cfy.url = ''; //$rootScope.opsMenu[0].url;
    $scope.ecdapp.cfy.site = '';
    $scope.ecdapp.cnsl = {};
    $scope.ecdapp.cnsl.url = ''; //$rootScope.opsMenu[2].url;  
    $scope.ecdapp.k8 = {};
    $scope.ecdapp.k8.site = '';
    $scope.ecdapp.k8.url = ''; //$rootScope.opsMenu[3].url;
    $scope.ecdapp.k8.tenant = '';
    $scope.ecdapp.prom = {};
    $scope.ecdapp.prom.tenant = '';
    $scope.ecdapp.prom.site = '';
    $scope.ecdapp.prom.url = ''; //$rootScope.opsMenu[4].url;
    $scope.ecdapp.grf = {};
    $scope.ecdapp.grf.site = '';
    $scope.ecdapp.grf.tenant = '';
    $scope.ecdapp.grf.url = ''; //$rootScope.opsMenu[1].url;
    $scope.ecdapp.isFrameLoaded = true;
    $scope.ecdapp.cfySite = '';
    $scope.ecdapp.cnslSite = '';
    $scope.ecdapp.appCluster = '';
    $scope.ecdapp.k8.cluster = '';
    $scope.ecdapp.grf.cluster = '';
    $scope.ecdapp.prom.cluster = '';
    
	var key = $scope.ecdapp.opsItem;
	
    // if it's not already part of our keys array
	if($rootScope.menuKeys.indexOf(key) === -1) {
        // add it to our keys array
		$rootScope.menuKeys.push(key);
		for (var itemTab = 0; itemTab < $rootScope.opsMenu.length; itemTab++) {
			if ($rootScope.opsMenu[itemTab].id === key) {
				$rootScope.gTabs.push($rootScope.opsMenu[itemTab]);
				//$scope.ecdapp.activeTabUrl = $rootScope.opsMenu[itemTab].url;
				break;
			}
		}
	}
    $scope.ecdapp.gTabs = $rootScope.gTabs;
	/*
    angular.forEach($rootScope.opsMenu, function(item) {
    	if 
                // we check to see whether our object exists
    	var key = $scope.ecdapp.opsItem;
                // if it's not already part of our keys array
        if($rootScope.menuKeys.indexOf(key) === -1) {
                    // add it to our keys array
        	$rootScope.menuKeys.push(key); 
                    // push this item to our final output array
            $rootScope.gTabs.push(item);
            $scope.ecdapp.activeTabUrl = item.url;
        } else {
        	if (item.id === key) {
        		$scope.ecdapp.activeTabUrl = item.url;
        	}
        }
    });


	for (var menuTab = 0; menuTab < $scope.ecdapp.gTabs.length; menuTab++) {
		if ($scope.ecdapp.gTabs[menuTab].id === key) {
			$scope.ecdapp.activeTabUrl = $scope.ecdapp.gTabs[menuTab].url;
			break;
		}
	}
	    */
    $scope.ecdapp.isInit = true;
    $rootScope.activeTabsId = $scope.ecdapp.opsItem;
    
	$rootScope.$watch('activeTabsId', function (newVal, oldVal) {           
        if(newVal !== oldVal) {
            var selectedTab;
            for (selectedTab = 0; selectedTab < $rootScope.opsMenu.length; selectedTab++) {
                if ($rootScope.opsMenu[selectedTab].id === newVal) {
                	//$scope.ecdapp.activeTabUrl = $rootScope.opsMenu[selectedTab].url;
                    break;
                }
            }
            var selectedTabPanelElement = document.getElementById($rootScope.opsMenu[selectedTab].tabPanelId);

            var elem = null;
            if (selectedTabPanelElement) {
                elem = b2bDOMHelper.firstTabableElement(selectedTabPanelElement);
            }

            if (elem) {
                $timeout(function () {
                    elem.focus();
                }, 100);
            }
        }
	});
	

	$scope.ecdapp.selectAppTenant = function(site) {
		if(site != "Select Site") {
			for (var indx = 0; indx < $rootScope.site_tenant_map.length; indx++) {
				if ($rootScope.site_tenant_map[indx].site === site) {
					$scope.ecdapp.appTenants = $rootScope.site_tenant_map[indx].tenant;
					break;
				}
			}
		}
	}

	$scope.ecdapp.selectCluster = function(tenant) {
		if(tenant != "Select Tenant") {
			for (var indx = 0; indx < $rootScope.tenant_cluster_map.length; indx++) {
				if ($rootScope.tenant_cluster_map[indx].tenant === tenant) {
					$scope.ecdapp.appCluster = $rootScope.tenant_cluster_map[indx].cluster;
				}
			}
		}
	}
	
	var stopPolling;
	 //var doIframePolling;
	$scope.ecdapp.appFrameReload = function(cluster, app) {
		if(cluster != "Select K8s cluster") {
			$scope.ecdapp.isFrameLoaded = false;
			for (var indx = 0; indx < $rootScope.tenant_cluster_apps_map.length; indx++) {
				if ($rootScope.tenant_cluster_apps_map[indx].cluster === cluster) {
					if (app === 'prom') {
					$scope.ecdapp.prom.url = $rootScope.tenant_cluster_apps_map[indx].prom;
					} else if (app === 'grf') {
					$scope.ecdapp.grf.url = $rootScope.tenant_cluster_apps_map[indx].grf;
					} else {
					$scope.ecdapp.k8.url = $rootScope.tenant_cluster_apps_map[indx].k8;
					}
					break;
				}
			}
			stopPolling = $timeout(function () {
		        $timeout.cancel(stopPolling);
		        stopPolling = undefined;
		        $scope.ecdapp.isFrameLoaded = true;     
			},30000);
			}
	}
	
	$scope.ecdapp.cfyCnslFrameReload = function(site, app) {
		if(site != "Select Site") {
			$scope.ecdapp.isFrameLoaded = false;
			for (var indx = 0; indx < $rootScope.site_cfy_cnsl_map.length; indx++) {
				if ($rootScope.site_cfy_cnsl_map[indx].site === site) {
					if (app === 'cfy') {
						$scope.ecdapp.cfy.url = $rootScope.site_cfy_cnsl_map[indx].cfy;
					} else {
						$scope.ecdapp.cnsl.url = $rootScope.site_cfy_cnsl_map[indx].cnsl;
					}
					break;
				}
			}
			stopPolling = $timeout(function () {
		        $timeout.cancel(stopPolling);
		        stopPolling = undefined;
		        $scope.ecdapp.isFrameLoaded = true;     
			},30000);
		}
	}
	document.querySelector("iframe").addEventListener("load", function() {
			$scope.ecdapp.isFrameLoaded = true;
			$scope.$apply();
		});
	 $scope.$on("$destroy",function() {
	        $timeout.cancel(stopPolling);
	        //$interval.cancel(doIframePolling);
	 });
	 
		/*
		 * 	$scope.ecdapp.selectK8Tenant = function(site) {
			if(site != "Select Site") {
				for (var indx = 0; indx < $rootScope.site_tenant_map.length; indx++) {
					if ($rootScope.site_tenant_map[indx].site === site) {
						$scope.ecdapp.k8Tenants = $rootScope.site_tenant_map[indx].tenant;
						break;
					}
				}
			}
		}
		
		$scope.ecdapp.selectK8App = function(t) {
			if(t != "Select Tenant for K8s components") {
			for (var indx = 0; indx < $rootScope.tenant_cluster_map.length; indx++) {
				if ($rootScope.tenant_cluster_map[indx].tenant === t) {
					$scope.ecdapp.k8.url = $rootScope.tenant_cluster_map[indx].k8s;
					break;
				}
			}
			}
		}

		$scope.ecdapp.selectGrfTenant = function(site) {
			if(site != "Select Site") {
			for (var indx = 0; indx < $rootScope.site_tenant_map.length; indx++) {
				if ($rootScope.site_tenant_map[indx].site === site) {
					$scope.ecdapp.grfTenants = $rootScope.site_tenant_map[indx].tenant;
					break;
				}
			}
			}
		}

		$scope.ecdapp.selectGrfApp = function(t) {
			if(t != "Select Tenant for Grafana") {
			for (var indx = 0; indx < $rootScope.tenant_cluster_map.length; indx++) {
				if ($rootScope.tenant_cluster_map[indx].tenant === t) {
					$scope.ecdapp.grf.url = $rootScope.tenant_cluster_map[indx].grf;
					break;
				}
			}
			}
		}
		
		$scope.ecdapp.selectPromTenant = function(site) {
			if(site != "Select Site") {
			for (var indx = 0; indx < $rootScope.site_tenant_map.length; indx++) {
				if ($rootScope.site_tenant_map[indx].site === site) {
					$scope.ecdapp.promTenants = $rootScope.site_tenant_map[indx].tenant;
					break;
				}
			}
			}
		}
		
		$scope.ecdapp.selectPromApp = function(t) {
			if(t != "Select Tenant for Prometheus") {
			for (var indx = 0; indx < $rootScope.tenant_cluster_map.length; indx++) {
				if ($rootScope.tenant_cluster_map[indx].tenant === t) {
					$scope.ecdapp.prom.url = $rootScope.tenant_cluster_map[indx].prom;
					break;
				}
			}
			}
		}

		 doIframePolling = $interval(function () {
			    if(document.querySelector("iframe") && 
			    		document.querySelector("iframe").contentDocument.head && 
			    		document.querySelector("iframe").contentDocument.head.innerHTML != '')
			    {
			        $interval.cancel(doIframePolling);
			        doIframePolling = undefined;
			        $timeout.cancel(stopPolling);
			        stopPolling = undefined;
			        $scope.ecdapp.isCfyLoadDone = true;
			    }
			},400);

			stopPolling = $timeout(function () {
			        //$interval.cancel(doIframePolling);
			        //doIframePolling = undefined;
			        $timeout.cancel(stopPolling);
			        stopPolling = undefined;
			        $scope.ecdapp.isCfyLoadDone = true;     
			},30000);
		}
	}
	
	*/


});