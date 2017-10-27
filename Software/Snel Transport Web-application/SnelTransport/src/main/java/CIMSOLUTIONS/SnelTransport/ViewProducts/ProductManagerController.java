package CIMSOLUTIONS.SnelTransport.ViewProducts;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import CIMSOLUTIONS.SnelTransport.PlaceOrder.Product;
import CIMSOLUTIONS.SnelTransport.PlaceOrder.searchProductService;

@RestController
public class ProductManagerController {


		@RequestMapping(value = "/searchSetProduct", method = RequestMethod.GET)
		@ResponseBody
		public List<Product> getAllProducts(
			@RequestParam("productNumber") String productNumber,
			@RequestParam("productName") String productName,
			@RequestParam("categoryList") String categoryList,
			@RequestParam("warehouse") String warehouse,
			@RequestParam(value = "compartimentNumber", required = false) int compartimentNumber,
			@RequestParam("productStatus") String productStatus){
		
		Product product = new Product(productNumber, productName, categoryList, 
			warehouse, compartimentNumber, productStatus);
		searchProductService searchProduct = new searchProductService();	
		searchProduct.lookUpProduct(product);
		return searchProduct.getResultSet();

	}
}
