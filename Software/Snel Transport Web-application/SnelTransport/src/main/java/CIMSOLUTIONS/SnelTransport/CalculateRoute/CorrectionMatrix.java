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

public class CorrectionMatrix {

	
	List<Integer> indexLimburgAddress = new ArrayList<>();
	List<Integer> indexZeelandAddress = new ArrayList<>();
	List<Integer> indexOverijsselAddress = new ArrayList<>();
	
	// Check if any address is in 
	public CorrectionMatrix(AddressList addresses, DistanceMatrix matrix) {
		
		int nrOfAddresses = addresses.getNumberOfAddresses();
		for (int iter = 0 ; iter < nrOfAddresses; iter++) {
			Address address = addresses.getSingleAddress(iter); 
			if (address.getCity().equals("Maastricht") || address.getCity().equals("Roermond") ||  address.getCity().equals("Sittard")) {
				indexLimburgAddress.add(iter);
			}
			if (address.getCity().equals("Middelburg") || address.getCity().equals("Zierikzee")) {
				indexZeelandAddress.add(iter);
			}
			if (address.getCity().equals("Enschede")) {
				indexOverijsselAddress.add(iter);
			}
		}
				
		if ((indexLimburgAddress.size()!=0) && (indexOverijsselAddress.size()!=0)){
			
			for (int indexLimburg:indexLimburgAddress) {
				String limToNijmegen = 
							"https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + 
							addresses.getSingleAddress(indexLimburg).getUrlPartition() + "|A73+Nijmegen" +
							"&destinations=" +
							addresses.getSingleAddress(indexLimburg).getUrlPartition() + "|A73+Nijmegen" +
							"&mode=driving&language=nl-NL&key=" +
							Keys.distanceKey;
				int[] distancesLimToNijm = setPairFromJSON(limToNijmegen,"duration");
				for (int indexOverijssel:indexOverijsselAddress) {
					String overToNijmegen = 
							"https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + 
							addresses.getSingleAddress(indexOverijssel).getUrlPartition() + "|A73+Nijmegen" +
							"&destinations=" +
							addresses.getSingleAddress(indexOverijssel).getUrlPartition() + "|A73+Nijmegen" +
							"&mode=driving&language=nl-NL&key=" +
							Keys.distanceKey;
					int[] distancesOverToNijm = setPairFromJSON(overToNijmegen,"duration");
					matrix.setElement(indexOverijssel, indexLimburg, distancesLimToNijm[0] + distancesOverToNijm[1]);
					matrix.setElement(indexLimburg, indexOverijssel, distancesLimToNijm[1] + distancesOverToNijm[0]);		
				}		
			}
		}
				
		if((indexLimburgAddress.size()!=0) && (indexZeelandAddress.size()!=0)){
			for (int indexLimburg:indexLimburgAddress) {
				String limToBreda = 
							"https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + 
							addresses.getSingleAddress(indexLimburg).getUrlPartition() + "|A58+BredaA73" +
							"&destinations=" +
							addresses.getSingleAddress(indexLimburg).getUrlPartition() + "|A58+BredaA73" +
							"&mode=driving&language=nl-NL&key=" +
							Keys.distanceKey;
				int[] distancesLimToBre = setPairFromJSON(limToBreda,"duration");
				for (int indexZeeland:indexZeelandAddress) {
					String zeeToBreda = 
							"https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + 
							addresses.getSingleAddress(indexZeeland).getUrlPartition() + "|A58+BredaA73" +
							"&destinations=" +
							addresses.getSingleAddress(indexZeeland).getUrlPartition() + "|A58+BredaA73" +
							"&mode=driving&language=nl-NL&key=" +
							Keys.distanceKey;
					int[] distancesZeeToBre = setPairFromJSON(zeeToBreda,"duration");
					matrix.setElement(indexZeeland, indexLimburg, distancesLimToBre[0] + distancesZeeToBre[1]);
					matrix.setElement(indexLimburg, indexZeeland, distancesLimToBre[1] + distancesZeeToBre[0]);		
					
				}
			}
		}
	}
	
	int[] setPairFromJSON(String urlString, String targetLine) {
		HttpURLConnection request = null;
		int[] returnInt = new int[2]; 
		try {
			URL url = new URL(urlString);
			System.out.println(url);
			request = (HttpURLConnection) url.openConnection();
			request.connect();

			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			JsonObject jsonFile = root.getAsJsonObject();
			JsonArray jsonRes = (JsonArray) jsonFile.get("rows");
			for (int yIter = 0; yIter < 2; yIter++) {
				JsonObject jsonResyIter = (JsonObject) jsonRes.get(yIter);
				JsonArray jsonResyIterElements = (JsonArray) jsonResyIter.get("elements");
				int xIter = (yIter + 1)%2;
				JsonObject jsonResyIterElementsxIter = (JsonObject) jsonResyIterElements.get(xIter);
				returnInt[yIter]= ((JsonObject) jsonResyIterElementsxIter.get(targetLine)).get("value").getAsInt();	
				
			}
			request.disconnect();
		} catch (IllegalStateException | JsonSyntaxException  e) {
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

		return returnInt;
	}
}
