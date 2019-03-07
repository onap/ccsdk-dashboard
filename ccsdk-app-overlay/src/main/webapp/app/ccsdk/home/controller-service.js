appDS2.factory('ControllerService', function ($http, $q, $log) {
	return {
		/**
		 * Get the application display label
		 * 
		 */
		getAppLabel: function() {
			return $http({
				method: 'GET',
				url: 'app-label',
				cache: true,
				responseType: 'text'
			}).then(function(response) {
					return response.data;
			}, 
			function(error) {
				$log.error('ControllerService.getAppLabel failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		/**
		 * Gets the cloudify tenant names.
		 * 
		 */
		getTenants: function() {
			return $http({
				method: 'GET',
				url: 'tenants',
				cache: false,
				responseType: 'json'
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('ControllerService.getControllers: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('ControllerService.getControllers failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		/**
		 * Gets the ops tools info
		 * 
		 */
		getOpsUrls: function() {
			return $http({
				method: 'GET',
				url: 'ops',
				cache: true,
				responseType: 'json'
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('ControllerService.getOpsUrls: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('ControllerService.getOpsUrls failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		/**
		 * Gets ECOMP-C Endpoint objects.
		 * No provision for pagination here.
		 * @return {JSON} Response object from remote side
		 */
		getControllers: function() {
			// cache control for IE
			var cc = "?cc=" + new Date().getTime().toString();
			return $http({
					method: 'GET',
					url: 'controllers' + cc,
					cache: true,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('ControllerService.getControllers: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('ControllerService.getControllers failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		
		setControllerSelection: function(row) {
			// $log.debug('ControllerService.setControllerSelection: invoked with ' + JSON.stringify(row));
			return $http({
					method: 'POST',
					url: 'controllers',
					data: row,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('ControllerService.setControllerSelection: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('ControllerService.setControllerSelection failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		}		
		
	};
});
