/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2017 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ============LICENSE_END=========================================================
 *
 *  ECOMP is a trademark and service mark of AT&T Intellectual Property.
 *******************************************************************************/
appDS2.factory('ServiceHealthService', function ($http, $q, $log) {
	return {
		
		/**
		 * Gets information on all services.
		 */
		getServices: function() {			
			let cc = "?cc=" + new Date().getTime().toString();
			let url = 'healthservices/services' + cc;
			return $http({
					method: 'GET',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('serviceHealthService.getServices: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('serviceHealthService.getServices failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		
		/**
		 * Gets one page of objects.
		 * @param {Number} pageNum - page number; e.g., 1 
		 * @param {Number} viewPerPage - number of items per page; e.g., 25
		 * @return {JSON} Response object from remote side
		 */
		getServicesHealth: function(pageNum, viewPerPage) {
			// cache control for IE
			let cc = "&cc=" + new Date().getTime().toString();
			let url = 'healthservices/serviceshealth?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + cc;
			return $http({
					method: 'GET',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('serviceHealthService.getServicesHealth: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('serviceHealthService.getServicesHealth failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},
		
		/**
		 * Gets history of the specified service between the specified date-times.
		 */
		getServiceHealthHistory: function(serviceName, startDateTime, endDateTime) {
			// encode everything
			let enService = encodeURIComponent(serviceName);
			let enStart = encodeURIComponent(startDateTime);
			let enEnd = encodeURIComponent(endDateTime);
			let url = 'healthservices/svchist/' + enService + '?start=' + enStart + '&end=' + enEnd;
			return $http({
					method: 'GET',
					url: url,
					cache: false,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('serviceHealthService.getServiceHealthHistory: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('serviceHealthService.getServiceHealthHistory failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},

		/**
		 * Registers a service with Consul for health monitoring.
		 */
		registerService: function(request) {
			let url = 'healthservices/register';
			return $http({
					method: 'POST',
					url: url,
					data: request,
					responseType: 'json' 
			}).then(function(response) {
				if (response.data == null || typeof response.data != 'object') 
					return $q.reject('ServiceHealthService.registerService: response.data null or not object');
				else 
					return response.data;
			}, 
			function(error) {
				$log.error('ServiceHealthService.registerService failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		},

		/**
		 * Deregisters a service from Consul.
		 */
		deregisterService: function(serviceName) {
			let url = 'healthservices/deregister/' + serviceName;
			return $http({
					method: 'POST',
					url: url,
					responseType: 'json' 
			}).then(function(response) {
				// This is called on response code 200..299.
				// On success, response.data is null.
				// On failure, response.data has an error message.
				return response.data;
			}, 
			function(error) {
				$log.error('ServiceHealthService.deregisterService failed: ' + JSON.stringify(error));
				return $q.reject(error.statusText);
			});
		}

	};
});
