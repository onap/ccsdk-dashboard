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
appDS2.factory('DeploymentService', function ($http, $q, $log) {
	return {
		/**
		 * Gets one page of objects.
		 * @param {Number} pageNum - page number; e.g., 1 
		 * @param {Number} viewPerPage - number of items per page; e.g., 25
		 * @return {JSON} Response object from remote side
		 */
		getDeployments: function(pageNum,viewPerPage) {
			// cache control for IE
			let cc = "&cc=" + new Date().getTime().toString();
			let url = 'deployments?pageNum=' + pageNum + '&viewPerPage=' + viewPerPage + cc;
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
		}
	};
});
