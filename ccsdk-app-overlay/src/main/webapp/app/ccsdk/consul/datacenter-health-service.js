appDS2.factory('DatacenterHealthService', function ($http, $q, $log) {
	return {
		/**
		 * Gets one page of objects.
		 * @param {Number} pageNum - page number; e.g., 1 
		 * @param {Number} viewPerPage - number of items per page; e.g., 25
		 * @return {JSON} Response object from remote side
		 */
		getDatacentersHealth: function(pageNum,viewPerPage) {
			// cache control for IE
			var cc = "&cc=" + new Date().getTime().toString();
			return $http({
					method: 'GET',
					url: 'healthServices/datacenters?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + cc,
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
		
	};
});
