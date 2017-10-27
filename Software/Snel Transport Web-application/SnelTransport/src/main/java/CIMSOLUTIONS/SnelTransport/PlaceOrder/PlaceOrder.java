package CIMSOLUTIONS.SnelTransport.PlaceOrder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import CIMSOLUTIONS.Database.MySqlDB;

public class PlaceOrder extends MySqlDB {
	private Order order = new Order();
	private int idAddressCustomer;

	public PlaceOrder() {
		super();
	}

	public PlaceOrder(Order order) {
		super();
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public boolean checkCustomerNumber() throws SQLException {
		try {
			if (order.getCustomer().getCustomerNumber() > 0) {
				PreparedStatement preparedStmt = MyCon.prepareStatement(
						"SELECT idCustomer, customer_idAddress FROM databasesneltransport.customer WHERE customerNumber = ?");
				preparedStmt.setInt(1, order.getCustomer().getCustomerNumber());
				ResultSet myRs = preparedStmt.executeQuery();
				if (myRs.next()) {
					order.getCustomer().setId(myRs.getInt("idCustomer"));
					idAddressCustomer = myRs.getInt("customer_idAddress");
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			return false;
		}
	}

	private boolean checkAdres() throws SQLException {
		try {
			PreparedStatement preparedStmt = MyCon.prepareStatement(
					"SELECT idAddress FROM databasesneltransport.address WHERE city = ? AND street = ? AND houseNumber = ? AND postalCode = ?");
			preparedStmt.setString(1, order.getCustomer().getAddress().getCity());
			preparedStmt.setString(2, order.getCustomer().getAddress().getStreet());
			preparedStmt.setString(3, order.getCustomer().getAddress().getHouseNumber());
			preparedStmt.setString(4, order.getCustomer().getAddress().getPostalCode());

			ResultSet myRs = preparedStmt.executeQuery();
			if (myRs.next()) {
				order.getCustomer().getAddress().setId(myRs.getInt("idAddress"));
				return true;
			} else {
				return false;
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			return false;
		}
	}

	private boolean checkIfAddresIsFromCustomer() {

		if (idAddressCustomer == order.getCustomer().getAddress().getId()) {
			return true;
		} else {
			return false;
		}

	}

	private boolean checkIfProductsExcist() {
		PreparedStatement preparedStmt;
		try {
			for (Product product : order.getOrderLineList()) {
				System.out.println(product.getProductNumber() + "\t" + product.getName() + "\t" + product.getAmount());

				preparedStmt = MyCon.prepareStatement("SELECT idProductList FROM databasesneltransport.productlist WHERE productNumber = ?");
				preparedStmt.setString(1, product.getProductNumber());

				ResultSet myRs = preparedStmt.executeQuery();
				if (myRs.next()) {
					product.setId(myRs.getInt("idProductList"));
					System.out.println("productId: " + product.getId());
				} else {
					return false;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean insertOrderLines() {
		PreparedStatement preparedStmt;

		try {
				for (Product product : order.getOrderLineList()) {
				preparedStmt = MyCon.prepareStatement(
						"INSERT INTO databasesneltransport.orderline (amount, price, idOrderList, idProductNumber)" + " values (?, ?, ?, ?)");
				System.out.println(order.getOrderId());
				preparedStmt.setInt(1, product.getAmount());
				preparedStmt.setDouble(2, product.getPrice());
				preparedStmt.setInt(3, order.getOrderId());
				preparedStmt.setInt(4, product.getId());

				preparedStmt.execute();
				
				preparedStmt = MyCon.prepareStatement("UPDATE databasesneltransport.productlist SET amount =  amount -'"
						+ product.getAmount() + "' WHERE productNumber = '" + product.getProductNumber()
						+ "'");
				int count = preparedStmt.executeUpdate();
				if (!(count > 0)) {
					return false;
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean insertOrderInOrderList() {
		PreparedStatement preparedStmt;
		try {

			System.out.println("test2");
			preparedStmt = MyCon.prepareStatement(
					"INSERT INTO databasesneltransport.orderlist (orderlist.customerNumber, orderlist.orderDate, orderlist.deliveryDate, orderlist.idAddress)"
							+ " values (?, ?, ?, ?)");
			preparedStmt.setInt(1, order.getCustomer().getCustomerNumber());
			preparedStmt.setString(2, Order.switchDateFormat(order.getOrderDate()));
			preparedStmt.setString(3, Order.switchDateFormat(order.getDeliveryDate()));
			preparedStmt.setInt(4, order.getCustomer().getAddress().getId());

			preparedStmt.execute();
			System.out.println("test3");
			preparedStmt = MyCon.prepareStatement("SELECT MAX(idOrderList) AS idOrderList FROM databasesneltransport.orderlist");
			ResultSet myRs = preparedStmt.executeQuery();
			if (myRs.next()) {
				order.setOrderId(myRs.getInt("idOrderList") );
				order.setOrderNumber(myRs.getInt("idOrderList") );
				preparedStmt = MyCon.prepareStatement("UPDATE databasesneltransport.orderlist SET orderNumber = '"+ order.getOrderNumber() + "' WHERE idOrderList = '" + order.getOrderId() + "'");
				int count = preparedStmt.executeUpdate();
				if (!(count > 0)) {
					return false;
				}
			}
			else {
				return false;
			}

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public String writeOrderToDatabase() {
		try {
			if (checkCustomerNumber()) {
				if (checkAdres()) {
					if (checkIfAddresIsFromCustomer()) {
						if (checkIfProductsExcist()) {
							if (insertOrderInOrderList()) {
								if (insertOrderLines()) {
									return "De Bestelling met ordernummer: " + order.getOrderNumber()
											+ " is gelplaatst.";
								} else
									return "Een van de producten kan niet besteld worden.";
							} else {
								return "De Bestelling kan niet geplaatst worden.";
							}
						} else {
							return "Een of meerdere producten bestaan niet.";
						}
					} else {
						return "Het adres hoort niet bij deze klant.";
					}
				} else {
					return "Het Addres bestaat niet.";
				}

			} else {
				return "De klant bestaat niet.";
			}

		} catch (Exception exc) {
			exc.printStackTrace();
			return "ERROR: De bestelling kan niet worden geplaatst!";
		}

	}

}
