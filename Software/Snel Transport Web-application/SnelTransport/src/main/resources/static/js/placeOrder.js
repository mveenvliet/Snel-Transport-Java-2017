var pOrder = angular.module('placeOrder', []);

pOrder.controller('postCustomerController', function($scope, $http, $location){
	$scope.submitCustomer = function(){
		console.log("do something!")
		var url = $location.absUrl() + "postCustomerController";
		
		var config = {
                headers : {
                    'Accept': 'text/plain'
                }
        }
		var data = {
				customerNumber: $scope.customerNumber,
				companyName: $scope.companyName,
				firstname: $scope.firstname,
				lastname: $scope.lastname
	        };
		
		$http.post(url, data, config).then(function (response) {
			$scope.postResultMessage = response.data;
		}, function error(response) {
			$scope.postResultMessage = "Error with status: " +  response.statusText;
		});
		
		$scope.customerNumber = ""
		$scope.companyName = ""
		$scope.firstname = "";
		$scope.lastname = "";
		console.log("do something!")
		
	}
});



//app.controller('postcontroller', function($scope, $http, $location) {
//	$scope.submitForm = function(){
//		var url = $location.absUrl() + "postcustomer";
//		
//		var config = {
//                headers : {
//                    'Accept': 'text/plain'
//                }
//        }
//		var data = {
//            firstname: $scope.firstname,
//            lastname: $scope.lastname
//        };
//		
//		$http.post(url, data, config).then(function (response) {
//			$scope.postResultMessage = response.data;
//		}, function error(response) {
//			$scope.postResultMessage = "Error with status: " +  response.statusText;
//		});
//		
//		$scope.firstname = "";
//		$scope.lastname = "";
//	}
//});
//
//app.controller('getcontroller', function($scope, $http, $location) {
//	$scope.getfunction = function(){
//		var url = $location.absUrl() + "getallcustomer";
//		
//		$http.get(url).then(function (response) {
//			$scope.response = response.data
//		}, function error(response) {
//			$scope.postResultMessage = "Error with status: " +  response.statusText;
//		});
//	}
//});