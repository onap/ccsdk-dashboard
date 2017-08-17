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
appDS2.config(function($routeProvider) {
	$routeProvider
	.when('/orgchart', {
		/* horizontal layout */
		templateUrl: 'app/ecdapp/home/tree_view.html',
		controller : 'treeViewController'
	})
	.when('/tosca', { 
		templateUrl: 'app/ecdapp/cloudify/tosca_table.html',
		controller : 'toscaTableController'
	})
	.when('/bp', { 
		templateUrl: 'app/ecdapp/cloudify/blueprint_table.html',
		controller : 'blueprintTableController'
	})
	.when('/dep', { 
		templateUrl: 'app/ecdapp/cloudify/deployment_table.html',
		controller : 'deploymentTableController'
	})
	.when('/exe', { 
		templateUrl: 'app/ecdapp/cloudify/execution_table.html',
		controller : 'executionTableController'
	})
	.when('/sh', { 
		templateUrl: 'app/ecdapp/consul/service_health_table.html',
		controller : 'serviceHealthTableController'
	})
	.when('/nh', { 
		templateUrl: 'app/ecdapp/consul/node_table.html',
		controller : 'nodeTableController'
	})
	.when('/dc', { 
		templateUrl: 'app/ecdapp/consul/datacenter_table.html',
		controller : 'datacenterTableController'
	})
	.when('/profile/:profileId', {
		templateUrl: 'app/fusion/scripts/DS2-view-models/ds2-profile/self_profile.html',
		controller: 'selfProfileController'
	})
	.when('/profile_search', {
		templateUrl: 'app/fusion/scripts/DS2-view-models/ds2-profile/profile_searchDS2.html',
		controller : "profileSearchCtrlDS2"
	})
	.when('/post_search', {
		templateUrl: 'app/fusion/scripts/DS2-view-models/ds2-profile/post.html',
		controller: 'postController'
	})
	.when('/self_profile', {
		templateUrl: 'app/fusion/scripts/DS2-view-models/ds2-profile/self_profile.html',
		controller: 'selfProfileController'
	})	
	.when('/role_list', {
		templateUrl: 'app/fusion/scripts/DS2-view-models/ds2-admin/role_list.html',
		controller : 'adminController'
	})
	.when('/role_function_list', {
		templateUrl: 'app/fusion/scripts/DS2-view-models/ds2-admin/role-function.html',
		controller : "adminController"
	})
	.when('/jcs_admin', {
		templateUrl: 'app/fusion/scripts/DS2-view-models/ds2-admin/jcs_admin.html',
		controller: 'adminController'
	})
	.when('/admin_menu_edit', {
		templateUrl: 'app/fusion/scripts/DS2-view-models/ds2-admin/admin-menu-edit.html',
		controller: 'AdminMenuEditController'
	})
	.when('/usage_list', {
		templateUrl: 'app/fusion/scripts/DS2-view-models/ds2-admin/usage.html',
		controller: 'usageListControllerDS2'
	})
	.otherwise({
		templateUrl: 'app/ecdapp/home/executions_view.html',
		controller : 'executionsViewController'
	})
	;

});