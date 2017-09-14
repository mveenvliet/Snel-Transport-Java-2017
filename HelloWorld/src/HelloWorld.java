import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HelloWorld {
	public static void main(String args[]) throws IOException, InterruptedException {
		char ch;

		System.out.println("Hello world!");
		while (true) {

			ch = (char) System.in.read();
			// TimeUnit.SECONDS.sleep(1);
			if (ch > 'A' && ch < 'z')
				System.out.println("You pressed: " + ch);
			else if (ch == '\n')
				System.out.println("Return pressed");
		}

	}
}
