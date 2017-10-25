var pOrder = angular.module('showOrder', []);

pOrder.controller('tableController', function($scope, $http, $location) {
	$("tbody").on("click", "tr", function selectRow() {
		// $("tr").click(function selectRow() {
		console.log("click function called");

		/*
		 * Get all rows from your 'table' but not the first one that includes
		 * headers.
		 */
		var rows = $('tr').not('#tableHeaders');

		/* Create 'click' event handler for rows */
		rows.on('click', function(e) {

			/* Get current row */
			var row = $(this);

			/*
			 * Check if 'Ctrl', 'cmd' or 'Shift' keyboard key was pressed 'Ctrl' =>
			 * is represented by 'e.ctrlKey' or 'e.metaKey' 'Shift' => is
			 * represented by 'e.shiftKey'
			 */
			if ((e.ctrlKey || e.metaKey) || e.shiftKey) {
				/* If pressed highlight the other row that was clicked */
				row.addClass('highlight');
			} else {
				/* Otherwise just highlight one row and clean others */
				rows.removeClass('highlight');
				row.addClass('highlight');
			}

		});

		/*
		 * This 'event' is used just to avoid that the table text gets selected
		 * (just for styling). For example, when pressing 'Shift' keyboard key
		 * and clicking (without this 'event') the text of the 'table' will be
		 * selected. You can remove it if you want, I just tested this in Chrome
		 * v30.0.1599.69
		 */
		$(document).bind('selectstart dragstart', function(e) {
			e.preventDefault();
			return false;
		});

	});
});

pOrder
		.controller(
				'searchOrderController',
				function($scope, $http, $location) {
					$scope.submitCustomer = function() {

						var config = {
							headers : {
								'Accept' : 'application/json'
							}
						}
						var custNum = document.getElementById("customerNumber").value;
						console.log(typeof custNum);
						console.log(custNum)
						if (custNum < 0 || custNum.indexOf(".") !== -1) {
							window.alert("Foutive invoer voor klantnummer.")
							custNum = 0;
							return;
						}

						if (custNum == '') {
							custNum = 0;
						}
						console
								.log(document.getElementById("customerNumber").value)

						var data = {
							customerNumber : custNum,
							companyName : document
									.getElementById("companyName").value,
						};

						$
								.get("showOrder/searchCustomer", data, config)
								.then(
										function(response) {
											var select = document
													.getElementById("customerList");

											console.log(response.data);
											console.log(response);

											console.log(response.length);
											select.options.length = 0;
											for (i = 0; i < response.length; i++) {
												console.log(response[i]);
												console.log(response[i].info);
												select.options[select.options.length] = new Option(
														response[i].info);
											}

										},
										function error(response) {
											console.log(response);
											$scope.postResultMessage = "Error with status: "
													+ response.statusText;
										});

						$scope.customerNumber = ""
						$scope.companyName = ""
					}

					$scope.searchOrders = function() {
						var orderNumber = document
								.getElementById("orderNumber").value;
						var deliveryDate = document
								.getElementById("deliveryDate").value;
						var customer = document.getElementById("customerList");
						var str = customer.value;
						var customerNumber = "";
						var companyName = "";
						if (str != null && str != "") {
							customerNumber = str.slice(0, str.indexOf(":"));
							str = str.slice(str.indexOf(":") + 2);
							companyName = str.slice(0, str.indexOf(","));
							str = str.slice(str.indexOf(",") + 2);
						} else {
							customerNumber = 0;
							companyName = "";
						}

						if (orderNumber < 0 || orderNumber.indexOf(".") !== -1) {
							window.alert("Foutive invoer voor klantnummer.")
							orderNumber = 0;
							return;
						}

						if (orderNumber == '') {
							orderNumber = 0;
						}

						var config = {
							headers : {
								'Accept' : 'application/json'
							}
						}

						var data = {
							customerNumber : customerNumber,
							companyName : companyName,
							orderNumber : orderNumber,
							deliveryDate : deliveryDate
						};
						$
								.get("showOrder/searchOrders", data, config)
								.then(
										function(response) {
											var selectedRow = document
													.getElementsByClassName("highlight");
											var table = document
													.getElementById("orderTbody");
											$("#orderTbody").empty();
											for (i = 0; i < response.length; i++) {

												var row = table.insertRow(-1);
												var orderNumber = row
														.insertCell(0);
												var orderDate = row
														.insertCell(1);
												var deliveryDate = row
														.insertCell(2);
												var status = row.insertCell(3);
												var customerNumber = row
														.insertCell(4);
												var city = row.insertCell(5);
												var street = row.insertCell(6);
												var houseNumber = row
														.insertCell(7);
												var postalCode = row
														.insertCell(8);

												orderNumber.innerHTML = response[i].orderNumber;
												orderDate.innerHTML = response[i].orderDate;
												deliveryDate.innerHTML = response[i].deliveryDate;
												status.innerHTML = response[i].status;
												customerNumber.innerHTML = response[i].customer.customerNumber;
												city.innerHTML = response[i].customer.address.city;
												street.innerHTML = response[i].customer.address.street;
												houseNumber.innerHTML = response[i].customer.address.houseNumber;
												postalCode.innerHTML = response[i].customer.address.postalCode;
											}

										},
										function error(response) {
											console.log(response);
											$scope.postResultMessage = "Error with status: "
													+ response.statusText;
										});
						$scope.customerNumber = ""
						$scope.companyName = ""
						$scope.orderNumber = ""
						$("#deliveryDate").datepicker('setDate', null);
						document.getElementById("customerList").options.length = 0;
					}

				});

