appDS2.factory('InventoryBlueprintService', function ($http, $q, $log) {
	return {
		/**
		 * Gets one page of blue prints objects.
		 * @param {Number} pageNum - page number; e.g., 1 
		 * @param {Number} viewPerPage - number of items per page; e.g., 25
		 * @return {JSON} Response object from remote side
		 */
		getBlueprints: function(pageNum,viewPerPage,sortBy,searchBy) {
			// cache control for IE
			let  cc = "&cc=" + new Date().getTime().toString();
			let url = null;
			if (sortBy && searchBy) {
				url = 'inventory/dcae-service-types?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + '&sortBy=' + sortBy + '&searchBy=' + searchBy + cc;
			} else if (sortBy) {
				url = 'inventory/dcae-service-types?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + '&sortBy=' + sortBy + cc;
			} else if (searchBy) {
				url = 'inventory/dcae-service-types?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + '&searchBy=' + searchBy + cc;
			} else {
				url = 'inventory/dcae-service-types?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + cc;
			}
			return $http({
					method: 'GET',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('InventoryBlueprintService.getBlueprints: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('InventoryBlueprintService.getBlueprints failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		getBlueprintsSummary: function() {
      // cache control for IE
      let  cc = "&cc=" + new Date().getTime().toString();
      let url = null;
      url = 'inventory/dcae-service-types?_include=typeName,typeVersion,typeId' + cc;
      return $http({
          method: 'GET',
          url: url,
          cache: false,
          responseType: 'json' 
      }).then(function(response) {
        if (response.data == null || typeof response.data != 'object') 
          return $q.reject('InventoryBlueprintService.getBlueprintsSummary: response.data null or not object');
        else 
          return response.data;
      }, 
      function(error) {
        $log.error('InventoryBlueprintService.getBlueprintsSummary failed: ' + JSON.stringify(error));
        return $q.reject(error.statusText);
      });
    },
    getOwnersList: function() {
      let url = 'inventory/owners';
      return $http({
        method: 'GET',
        url: url,
        cache: false,
        responseType: 'json' 
      }).then(function(response) {
        if (response.data == null || typeof response.data != 'object') 
          return $q.reject('InventoryBlueprintService.getOwnersList: response.data null or not object');
        else 
          return response.data;
      }, 
      function(error) {
        $log.error('InventoryBlueprintService.getOwnersList failed: ' + JSON.stringify(error));
        return $q.reject(error.statusText);
      });
    },
		getBlueprintsList: function() {
      let url = 'inventory/service-type-list';
      return $http({
        method: 'GET',
        url: url,
        cache: false,
        responseType: 'json' 
      }).then(function(response) {
        if (response.data == null || typeof response.data != 'object') 
          return $q.reject('InventoryBlueprintService.getBlueprintsList: response.data null or not object');
        else 
          return response.data;
      }, 
      function(error) {
        $log.error('InventoryBlueprintService.getBlueprintsList failed: ' + JSON.stringify(error));
        return $q.reject(error.statusText);
      });
    },
    getBlueprintIdsList: function(searchBy) {
      let url = '';
      if (searchBy) {
        url = 'inventory/service-type-id-list?searchBy=' + searchBy;
      } else {
        url = 'inventory/service-type-id-list';
      }
      return $http({
        method: 'GET',
        url: url,
        cache: false,
        responseType: 'json' 
      }).then(function(response) {
        if (response.data == null || typeof response.data != 'object') 
          return $q.reject('InventoryBlueprintService.getBlueprintIdsList: response.data null or not object');
        else 
          return response.data;
      }, 
      function(error) {
        $log.error('InventoryBlueprintService.getBlueprintIdsList failed: ' + JSON.stringify(error));
        return $q.reject(error.statusText);
      });
    },
		/**
		 *  get deployments for a blueprint
		 */
		getDeploymentForBp: function(bpArr) {
		  let url = 'deployment_blueprint_map';
			//let url = 'inventory/dcae-services/typeIds';	
			return $http({
				method: 'POST',
				url: url,
				data: bpArr,
				responseType: 'json' 
				}).then(function(response) {
			if (response.data == null) 
				return $q.reject('InventoryBlueprintService.getDeploymentForBp: response.data null or not object');
			else {
                return response.data;
			}
            },
			function(error) {
				$log.error('InventoryBlueprintService.getDeploymentForBp failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		/** get all secrets
		 * 
		 */
		getSecrets: function(tenant_name) {
			let url = 'secrets?tenant=' + tenant_name;
			return $http({
				method: 'GET',
				url: url,
				cache: false,
				responseType: 'json' 
		}).then(function(response) {
			if (response.data == null) 
				return $q.reject('InventoryBlueprintService.getSecrets: response.data null or not object');
			else {
                console.log(response.data);
                return response.data;
            }
		}, 
		function(error) {
			$log.error('InventoryBlueprintService.getSecrets failed: ' + JSON.stringify(error));
			return $q.reject(error.statusText);
		});
		},
		/** get secret
		 * 
		 */
		getSecret: function(secret_name, tenant_name) {
			let url = 'secrets/' + secret_name + '?tenant=' + tenant_name;
			return $http({
				method: 'GET',
				url: url,
				cache: false,
				responseType: 'json' 
		}).then(function(response) {
			if (response.data == null) 
				return $q.reject('InventoryBlueprintService.getSecret: response.data null or not object');
			else {
                console.log(response.data);
                return response.data;
            }
		}, 
		function(error) {
			$log.error('InventoryBlueprintService.getSecret failed: ' + JSON.stringify(error));
			return $q.reject(error.statusText);
		});
		},
		/**
		 * create a cloudify secret
		 */
		createSecret: function(secretUploadRequest) {
			let url = 'secrets';
			return $http({
				method: 'POST',
				url: url,
				data: secretUploadRequest,
				responseType: 'json' 
		}).then(function(response) {
			// This is called on response code 200..299.
			// On success, response.data is null.
			// On failure, response.data has an error message.
			return response.data;
		}, 
		function(error) {
			$log.error('InventoryDeploymentService.createSecret failed: ' + JSON.stringify(error));
			return $q.reject(error.statusText);
		});
		},
		/**
		 * Get user apps
		 * @return {JSON} Response object from remote side
		 */
		getApps: function() {
			// cache control for IE
			let url = 'user-apps';
			return $http({
					method: 'GET',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null) 
					return $q.reject('InventoryBlueprintService.getApps: response.data null or not object');
				else {
                    console.log(response.data);
                    return response.data;
                }
			}, 
			function(error) {
				$log.error('InventoryBlueprintService.getApps failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		/**
		 * Gets blueprint components.
		 * @return {JSON} Response object from remote side
		 */
		getComponents: function() {
			// cache control for IE
			let url = 'components';
			return $http({
					method: 'GET',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null) 
					return $q.reject('InventoryBlueprintService.viewBlueprint: response.data null or not object');
				else {
                    return response.data;
                }
			}, 
			function(error) {
				$log.error('InventoryBlueprintService.getComponents failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},

		insertComponent: function(componentName, displayName) {
			let url = 'components';
			let component = {
				"cname": componentName,
				"dname": displayName
			};
            return $http({
                method: 'POST',
                url: url,
				data: component,
                cache: false,
                responseType: 'json'
            }).then(function(response) {
                    if (response.data == null)
                        return $q.reject('InventoryBlueprintService.insertComponent: response.data null or not object');
                    else {
                        console.log(response);
                        return response.data;
                    }
                },
                function(error) {
                    $log.error('InventoryBlueprintService.insertComponent failed: ' + JSON.stringify(error));
                    return $q.reject(error.statusText);
                });
		},
		
		/**
		 * Gets blueprint content.
		 * @return {JSON} Response object from remote side
		 */
		viewBlueprint: function(typeId) {
			// cache control for IE
			let url = 'inventory/dcae-service-type-blueprint/' + typeId;
			return $http({
					method: 'GET',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null) 
					return $q.reject('InventoryBlueprintService.viewBlueprint: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('InventoryBlueprintService.viewBlueprint failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},

		updateBlueprint: function(serviceType) {
			let url = 'inventory/dcae-service-types/update';
			return $http({
					method: 'POST',
					url: url,
					data: serviceType,
					responseType: 'json' 
			}).then(function(response) {
				// This is called on response code 200..299.
				// On success, response.data is null.
				// On failure, response.data has an error message.
				return response.data;
			}, 
			function(error) {
				$log.error('InventoryBlueprintService.updateBlueprint failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		
		uploadBlueprint: function(serviceTypeRequest) {
			let url = 'inventory/dcae-service-types/upload';
		    let deferred = $q.defer();
		    $http({
		       method: "POST",
		       url: url,
		       data: serviceTypeRequest
		    })
		    .then( res => {
		                       deferred.resolve(res.data);
		         })
		    .catch( status => {
		                    $log.error('InventoryBlueprintService.uploadBlueprint failed: ' + status);
		                    deferred.reject(status);
		                });
		      return deferred.promise;
		},
/*
			return $http({
					method: 'POST',
					url: url,
					data: serviceTypeRequest,
					responseType: 'json' 
			}).then(function(response) {
				// This is called on response code 200..299.
				// On success, response.data is null.
				// On failure, response.data has an error message.
				return response.data;
			}, 
			function(error) {
				$log.error('InventoryBlueprintService.uploadBlueprint failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},	
*/		
		deleteBlueprint: function(typeId) {
		  /*var typeId = blueprint.typeId;
		  var deploymentItems = blueprint.deployments.items;
		  var deplIdList = [];
		  for (var i=0; i < deploymentItems.length; i++) {
		    deplIdList.push(deploymentItems.id);
		  }*/
			let url = 'inventory/dcae-service-types/' + typeId;
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
		
	};
});