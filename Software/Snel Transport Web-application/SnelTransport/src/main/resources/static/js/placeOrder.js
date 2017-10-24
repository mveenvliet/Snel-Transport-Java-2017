var pOrder = angular.module('placeOrder', []);

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

pOrder.controller('postCustomerController', function($scope, $http, $location) {
	$scope.submitCustomer = function() {

		var config = {
			headers : {
				'Accept' : 'application/json'
			}
		}
		var custNum = document.getElementById("customerNumber").value;
		console.log(typeof custNum);
		if (custNum == '') {
			custNum = 0;
		}
		console.log(document.getElementById("customerNumber").value)

		var data = {
			customerNumber : custNum,
			companyName : document.getElementById("companyName").value,
			firstname : document.getElementById("firstname").value,
			lastname : document.getElementById("lastname").value
		};

		$.get("searchCustomer", data, config).then(
				function(response) {
					var select = document.getElementById("customerList");

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
		$scope.firstname = "";
		$scope.lastname = "";
	}

});

pOrder.controller('productController', function($scope, $http, $location) {

	$scope.submitProduct = function() {
		var config = {
			headers : {
				'Accept' : 'application/json'
			}
		}

		var data = {
			productNumber : document.getElementById("productId").value,
			productName : document.getElementById("productName").value,
			productType : document.getElementById("catagoryList").value

		};
		console.log(data);

		$.get("searchProduct", data, config).then(
				function(response) {
					var table = document.getElementById("inventarisTbody");

					console.log(response.data);
					console.log(response);

					$("#inventarisTbody").empty();
					for (i = 0; i < response.length; i++) {

						var row = table.insertRow(-1);
						var productNumber = row.insertCell(0);
						var name = row.insertCell(1);
						var type = row.insertCell(2);
						var price = row.insertCell(3);
						var amount = row.insertCell(4);
						var status = row.insertCell(5);

						productNumber.innerHTML = response[i].productNumber;
						name.innerHTML = response[i].name;
						type.innerHTML = response[i].typeListString;
						price.innerHTML = response[i].price;
						amount.innerHTML = response[i].amount;
						status.innerHTML = response[i].status;
					}

				},
				function error(response) {
					console.log(response);
					$scope.postResultMessage = "Error with status: "
							+ response.statusText;
				});

	}

	$scope.addItemToShoppingCart = function($scope, $http, $location) {
		var sbAmount = document.getElementById("sbAmount");
		var selectedRow = document.getElementsByClassName("highlight");
		var table = document.getElementById("winkelwagenTbody");

		var row = table.insertRow(-1);
		var productNumber = row.insertCell(0);
		var name = row.insertCell(1);
		var type = row.insertCell(2);
		var price = row.insertCell(3);
		var amount = row.insertCell(4);

		console.log(selectedRow);
		console.log(selectedRow[0].cells[0].innerHTML);
		console.log(selectedRow[0].cells[1].innerHTML);
		console.log(selectedRow[0].cells[2].innerHTML);
		console.log(selectedRow[0].cells[3].innerHTML);
		console.log(selectedRow[0].cells[4].innerHTML);
		console.log(selectedRow[0].cells[5].innerHTML);

		productNumber.innerHTML = selectedRow[0].cells[0].innerHTML;
		name.innerHTML = selectedRow[0].cells[1].innerHTML;
		type.innerHTML = selectedRow[0].cells[2].innerHTML;
		price.innerHTML = selectedRow[0].cells[3].innerHTML * sbAmount.value;
		amount.innerHTML = sbAmount.value;
	}
});

pOrder
		.controller(
				'shoppingBasketController',
				function($scope, $http, $location) {

					$scope.removeItemFromShoppingCart = function($scope, $http,
							$location) {
						var selectedRow = document
								.getElementsByClassName("highlight");
						var rows = document.getElementById("winkelwagenTbody").rows;
						var table = document.getElementById("winkelwagenTbody");

						// console.log(selectedRow);

						for (var i = 0; i < rows.length; i++) {
							if (selectedRow[0].cells[0].innerHTML == rows[i].cells[0].innerHTML) {
								table.deleteRow(i);
							}
						}
					}

					$scope.placeOrder = function($scope, $http, $location) {
						var rows = document.getElementById("winkelwagenTbody").rows;
						var products = [];

						var config = {
							headers : {
								'Accept' : 'application/json'
							}
						}

						var customer = document.getElementById("customerList");
						// console.log(customer.value);
						var str = customer.value;
						var customerNumber = str.slice(0, str.indexOf(":"));
						str = str.slice(str.indexOf(":") + 2);
						var companyName = str.slice(0, str.indexOf(","));
						str = str.slice(str.indexOf(",") + 2);
						var city = str.slice(0, str.indexOf(","));
						str = str.slice(str.indexOf(",") + 2);
						var street = str.slice(0, str.indexOf(","));
						str = str.slice(str.indexOf(",") + 2);
						var houseNumber = str.slice(0, str.indexOf(","));
						str = str.slice(str.indexOf(",") + 2);
						var postalcode = str;

						 for (var i = 0; i < rows.length; i++) {
						
						 products.push({
						 productNumber : rows[i].cells[0].innerHTML,
						 discription : rows[i].cells[1].innerHTML,
						 type : rows[i].cells[2].innerHTML,
						 amount : rows[i].cells[4].innerHTML,
						 price : rows[i].cells[3].innerHTML
						 });
						 // if (selectedRow[0].cells[0].innerHTML ==
						 // rows[i].cells[0].innerHTML) {
						 // table.deleteRow(i);
						 // }
						 }
//						 console.log(products);

						var deliveryDate = document
								.getElementById("deliveryDate");
						// console.log(deliveryDate);
						// console.log(deliveryDate.value);
						// console.log(deliveryDate.innerHTML);

						 var data = {
						  customerNumber : customerNumber,
						  companyName : companyName,
						  city : city,
						  street : street,
						  houseNumber : houseNumber,
						  postalcode : postalcode,
						  deliveryDate : deliveryDate.value,
						 products : products
						 };
//						products.push("hoi");
//						products.push("Mark");
//						var data = {
//							//								
//							hoi : "hoi",
//							mark : "mark",
//							products : products
//						};
						console.log(data);

						$.post("placeOrder", data, config).then(
								function(response) {
									window.alert(response);
									// clear vieuw if order was
									// succesvol

								}, function error(response) {
									// console.log("response: " + response);
									// $scope.postResultMessage = "Error with status: " + response.statusText;
								});

					}

				});
