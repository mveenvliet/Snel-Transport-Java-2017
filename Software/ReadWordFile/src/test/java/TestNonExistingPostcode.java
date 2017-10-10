import static org.junit.Assert.*;

import org.junit.Test;

public class TestNonExistingPostcode {

	@Test
	public void test() {
		ContactGegevens test = new ContactGegevens();
		test.setKlantnummer(1);
		test.setNaamBedrijf("Test");
		test.setPostcode("3404BD");
		test.setStad("Assen");
		test.setStraat("Oosterpark");
		test.setHuisnummer("13");
		test.setTelefoonnummer("0000000000");
		
		CheckAddress AddressdftKey = new CheckAddress(); 
		AddressdftKey.checkAddressComponents(test); 		
		
		assertTrue(AddressdftKey.getProgress());
		
	}

}
