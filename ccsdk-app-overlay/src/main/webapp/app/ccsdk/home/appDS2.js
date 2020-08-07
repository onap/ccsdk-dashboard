/* Angular application for the EC Dashboard web UI */
var appDS2 = angular.module("abs", 
		[
			'ngCookies', 'ngRoute', 'ngMessages','ngSanitize',
			'ui.bootstrap', 'ui.bootstrap.modal', 'ui.select',
			'b2b.att',
			'modalServices', 'LocalStorageModule'
		]
	).config(function($sceDelegateProvider) {
		  $sceDelegateProvider.resourceUrlWhitelist(['**']);
	}).config(['localStorageServiceProvider', function(localStorageServiceProvider) {
        localStorageServiceProvider.setPrefix('eom')
        .setStorageType('sessionStorage');
  }]);
