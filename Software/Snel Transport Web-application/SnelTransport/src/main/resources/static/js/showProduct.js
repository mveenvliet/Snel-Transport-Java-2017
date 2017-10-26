var vProduct = angular.module('showProducts', []);

vProduct.controller('viewProductController', function($scope, $http, $location) {
	$scope.EmptyAll = function() {
		document.getElementById('productNumber').value='';
		document.getElementById('productName').value='';
		document.getElementById('categoryList').value='';
		document.getElementById('productPrice').value='';
		document.getElementById('amount').value='';
		document.getElementById('warehouse').value='';
		document.getElementById('compartimentNumber').value='';
		document.getElementById('productStatus').value='';
	}
	
	$scope.submitProduct = function() {
		var config = {
			headers : {
				'Accept' : 'application/json'
			}
		}

		var data = {
			productNumber : document.getElementById("productNumber").value,
			productName : document.getElementById("productName").value,
			catgoryList : document.getElementById("categoryList").value,
			productPrice : document.getElementById("productPrice").value,
			amount : document.getElementById("amount").value,
			warehouse : document.getElementById("warehouse").value,
			compartimentNumber : document.getElementById("compartimentNumber").value,
			productStatus : document.getElementById("productStatus").value
		};
		$.get("searchSetProduct", data, config).then(
				function(response) {
					console.log(response)
					if (response.length < 1){
						window.alert('Er zijn geen resultaten gevonden');
					}
					var table = document.getElementById("productTbody");
					$("#productTbody").empty();
					for (i = 0; i < response.length; i++) {
			
						var row = table.insertRow(-1);
						
						var productNumber = row.insertCell(0);
						var productName = row.insertCell(1);
						var categoryList = row.insertCell(2);
						var productPrice = row.insertCell(3);
						var amount = row.insertCell(4);
						var warehouse = row.insertCell(5);
						var compartimentNumber = row.insertCell(6);
						var status = row.insertCell(7);
						
						productNumber.innerText = response[i].productNumber;
						productName.innerText = response[i].productName;
						categoryList.innerText = response[i].categoryList;
						productPrice.innerText = response[i].productPrice;
						amount.innerText = response[i].amount;
						warehouse.innerText = response[i].warehouse;
						compartimentNumber.innerText = response[i].compartimentNumber;
						status.innerText = response[i].status;
						
					}
				},
				function error(response) {
					console.log(response);
					$scope.postResultMessage = "Error with status: "
							+ response.statusText;
				});
		
		$scope.productNumber = "";
		$scope.productName = "";
		$scope.categoryList = "";
		$scope.productPrice = "";
		$scope.amount = "";
		$scope.warehouse = "";
		$scope.compartimentNumber = "";
		$scope.status = "";

	}

});


vProduct.controller('tableController', function($scope, $http, $location) {
	$("tbody").on("click", "tr", function selectRow() {
		
		var rows = $('tr').not('#tableHeaders');
		var row = $(this);
		
		rows.removeClass('highlight');
		row.addClass('highlight');
		
		document.getElementById('productNumber').value=row[0].cells[0].innerText;
		document.getElementById('productName').value=row[0].cells[1].innerText;
		document.getElementById('categoryList').value=row[0].cells[2].innerText;
		document.getElementById('productPrice').value=row[0].cells[3].innerText;
		document.getElementById('amount').value=row[0].cells[4].innerText;
		document.getElementById('warehouse').value=row[0].cells[5].innerText;
		document.getElementById('compartimentNumber').value=row[0].cells[6].innerText;
		document.getElementById('status').value=row[0].cells[7].innerText;
		
	});
});
