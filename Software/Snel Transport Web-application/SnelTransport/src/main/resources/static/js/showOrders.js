var pOrder = angular.module('showOrder', []);

pOrder.controller('tableController', function($scope, $http, $location) {
	$("tbody").on("click", "tr", function selectRow() {

		var rows = $('tr').not('#tableHeaders');
		var row = $(this);
		rows.removeClass('highlight');
		row.addClass('highlight');


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

pOrder
		.controller(
				'orderLineController',
				function($scope, $http, $location) {
					$scope.selectOrder = function() {
						var selectedRow = document
								.getElementsByClassName("highlight");
						var orderNumber = selectedRow[0].cells[0].innerHTML;
						var table = document.getElementById("orderLineTbody");
						var orderStatus = document
								.getElementById("statusBestelling");

						var config = {
							headers : {
								'Accept' : 'application/json'
							}
						}

						var data = {
							orderNumber : orderNumber,
							status : statusBestelling.value
						};

						$
								.get("showOrder/searchOrderLines", data, config)
								.then(
										function(response) {
											console.log(response);
											$("#orderLineTbody").empty();
											for (i = 0; i < response[0].orderLineList.length; i++) {

												var row = table.insertRow(-1);
												var orderNumber = row
														.insertCell(0);
												var productNumber = row
														.insertCell(1);
												var productDiscription = row
														.insertCell(2);
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
					$scope.editStatusOrder = function() {
						if (!$(".highlight")[0]) {
							window.alert("Er is geen bestelling geselecteerd");
							return;
						}
						var selectedRow = document
								.getElementsByClassName("highlight");
						var orderNumber = selectedRow[0].cells[0].innerHTML;
						var status = document
								.getElementById("statusBestelling").value;

						var config = {
							headers : {
								'Accept' : 'application/json'
							}
						}

						var data = {
							orderNumber : orderNumber,
							status : status
						};

						$
								.post("showOrder/editStatusOrder", data, config)
								.then(
										function(response) {
											console.log(response);
											if (response.indexOf("Order status changed to:") != -1){
												window.alert('De status van de bestelling is gewijzigd naar: ' + status)
												document.getElementById("orderTable").rows[selectedRow[0].rowIndex].cells[3].innerHTML = status;
											} else{
												window.alert(response);
											}
											
											
										},
										function error(response) {
											console.log(response);
											$scope.postResultMessage = "Error with status: "
													+ response.statusText;
										});
					}

					$scope.editStatusProduct = function() {
						if (!$(".highlight")[0]) {
							window.alert("Er is geen Product geselecteerd");
							return;
						}
						var selectedRow = document
								.getElementsByClassName("highlight");
						console.log("selected row length: "
								+ selectedRow[0].cells.length);
						if (selectedRow[0].cells.length == 6) {
							var orderNumber = selectedRow[0].cells[0].innerHTML;
							var productNumber = selectedRow[0].cells[1].innerHTML;
							var status = document
									.getElementById("statusProduct").value;
							console.log(status);

							var config = {
								headers : {
									'Accept' : 'application/json'
								}
							}

							var data = {
								orderNumber : orderNumber,
								productNumber : productNumber,
								status : status
							};

							$
									.post("showOrder/editStatusProductInOrder",
											data, config)
									.then(
											function(response) {
												console.log(response);
												if (response.indexOf("Orderline status changed to:") != -1){
													window.alert('De status van het product is gewijzigd naar: ' + status)
													document.getElementById("tableShoppinfBasket").rows[selectedRow[0].rowIndex].cells[5].innerHTML = status;
												} else{
													window.alert(response);
												}
											},
											function error(response) {
												console.log(response);
												$scope.postResultMessage = "Error with status: "
														+ response.statusText;
											});
						} else {
							window.alert("Er is geen Product geselecteerd");
							return;
						}
					}

					$scope.deleteOrder = function() {
						if (!$(".highlight")[0]) {
							window.alert("Er is geen Product geselecteerd");
							return;
						}
						var selectedRow = document
								.getElementsByClassName("highlight");
						console.log("selected row length: "
								+ selectedRow[0].cells.length);

						if (selectedRow[0].cells.length > 6) {
							if (confirm("Weet je zeker dat je deze bestelling wilt verwijderen?"))
								var orderNumber = selectedRow[0].cells[0].innerHTML;
							console.log(status);

							var config = {
								headers : {
									'Accept' : 'application/json'
								}
							}

							var data = {
								orderNumber : orderNumber
							};

							$
									.post("showOrder/deleteOrder", data, config)
									.then(
											function(response) {
												console.log(response);
												if(response.indexOf('was deleted') != -1){
													document.getElementById("orderTable").deleteRow(selectedRow[0].rowIndex);
													window.alert("De Bestelling met Ordernummer: " + orderNumber + " is succesvol verwijderd.")
												}else{
													window.alert(response)	
												}
											},
											function error(response) {
												console.log(response);
												$scope.postResultMessage = "Error with status: "
														+ response.statusText;
											});
						} else {
							window.alert("Er is geen Bestelling geselecteerd");
							return;
						}
					}
					$scope.deleteOrderLine = function() {
						if (!$(".highlight")[0]) {
							window.alert("Er is geen Product geselecteerd");
							return;
						}
						var selectedRow = document
								.getElementsByClassName("highlight");
						console.log("selected row length: "
								+ selectedRow[0].cells.length);
						if (selectedRow[0].cells.length == 6) {
							if (confirm("Weet je zeker dat je dit product uit de bestelling wil verwijderen?"))
								var orderNumber = selectedRow[0].cells[0].innerHTML;
							var productNumber = selectedRow[0].cells[1].innerHTML;
							console.log(status);

							var config = {
								headers : {
									'Accept' : 'application/json'
								}
							}

							var data = {
								orderNumber : orderNumber,
								productNumber : productNumber
							};

							$
									.post("showOrder/deleteOrderLine", data,
											config)
									.then(
											function(response) {
												console.log(response);
												if(response.indexOf('verwijderd') != -1){
													document.getElementById("tableShoppinfBasket").deleteRow(selectedRow[0].rowIndex);
												}
													window.alert(response)	
											},
											function error(response) {
												console.log(response);
												$scope.postResultMessage = "Error with status: "
														+ response.statusText;
											});
						} else {
							window.alert("Er is geen Product geselecteerd");
							return;
						}
					}

				});
