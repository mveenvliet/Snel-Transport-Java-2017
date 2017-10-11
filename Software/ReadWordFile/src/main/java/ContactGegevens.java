
public class ContactGegevens {

	private int klantnummer = 0;
	private String naamBedrijf = "";
	private String straat = "";
	private String huisnummer = "";
	private String postcode = "";
	private String stad = "";
	private String telefoonnummer = "";
	private boolean errorHappend = false;
	//private boolean isChecked = false; 
	
	private boolean isNumeric(String string) {
		if (string.matches("-?\\d+(\\.\\d+)?"))
			return true;
		else
			return false;
	}
	
	private boolean isLetter(String string) {
		char[] chars = string.toCharArray();
		for (char ch : chars) {
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
			} else {
				return false;
			}
		}
		return true;
	}
	
	public boolean errorCheck() {
		if (errorHappend) {
			System.out.println("Foute contactgegevens bij klant:\n");
			System.out.println("\t klantnummer:    " + this.klantnummer + "\n");
			System.out.println("\t bedrijf:        " + this.naamBedrijf + "\n");
			System.out.println("\t straatnaam:     " + this.straat + "\n");
			System.out.println("\t huisnummer:     " + this.huisnummer + "\n");
			System.out.println("\t postcode:       " + this.postcode + "\n");
			System.out.println("\t telefoonnummer: " + this.telefoonnummer + "\n");
			return false;
		} else {
			return true;
		}
		
		
	}
	
	
	public int getKlantnummer() {
		return klantnummer;
	}
	public void setKlantnummer(int klantnummer) {
		// TO DO:
		// Check of klantnummer al bestaat
		this.klantnummer = klantnummer;
	}
	public String getNaamBedrijf() {
		return naamBedrijf;
	}
	public void setNaamBedrijf(String naamBedrijf) {
		this.naamBedrijf = naamBedrijf;
	}
	
	public String getStraat() {
		return straat;
	}
	public void setStraat(String straat) {
		this.straat = straat;
	}
	public String getHuisnummer() {		
		return huisnummer;
	}
	public void setHuisnummer(String huisnummer) {
		this.huisnummer = huisnummer.toUpperCase();
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcodeIn) {
		this.postcode = "error";
		postcodeIn = postcodeIn.replace(" ","");
		if 	((postcodeIn.length() !=6) || 
			(!isNumeric(postcodeIn.substring(0, 3))) ||
			(!isLetter(postcodeIn.substring(4,5)))){
			errorHappend = true;	
		} else {
			this.postcode = postcodeIn;
		}
	}
	public String getTelefoonnummer() {
		return telefoonnummer;
	}
	public void setTelefoonnummer(String telefoonnummer) {
		this.telefoonnummer = "error";
		telefoonnummer = telefoonnummer.replace(" ","");
		telefoonnummer = telefoonnummer.replace("+","00");
		telefoonnummer = telefoonnummer.replace("-","");
		if (!isNumeric(telefoonnummer)) {
			errorHappend = true;
		} else {
			this.telefoonnummer = telefoonnummer;
		}
	}

	public String getStad() {
		return stad;
	}

	public void setStad(String stad) {
		this.stad = stad;
	}
	
	
	public void resetAll() {
		this.klantnummer = 0;
		this.naamBedrijf = "";
		this.straat = "";
		this.huisnummer = "";
		this.postcode = "";
		this.stad = "";
		this.telefoonnummer = "";
		this.errorHappend = false;
	}
	
	ContactGegevens(){}
	
	ContactGegevens(String straat, String huisnummer, String postcode, String stad){
		this.straat = straat;
		this.huisnummer = huisnummer;
		this.postcode = postcode;
		this.stad = stad;
	}
}
