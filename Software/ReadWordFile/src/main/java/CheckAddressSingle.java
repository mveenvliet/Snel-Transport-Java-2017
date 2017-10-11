import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class CheckAddressSingle {

	
	private String key = "AIzaSyDwhVdtj_OGqgZFQX-HLvgFtSTUjW1wDS4";
	private String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json?";

	private String straat = "";
	private String huisnummer = "";
	private String postcode = "";
	private String stad = "";

	private List<ContactGegevens> InvalidContacts = new ArrayList<>();
	
	private void setGoogleValues(ContactGegevens address) {

		String urlString = baseUrl + "address=" 
				+ address.getStraat().replaceAll(" ", "%20") + "+"
				+ address.getHuisnummer().replaceAll(" ", "%20") + "+" 
				+ address.getStad().replaceAll(" ", "%20") + "+"
				+ address.getPostcode()
				+ "&key=" + key;
		
		HttpURLConnection request = null;
		try {
			URL url = new URL(urlString);
			System.out.println(url);
			request = (HttpURLConnection) url.openConnection();
			request.connect();
			
			
			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			JsonObject jsonFile = root.getAsJsonObject();
			JsonArray jsonRes = (JsonArray) jsonFile.get("results");
			JsonObject jsonRes0 = (JsonObject) jsonRes.get(0);
			JsonArray jsonRes0AC = (JsonArray) jsonRes0.get("address_components");
			String jsonElementList = new String();
			for (int iter = 0; iter < jsonRes0AC.size() ; iter++) {
				jsonElementList = ((JsonObject) jsonRes0AC.get(iter)).get("types").toString();
				if (jsonElementList.contains("route")) {
					JsonObject jsonRes0AC1 = (JsonObject) jsonRes0AC.get(iter);
					straat = jsonRes0AC1.get("long_name").getAsString();
				}
				if (jsonElementList.contains("locality")) {
					JsonObject jsonRes0AC1 = (JsonObject) jsonRes0AC.get(iter);
					stad = jsonRes0AC1.get("long_name").getAsString();
				}
				if (jsonElementList.contains("postal_code")) {
					JsonObject jsonRes0AC1 = (JsonObject) jsonRes0AC.get(iter);
					postcode = jsonRes0AC1.get("long_name").getAsString().replaceAll(" ","");
				}
				if (jsonElementList.contains("street_number")) {
					JsonObject jsonRes0AC1 = (JsonObject) jsonRes0AC.get(iter);
					huisnummer = jsonRes0AC1.get("long_name").getAsString();
				}
			}	

		} catch (IllegalStateException  | JsonSyntaxException | UnsupportedEncodingException e) {
			System.out.println(e);
			request.disconnect();
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.out.println("Invalid URL");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Valid to connect");
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("There was an invalid Contact");
			InvalidContacts.add(address);
		}
			

	}
	
	public void checkAddress(ContactGegevens address) {

		setGoogleValues(address);
		
		if (!this.stad.equals(address.getStad())){
			address.setStad("ERROR "  + this.stad);
		}
		if (!this.straat.equals(address.getStraat())){
			if (this.straat.equals(address.getStraat().replaceAll("str","straat"))) {
				address.setStraat(this.straat);
			} else if (this.straat.equals(address.getStraat().replaceAll("wg","weg"))){
				address.setStraat(this.straat);
			} else {
				address.setStraat("ERROR " + this.straat);
			}
		}
		if (!this.postcode.replaceAll("\\D+", "").equals(address.getPostcode().replaceAll("\\D+", ""))){
			address.setPostcode("ERROR " + this.postcode);
		}
		if (!this.huisnummer.replaceAll("\\W", "").equals(address.getHuisnummer().replaceAll("\\W", ""))){
			address.setHuisnummer("ERROR " + this.huisnummer);
		}
		

	}
	
	
	String getStraat() {
		return this.straat;
	}
	
	String getStad() {
		return this.stad;
	}
	
	String getPostcode() {
		return this.postcode;
	}
	
	
	CheckAddressSingle() {
	}

	CheckAddressSingle(String key) {
		this.key = key;
	}

}
