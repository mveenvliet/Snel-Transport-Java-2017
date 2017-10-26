var vCustomer = angular.module('showCustomer', []);

vCustomer.controller('postCustomerController', function($scope, $http, $location) {
	$scope.submitCustomer = function() {
		var config = {
			headers : {
				'Accept' : 'application/json'
			}
		}

		var custNum = document.getElementById("customerId").value;
		if (custNum < 0 || custNum.indexOf(".") !== -1) {
			window.alert("Foutive invoer voor klantnummer.")
			custNum = 0;
			return;
		}

		if (custNum == '') {
			custNum = 0;
		}
		

		var data = {
			customerNumber : custNum,
			companyName : document.getElementById("companyName").value,
			firstName : document.getElementById("firstName").value,
			lastName : document.getElementById("lastName").value,
			emailAddress : document.getElementById("emailAddress").value,
			status : document.getElementById("StatusCustomer").value
		};
		$.get("searchSetCustomer", data, config).then(
				function(response) {
					console.log(response)
					if (response.length < 1){
						window.alert('Er zijn geen resultaten gevonden');
					}
					var table = document.getElementById("customerTbody");
					$("#customerTbody").empty();
					for (i = 0; i < response.length; i++) {
			
						var row = table.insertRow(-1);
						var customerNumber = row.insertCell(0);
						var companyName = row.insertCell(1);
						var firstName = row.insertCell(2);
						var lastName = row.insertCell(3);
						var emailAddress = row.insertCell(4);
						var phoneNumber = row.insertCell(5);
						customerNumber.innerHTML = response[i].customerNumber;
						companyName.innerHTML = response[i].company.name;
						firstName.innerHTML = response[i].firstname;
						lastName.innerHTML = response[i].lastname;
						emailAddress.innerHTML = response[i].emailAddress;
						phoneNumber.innerHTML = response[i].phoneNumber;
						
					}
				},
				function error(response) {
					console.log(response);
					$scope.postResultMessage = "Error with status: "
							+ response.statusText;
				});

		$scope.customerNumber = ""
		$scope.companyName = ""
		$scope.firstName = "";
		$scope.lastName = "";
		$scope.emailAddress = "";
		$scope.phoneNumber = "";
	}

});


vCustomer.controller('tableController', function($scope, $http, $location) {
	$("tbody").on("click", "tr", function selectRow() {
		
		var rows = $('tr').not('#tableHeaders');
		var row = $(this);
		
		rows.removeClass('highlight');
		row.addClass('highlight');
		
		var config = {
				headers : {
					'Accept' : 'application/json'
				}
			}
		data = {customerNumber : parseInt(row[0].cells[0].innerHTML),
				companyName : row[0].cells[1].innerHTML,
				firstName : row[0].cells[2].innerHTML,
				lastName : row[0].cells[3].innerHTML,
				emailAddress : row[0].cells[4].innerHTML,
				telNumber : row[0].cells[5].innerHTML
		}

		$.get("searchSetCustomer", data, config).then(
				function(response) {
					document.getElementById('cityName').value=response[0].address.city;
					document.getElementById('houseNumber').value=response[0].address.houseNumber;
					document.getElementById('postalCode').value=response[0].address.postalCode;
					document.getElementById('streetName').value=response[0].address.street;
		})
		
		document.getElementById('customerId').value=row[0].cells[0].innerHTML;
		document.getElementById('companyName').value=row[0].cells[1].innerHTML;
		document.getElementById('firstName').value=row[0].cells[2].innerHTML;
		document.getElementById('lastName').value=row[0].cells[3].innerHTML;
		document.getElementById('emailAddress').value=row[0].cells[4].innerHTML;
		document.getElementById('telNumber').value=row[0].cells[5].innerHTML;
		
		$scope.companyName  = '?';
		
	});
});