pOrder.controller('orderLineController', function($scope, $http, $location) {
	$scope.selectOrder = function() {
		var selectedRow = document.getElementsByClassName("highlight");
		var orderNumber = selectedRow[0].cells[0].innerHTML;
		var table = document.getElementById("orderLineTbody");
		var orderStatus = document.getElementById("statusBestelling");

		var config = {
			headers : {
				'Accept' : 'application/json'
			}
		}

		var data = {
			orderNumber : orderNumber,
			status : statusBestelling.value
		};

		$.get("showOrder/searchOrderLines", data, config).then(
				function(response) {
					console.log(response);
					$("#orderLineTbody").empty();
					for (i = 0; i < response[0].orderLineList.length; i++) {

						var row = table.insertRow(-1);
						var orderNumber = row.insertCell(0);
						var productNumber = row.insertCell(1);
						var productDiscription = row.insertCell(2);
						var amount = row.insertCell(3);
						var price = row.insertCell(4);
						var status = row.insertCell(5);

						orderNumber.innerHTML = response[i].orderNumber;
						productNumber.innerHTML = response[i].orderLineList[i].productNumber;
						productDiscription.innerHTML = response[i].orderLineList[i].name;
						amount.innerHTML = response[i].orderLineList[i].amount;
						price.innerHTML = response[i].orderLineList[i].price;
						status.innerHTML = response[i].orderLineList[i].status;
					}
					
				},
				function error(response) {
					console.log(response);
					$scope.postResultMessage = "Error with status: "
							+ response.statusText;
				});

	}
	$scope.editStatusOrder = function () {
		if(!$(".highlight")[0]){
			window.alert("Er is geen bestelling geselecteerd");
			return;
		}
		var selectedRow = document.getElementsByClassName("highlight");
		var orderNumber = selectedRow[0].cells[0].innerHTML;
		var status = document.getElementById("statusBestelling").value;
		
		var config = {
				headers : {
					'Accept' : 'application/json'
				}
			}

			var data = {
				orderNumber : orderNumber,
				status : status
			};
		
		$.post("showOrder/editStatusOrder", data, config).then(
				function(response) {
					console.log(response);
					window.allert(response)
					
				},
				function error(response) {
					console.log(response);
					$scope.postResultMessage = "Error with status: "
							+ response.statusText;
				});
		
		
		
	}
});
