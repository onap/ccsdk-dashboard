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
    getApiSwaggerSpec: function() {
      return $http({
        method: 'GET',
        url: 'api-docs',
        cache: true,
        responseType: 'json'
      }).then(function(response) {
          return response.data;
      }, 
      function(error) {
        $log.error('ControllerService.getApiSwaggerSpec failed: ' + JSON.stringify(error));
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
		getConsulLink: function() {
      return $http({
        method: 'GET',
        url: 'consul_url',
        cache: false,
        responseType: 'text'
      }).then(function(response) {
        if (response.data == null) 
          return $q.reject('ControllerService.getConsulLink: response.data null or not object');
        else 
          return response.data;
      }, 
      function(error) {
        $log.error('ControllerService.getConsulLink failed: ' + JSON.stringify(error));
        return $q.reject(error.statusText);
      });
		},
		/**
		 * Gets the ops tools info
		 * 
		 */
		getDcaeLinks: function() {
			return $http({
				method: 'GET',
				url: 'dcae_links',
				cache: false,
				responseType: 'text'
			}).then(function(response) {
				if (response.data == null) 
					return $q.reject('ControllerService.getDcaeLinks: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('ControllerService.getDcaeLinks failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},

		getOpsToolUrls: function() {
			return $http({
				method: 'GET',
				url: 'ops_links',
				cache: false,
				responseType: 'json'
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('ControllerService.getOpsToolUrls: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('ControllerService.getOpsToolUrls failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});				
		},
		
		//Get the links from database table along with site/tenant list
		getOpsToolMenuLinks: function() {
			return $http({
				method: 'GET',
				url: 'ops_menu_links',
				cache: false,
				responseType: 'json'
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('ControllerService.getOpsToolMenuLinks: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('ControllerService.getOpsToolMenuLinks failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});				
		},		
		getUserRoleData: function() {
      return $http({
        method: 'GET',
        url: 'user-role-details',
        cache: false,
        responseType: 'json'
      }).then(function(response) {
        if (response.data == null || typeof response.data != 'object') 
          return $q.reject('ControllerService.getUserRoleData: response.data null or not object');
        else 
          return response.data;
      }, 
      function(error) {
        $log.error('ControllerService.getUserRoleData failed: ' + JSON.stringify(error));
        return $q.reject(error.statusText);
      }); 
		},
		getPluginsCount: function() {
      return $http({
        method: 'GET',
        url: 'plugins-count',
        cache: false,
        responseType: 'json'
      }).then(function(response) {
        if (response.data == null || typeof response.data != 'object') 
          return $q.reject('ControllerService.getPluginsCount: response.data null or not object');
        else 
          return response.data;
      }, 
      function(error) {
        $log.error('ControllerService.getPluginsCount failed: ' + JSON.stringify(error));
        return $q.reject(error.statusText);
      }); 		  
		}	
	};
});
