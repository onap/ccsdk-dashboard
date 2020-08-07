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
appDS2.config(function($routeProvider) {
  $routeProvider 
  .when('/api', { 
    templateUrl: 'app/ccsdk/home/api_view.html',
    controller : ''
  })
  .when('/api-spec', { 
    templateUrl: 'app/ccsdk/home/rest-api-spec.html',
    controller : 'apiDocsController'
  })
  .when('/ibp', { 
    templateUrl: 'app/ccsdk/inventory/inventory_blueprint_table.html',
    controller : 'inventoryBlueprintTableController'
  })
  .when('/ibp/:bpId', { 
    templateUrl: 'app/ccsdk/inventory/inventory_blueprint_table.html',
    controller : 'inventoryBlueprintTableController'
  })
  .when('/idep', { 
    templateUrl: 'app/ccsdk/cloudify/deployment_table.html',
    controller : 'deploymentTableController'
  })
  .when('/idep/:depId', { 
    templateUrl: 'app/ccsdk/cloudify/deployment_table.html',
    controller : 'deploymentTableController'
  })
  .when('/iplug', { 
    templateUrl: 'app/ccsdk/cloudify/plugin_table.html',
    controller : 'PluginsTableController'
  })
  .when('/sh', { 
    templateUrl: 'app/ccsdk/consul/service_health_table.html',
    controller : 'serviceHealthTableController'
  })
  .when('/nh', { 
    templateUrl: 'app/ccsdk/consul/node_table.html',
    controller : 'nodeTableController'
  })
  .when('/dc', { 
    templateUrl: 'app/ccsdk/consul/datacenter_table.html',
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
    templateUrl: 'app/ccsdk/home/executions_view.html',
    controller : 'executionsViewController'
  })
  ;

});