appDS2.factory('InventoryExecutionService', function ($http, $q, $log) {
	return {
		/**
		 * Gets one page of objects.
		 * @param {Number} pageNum - page number; e.g., 1 
		 * @param {Number} viewPerPage - number of items per page; e.g., 25
		 * @return {JSON} Response object from remote side
		 */
		getExecutionsByDeployment: function(deploymentId, tenant, pageNum, viewPerPage) {
			// cache control for IE
			let cc = "&cc=" + new Date().getTime().toString();
			let url = 'executions?deployment_id=' + deploymentId + '&pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + '&tenant=' + tenant + cc;
			return $http({
					method: 'GET',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('InventoryExecutionService.getExecutionsByDeployment: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('InventoryExecutionService.getExecutionsByDeployment failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		getEventsByExecution: function(executionId, isLogType, tenant, pageNum, viewPerPage) {
			let cc = "&cc=" + new Date().getTime().toString();
			let url = 'events?execution_id=' + executionId + '&logType=' + isLogType + '&tenant=' + tenant + '&pageNum=' + pageNum + '&viewPerPage=' + viewPerPage +cc;
			return $http({
				method: 'GET',
				url: url,
				cache: false,
				responseType: 'json' 
		}).then(function(response) {
			if (response.data == null || typeof response.data != 'object') 
				return $q.reject('InventoryExecutionService.getExecutionsByDeployment: response.data null or not object');
			else 
				return response.data;
		}, 
		function(error) {
			$log.error('InventoryExecutionService.getExecutionsByDeployment failed: ' + JSON.stringify(error));
			return $q.reject(error.statusText);
		});
		},
		cancelExecution: function(execution_id, dep_id, action, tenant) {
			let url = 'executions/' + execution_id;
			let body = {
					"deployment_id": dep_id,
					"action": action};
			let hdrs = {"tenant": tenant};
			
			return $http({
					method: 'POST',
					url: url,
					data: body,
					headers: hdrs,
					responseType: 'json' 
			}).then(function(response) {
				// This is called on response code 200..299.
				// On success, response.data is null.
				// On failure, response.data has an error message.
				return response.data;
			}, 
			function(error) {
				$log.error('InventoryExecutionService.cancelExecution failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		}
	};
});
