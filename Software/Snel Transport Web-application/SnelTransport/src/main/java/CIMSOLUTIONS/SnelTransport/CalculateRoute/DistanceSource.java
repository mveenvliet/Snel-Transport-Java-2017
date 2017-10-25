package CIMSOLUTIONS.SnelTransport.CalculateRoute;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class DistanceSource {

	private String sourceAddress= "Zeugstraat+92+2801JD+gouda";
	
	List<Integer> distanceToSource = new ArrayList<>();
	List<Integer> distanceFromSource = new ArrayList<>();
	
	DistanceSource(AddressList addresses){
		setDistanceFromSource(addresses);
		setDistanceToSource(addresses);
	}
	
	DistanceSource(AddressList addresses,Address homeAddress){
		setHomeAddress(homeAddress);
		setDistanceFromSource(addresses);
		setDistanceToSource(addresses);
	}
	
	
	public void setDistanceFromSource(AddressList addresses) {
		String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" +
		sourceAddress + "&destinations=";
		for (int iter = 0 ; iter < addresses.getNumberOfAddresses() ; iter++) {
			url += addresses.getSingleAddress(iter).getUrlPartition();
			if (iter + 1 != addresses.getNumberOfAddresses()) {
				url += "|";
			}
		}
		url += "&mode=driving&language=nl-NL&key=" + Keys.distanceKey;				
		setListFromJSON(url,distanceFromSource);		
		
	}
	
	public void setDistanceToSource(AddressList addresses) {
		String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
		for (int iter = 0 ; iter < addresses.getNumberOfAddresses() ; iter++) {
			url += addresses.getSingleAddress(iter).getUrlPartition();
			if (iter + 1 != addresses.getNumberOfAddresses()) {
				url += "|";
			}
		}
		url += "&destinations=" + sourceAddress;
		url += "&mode=driving&language=nl-NL&key=" + Keys.distanceKey;
		
		setListFromJSON(url,distanceToSource);
	}
	
	public String getHomeAddress() {
		return sourceAddress;
	}

	public void setHomeAddress(Address address) {
		address.setUrlPartition();
		this.sourceAddress = address.getUrlPartition();
	}
	
	
	void setListFromJSON(String urlString, List<Integer> distanceList) {
		HttpURLConnection request = null;
		try {
			URL url = new URL(urlString);
			System.out.println(url);
			request = (HttpURLConnection) url.openConnection();
			request.connect();

			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			JsonObject jsonFile = root.getAsJsonObject();
			JsonArray jsonRes = (JsonArray) jsonFile.get("rows");
			for (int yIter = 0; yIter < jsonRes.size(); yIter++) {
				JsonObject jsonResyIter = (JsonObject) jsonRes.get(yIter);
				JsonArray jsonResyIterElements = (JsonArray) jsonResyIter.get("elements");
				for (int xIter = 0; xIter < jsonResyIterElements.size(); xIter++) {
					JsonObject jsonResyIterElementsxIter = (JsonObject) jsonResyIterElements.get(xIter);
					distanceList.add(((JsonObject) jsonResyIterElementsxIter.get("duration")).get("value").getAsInt());
				}
			}
			request.disconnect();
		} catch (IllegalStateException | JsonSyntaxException e) {
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

	public int getSizeList() {
		return Math.min(distanceToSource.size(),distanceFromSource.size());
	}
	public int getDistanceFromSourceToPoint(int indexAddress) {
		return distanceFromSource.get(indexAddress);
	}
	
	public int getDistanceToSourceFromPoint(int indexAddress) {
		return distanceToSource.get(indexAddress);
	}
}
