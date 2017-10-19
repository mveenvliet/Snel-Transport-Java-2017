var pOrder = angular.module('placeOrder', []);

pOrder.controller('postCustomerController', function($scope, $http, $location){
	$scope.submitCustomer = function(){
		var url = $location.absUrl() + "postCustomerController";
		
		var config = {
                headers : {
                    'Accept': 'application/json'
                }
        }
		var custNum = document.getElementById("customerNumber").value;
		console.log(typeof custNum);
		if (custNum == ''){
			custNum = 0;
		}
		console.log(document.getElementById("customerNumber").value)
		
		var data = {
				customerNumber: custNum,
				companyName: document.getElementById("companyName").value,
				firstname: document.getElementById("firstname").value,
				lastname: document.getElementById("lastname").value
	        };
		
		$.get("searchCustomer", data, config).then(function (response) {
			var select = document.getElementById("customerList");
			
			console.log(response.data);
			console.log(response);
			
			console.log(response.length);
			select.options.length = 0;
			for (i=0; i < response.length; i++){
				console.log(response[i]);
				console.log(response[i].info);
				select.options[select.options.length] = new Option(response[i].info);
			}
			
			
		}, function error(response) {
			console.log(response);
			$scope.postResultMessage = "Error with status: " +  response.statusText;
		});
		
		$scope.customerNumber = ""
		$scope.companyName = ""
		$scope.firstname = "";
		$scope.lastname = "";
		
		
//		var data = {
//				customerNumber: $scope.customerNumber
//	        };
//		
//		
//		var url = $location.absUrl() + "custo";
//		
//		$.get("custom", {customerNumber: 1},config).then(function (response) {
//			var select = document.getElementById("customerList");
//			
//			console.log(response.data);
//			console.log(response);
//			
////			console.log(response.data.length);
////			for (i=0; i < response.data.length; i++){
////				console.log(response.data[i]);
////				console.log(response.data[i].companyName);
////			}
////			select.options.length = 0;
////			select.options[select.options.length] = new Option(response.data[1]);
//			
//		}, function error(response) {
//			console.log(response);
//			$scope.postResultMessage = "Error with status: " +  response.statusText;
//		});
		
		

	}
});
