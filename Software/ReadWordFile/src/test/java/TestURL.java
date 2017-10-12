import java.io.IOException;

import org.junit.Test;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;


public class TestURL {

	@Test
	public void test() throws JsonIOException, JsonSyntaxException, IOException {
		ContactGegevens test = new ContactGegevens();
		test.setKlantnummer(1);
		test.setNaamBedrijf("Test");
		test.setPostcode("9404BD");
		test.setStad("Assen");
		test.setStraat("Oosterpark");
		test.setHuisnummer("13");
		test.setTelefoonnummer("0000000000");
		
		CheckAddressSingle AddressdftKey = new CheckAddressSingle(); 
		AddressdftKey.checkAddress(test); 		

		assert(!test.getPostcode().contains("ERROR: "));
	}

}
