package proseccoCoding.TLN.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.scene.image.Image;
import javafx.util.Pair;

public class APIHandler {
	
	private static JSONArray countriesName;
	private static JSONArray countriesData;
	
	/**
	 * Constructor of the class
	 */
	private static void init() {
		
		try {
			URL url;
			url = new URL("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			int responseCode = connection.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				// read from response body
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				// parse the response body of the jsonfile
				countriesData = new JSONArray(new JSONTokener(in));
				in.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			URL url;
			url = new URL("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			int responseCode = connection.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				countriesName = new JSONArray(new JSONTokener(in));
				in.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Static method that build an ArrayList of Strings with only the names of the countries
	 * @return ArrayList with all the names of the countries
	 */
	public static ArrayList<String> retrieveCountriesNames(){
		ArrayList<String> countries = new ArrayList<String>();
		Iterator<Object> it = countriesName.iterator();
		while (it.hasNext()) {
			JSONObject object = (JSONObject) it.next();
			countries.add((String) object.get("countryName"));		
		}
		
		return countries;
	}
	
	/**
	 * Static method that populate an HashMap with country codes and Country objects from the API call
	 * @return HashMap countries with the country code as key and the an object of class country as value
	 */
	public static HashMap<String,Country> retrieveCountries(){	
		HashMap<String,Country> countries = new HashMap<String,Country>();
		Iterator<Object> it = countriesName.iterator();
		while(it.hasNext()) {
			JSONObject obj = (JSONObject)it.next();
			countries.put((String)obj.get("countryCode"), new Country((String)obj.get("countryCode"),
					(String)obj.get("countryName")));
		}	
		
		return countries;
	}
	
	/**
	 * Static method that parse the countryData and extract all types of services from all countries
	 * @return An ArrayList of String with the types
	 */
	public static ArrayList<Pair> retriveServiceTypes() {
		ArrayList<Pair> serviceTypes = new ArrayList<Pair>();
		Iterator<Object> it = countriesData.iterator();
		
		while (it.hasNext()) {
			JSONObject obj = (JSONObject) it.next();
			//Each array of services is going to be parse
			try (Scanner in = new Scanner((String)obj.get("qServiceTypes").toString())) {
				in.useDelimiter("\\[|\\]|,|\" ");
				String temp;
				while(in.hasNext()) {
					temp = (String)in.next();
					temp = temp.substring(1, temp.length()-1);
					Scanner s = null;
					
					//PROBLEMA SCANNER
					s = new Scanner(APIHandler.class.getClassLoader().getResourceAsStream("tipi_servizi.txt"));
					while(s.hasNextLine()) {
				    	String line = s.nextLine();
				    	String[] elem = line.split(";");
				    	Pair service = new Pair(temp, elem[0]);
				    	//If the pair is not already in the ArrayList, we add it
				    	if(service.getKey().equals(elem[1]) && !serviceTypes.contains(service)) {
				    		serviceTypes.add(service);
				    		break;
				    	}
				    }
				}
				//Close the stream
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return serviceTypes;
	}
	
	/**
	 * Static method that parse the countryData array and extract the data to create a class Country object
	 * @param countryCode The code that identifies each country
	 * @return the Country object just created 
	 */
	public static Country retriveCountryData(String countryCode) {
		if(countriesData == null || countriesName == null)
			init();
		Country tempCountry = null;
		
		//Build the country object with only name and country as attribute
		Iterator<Object> itCountry = countriesName.iterator();
		while(itCountry.hasNext()) {
			JSONObject countryObject = (JSONObject) itCountry.next();
			if((((String)countryObject.get("countryCode")).equals(countryCode))) {
				tempCountry = new Country((String)countryObject.get("countryCode"), 
						(String)countryObject.get("countryName"));
			}
		}
		
		//Add the providers with the relative services
		Iterator<Object> itProviders = countriesData.iterator();
		
		while(itProviders.hasNext()) {
			JSONObject obj = (JSONObject)itProviders.next();
			
			if(((String)obj.get("countryCode")).equals(countryCode) && itProviders.hasNext()) {
				Provider tempProvider = new Provider((String)obj.get("name"), tempCountry);
				JSONArray services = obj.getJSONArray("services");
				
				//Build each service object with status, name service and country code. After that it is put provider's Multimap 
				for(int i=0; i<services.length(); i++) {
					JSONObject temp = services.getJSONObject(i);
					String tempStatus = temp.getString("currentStatus");
					Service tempService = new Service(temp.getString("serviceName"), ServiceType.getInstance(temp.getString("countryCode")),
								tempStatus.substring(50, tempStatus.length()), tempProvider);
					tempProvider.addService(tempService);
				}
				tempCountry.addProvider(tempProvider);
			}
		
		}
		return tempCountry;
	}

}







