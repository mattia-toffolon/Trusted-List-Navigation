package proseccoCoding.TLN.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class UrlRequester {
	
	public static ArrayList<String> requestNations() {
		ArrayList<String> nations = new ArrayList<String>();
		
		try {
			// try connection
			URL url;
			url = new URL("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			// creating json object
			JSONArray jsonArr = new JSONArray();
			
			int responseCode = connection.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			
			// if get request status is OK
			if (responseCode == HttpURLConnection.HTTP_OK) {
				// read from response body
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				// parse response body json
				jsonArr = new JSONArray(new JSONTokener(in));
				in.close();
			}
			// iterate over parsed json and print provider names
			Iterator<Object> it = jsonArr.iterator();
			while (it.hasNext()) {
				JSONObject object = (JSONObject) it.next();
				nations.add((String) object.get("countryName"));		
			}
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nations;
	}
	
}
