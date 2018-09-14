app.controller("crudController", function ($scope) 
{

	
	$scope.UserFormContainer = false;
	$scope.itemShowCount 		  = ['5','10','20', '30'];
	$scope.typeList 					  = [1,2,3,4,5,6,7,8,9,10];
	$scope.date 							  = new Date();
	$scope.message 					  = "Hello World";
	$scope.users 						  = 
	[	
		{ firstname : 'Bipin', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Anuprabha', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Aswathi', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Bipin', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Anuprabha', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Aswathi', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Bipin', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Anuprabha', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Aswathi', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Bipin', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Anuprabha', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Aswathi', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Bipin', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Anuprabha', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Aswathi', lastname : 'Banathur',type :'OK', active :'Yes'},
		{ firstname : 'Bipin', lastname : 'Banathur',type :'OK', active :'Yes'}
	];


	$scope.editUser = function (user) 
	{



        var getUserData = crudService.getUser(user.id);

	

		getUserData.then( function(_user) 
		{
			$scope.user = _user.data;
			$scope.UserId = user.id
			$scope.UserFirstname	= user.firstname;
			$scope.UserLastname		= user.lastname;
			$scope.UserType	= user.type;
			var isActive = (user.active == 1) ? true : false;
			$scope.UserActiveChecked = isActive ;
			$scope.Action = "Update";
			$scope.UserFormContainer = true;
		}, 
		function () 
		{
			alert('Error in getting User record');
		});
	}
	$scope.addUser = function () 
	{
		ClearFields();
		$scope.Action = "Add";
		$scope.UserFormContainer = true;	
	}

	function ClearFields() 
	{

        $scope.UserId = "";
        $scope.UserFirstname = "";
        $scope.UserLastname = "";
        $scope.UserType = "";
        $scope.UserActive = "";
    }

	$scope.closeFrmBtn = function () 
	{
		$scope.UserFormContainer = false;
	}

	$scope.Cancel = function () 
	{

		$scope.UserFormContainer = false;

	}

	$scope.AddUpdateUser = function () 
	{

		var user =
		{
				firstname	: $scope.UserFirstname,
				lastname	: $scope.UserLastname,
				type		    : $scope.UserType,
				active		    : ( ($scope.UserActive) ? "1" : "0" )
		};
		var getUserAction = $scope.Action;
		if(getUserAction == "Update")
		{

			user.userid = $scope.UserId;
			var getUserData = crudService.updateUser(user);
			getUserData.then (function (response) 
			{

								GetAllUsers();

								var msg = response.data.msg;

								alert(msg);

							}, function () {

								alert('Error in updating User record');								

							});

			

		}else{

			//Add Use Code Come Here

			var addUserData = crudService.addUser(user);

			addUserData.then (function (response) {

								GetAllUsers();

								var msg = response.data.msg;

								alert(msg);

							}, function () {

								alert('Error in adding User record');								

							}

			);

		}

		$scope.UserFormContainer = false;

	

	} 

	$scope.deleteUser = function (user) 
	{

		var ans = confirm('Are you sure to delete it?');

		if(ans) 
		{

			var delUserData = crudService.deleteUser(user.id);

			GetAllUsers();

			alert(delUserData.msg.data);

		}
	}
	
	$scope.sort = function(keyname)
	{

        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    }
	$scope.activeChange = function() 
	{
		$scope.search.active = ( ($scope.uActive) ? "1" : "0" );
	};

	$scope.reset = function()
	{
		$scope.search = '';
	};

});