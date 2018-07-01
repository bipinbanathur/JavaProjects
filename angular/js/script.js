var app 	= angular.module('myApp',[]);
app.controller('myctr', function($scope, $http) 
{
	
	$scope.restURI			=   "http://bbanathu-in/employee/emp/restex/"	
	$scope.jsonURI			=   "http://bbanathu-in/angular/geonames.json";
	$scope.bugURI			= 	"http://bbanathu-in/bugrepo/bugs/restbug/?updatedBy=SHATHAKU&fromDate=01-JAN-2007&toDate=31-DEC-2015";
	
	$scope.idMessage		=	"Employee ID";
	$scope.nameMessage		=	"Employee Name";
	$scope.expMessage		=	"Experience";
	$scope.salMessage		=	"Salary";
	
	$scope.showCreateModal 	= 	false;
	$scope.showUpdateModal 	= 	false;
	$scope.showTableModal 	= 	false;
	$scope.showEmpTree 		= 	false;
	$scope.showBugModal		=   false;

	$scope.pfs				=	[];
	$scope.filteredTodos 	= 	[]
	$scope.currentPage 		= 	1
	$scope.numPerPage 		= 	10
	$scope.maxSize 			= 	10;
	
	$scope.employees 		= 	[];
	$scope.bugs				=   [];	
	$scope.makeTodos = function() 
	{
		$scope.todos = [];
		for (i=1;i<=1000;i++) 
		{
			$scope.todos.push({ text:'Item '+i, done:false});
		}
	};
	$scope.makeTodos(); 	
	$scope.$watch('currentPage + numPerPage', function() 
	{
		var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin + $scope.numPerPage;    
		$scope.filteredTodos = $scope.todos.slice(begin, end);
	});	
	$http.get($scope.restURI).success(function (response) {$scope.employees = response.employee;});
	$http.get($scope.jsonURI).success(function(response)  {$scope.geonames 	= response.geonames;});
	$http.get($scope.bugURI).success(function(response)   {$scope.bugs 		= response.bug;});	
    $scope.viewCreateModal = function()
	{
		$scope.showCreateModal = !$scope.showModal;
	};   	
	$scope.createEmployee = function(emp)
	{	
		$scope.showCreateModal  = false;	
		$scope.showTableModal   = false;
		var emp_id				= emp.cid;
		var emp_name 			= emp.cname;
		var emp_exp 			= emp.cexperience;
		var emp_sal 			= emp.csalary;	
		var emp_obj 			= {name:emp_name,id:emp_id,experience:emp_exp,salary:emp_sal};	
		
		$http.post($scope.restURI+"create/",emp_obj).success(function(data,status,header,config){$scope.message=data;});			
		alert("Employee "+emp_name+" got Added Succesfully");				
	};
	$scope.deleteEmp=function(id,name)
	{
		$scope.showTableModal = false;			
		$http.delete($scope.restURI+"?id="+id).success(function(data,status,header,config){$scope.message=data;})  
		alert('Employee '+name+' Deleted Succesfully');	
	}
	$scope.updateEmployee = function(uemp)
	{	
			$scope.showUpdateModal 	= false;	
			$scope.showTableModal   = false;				
			var emp_id				= uemp.uid;
			var emp_name 			= uemp.uname;
			var emp_exp 			= uemp.uexperience;
			var emp_sal 			= uemp.usalary;
						
			$http.post($scope.restURI+"?id="+emp_id+"&name="+emp_name+"&experience="+emp_exp+"&salary="+emp_sal).success(function(data,status,header,config){$scope.message=data;});				
			alert("Employee "+emp_name+" got Updated Succesfully");			
	};	
	$scope.viewEmployeeTree = function()
	{		
		$http.get($scope.jsonURI).success(function(response)  {$scope.geonames 	= response.geonames;});
		$scope.showEmpTree = true;			
	};	
	$scope.viewEmployee = function()
	{	
		$http.get($scope.restURI).success(function (response) {$scope.employees = response.employee;});
		$scope.showTableModal = true;			
	};
	
	$scope.viewBugs = function()
	{	
		$http.get($scope.bugURI).success(function (response) {$scope.bugs = response.bug;});
		$scope.showBugModal = true;			
	};
	
	$scope.viewUpdateEmployee = function(uEmp)
	{			
		$scope.uemp 				= 	uEmp;
		$scope.uemp.uid				=	uEmp.id;
		$scope.uemp.uname			=	uEmp.name;
		$scope.uemp.uexperience		=	uEmp.experience;
		$scope.uemp.usalary			=	uEmp.salary;	
		$scope.showUpdateModal 		= 	true;			
	};	
	
	$scope.viewData = function()
	{			
		window.open('table.html', 'Tree');
	};

	$scope.treeSelect = function(value)
	{			
		alert('Hi Tree Item Selected  : '+value);
	};
	
});
  
app.directive('modal', function () 
{
    return {
      template		: 	'<div class="modal fade">' + '<div class="modal-dialog">' + '<div class="modal-content">' + '<div class="modal-header">' + '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' + '<h4 class="modal-title">{{ title }}</h4>' + '</div>' + '<div class="modal-body" ng-transclude></div>' + '</div>' + '</div>' + '</div>',
      restrict		: 	'E',
      transclude	: 	true,
      replace		:	true,
      scope			:	true,
      link			: 
	  function postLink(scope, element, attrs) 
	  {
        scope.title = attrs.title;

        scope.$watch(attrs.visible, function(value)
		{
          if(value == true)
            $(element).modal('show');
          else
            $(element).modal('hide');
        });

        $(element).on('shown.bs.modal', function()
		{
          scope.$apply(function()
		  {
            scope.$parent[attrs.visible] = true;
          });
        });

        $(element).on('hidden.bs.modal', function()
		{
			scope.$apply(function()
			{
				scope.$parent[attrs.visible] = false;
			});
        });
      }
    };
});

app.directive("tree", function($compile) 
{
    return {
        restrict	: "E",
        scope		: {employs: '='},
        template	: '<ul><li ng-repeat="employee in employees">{{employee.name}}</li></ul>',
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
