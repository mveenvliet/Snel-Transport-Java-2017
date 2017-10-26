package CIMSOLUTIONS.SnelTransport.ShowOrder;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import CIMSOLUTIONS.SnelTransport.PlaceOrder.Customer;
import CIMSOLUTIONS.SnelTransport.PlaceOrder.Order;
import CIMSOLUTIONS.SnelTransport.PlaceOrder.Product;
import CIMSOLUTIONS.SnelTransport.PlaceOrder.searchCustomerService;

@RestController
public class ShowOrderController {
	
	@RequestMapping(value = "/showOrder/searchCustomer", method = RequestMethod.GET)
	@ResponseBody
	public List<Customer> getAllCustomers(@RequestParam(value = "customerNumber", required = false) int customerNumber,
			@RequestParam("companyName") String companyName) {

		searchCustomerService searchCustomer = new searchCustomerService();
		Customer customer = new Customer(customerNumber, companyName);
		searchCustomer.createQuerry(customer);
		searchCustomer.lookUpCustomer(customer);
		return searchCustomer.getResultSet();
	}
	
	@RequestMapping(value = "/showOrder/searchOrders", method = RequestMethod.GET)
	@ResponseBody
	public List<Order> getAllOrders(@RequestParam(value = "customerNumber") int customerNumber,
			@RequestParam("companyName") String companyName, @RequestParam("orderNumber") int orderNumber, @RequestParam("deliveryDate") String deliveryDate) {
		System.out.println(customerNumber);
		System.out.println(companyName);
		System.out.println(orderNumber);
		System.out.println(deliveryDate);

		Order order = new Order(customerNumber, companyName, orderNumber, deliveryDate);
		SearchOrder searchOrder = new SearchOrder();
		searchOrder.lookUpOrder(order);
		return searchOrder.getResultSet();
	}
	
	@RequestMapping(value = "/showOrder/searchOrderLines", method = RequestMethod.GET)
	@ResponseBody
	public List<Order> getAllOrders(@RequestParam(value = "orderNumber") int orderNumber) {
		Order order = new Order();
		order.setOrderNumber(orderNumber);
		SearchOrder searchOrder = new SearchOrder();
		searchOrder.lookUpOrderLines(order);
		return searchOrder.getResultSet();
	}
	
	@RequestMapping(value = "/showOrder/editStatusOrder", method = RequestMethod.POST)
	@ResponseBody
	public String editStatusOrder(@RequestParam("orderNumber") int orderNumber, @RequestParam("status") String status ) {
		Order order = new Order();
		order.setOrderNumber(orderNumber);
		order.setStatus(status);
		SearchOrder searchOrder = new SearchOrder();
		return searchOrder.setOrderStatus(order);
	}
	
	@RequestMapping(value = "/showOrder/editStatusProductInOrder", method = RequestMethod.POST)
	@ResponseBody
	public String editStatusOrderLine(@RequestParam("orderNumber")  int orderNumber, @RequestParam("productNumber") String productNumber, @RequestParam("status") String status ) {
		Order order = new Order();
		Product product = new Product();
		product.setProductNumber(productNumber);
		product.setStatus(status);
		order.setOrderNumber(orderNumber);
		
		System.out.println(status);
		System.out.println(productNumber);
		System.out.println(orderNumber);
		order.getOrderLineList().add(product);
		SearchOrder searchOrder = new SearchOrder();
		return searchOrder.setOrderLineStatus(order);
	}
	
	@RequestMapping(value = "showOrder/deleteOrder", method = RequestMethod.POST)
	@ResponseBody
	public String deleteOrder(@RequestParam("orderNumber")  int orderNumber) {
	
		System.out.println(orderNumber);
		SearchOrder searchOrder = new SearchOrder();
		return searchOrder.deleteOrder(orderNumber);
	}


}
