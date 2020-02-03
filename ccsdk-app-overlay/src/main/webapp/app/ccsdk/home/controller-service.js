/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2020 AT&T Intellectual Property. All rights reserved.
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
 *******************************************************************************/

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
				url: 'nb-api/api-docs',
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
