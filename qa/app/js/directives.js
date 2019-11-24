'use strict';

/* Directives */


angular.module('myApp.directives', []).

  directive('appVersion', ['version', function(version) 
  {
	return function(scope, elm, attrs) {elm.text(version);};
  }
    ])
  .directive('ngTree', function() 
  {
	return {restrict: 'E',transclude: true,templateUrl: 'app/pages/soltree.html'};
  })
  .directive("tree", function($compile) 
{
    return {
        restrict	: "E",
        scope		: {employs: '='},
        template	: '<ul><li ng-repeat="geoname in geonames">{{geoname.toponymName}}</li></ul>',
		compile		: 
		function(tElement, tAttr)
		{
				var contents = tElement.contents().remove();
				var compiledContents;
				return function(scope, iElement, iAttr) 
				{
					if(!compiledContents)
					{
						compiledContents = $compile(contents);
					}
					compiledContents(scope, function(clone, scope) 
					{
                         iElement.append(clone); 
					});
				};
        }
    };
});

