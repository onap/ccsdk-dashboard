appDS2.factory('InventoryDeploymentService', function ($http, $q, $log) {
	return {
		/**
		 * Gets one page of objects.
		 * @param {Number} pageNum - page number; e.g., 1 
		 * @param {Number} viewPerPage - number of items per page; e.g., 25
		 * @return {JSON} Response object from remote side
		 */
		getDeployments: function(pageNum,viewPerPage,sortBy,searchBy) {
			// cache control for IE
			let cc = "&cc=" + new Date().getTime().toString();
			let url = null;
			if (sortBy && searchBy) {
				url = 'inventory/dcae-services?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + '&sortBy=' + sortBy + '&searchBy=' + searchBy + cc;
			} else if (sortBy) {
				url = 'inventory/dcae-services?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + '&sortBy=' + sortBy + cc;
			} else if (searchBy) {
				url = 'inventory/dcae-services?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + '&searchBy=' + searchBy + cc;
			} else {
				url = url = 'inventory/dcae-services?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + cc;
			}
			return $http({
					method: 'GET',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('InventoryDeploymentService.getDeployments: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('InventoryDeploymentService.getDeployments failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		getDeploymentStatus: function(srvcIds) {
			let url = 'deployment-status';	
			return $http({
				method: 'POST',
				url: url,
				data: srvcIds,
				responseType: 'json' 
				}).then(function(response) {
			if (response.data == null) 
				return $q.reject('InventoryDeploymentService.getDeploymentStatus: response.data null or not object');
			else {
                console.log(response.data);
                return response.data;
			}
            },
			function(error) {
				$log.error('InventoryDeploymentService.getDeploymentStatus failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});			
		},
		getDeployment: function(deploymentRef, tenant) {
			let url = 'deployments/' + deploymentRef + '?tenant=' + tenant;
			return $http({
					method: 'GET',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('InventoryDeploymentService.getDeployment: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('InventoryDeploymentService.getDeployment failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		
		deployBlueprint: function(deploymentRequestObject) {
			let url = 'deploymenthandler/dcae-deployments/' + deploymentRequestObject.deploymentId;
			return $http({
					method: 'PUT',
					url: url,
					data: deploymentRequestObject,
					responseType: 'json' 
			}).then(function(response) {
				// This is called on response code 200..299.
				// On success, response.data is null.
				// On failure, response.data has an error message.
				return response.data;
			}, 
			function(error) {
				$log.error('InventoryDeploymentService.deployBlueprint failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},	
		deleteDeployment: function(deploymentRef, tenant) {
			let url = 'deploymenthandler/dcae-deployments/' + deploymentRef + '?tenant=' + tenant;
			return $http({
					method: 'DELETE',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				// This is called on response code 200..299.
				// On success, response.data is null.
				// On failure, response.data has an error message.
				return response.data;
			}, 
			function(error) {
				$log.error('InventoryDeploymentService.deleteDeployment failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		deleteService: function(serviceId) {
			let url = 'inventory/dcae-services/' + serviceId;
			return $http({
					method: 'DELETE',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				// This is called on response code 200..299.
				// On success, response.data is null.
				// On failure, response.data has an error message.
				return response.data;
			}, 
			function(error) {
				$log.error('InventoryBlueprintService.deleteBlueprint failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		getBlueprint: function(blueprintId, tenant) {
			let url = 'blueprints/' + blueprintId + '?tenant=' + tenant;
			//console.log("url: " + url);
			return $http({
				method: 'GET',
				url: url,
				cache: false,
				responseType: 'json'
			}).then(function(response) {
				//console.log("entering .then of getNodeId");
				if (response.data == null)  
					return $q.reject('InventoryDeploymentService.getNodeId: response.data null or not object');
				else {
					console.log('%c RETRIEVED BLUEPRINT FOR ID ' + blueprintId, 'color: blue; font-weight: bold;');
					console.log(response.data);
					return response.data;
				}
			},
			function(error) {
				$log.error('InventoryDeploymentService.getNodeId failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		getNodeInstanceVersions: function(deploymentId, tenant) {
			console.log("entered getNodeInstanceVersions service function");
			let url = 'deployments/' + deploymentId + '/revisions' + '?tenant=' + tenant;
			return $http({
				method: 'GET',
				url: url,
				cache: false,
				responseType: 'json'
			}).then(function(response) {
				if (response.data == null) 
					return $q.reject('InventoryDeploymentService.getNodeInstanceId: response.data null or not object');
				else {
					return response.data;
				}
			},
			function(error) {
				$log.error('InventoryDeploymentService.getNodeInstanceId failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},	
		getNodeInstanceId: function(deploymentId, nodeId, tenant) {
			console.log("entered getNodeInstanceId service function");
			let url = 'node-instances/' + deploymentId + '/' + nodeId + '?tenant=' + tenant;
			return $http({
				method: 'GET',
				url: url,
				cache: false,
				responseType: 'json'
			}).then(function(response) {
				if (response.data == null) 
					return $q.reject('InventoryDeploymentService.getNodeInstanceId: response.data null or not object');
				else {
					return response.data;
				}
			},
			function(error) {
				$log.error('InventoryDeploymentService.getNodeInstanceId failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},	
		updateResources: function(deploymentId, nodeInstanceId, resource_definition_changes) {
			let body = {
				"deployment_id": deploymentId,
				"workflow_name": "update_resource_definition",
				"allow_custom_parameter": true,
				"force": true,
				"node_instance_id": nodeInstanceId,
				"limits_cpu": resource_definition_changes.limits_cpu + resource_definition_changes.cpuSize,
				"limits_mem": resource_definition_changes.limits_mem + resource_definition_changes.memSize,
				"image": resource_definition_changes.image,
				"replicas": resource_definition_changes.replicas,
				"container_name": resource_definition_changes.container_name
			};
			let url = 'update-deployment';
			return $http({
				method: 'POST',
				url: url,
				data: body,
				cache: false,
				responseType: 'json'
			}).then(function(response) {
				if (response.data == null) 
					return $q.reject('InventoryDeploymentService.updateResources: response.data null or not object');
				else {
					console.log(response);
					return response.data;
				}
			},
			function(error) {
				$log.error('InventoryDeploymentService.updateResources failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		helmStatusFlow: function(helmStatusRequest) {
			let body =
				{
					"deployment_id": helmStatusRequest.deployment_id,
					"workflow_id": "status",
					"allow_custom_parameters": false,
					"force": false,
					"tenant": helmStatusRequest.tenant
				};
			let urlStr = 'executions';
			return $http({
				method: 'POST',
				url: urlStr,
				data: body,
				cache: false,
				responseType: 'json'
			}).then(function(response) {
				if (response.data == null) 
					return $q.reject('InventoryDeploymentService.helmStatusFlow: response.data null or not object');
				else {
					console.log(response);
					return response.data;
				}
			},
			function(error) {
				$log.error('InventoryDeploymentService.helmStatusFlow failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		rollbackFlow: function(rollbackRequest) {
			let body =
			{
				"deployment_id": rollbackRequest.deployment_id,
				"workflow_id": "rollback",
				"allow_custom_parameters": false,
				"force": false,
				"tenant": rollbackRequest.tenant,
				"parameters" : 
					{ 
						"revision": rollbackRequest.revision
					}
			};
			let urlStr = 'executions';
			return $http({
				method: 'POST',
				url: urlStr,
				data: body,
				cache: false,
				responseType: 'json'
			}).then(function(response) {
				if (response.data == null) 
					return $q.reject('InventoryDeploymentService.rollbackFlow: response.data null or not object');
				else {
					console.log(response);
					return response.data;
				}
			},
			function(error) {
				$log.error('InventoryDeploymentService.rollbackFlow failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		upgradeFlow: function(deploymentId, nodeInstanceId, resource_definition_changes, tenant) {
			let configUrl =  resource_definition_changes["config-url"];
			let configFormat = resource_definition_changes["config-format"];
			let chartUrl = resource_definition_changes["chart-repo-url"];
			let chartVersion = resource_definition_changes["chart-version"];
			let body = {
				"deployment_id": deploymentId,
				"workflow_id": "upgrade",
				"allow_custom_parameters": true,
				"force": true,
				"tenant": tenant,
				"parameters": {
					"node_instance_id": nodeInstanceId,
					"config_url": configUrl,
					"config_format": configFormat,
					"chartRepo": chartUrl,
					"chartVersion": chartVersion
				}
			};
			let urlStr = 'executions';
			return $http({
				method: 'POST',
				url: urlStr,
				data: body,
				cache: false,
				responseType: 'json'
			}).then(function(response) {
				if (response.data == null) 
					return $q.reject('InventoryDeploymentService.updateResources: response.data null or not object');
				else {
					console.log(response);
					return response.data;
				}
			},
			function(error) {
				$log.error('InventoryDeploymentService.updateResources failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		}
	};
});