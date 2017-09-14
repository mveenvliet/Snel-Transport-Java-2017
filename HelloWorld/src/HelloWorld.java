import java.io.IOException;

public class HelloWorld {
	public static void main(String args[]) throws IOException{
		char ch;

		ResponseRoel test = new ResponseRoel();
		test.answer();
		
		EverythingIsAwsome legoSong = new EverythingIsAwsome();
		legoSong.awsome();
		
		System.out.println("Hello world!");
		while (true) {

			ch = (char) System.in.read();
			
			if (ch > 'A' && ch < 'z')
				System.out.println("You pressed: " + ch);
			else if (ch == '\n')
				System.out.println("Return pressed");
		}

	}
}
