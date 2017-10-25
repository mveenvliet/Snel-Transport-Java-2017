package CIMSOLUTIONS.SnelTransport.CalculateRoute;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class DistanceMatrix {

	public Vector<Vector<Integer>> matrix = new Vector<Vector<Integer>>();

	public enum MinimizationParameter {
		DISTANCE, TIME
	}

	public DistanceMatrix(String url, MinimizationParameter minDistOrTime){
		
		String targetLine;

		switch (minDistOrTime) {
		case DISTANCE:
			targetLine = "distance";
			break;
		case TIME:
			targetLine = "duration";
			break;
		default:
			throw new IllegalArgumentException("Invalid minimization parameter");
		}

		setMatrixFromJSON(url,targetLine);

	}

	void setMatrixFromJSON(String urlString, String targetLine) {
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
				Vector<Integer> rowMatrix = new Vector<>();
				for (int xIter = 0; xIter < jsonResyIterElements.size(); xIter++) {
					JsonObject jsonResyIterElementsxIter = (JsonObject) jsonResyIterElements.get(xIter);
					rowMatrix.add(xIter,
							((JsonObject) jsonResyIterElementsxIter.get(targetLine)).get("value").getAsInt());
				}
				matrix.add(yIter, rowMatrix);
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
	}
	
	public void viewMatrix() {
		for (int yIter = 0 ; yIter < matrix.size() ; yIter++) {
			for (int xIter = 0 ; xIter < matrix.get(0).size(); xIter++) {
				System.out.print(matrix.get(yIter).get(xIter) + "\t");
			}
			System.out.println();
		}
	}
	
	public int getWidth() {
		return matrix.get(0).size();
	}
	
	public int getHeight() {
		return matrix.size();
	}
	
	public int getElement(int x, int y) {
		return matrix.get(y).get(x);
	}
	
	public void setElement(int dest, int src, int value) {
		matrix.get(dest).set(src,value);
	}
}
