package CIMSOLUTIONS.SnelTransport.PlaceOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import CIMSOLUTIONS.Database.MySqlDB;

public class searchProductService extends MySqlDB {

	private ArrayList<Product> resultSet = new ArrayList<>();

	public searchProductService() {
		super();
	}

	public ArrayList<Product> getResultSet() {
		return resultSet;
	}

	private boolean checkIfProductInResultset(int id) {
		for (Product product : resultSet) {
			if (product.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public String createQuerry(Product p) {
		String sqlQuerry = "SELECT productlist.idProductList, productlist.description, productlist.productNumber, productlist.compartmentNumber, productlist.status, productlist.location, productlist.price, productlist.amount, databasesneltransport.type.type "
				+ "FROM databasesneltransport.productlist, databasesneltransport.productcrosstype , databasesneltransport.type "
				+ "WHERE productlist.idProductList = productcrosstype.idProductList AND productcrosstype.idType = type.idType ";

		if (p.getProductNumber() != null && !p.getProductNumber().isEmpty()) {

			sqlQuerry += " AND productNumber LIKE '%" + p.getProductNumber() + "%' ";
		}
		if (p.getName() != null && !p.getName().isEmpty()) {
			sqlQuerry += "AND description LIKE '%" + p.getName() + "%' ";
		}
		for (String type : p.getTypeList()) {
			if (type != null && !type.isEmpty()) {
				sqlQuerry += "AND type LIKE '%" + type + "%' ";
			}
		}

		System.out.println(sqlQuerry);
		return sqlQuerry;
	}

	public void lookUpProduct(Product p) {

		String sqlQuerry = createQuerry(p);

		try {
			Statement myStmt = MyCon.createStatement();
			ResultSet myRs = myStmt.executeQuery(sqlQuerry);
			

			while (myRs.next()) {
				Product tempProduct = new Product();
				if (!checkIfProductInResultset(myRs.getInt("idProductList"))) {
					tempProduct.setId(myRs.getInt("idProductList"));
					tempProduct.setProductNumber(myRs.getString("productNumber"));
					tempProduct.setName(myRs.getString("description"));
					tempProduct.setPrice(myRs.getDouble("price"));
					tempProduct.setAmount(myRs.getInt("amount"));
					tempProduct.setLocation(myRs.getString("location"));
					tempProduct.setCompatmentNumber(myRs.getInt("compartmentNumber"));
					tempProduct.addToTypeList(myRs.getString("type"));
				} else {
					for (Product product : resultSet) {
						if (product.getId() == myRs.getInt("idProductList")) {
							product.addToTypeList(myRs.getString("type"));
						}
					}
				}

				tempProduct.printValues();
				resultSet.add(tempProduct);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
