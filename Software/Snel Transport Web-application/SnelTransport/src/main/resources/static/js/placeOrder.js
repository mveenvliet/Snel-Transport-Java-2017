var pOrder = angular.module('placeOrder', []);

pOrder.controller('postCustomerController', function($scope, $http, $location){
	$scope.submitCustomer = function(){
		var url = $location.absUrl() + "postCustomerController";
		
		var config = {
                headers : {
                    'Accept': 'application/json'
                }
        }
		var data = {
				customerNumber: $scope.customerNumber,
				companyName: $scope.companyName,
				firstname: $scope.firstname,
				lastname: $scope.lastname
	        };
		
		$http.get(url, data, config).then(function (response) {
			var select = document.getElementById("customerList");
			
			console.log(response.data);
			console.log(response);
			for (i=0; i < data.length; i++){
				console.log(data.companyName[i]);
			}
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
