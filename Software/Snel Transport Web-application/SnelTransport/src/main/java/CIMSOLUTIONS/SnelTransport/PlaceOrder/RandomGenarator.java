package CIMSOLUTIONS.SnelTransport.PlaceOrder;

import java.util.Random;

public class RandomGenarator {
	private static int minimum = 1;
	private static int maximum = 2147483647;
	
	RandomGenarator(int min, int max){
		minimum = min;
		maximum = max;
	}
	
	public static int  generateNumber() {
		int randNum = 0;
		Random random = new Random();
		
		randNum = random.nextInt((maximum - minimum) + 1) + minimum;		
		
		return randNum;
	}
}
