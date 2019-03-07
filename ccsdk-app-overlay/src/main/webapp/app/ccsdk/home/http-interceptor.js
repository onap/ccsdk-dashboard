appDS2.factory('httpInterceptor', function ($q, $rootScope, $location) {
        return {
            'request': function (config) {
                return config;
            },
        	/*
            'requestError': function (rejection) {
            },
            */
            'response': function (response) {
                if (response.data == null) {
                	var loc = location.pathname;
                	console.log("location path name: " + loc);
                	var loc1 = loc.replace("/", "");
                	var loc2 = loc1.replace("/ecd", "/login.htm");
                	console.log("location url: " + loc2);
                    alert("Your session has expired. Please log in again...");
                    location.replace("/"+loc2);
                }
                return response;
            },
            // optional method
            'responseError': function (rejection) {
            }
        };
    })
    .config(function($httpProvider) {
		  $httpProvider.interceptors.push('httpInterceptor');
	});