appDS2.factory('NodeHealthService', function ($http, $q, $log) {
	return {
		/**
		 * Get all the datacenter names
		 */
		getDatacentersHealth: function(pageNum,viewPerPage) {
			// cache control for IE
			var cc = "&cc=" + new Date().getTime().toString();
			return $http({
					method: 'GET',
					url: 'healthservices/datacenters',
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('DatacentersService.getDatacentersHealth: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('DatacenterService.getDatacentersHealth failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		/**
		 * Gets one page of objects.
		 * @param {Number} pageNum - page number; e.g., 1 
		 * @param {Number} viewPerPage - number of items per page; e.g., 25
		 * @return {JSON} Response object from remote side
		 */
		getNodesHealth: function(pageNum,viewPerPage, dc) {
			// cache control for IE
			let cc = "&cc=" + new Date().getTime().toString();
			let url = 'healthservices/nodes?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage +  
			'&dc=' + dc +cc;
			return $http({
					method: 'GET',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('nodeHealthService.getNodesHealth: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('nodeHealthService.getNodesHealth failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		
		/**
		 * Gets all services on the specified node.
		 * @param {nodeName} node name (not the UUID)
		 * @return {JSON} Response object from remote side
		 */
		getNodeServicesHealth: function(nodeName, dc) {
			let url = 'healthservices/nodes/' + nodeName + '?dc=' + dc;
			return $http({
					method: 'GET',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('serviceHealthService.getNodeServicesHealth: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('serviceHealthService.getNodeServicesHealth failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		
	};
});
