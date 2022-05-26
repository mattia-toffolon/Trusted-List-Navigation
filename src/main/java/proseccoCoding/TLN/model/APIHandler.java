package proseccoCoding.TLN.model;

import java.io.BufferedReader;
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

public class APIHandler {
	
	private static JSONArray countriesName;
	private static JSONArray countriesData;
	
	/**
	 * Constructor of the class
	 */
	public APIHandler() {
		
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
				// parse response body json
				countriesData = new JSONArray(new JSONTokener(in));
				in.close();
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
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
				// read from response body
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				// parse response body json
				countriesName = new JSONArray(new JSONTokener(in));
				in.close();
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
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
	public static ArrayList<String> retriveServiceTypes() {
		ArrayList<String> serviceTypes = new ArrayList<String>();
		Iterator<Object> it = countriesData.iterator();
		while (it.hasNext()) {
			JSONObject obj = (JSONObject) it.next();
			try (Scanner in = new Scanner((String)obj.get("qServiceTypes").toString())) {
				in.useDelimiter("\\[|\\]|,|\" ");
				String temp;
				while(in.hasNext()) {
					temp = (String)in.next();
					temp = temp.substring(1, temp.length()-1);
					//if the types map do not contains the type, it will be add
					if(!serviceTypes.contains(temp)) {
						serviceTypes.add(temp);
					}	
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
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
		Country tempCountry = null;
		Iterator<Object> itCountry = countriesName.iterator();
		while(itCountry.hasNext()) {
			JSONObject countryObject = (JSONObject) itCountry.next();
			if((((String)countryObject.get("countryCode")).equals(countryCode))) {
				tempCountry = new Country((String)countryObject.get("countryCode"), 
						(String)countryObject.get("countryName"));
			}
		}
		
		Iterator<Object> itProviders = countriesData.iterator();
		
		while(itProviders.hasNext()) {
			JSONObject obj = (JSONObject)itProviders.next();
			
			if(((String)obj.get("countryCode")).equals(countryCode) && itProviders.hasNext()) {// && itProviders.hasNext()
				Provider tempProvider = new Provider((String)obj.get("name"), countryCode);
				JSONArray services = obj.getJSONArray("services");
				
				for(int i=0; i<services.length(); i++) {
					System.out.println(i);
					JSONObject temp = services.getJSONObject(i);
					String tempStatus = temp.getString("currentStatus");
					Service tempService = new Service(temp.getString("serviceName"),
							new ServiceType((String)temp.get("type"), "TERMINARE"),
								tempStatus.substring(50, tempStatus.length()));
					tempProvider.addService(tempService);
				}
				
				tempCountry.addProvider(tempProvider);
			}
		
		}
		return tempCountry;
	}
	
	public static void main(String[] args) {
        APIHandler api = new APIHandler();
        
        //Test Metodo retrieveCountries
        HashMap<String,Country> test1 = retrieveCountries();
        for (HashMap.Entry<String, Country> entry : test1.entrySet()) 
            System.out.println(entry.getKey() + " / " + entry.getValue().getName());
        
    	   
        //Test metodo retrieveServiceTypes
        ArrayList<String> test2 = retriveServiceTypes();
        for ( String e : test2)
        	System.out.println(e);
        
        //Test metodo retrieveCountryData
        Country test3 = retriveCountryData("AT");
        System.out.println(test3.getCode() + " " + test3.getName());
        for(Provider e : test3.getProviders()) {
        	System.out.println(e.getName());
        	for(ServiceType t : e.getServiceTypes()) {
        	System.out.println(t.getCode() + " " + t.getName());
        	ArrayList<Service> voglioService = e.getServices(t);
        	for(Service o : voglioService) {
        		System.out.println(o.getName()+ " " + o.getStatus());
        	}
        	}
        }
    }
	
}






