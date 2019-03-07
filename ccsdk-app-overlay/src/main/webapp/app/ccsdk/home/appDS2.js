/* Angular application for the EC Dashboard web UI */
var appDS2 = angular.module("abs", 
		[
			'ngCookies', 'ngRoute', 'ngMessages','ngSanitize',
			'ui.bootstrap', 'ui.bootstrap.modal',
			'b2b.att',
			'modalServices'
		]
	).config(function($sceDelegateProvider) {
		  $sceDelegateProvider.resourceUrlWhitelist(['**']);
	});
