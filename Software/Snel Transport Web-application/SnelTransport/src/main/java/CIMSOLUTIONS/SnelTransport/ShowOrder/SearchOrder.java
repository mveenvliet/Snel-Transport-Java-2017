package CIMSOLUTIONS.SnelTransport.ShowOrder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import CIMSOLUTIONS.Database.MySqlDB;
import CIMSOLUTIONS.SnelTransport.PlaceOrder.Order;
import CIMSOLUTIONS.SnelTransport.PlaceOrder.Product;

public class SearchOrder extends MySqlDB {
	private ArrayList<Order> resultSet = new ArrayList<>();

	public SearchOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchOrder(ArrayList<Order> resultSet) {
		super();
		this.resultSet = resultSet;
	}

	public ArrayList<Order> getResultSet() {
		return resultSet;
	}

	public void setResultSet(ArrayList<Order> resultSet) {
		this.resultSet = resultSet;
	}

	public String createQuerryOrder(Order o) {
		List<Integer> idAddressen = new ArrayList<>();
		if (o.getCustomer().getCompany().getName() != null && !o.getCustomer().getCompany().getName().isEmpty()) {
			String querry = "SELECT * FROM databasesneltransport.company WHERE name = '"
					+ o.getCustomer().getCompany().getName() + "'";
			int companyId;

			try {
				Statement myStmt = MyCon.createStatement();

				ResultSet myRs = myStmt.executeQuery(querry);
				if (myRs.next()) {
					companyId = myRs.getInt("idCompany");
				} else {
					return "SELECT * FROM databasesneltransport.orderlist, databasesneltransport.address WHERE orderlist.idAddress = address.idAddress";
				}
				querry = "SELECT idAddress FROM databasesneltransport.address WHERE idCompany = '" + companyId + "'";
				myRs = myStmt.executeQuery(querry);
				while (myRs.next()) {
					idAddressen.add(myRs.getInt("idAddress"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		String sqlQuerry = "SELECT * FROM databasesneltransport.orderlist, databasesneltransport.address WHERE orderlist.idAddress = address.idAddress ";

		if (o.getCustomer().getCustomerNumber() != 0) {
			sqlQuerry += "AND customerNumber = '" + o.getCustomer().getCustomerNumber() + "' ";
		}
		if (!idAddressen.isEmpty()) {
			boolean first = true;
			sqlQuerry += "AND orderlist.idAddress IN(";
			for (Integer integer : idAddressen) {
				if (first) {
					sqlQuerry += integer;
					first = false;
				} else {
					sqlQuerry += ", " + integer;
				}
			}
			sqlQuerry += ") ";
		}
		if (o.getOrderNumber() != 0) {
			sqlQuerry += "AND orderNumber = '" + o.getOrderNumber() + "' ";
		}
		if (o.getDeliveryDate() != null && !o.getDeliveryDate().isEmpty()) {
			sqlQuerry += "AND deliveryDate = '" + Order.switchDateFormat(o.getDeliveryDate()) + "' ";
		}
		sqlQuerry += "ORDER BY deliveryDate DESC";
		System.out.println(sqlQuerry);
		return sqlQuerry;
	}

	public void lookUpOrder(Order o) {

		String sqlQuerry = createQuerryOrder(o);

		try {
			Statement myStmt = MyCon.createStatement();
			ResultSet myRs = myStmt.executeQuery(sqlQuerry);

			while (myRs.next()) {
				Order tempOrder = new Order();
				tempOrder.getCustomer().setCustomerNumber(myRs.getInt("customerNumber"));
				tempOrder.getCustomer().getCompany().setName(o.getCustomer().getCompany().getName());
				tempOrder.setOrderId(myRs.getInt("idOrderList"));
				tempOrder.setOrderNumber(myRs.getInt("orderNumber"));
				tempOrder.setOrderDate(Order.switchDateFormat(myRs.getString("orderDate")));
				tempOrder.setDeliveryDate(Order.switchDateFormat(myRs.getString("deliveryDate")));
				tempOrder.setStatus(myRs.getString("status"));
				tempOrder.getCustomer().getAddress().setId(myRs.getInt("idAddress"));
				tempOrder.getCustomer().getAddress().setCity(myRs.getString("city"));
				tempOrder.getCustomer().getAddress().setStreet(myRs.getString("street"));
				tempOrder.getCustomer().getAddress().setHouseNumber(myRs.getString("houseNumber"));
				tempOrder.getCustomer().getAddress().setPostalCode(myRs.getString("postalCode"));

				tempOrder.printValues();
				resultSet.add(tempOrder);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<String> lookUpProductNumber(int productId) {
		List<String> productNumName = new ArrayList<>();

		Statement myStmt;
		try {
			myStmt = MyCon.createStatement();
			ResultSet myRs = myStmt.executeQuery(
					"SELECT description, productNumber FROM databasesneltransport.productlist WHERE idProductList = '"
							+ productId + "'");
			if (myRs.next()) {
				productNumName.add(myRs.getString("productNumber"));
				productNumName.add(myRs.getString("description"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productNumName;
	}

	public String createQuerryOrderLines(Order o) {
		String sqlQuerry = "SELECT * FROM databasesneltransport.orderline WHERE idOrderList = '" + o.getOrderNumber()
				+ "'";

		System.out.println(sqlQuerry);
		return sqlQuerry;
	}

	public void lookUpOrderLines(Order o) {

		String sqlQuerry = createQuerryOrderLines(o);

		try {
			Statement myStmt = MyCon.createStatement();
			ResultSet myRs = myStmt.executeQuery(sqlQuerry);
			Order tempOrder = new Order();
			boolean first = true;

			while (myRs.next()) {
				if (first) {
					tempOrder.setOrderId(myRs.getInt("idOrderList"));
					tempOrder.setOrderNumber(myRs.getInt("idOrderList"));
					first = false;
				}

				Product tempProduct = new Product();
				List<String> productNumName = new ArrayList<>();
				tempProduct.setId(myRs.getInt("idProductNumber"));
				tempProduct.setAmount(myRs.getInt("amount"));
				tempProduct.setPrice(myRs.getDouble("price"));
				tempProduct.setStatus(myRs.getString("status"));
				productNumName = lookUpProductNumber(tempProduct.getId());
				tempProduct.setProductNumber(productNumName.get(0));
				tempProduct.setName(productNumName.get(1));
				tempOrder.getOrderLineList().add(tempProduct);

				tempOrder.printValues();
				resultSet.add(tempOrder);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String setOrderStatus(Order o) {
		PreparedStatement myStmt;
		System.out.println(o.getStatus());

		if (!o.getStatus().isEmpty()) {
			if (o.getStatus().contentEquals("Bezorgd")) {
				try {
					myStmt = MyCon.prepareStatement(
							"UPDATE databasesneltransport.orderlist SET status = 'Bezorgd' WHERE idOrderList = '"
									+ o.getOrderNumber() + "'");
					int count = myStmt.executeUpdate();
					if (!(count > 0)) {
						return "Failed to change order status to: " + o.getStatus();
					}

					myStmt = MyCon.prepareStatement(
							"UPDATE databasesneltransport.orderLine SET status = 'Bezorgd' WHERE idOrderList = '"
									+ o.getOrderNumber() + "'");
					count = myStmt.executeUpdate();
					if (count > 0) {
						System.out.println(o.getStatus());
						return "Order status changed to: " + o.getStatus();
					}

				} catch (SQLException e) {
					e.printStackTrace();
					return "Failed to change order status to: " + o.getStatus();
				}

			} else {
				try {
					myStmt = MyCon.prepareStatement("UPDATE databasesneltransport.orderlist SET status = '"
							+ o.getStatus() + "' WHERE idOrderList = '" + o.getOrderNumber() + "'");
					int count = myStmt.executeUpdate();
					if (count > 0) {
						return "Order status changed to: " + o.getStatus();
					}
				} catch (SQLException e) {
					e.printStackTrace();
					return "Failed to change order status to: " + o.getStatus();
				}
			}
		}
		return "Failed to change order status to: " + o.getStatus();
	}

	public String setOrderLineStatus(Order o) {
		if (!(o.getOrderLineList().get(0).getStatus().isEmpty())) {
			PreparedStatement myStmt;
			try {
				myStmt = MyCon.prepareStatement("UPDATE databasesneltransport.orderline SET status = '"
						+ o.getOrderLineList().get(0).getStatus() + "' WHERE idOrderList = '" + o.getOrderNumber()
						+ "'");
				int count = myStmt.executeUpdate();
				if (count > 0) {
					return "Orderline status changed to: " + o.getOrderLineList().get(0).getStatus();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return "Failed to change orderline status to: " + o.getOrderLineList().get(0).getStatus();
			}
		}

		return "Failed to change orderline status to: " + o.getOrderLineList().get(0).getStatus();
	}

	public String deleteOrder(int orderNumber) {
		if (orderNumber > 0) {
			PreparedStatement myStmt;
			try {
				if(orderlineToInventaris(orderNumber)) {
				myStmt = MyCon.prepareStatement("DELETE FROM databasesneltransport.orderline WHERE idOrderList = '"
						+ orderNumber + "'");
				int count = myStmt.executeUpdate();
				if (!(count > 0)) {
					return "Could not delete the order with ordernumber: " + orderNumber;
				}
				myStmt = MyCon.prepareStatement("DELETE FROM databasesneltransport.orderlist WHERE idOrderList = '"
						+ orderNumber + "'");
				count = myStmt.executeUpdate();
				if (!(count > 0)) {
					return "Could not delete the order with ordernumber: " + orderNumber;
				}
				}else {
					return "Could not delete the order with ordernumber: " + orderNumber;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				return "Could not delete the order with ordernumber: " + orderNumber;
			}

		} else {
			return "Could not delete the order with ordernumber: " + orderNumber;
		}
		return "The order with ordernumber: " + orderNumber + " was delleted.";
	}

	public boolean orderlineToInventaris(int orderNumber) {
		List<Product> productList = new ArrayList<>();
		PreparedStatement myStmt;
		
		try {
			myStmt = MyCon.prepareStatement("Select idProductNumber, amount FROM databasesneltransport.orderline WHERE idOrderList = '"
					+ orderNumber + "'");
			
			ResultSet myRs = myStmt.executeQuery();
			while(myRs.next()) {
				Product p = new Product();
				p.setId(myRs.getInt("idProductNumber"));
				p.setAmount(myRs.getInt("amount"));
				productList.add(p);
			}
			for (Product product : productList) {
				myStmt = MyCon.prepareStatement("UPDATE databasesneltransport.productlist SET amount =  amount +'"
						+ product.getAmount() + "' WHERE idProductList = '" + product.getId()
						+ "'");
				int count = myStmt.executeUpdate();
				if (count > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

	public String deleteOrderLine(int orderNumber, String productNumber) {
		PreparedStatement myStmt;
		
		
		return null;
	}


}


