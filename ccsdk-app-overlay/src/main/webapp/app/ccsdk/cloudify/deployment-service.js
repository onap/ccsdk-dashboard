appDS2.factory('DeploymentService', function ($http, $q, $log) {
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
	        url = 'deployments?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + '&sortBy=' + sortBy + '&searchBy=' + searchBy + cc;
	    } else if (sortBy) {
	        url = 'deployments?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + '&sortBy=' + sortBy + cc;
	    } else if (searchBy) {
			    url = 'deployments?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + '&searchBy=' + searchBy + cc;
			} else {
			    url = 'deployments?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage +cc;
			}
			return $http({
					method: 'GET',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('DeploymentService.getDeployments: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('DeploymentService.getDeployments failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		
		deployBlueprint: function(deployRequest) {
			let url = 'deployments';
			return $http({
					method: 'POST',
					url: url,
					data: deployRequest,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('DeploymentService.deployBlueprint: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('DeploymentService.deployBlueprint failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		
		deleteDeployment: function(id, forceDelete) {
			let url = 'deployments/' + id + '?ignore_live_nodes=' + forceDelete;
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
				$log.error('DeploymentService.deleteDeployment failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		
		getBlueprintContent: function(id, tenant) {
		  let url ='blueprints/' + id + '/archive?tenant=' + tenant;
      return $http({
        method: 'GET',
        url: url,
        cache: false,
        responseType: 'yaml' 
    }).then(function(response) {
      // This is called on response code 200..299.
      // On success, response.data is null.
      // On failure, response.data has an error message.
/*      var contentType = "application/octet-stream";
      var data = response.data
      var urlCreator = window.URL || window.webkitURL || window.mozURL || window.msURL;
      if (urlCreator) {
          var blob = new Blob([data], { type: contentType });
          var url = urlCreator.createObjectURL(blob);
          var a = document.createElement("a");
          document.body.appendChild(a);
          a.style = "display: none";
          a.href = url;
          a.download = "blueprint.yaml"; //you may assign this value from header as well 
          a.click();
          window.URL.revokeObjectURL(url);
      }*/
      return response;
    }, 
    function(error) {
      $log.error('DeploymentService.getBlueprintContent failed: ' + JSON.stringify(error));
      return $q.reject(error.statusText);
    });
		},
		
		reconfigFlow: function(reconfigRequest) {
		  let body = {
		      "deployment_id": reconfigRequest.deployment_id,
		      "workflow_id": reconfigRequest.workflow_id,
		      "allow_custom_parameters": true,
		      "force": true,
		      "tenant": reconfigRequest.tenant,
		      "parameters": reconfigRequest.parameters
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
		      return $q.reject('DeploymentService.reconfigFlow: response.data null or not object');
		    else {
		      console.log(response);
		      return response.data;
		    }
		  },
		  function(error) {
		    $log.error('DeploymentService.reconfigFlow failed: ' + JSON.stringify(error));
		    return $q.reject(error.statusText);
		  });
		}
	};
});
