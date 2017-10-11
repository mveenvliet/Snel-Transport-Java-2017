import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

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
			int counter = 0;
			ContactGegevens  newContact = new ContactGegevens();
			for (int iter = 1 ; iter < dataArray.length ; iter ++)
			{
				outerloop:
				if (dataArray[iter].replace("\\W","").trim().isEmpty()) {
					
					// TO DO:
					// check if number is unique
					// skip first sentence
					if (counter > 1) {
						System.out.println("----> naam bedrijf  : " + newContact.getNaamBedrijf());
						System.out.println("----> postcode      : " + newContact.getPostcode());
						System.out.println("----> stad          : " + newContact.getStad());
						System.out.println("----> straat        : " + newContact.getStraat());
						System.out.println("----> huisnummer    : " + newContact.getHuisnummer());
						
						CheckAddressSingle dftKey = new CheckAddressSingle();
						dftKey.checkAddress(newContact);

						System.out.println("----> postcode      : " + newContact.getPostcode());
						System.out.println("----> stad          : " + newContact.getStad());
						System.out.println("----> straat        : " + newContact.getStraat());
						System.out.println("----> huisnummer    : " + newContact.getHuisnummer());
						
						
						newContact.resetAll();
						System.out.println("Start new contact");
					}
					counter = 1;
				} else {
					switch (counter) {
					case 1:
						newContact.setKlantnummer(Integer.parseInt(dataArray[iter].replaceAll("\\D+", "")));
						counter++;
						break;
					case 2:
						newContact.setNaamBedrijf(dataArray[iter].trim());
						counter++; 
						break;
					case 3:
						
						if (dataArray[iter].indexOf("Tel:")!=-1) {
							newContact.setTelefoonnummer(dataArray[iter].replaceAll("Tel:","").trim());
							break;
						}
						for (int chIter = 0; chIter < dataArray[iter].length(); chIter++) {
							if (Character.isLetter(dataArray[iter].charAt(chIter))) {
								break;
							}
							if (chIter == dataArray[iter].length() - 1) {
								newContact.setTelefoonnummer(dataArray[iter].trim());
								break;
							}
						}
	

						int numberCounter = 0;
						boolean postcodeSet = false;
						for (int chIter = 0; chIter < dataArray[iter].length(); chIter++) {
							if (postcodeSet)
								break;
							char ch = dataArray[iter].charAt(chIter);
							if (ch >= '0' && ch <= '9') {
								numberCounter++;
							} else {
								if (numberCounter == 4) {

									int counterPostcode = 0;
									int counterPostcodeCh = 0;

									while (chIter + counterPostcode < dataArray[iter].length() && !postcodeSet && counterPostcode <= 3) {

										if (Character.isLetter(dataArray[iter].charAt(chIter + counterPostcode))) {
											counterPostcodeCh++;
										}
										if (counterPostcodeCh == 2) {
											if (Arrays.asList(' ','\t','\r','\n').contains(dataArray[iter].charAt(chIter + counterPostcode - counterPostcodeCh + 3))){
												
												String newPostcode = dataArray[iter]
														.substring(chIter - 4, chIter + counterPostcode - counterPostcodeCh + 3);
												newContact.setPostcode(newPostcode);
												
												//System.out.println("Postcode is set here");
												String newStad ="";
												postcodeSet = true;
												if(!dataArray[iter].substring(chIter + counterPostcode - counterPostcodeCh + 4, dataArray[iter].length()).isEmpty()) {
													//System.out.println("Stad set");
													newStad = dataArray[iter].substring(chIter + counterPostcode - counterPostcodeCh + 4, dataArray[iter].length());
													newContact.setStad(newStad.trim());
												}
												if(!dataArray[iter].substring(0, chIter - 4).isEmpty()) {
													String residue = dataArray[iter].replaceAll(newPostcode,"").replaceAll(newStad, "");
													
													int firstDigit = 0;
													while (firstDigit < residue.length() && !Character.isDigit(residue.charAt(firstDigit))) {
														firstDigit++;
													}
													

													newContact.setStraat(residue.substring(0,firstDigit - 1).replaceAll(",", "").trim());
													newContact.setHuisnummer(residue.substring(firstDigit, residue.length()).replaceAll(",", "").trim());
													
												}
												break outerloop;
											} else {
												numberCounter = 0;
												break;
											}
										}
										counterPostcode++;
									}
									break;
								}								
							numberCounter = 0;
							}	
						}
						if(!dataArray[iter].contains("Fax:")) {
							int firstDigit = 0;
							while (firstDigit < dataArray[iter].length() && !Character.isDigit(dataArray[iter].charAt(firstDigit))) {
								firstDigit++;
							}
							if(firstDigit!=0) {
								newContact.setStraat(dataArray[iter].substring(0,firstDigit - 1).replaceAll(",", "").trim());
								newContact.setHuisnummer(dataArray[iter].substring(firstDigit, dataArray[iter].length()).replaceAll(",", "").trim());
							}
						}
							
						
						break;
					default:
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Hoi");						
		}
	}
}
