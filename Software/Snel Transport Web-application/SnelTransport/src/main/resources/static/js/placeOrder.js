var pOrder = angular.module('placeOrder', []);

pOrder.controller('postCustomerController', function($scope, $http, $location){
	$scope.submitCustomer = function(){
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
			var select = document.getElementById("customerList");
			
			console.log(response.data);
			console.log(response);
			
			select.options.length = 0;
			select.options[select.options.length] = new Option(response.data);
			
		}, function error(response) {
			console.log(response);
			$scope.postResultMessage = "Error with status: " +  response.statusText;
		});
		
		$scope.customerNumber = ""
		$scope.companyName = ""
		$scope.firstname = "";
		$scope.lastname = "";

	}
});
