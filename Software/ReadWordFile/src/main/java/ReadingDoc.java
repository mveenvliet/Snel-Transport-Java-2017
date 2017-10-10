import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class ReadingDoc {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		FileInputStream fileStream;
		WordExtractor extractor;
		HWPFDocument doc;
		try {
			fileStream = new FileInputStream("Klanten en adressen Snel Transport.doc");
			doc = new HWPFDocument(fileStream);
			
			fileStream.close();
			extractor = new WordExtractor(doc);
			
			String [] dataArray = extractor.getParagraphText();
	
			for (int iter = 0 ; iter < 10 ; iter ++)
			{
				System.out.print(dataArray[iter]);
			}
			} catch (IOException e) {
			System.out.println("Hoi");
		}
	}

}
