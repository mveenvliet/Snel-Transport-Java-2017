import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class CheckAddress {

	private boolean done = false;		// 0 is hasnt started, 1 an error happend , 2 is succes
	private String key = "AIzaSyDwhVdtj_OGqgZFQX-HLvgFtSTUjW1wDS4";
	private String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json?";

	private String straat = "";
	//private String huisnummer = "";
	private String postcode = "";
	private String stad = "";

	private String lat;
	private String lng;
	


	private void setLatLng(ContactGegevens address) {

		String urlString = baseUrl + "address=" + address.getStraat() + "+" + address.getHuisnummer() + "+"
				+ address.getStad() + "&key=" + key;
		
		HttpURLConnection request = null;
		try {
			URL url = new URL(urlString);
			
			request = (HttpURLConnection) url.openConnection();
			request.connect();
			
			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			JsonObject jsonFile = root.getAsJsonObject();
			JsonArray jsonResults = (JsonArray) jsonFile.get("results");
			JsonObject jsonResults0 = (JsonObject) jsonResults.get(0);
			JsonObject jsonResults0Geometry = (JsonObject) jsonResults0.get("geometry");
			JsonObject jsonResults0GeometryLocation = (JsonObject) jsonResults0Geometry.get("location");

			lat = jsonResults0GeometryLocation.get("lat").getAsString();
			lng = jsonResults0GeometryLocation.get("lng").getAsString();

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
		}

	}
	
	public void checkAddressComponents(ContactGegevens address) {

		setLatLng(address);
		String urlString = baseUrl + "latlng=" + lat + "," + lng + 
			"&location_type=ROOFTOP&result_type=street_address&key=" + key;
		
		HttpURLConnection request = null;
		try {
			URL url = new URL(urlString);
			
			request = (HttpURLConnection) url.openConnection();
			request.connect();
			
			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			JsonObject jsonFile = root.getAsJsonObject();
			JsonArray jsonRes = (JsonArray) jsonFile.get("results");
			JsonObject jsonRes0 = (JsonObject) jsonRes.get(0);
			JsonArray jsonRes0AC = (JsonArray) jsonRes0.get("address_components");
			// Een lengte- en breedtegraad kan meerdere huisnummers hebben, zoals in een flat
			//JsonObject jsonRes0AC0 = (JsonObject) jsonRes0AC.get(0);
			//huisnummer = jsonRes0AC0.get("long_name").getAsString();	 
			JsonObject jsonRes0AC1 = (JsonObject) jsonRes0AC.get(1);
			straat = jsonRes0AC1.get("long_name").getAsString();
			JsonObject jsonRes0AC2 = (JsonObject) jsonRes0AC.get(2);
			stad = jsonRes0AC2.get("long_name").getAsString();
			JsonObject jsonRes0AC6 = (JsonObject) jsonRes0AC.get(6);
			postcode = jsonRes0AC6.get("long_name").getAsString().replace(" ","");			
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
		}

				
		if (!this.stad.equals(address.getStad())){
			address.setStad("error");
		}
		if (!this.straat.equals(address.getStraat())){
			address.setStraat("error");
		}
		if (!this.postcode.equals(address.getPostcode())){
			address.setPostcode("error");
		}
		if (this.stad.equals(address.getStad()) &&
				this.straat.equals(address.getStraat()) &&
				this.postcode.equals(address.getPostcode())) {
			done = true;
		}
	}
	
	boolean getProgress() {
		return done;
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
	
	
	CheckAddress() {
	}

	CheckAddress(String key) {
		this.key = key;
	}

}
