var vTruck = angular.module('showTruck', []);

vTruck.controller('viewTruckController', function($scope, $http, $location) {
	$scope.EmptyAll = function() {
		document.getElementById('licencePlate').value='';
		document.getElementById('chauffeur').value='';
		document.getElementById('brand').value='';
		document.getElementById('type').value='';
		document.getElementById('owner').value='';
		document.getElementById('availableFrom').value='';
		document.getElementById('notAvailableFrom').value='';
	}

	$scope.submitTruck = function() {
		var config = {
			headers : {
				'Accept' : 'application/json'
			}
		}

		var data = {
			licencePlate : document.getElementById("licencePlate").value,
			chauffeur : document.getElementById("chauffeur").value,
			brand : document.getElementById("brand").value,
			type : document.getElementById("type").value,
			owner : document.getElementById("owner").value,
			availableFrom : document.getElementById("availableFrom").value,
			notAvailableFrom : document.getElementById("notAvailableFrom").value
		};


		$.get("searchSetTruck", data, config).then(
				function(response) {
					console.log(response)
					if (response.length < 1){
						window.alert('Er zijn geen resultaten gevonden');
					}
					var table = document.getElementById("truckTbody");
					$("#truckTbody").empty();
					for (i = 0; i < response.length; i++) {

						var row = table.insertRow(-1);

						var licencePlate  = row.insertCell(0);
						var chauffeur  = row.insertCell(1);
						var brand  = row.insertCell(2);
						var type  = row.insertCell(3);
						var owner  = row.insertCell(4);
						var availableFrom  = row.insertCell(5);
						var notAvailableFrom  = row.insertCell(6);

						licencePlate .innerText = response[i].licencePlate;
						chauffeur .innerText = response[i].chauffeur;
						brand .innerText = response[i].brand;
						type .innerText = response[i].type;
						owner .innerText = response[i].owner;
						if(response[i].availableFrom){
							var tempTime = response[i].availableFrom.split('-');
							availableFrom .innerText = tempTime[2] + '-' + tempTime[1] + '-' + tempTime[0] ;//response[i].availableFrom;
						}
						if(response[i].notAvailableFrom){
							var tempTime = response[i].notAvailableFrom.split('-');
							notAvailableFrom .innerText = tempTime[2] + '-' + tempTime[1] + '-' + tempTime[0];
						}						
					}
				},
				function error(response) {
					console.log(response);
					$scope.postResultMessage = "Error with status: "
							+ response.statusText;
				});

		$scope.licencePlate  = "";
		$scope.chauffeur  = "";
		$scope.brand  = "";
		$scope.type  = "";
		$scope.owner  = "";
		$scope.availableFrom  = "";
		$scope.notAvailableFrom  = "";
	}
});


vTruck.controller('tableController', function($scope, $http, $location) {
	$("tbody").on("click", "tr", function selectRow() {

		var rows = $('tr').not('#tableHeaders');
		var row = $(this);

		rows.removeClass('highlight');
		row.addClass('highlight');

		document.getElementById('licencePlate').value=row[0].cells[0].innerText;
		document.getElementById('chauffeur').value=row[0].cells[1].innerText;
		document.getElementById('brand').value=row[0].cells[2].innerText;
		document.getElementById('type').value=row[0].cells[3].innerText;
		document.getElementById('owner').value=row[0].cells[4].innerText;
		document.getElementById('availableFrom').value=row[0].cells[5].innerText;
		document.getElementById('notAvailableFrom').value=row[0].cells[6].innerText;
	});
});
