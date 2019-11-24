'use strict';

/* Controllers */

angular.module('myApp.controllers', [])

	.controller('OfferCtrl', ['$scope','$http', function ($scope,$http)
	{		
		$scope.$selectedOffering 		= 	"";	
		$scope.offeringID 				=	"90e796bb54c8750401550a84c170723c";
		$scope.offerID 					=	"";
		$scope.off_url					=	$scope.baseURL+"subscribe?offeringID="+$scope.offeringID;
		$scope.getOfferings = function()
		{	
			$http.get($scope.baseURL+'offerings/').success(function(data) {$scope.offerings = data;});
			console.log('Offerings : ',$scope.offerings);	
		
		};		
		$scope.assignOffering = function(offeringID)
		{	
			$scope.selectedOffering 		= offeringID;			
		};

		$scope.raiseRequest = function()
		{	
			alert("Selected Offering "+$scope.selectedOffering);		
		};	
		
		$scope.setOfferings = function(offID)
		{	
			$scope.offerID = offID;
			$scope.off_url = $scope.baseURL+"subscribe?offeringID="+offID;
			$http.get( $scope.off_url  ).success(function(data) {$scope.resp = data;});
		};	
		
		$scope.getRequestStatus = function(reqID)
		{	
			$scope.req_url = $scope.baseURL+"request?requestID="+reqID;
			$http.get( $scope.req_url  ).success(function(data) {$scope.req = data;});
		};	
    }])

    .controller('HomeCtrl', ['$scope', function ($scope) 
	{
		$scope.selectedSolution = "";
		$scope.selectedRelease 	= "";
		$scope.selectedProduct 	= "";
		
		$scope.solutions =
		[			
				{
					solution: "Gauss Tools",
					releases: 
					[
						{release: "V100R001", products: [ {product: "Roach"}, {product: "Data Studio"},  {product: "Data Manger"}  ]},
						{release: "V100R002", products: [ {product: "Roach"}, {product: "Data Studio"},  {product: "Data Manger"}, {product: "CAT"} ]},
						{release: "V100R003", products: [ {product: "Roach"}, {product: "Data Studio"},  {product: "Data Manger"}, {product: "Migration"} ]},
					]
				},
				{
					solution: "Gauss 200 OLAP",
					releases: 
					[
						{release: "V100R001", products: [ {product: "Gauss200 OLAP Server"} ]},
						{release: "V100R002", products: [ {product: "Gauss200 OLAP Server"} ]},
						{release: "V100R003", products: [ {product: "Gauss200 OLAP Server"} ]},
						{release: "V100R004", products: [ {product: "Gauss200 OLAP Server"} ]},
						{release: "V100R005", products: [ {product: "Gauss200 OLAP Server"} ]},
						{release: "V100R006", products: [ {product: "Gauss200 OLAP Server"} ]},
						{release: "V100R007", products: [ {product: "Gauss200 OLAP Server"} ]}
					]
				}
		];

		$scope.showReleases 	= function(sol)
		{

				$scope.selectedSolution = sol.solution;
				sol.active = !sol.active;
		};
		$scope.showProducts 	= function(rel)
		{			
				$scope.selectedRelease = rel.release;
				rel.active = !rel.active;
		};
		$scope.selectProducts 	= function(prod)
		{
				$scope.selectedProduct = prod.product;
				prod.active = !prod.active;
		};

		$scope.startSession 	= function(selectedSolution,selectedRelease,selectedProduct)
		{		  
		  $scope.$parent.solution	=   $scope.selectedSolution;
		  $scope.$parent.release	=	$scope.selectedRelease;
		  $scope.$parent.product    =	$scope.selectedProduct;
		  
		  if($scope.$parent.product === "OpsA")
		  {
			  	$scope.$parent.deployments 	= 
				[
					{"name":"Deploy OpsA Server (Linux)"		}, 
					{"name":"Deploy ArcSight Server (Linux)"	}, 
					{"name":"Deploy Vertica DB (Linux)"			}
				];  
		  }
		  
		  if($scope.$parent.product === "ITBA")
		  {
			  	$scope.$parent.deployments 	= 
				[
					{"name":"ITBA-BA"		}, 
					{"name":"ITBA-SAP BI"	}, 
					{"name":"ITBA-Vertica"	}
				];  
		  }
		  
		  if($scope.$parent.product === "DCAA")
		  {
			  	$scope.$parent.deployments 	= 
				[
					{"name":"Deploy DCAA Server (Linux)"	}, 
					{"name":"Deploy DCAA Server (Windows)"	}, 
					{"name":"Deploy DCAA Server (Linux)"	}
				];  
				
				$scope.$parent.test_sets 	= 
				[
					{"name"	:"DCAA: vCenter"}, 
					{"name"	:"DCAA: Amazon AWS"}, 
					{"name"	:"DCAA: Microsoft Azure"},
					{"name"	:"DCAA: OpenStack"},
					{"name"	:"DCAA: HyperV"}
				];
		  }
		  
		  if($scope.$parent.product === "CSA")
		  {
			  	$scope.$parent.deployments 	= 
				[
					{"name":"Deploy CSA Server (Windows)"}
				];  
				$scope.$parent.test_sets 	= 
				[
					{"name"	:"vCenter"			}, 
					{"name"	:"Amazon AWS"		}, 
					{"name"	:"Microsoft Azure"	},
					{"name"	:"OpenStack"		},
					{"name"	:"HyperV"			}
				];
		  }
		  
		  if (($scope.$parent.product != "CSA") && ($scope.$parent.product != "ITBA") && ($scope.$parent.product != "OpsA") && ($scope.$parent.product != "DCAA"))
		  {
			  	$scope.$parent.deployments 	= 
				[
					{"name":"Deploy CSA Server (Windows)"},
					{"name":"ITBA-BA"		}, 
					{"name":"ITBA-SAP BI"	}, 
					{"name":"ITBA-Vertica"	}
				];  
				$scope.$parent.test_sets 	= 
				[
					{"name"	:"vCenter"			}, 
					{"name"	:"Amazon AWS"		}, 
					{"name"	:"Microsoft Azure"	},
					{"name"	:"OpenStack"		},
					{"name"	:"HyperV"			}
				];
		  }
		};
		


    }])
	
	.controller('TadCtrl', ['$scope','$http', function ($scope, $http) 
	{
		$scope.jsonURI			=   "http://localhost:8080/qa/json/geonames.json";
		$http.get($scope.jsonURI).success(function(response)  {$scope.stors 	= response.stors;});
		$scope.srs 	= 
		[
		
			{"id":"GaussTools.SR.200",	"name" : "Cluster Incrementat Backup & Restore","desc":"Cumulative Backup and Restore NBU/DISK"}, 
			{"id":"GaussTools.SR.201",	"name" : "Generate Topology XML from Backup","desc":"Generate Topology XML from Cluster Backup"},

		];
		
		$scope.tors 	= 
		[
			{"srid":"GaussTools.SR.200", "torid":"TOR1.GaussTools.SR.200",	"name" : "Cluster Incrementat Backup & Restore to DISK","desc":"Cumulative Backup and Restore DISK"}, 
			{"srid":"GaussTools.SR.200", "torid":"TOR2.GaussTools.SR.200",	"name" : "Cluster Incrementat Backup & Restore to NBU","desc":"Cumulative Backup and Restore NBU"}, 
			{"srid":"GaussTools.SR.201", "torid":"TOR3.GaussTools.SR.200",	"name" : "Generate Topology XML from Backup","desc":"Generate Topology XML from Cluster Backup"},

		];
		
		$scope.ttypes 	= 
		[
			{"typeid":"GaussTools.SR.200", 	"name" : "Functional Valid"}, 
			{"typeid":"GaussTools.SR.200", 	"name" : "Functional Invalid"}, 
			{"typeid":"GaussTools.SR.201", 	"name" : "Memory Leak"},
			{"typeid":"GaussTools.SR.201", 	"name" : "Performance"},
			{"typeid":"GaussTools.SR.201", 	"name" : "Security"},
			{"typeid":"GaussTools.SR.201", 	"name" : "Binary Comparison"},
			{"typeid":"GaussTools.SR.201", 	"name" : "Information"},
			{"typeid":"GaussTools.SR.201", 	"name" : "Stress Tesst"}

		];
		
		$scope.features 	= 
		[
			{"typeid":"GaussTools.SR.200", 	"name" : "Table Backup"}, 
			{"typeid":"GaussTools.SR.200", 	"name" : "DB Backup"}, 
			{"typeid":"GaussTools.SR.201", 	"name" : "Table Restore"},
			{"typeid":"GaussTools.SR.201", 	"name" : "Cluster Backup to NBU"},
			{"typeid":"GaussTools.SR.201", 	"name" : "Parallel Processing"},
			{"typeid":"GaussTools.SR.201", 	"name" : "Backup Failiure"},
			{"typeid":"GaussTools.SR.201", 	"name" : "Stop Backup"},
			{"typeid":"GaussTools.SR.201", 	"name" : "Topology Change"}

		];
		
		$scope.addSR = function()
		{	
			$scope.activeTS = tsname;
		};
		$scope.updateSR = function(sr)
		{	
			$scope.activeSR   = sr;
		};
		
		$scope.updateTOR = function(tor)
		{	
			$scope.activeTOR   = tor;
		};
		$scope.treeSelect = function(value)
		{			
			alert('Hi Tree Item Selected  : '+value);
		};
		
	}])
	
	.controller('TestCtrl', ['$scope','$http', function ($scope,$http) 
	{
		$scope.subNav 					= 	0;
		$scope.ts_name					= 	"Vcenter_Compute*";
		$scope.ts_id 					= 	"820";
		$scope.test_id					=	"11";
		$scope.testOwner				=	"naveen.kumar2";

		$scope.getTestInstance = function(tsID)
		{	
			alert($scope.almURL+'testinstances?setID='+tsID);
			$http.get($scope.almURL+'testinstances?setID='+tsID).success(function(data) {$scope.testinstances = data;});
		};	
		$scope.getTest = function(testID)
		{	
			alert($scope.almURL+'test?testID='+testID);
			$http.get($scope.almURL+'test?testID='+testID).success(function(data) {$scope.test = data;});
		};	
		
		$scope.getTests = function(testO)
		{	
			alert($scope.almURL+'tests?testOwner='+testO);
			$http.get($scope.almURL+'tests?testOwner='+testO).success(function(data) {$scope.tests = data;});
		};	
		
		$scope.getTestSets = function(tset)
		{	
			alert($scope.almURL+'testsets?setName='+tset);
			$http.get($scope.almURL+'testsets?setName='+tset).success(function(data) {$scope.testsets = data;});
		};
		
		$scope.loadTests = function(testName)
		{	
			
			if( testName !="vCenter"  )
			{
						$scope.test_cases 	= 
						[
							{"name": testName+": Verify_Prov_Assocition",	"type" : "QUICKTEST_TEST","status":"No Run"}, 
							{"name": testName+": Create_Service_OfferingS",	"type" : "QUICKTEST_TEST","status":"No Run"}, 
							{"name": testName+": Subscribe_Offering", 		"type" : "QUICKTEST_TEST","status":"No Run"}
						];
			}
		};	
    }])
	
    .controller('NotifyCtrl', ['$scope', function ($scope) 
	{
    }])

	.controller('RepoCtrl', ['$scope', function ($scope) 
	{
    }])
	
	.controller('TrigCtrl', ['$scope','$http', function ($scope,$http) 
	{
		$scope.hresp = {"response" : "Not Connected"};
		$scope.vms 	= 
		[		
		{"hostname":"GUI Test Execution", 	 		 "ip":"16.103.23.2"},
		{"hostname":"API Script Execution",     	 "ip":"16.103.23.6"},
		{"hostname":"Performance Test Execution",	 "ip":"16.103.23.14"}
		];
		$scope.url 	= "";
		$scope.activeIP 	= "";
		$scope.activeTS		= "vCenter";
	
		$scope.assignIP = function(ipAddress)
		{	
			$scope.activeIP = ipAddress;
		};
		$scope.assignTS = function(tsname)
		{	
			$scope.activeTS = tsname;
		};
		
		
		$scope.triggerScriptNew = function()
		{
			
			
			$scope.hresp = {"response" : " Attempting to Connect.."};	
			
			$scope.functional();
			
			$scope.performance();
			
			$scope.api();
		
		};
		
				
		$scope.functional = function()
		{					
			$scope.url 		= $scope.baseURL+"runbatch?hostIP=16.103.23.2";
			alert($scope.url);
			$http.get($scope.url).success(function(data) {$scope.hresp = data;});
		}
		
		$scope.performance = function()
		{
			
			$scope.url 		= $scope.baseURL+"runbatch?hostIP=16.103.23.14";
			alert($scope.url);
			$http.get($scope.url).success(function(data) {$scope.hresp = data;});
		}
		
		$scope.api = function()
		{
			
			$scope.url 		= $scope.baseURL+"runbatch?hostIP=16.103.23.6";
			alert($scope.url);
			$http.get($scope.url).success(function(data) {$scope.hresp = data;});
		}
		

		
		
    }])
	
    .controller('AppCtrl', ['$scope', '$location', '$http', function ($scope, $location,$http) 
	{
		
		$scope.title 			= 	"Gauss Tools Test Portal";
        $scope.subNav1 			= 	0;
        $scope.img 				= 	"img/huawei.png";
        $scope.showTopToggle 	= 	false;
		$scope.baseURL			= 	"http://16.103.22.124/tap/fwk/csa/";
				
		$scope.defects 			= 	"";
		$scope.tests 			= 	"";
		$scope.test 			= 	"";
		$scope.testsets 		= 	"";	
		$scope.testinstances	= 	"";		
		$scope.almURL			= 	"http://16.103.22.124/tap/fwk/alm/";
		$scope.solution			=   "";
		$scope.release			=	"";
		$scope.product  		=	"";
		
		
		$scope.headerData 		= 
		{
			"Access-Control-Allow-Origin"		: "*",		
			"Access-Control-Allow-Credentials"	: "true",
			"Access-Control-Allow-Methods"		: "GET,POST",
			"Access-Control-Allow-Headers"		: "Content-Type, Depth, User-Agent, X-File-Size, X-Requested-With, If-Modified-Since, X-File-Name, Cache-Control",	
			"Content-Type"						: "text/plain",
			"Accept"							: "text/plain",			
			"Content-Language"					: "en-US"
		};
		
		$scope.post_uri		=		$scope.almURL+"setup";
		
		$scope.postData		=	
		{
			"instance" 		: "https://qc2f.austin.hpecorp.net:8443/qcbin/" , 
			"alm_user" 		: "bipin.banathur" , 
			"alm_password"	: "Welcome@321",
			"alm_domain"	: "BTO",
			"alm_project"	: "ITOM_Solutions"
		};
		
		$scope.post_result	=	"";
		
		$scope.postCall	 = function() 
		{			
		    $scope.solution	=   "Gauss Tools";
			$scope.release	=	"V100R003";
			$scope.product  =	"Roach";
			
			return $http
			({		
				method	: "POST",
				headers	: $scope.headerData,
				url		: $scope.post_uri,
				data	: $scope.postData
			}).success(function(result) 
			{
				console.log("POST Request Successfully Executed");				
				$scope.post_result = result;
				$scope.initApp();
			}).error(function(data, status, headers, config) 
			{
				console.log("POST Request Failed to Executed")
				console.log(data);
				console.log(status);
				console.log(headers);
				console.log(config);			
			});

		};
		$scope.postCall();

		$scope.test_sets 	= 
		[
		{"name"	:"Integrated Cloud Management"}, 
		{"name"	:"Infrastrucutre as Service across Hyrid IT"}, 
		{"name"	:"Application Delivery & Management"},
		{"name"	:"Database and Middleware Provisioning"}
		];
		
		$scope.test_cases 	= 
		[
			{"name":"Verify_Prov_Assocition",	"type" : "QUICKTEST_TEST","status":"No Run"}, 
			{"name":"Create_Service_OfferingS",	"type" : "QUICKTEST_TEST","status":"No Run"}, 
			{"name":"Subscribe_Offering", 		"type" : "QUICKTEST_TEST","status":"No Run"}
		];
					
		$scope.initApp = function () 
		{
			$http.get($scope.almURL+'tests1?testOwner=naveen.kumar2').success(function(data) 				{ 	$scope.tests 			= data;});	
			$http.get($scope.almURL+'testsets1?setName=Vcenter_Compute*').success(function(data) 			{ 	$scope.testsets 		= data;});
			$http.get($scope.almURL+'defects1/').success(function(data) 									{ 	$scope.defects 			= data;});
			$http.get($scope.almURL+'testinstances1?setID=820').success(function(data) 						{	$scope.testinstances 	= data;});
			$http.get($scope.almURL+'test1?testID=11').success(function(data) 								{	$scope.test 			= data;});
		};
		
		
		
		$scope.deployments 	= 
		[
			{"name":"Deploy CSA Server (Windows)"}, 
			{"name":"Deploy CSA Server (Windows)"}, 
			{"name":"Deploy PostgressSQL (Windows)"},
			{"name":"Deploy Apache Directory Studio"}
		];
			
        $scope.isActive = function (viewLocation) 
		{
            return viewLocation === $location.path();
        };

    }]);
	

