'use strict';

angular.module('myApp', [
    'ngRoute',
    'myApp.filters',
    'myApp.services',
    'myApp.directives',
    'myApp.controllers'
]).
    config(['$routeProvider', function ($routeProvider)
	{
		$routeProvider.when('/home', 		{templateUrl: 'app/pages/home.html', 		controller: 'HomeCtrl'});
		$routeProvider.when('/tad', 		{templateUrl: 'app/pages/tad.html', 		controller: 'TadCtrl'});
        $routeProvider.when('/offerings', 	{templateUrl: 'app/pages/offerings.html', 	controller: 'OfferCtrl'});        
        $routeProvider.when('/notify', 		{templateUrl: 'app/pages/notify.html', 		controller: 'NotifyCtrl'});
		$routeProvider.when('/trigger', 	{templateUrl: 'app/pages/trigger.html', 	controller: 'TrigCtrl'});
		$routeProvider.when('/testinfo', 	{templateUrl: 'app/pages/testinfo.html', 	controller: 'TestCtrl'});
		$routeProvider.when('/testrepo', 	{templateUrl: 'app/pages/testrepo.html', 	controller: 'RepoCtrl'});
		
		
        $routeProvider.otherwise({redirectTo: '/home'});
    }]);
