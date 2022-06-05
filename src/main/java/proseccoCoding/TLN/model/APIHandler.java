package proseccoCoding.TLN.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.util.Pair;
/**
 * This class allows to interact with the TrustedListAPI and retrieve data from it.
 * This class contains two methods for the API calls and others static methods 
 * used to mold the data and create Objects with the required specifications.
 */
public class APIHandler {

	/**
	 * JSONArray that contains JSONObjects with the country names and codes
	 */
	private static JSONArray countriesNames;
	/**
	 * JSONArray that contains JSONObjects with all the information about each country
	 */
	private static JSONArray countriesData;

	/**
	 * Initializer method for variable countriesNames. It initializes countriesNames with countries codes and full names
	 */
	public static void initCountriesNames() throws Exception{
		
		if(countriesNames!=null)
			return;
		
		try {
			URL url;
			url = new URL("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list_no_lotl_territory");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				countriesNames = new JSONArray(new JSONTokener(in));
				in.close();
			}
		}
		catch(Exception e) {
			System.err.println("Could not acces to the server " + e);
		}
	}
	
	/**
	 * Initializer method for variable countriesData. It initializes countriesData with all the information about each country
	 */
	public static void initCountriesData() {
		
		if(countriesData!=null)
			return;
		
		try {
			URL url;
			url = new URL("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
				countriesData = new JSONArray(new JSONTokener(in));
				in.close();
			}
		} catch (Exception e) {
			System.err.println("Could not acces to the server " + e);
		}
	}

	/**
	 * Static method that returns an ArrayList of Strings containing the names of the EU countries
	 * @return ArrayList with all the names of the countries
	 */
	public static ArrayList<String> retrieveCountriesNames() {
		if (countriesNames == null) {
			try {
				initCountriesNames();
			} catch (Exception e) {
				System.err.print(e);
			}
		}
		
		ArrayList<String> countries = new ArrayList<String>();
		Iterator<Object> it = countriesNames.iterator();
		while (it.hasNext()) {
			JSONObject object = (JSONObject) it.next();
			countries.add(object.getString("countryName"));
		}

		return countries;
	}

	/**
	 * Static method that returns an HashMap containing entries with country codes as keys and Country objects as values.
	 * @return HashMap containing entries with country codes as keys and Country objects (without complete data) as values
	 */
	public static HashMap<String, Country> retrieveCountries() {
		if (countriesNames == null){
			try {
				initCountriesNames();
			} catch (Exception e) {
				System.err.print(e);
			}
		}
		
		HashMap<String, Country> countries = new HashMap<String, Country>();
		Iterator<Object> it = countriesNames.iterator();
		while (it.hasNext()) {
			JSONObject obj = (JSONObject) it.next();
			countries.put(obj.getString("countryCode"),
					new Country(obj.getString("countryCode"), obj.getString("countryName")));
		}

		return countries;
	}
	
	/**
	 * Private method that reads from local file the service types data and return an HashMap containing entries with service type codes as keys and service type names as values.
	 * @return HashMap containing entries with service type codes as keys and service type names as values
	 */
	private static HashMap<String,String> readFromFile(){
		HashMap<String,String> ret = new HashMap<String,String>();
		Scanner s = null;
		try {
			s = new Scanner(APIHandler.class.getResourceAsStream("tipi_servizi.txt"));
		} catch (Exception e) {
			System.err.println(e);
		}
		while (s.hasNextLine()) {
    	 		String line = s.nextLine();
    	  		String[] array = line.split(";");
    	  		ret.put(array[1], array[0]);
		}
		s.close();
		return ret;
	}
	
	/**
	 * Static method that parses countryData and extracts all service types from all countries
	 * @return ArrayList of Pair<String, String> composed of a service type code as first value and a service type full name as second value
	 */	
	public static ArrayList<Pair<String,String>> retriveServiceTypes() {
		if(countriesData == null)
			initCountriesData();
		
		ArrayList<Pair<String,String>> serviceTypes = new ArrayList<Pair<String,String>>();
		HashMap<String,String> pairSet = APIHandler.readFromFile(); 
		Iterator<Object> it = countriesData.iterator();

		while (it.hasNext()) {
			JSONObject obj = (JSONObject) it.next();
			// Each array of services is going to be parsed
			try {
				Scanner in = new Scanner(obj.get("qServiceTypes").toString());
				in.useDelimiter("\\[|\\]|,|\" ");
				String temp;
				while (in.hasNext()) {
					temp = (String) in.next();
					// current type code
					temp = temp.substring(1, temp.length() - 1);
					Pair<String,String> tempPair = null;
					// if the current service type code is not in the local file
					if(pairSet.get(temp) == null)
						tempPair = new Pair<String,String>(temp,temp+" complete name not found");
					// if the current service type code is in the local file
					else 
						tempPair = new Pair<String,String>(temp,pairSet.get(temp));
					// only pairs not already processed are added to serviceTypes
					if(!serviceTypes.contains(tempPair)) 
						serviceTypes.add(tempPair);
				}
				in.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		return serviceTypes;
	}

	/**
	 * Static method that parses countryData and extracts the data to create a Country object
	 * @param countryCode The code that identifies each country
	 * @return the Country object just created
	 * @throws IllegalArgumentException in case of null parameter 
	 */
	public static Country retriveCountryData(String countryCode) throws IllegalArgumentException {
		if(countryCode == null)
			throw new IllegalArgumentException("Argument must be not null");
		if (countriesData == null || countriesNames == null) {
			try {
				initCountriesNames();
				initCountriesData();
			} catch (Exception e) {
				System.err.print(e);
			}
		}

		Country tempCountry = null;

		// Build the country object with only name and country as attributes
		Iterator<Object> itCountry = countriesNames.iterator();
		while (itCountry.hasNext()) {
			JSONObject countryObject = (JSONObject) itCountry.next();
			String tempCode = countryObject.getString("countryCode");
			if (tempCode.equals(countryCode)) {
				tempCountry = new Country(tempCode, countryObject.getString("countryName"));
				break;
			}
		}

		// Add the providers with the relative services to the Country object
		Iterator<Object> itProviders = countriesData.iterator();

		while (itProviders.hasNext()) {
			JSONObject obj = (JSONObject) itProviders.next();
			// look if the provider is from the current country
			if ((obj.getString("countryCode")).equals(countryCode)) {
				Provider tempProvider = new Provider((String) obj.get("name"), tempCountry);
				JSONArray services = obj.getJSONArray("services");
				
				// Build each service object with status, name service and country code.
				// After that, it gets added to the country's providers MultiMap
				for (int i = 0; i < services.length(); i++) { // loop for every service
					JSONObject temp = services.getJSONObject(i);
					String tempName = temp.getString("serviceName");
					JSONArray serviceTypes = temp.getJSONArray("qServiceTypes");
					ArrayList<ServiceType> tempServiceTypes = new ArrayList<ServiceType>();
					
					for(int j=0; j<serviceTypes.length(); j++) {
						String tempTypeCode = temp.getJSONArray("qServiceTypes").getString(j);
						tempServiceTypes.add(ServiceType.getInstance(tempTypeCode));
					}
					
					//retrieve Status as String
					String tempStatus = temp.getString("currentStatus");
					tempStatus = tempStatus.substring(50, tempStatus.length());
					//create Service object
					Service tempService = new Service(tempName,tempServiceTypes,tempStatus,tempProvider);
					tempProvider.addService(tempService);
				}
				tempCountry.addProvider(tempProvider);
			}
		}
		
		return tempCountry;
	}
}