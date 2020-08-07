appDS2.factory('PluginService', function ($http, $q, $log) {
	return {
		/**
		 * Gets one page of objects.
		 * @param {Number} pageNum - page number; e.g., 1 
		 * @param {Number} viewPerPage - number of items per page; e.g., 25
		 * @return {JSON} Response object from remote side
		 */
		getPlugins: function(pageNum,viewPerPage) {
			// cache control for IE
			let cc = "&cc=" + new Date().getTime().toString();
			let url = 'plugins?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + cc;
			return $http({
					method: 'GET',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('PluginService.getPlugins: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('PluginService.getPlugins failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		}
		
	};
});
