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

public class APIHandler {

	private static JSONArray countriesName;
	private static JSONArray countriesData;

	/**
	 * Method that make a call to the API and initialize countriesName with countries codes and their full name
	 */
	public static void initCountriesName() {
		
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
		} catch (Exception e) {
			System.err.println("error 2" + e);
			// e.printStackTrace();
		}
	}
	
	/**
	 * Method that make a call to the API and initialize countriesData
	 */
	public static void initCountriesData() {
		
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
		} catch (Exception e) {
			System.err.println("error 1" + e);
			// e.printStackTrace();
		}
	}

	/**
	 * Static method that build an ArrayList of Strings with only the names of the
	 * countries
	 * 
	 * @return ArrayList with all the names of the countries
	 */
	public static ArrayList<String> retrieveCountriesNames() {
		if (countriesName == null)
			initCountriesName();
		
		ArrayList<String> countries = new ArrayList<String>();
		Iterator<Object> it = countriesName.iterator();
		while (it.hasNext()) {
			JSONObject object = (JSONObject) it.next();
			countries.add(object.getString("countryName"));
		}

		return countries;
	}

	/**
	 * Static method that populate an HashMap with country codes and Country objects
	 * from the API call. Country objects has complete data not retrieved, or in other words, 
	 * if isRetrieved() is called it returns false
	 * 
	 * @return HashMap countries with the country code as key and the Country object without
	 *         complete data as value
	 */
	public static HashMap<String, Country> retrieveCountries() {
		if (countriesName == null)
			initCountriesName();
		
		HashMap<String, Country> countries = new HashMap<String, Country>();
		Iterator<Object> it = countriesName.iterator();
		while (it.hasNext()) {
			JSONObject obj = (JSONObject) it.next();
			countries.put(obj.getString("countryCode"),
					new Country(obj.getString("countryCode"), obj.getString("countryName")));
		}

		return countries;
	}
	
	/**
	 * private method that read from local file the service type data and return a map
	 * where the key is the code of the type, the value is the full name
	 * @return
	 */
	private static HashMap<String,String> readFromFile(){
		HashMap<String,String> ret = new HashMap<String,String>();
		Scanner s = null;
		try {
			s = new Scanner(APIHandler.class.getResourceAsStream("tipi_servizi.txt"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * Static method that parse the countryData and extract all types of services
	 * from all countries
	 * 
	 * @return An ArrayList of String with the types
	 */	
	public static ArrayList<Pair<String,String>> retriveServiceTypes() {
		if(countriesData == null)
			initCountriesData();
		
		ArrayList<Pair<String,String>> serviceTypes = new ArrayList<Pair<String,String>>();
		HashMap<String,String> pairSet = APIHandler.readFromFile(); // <type code, type full name>
		Iterator<Object> it = countriesData.iterator();

		while (it.hasNext()) {
			JSONObject obj = (JSONObject) it.next();
			// Each array of services is going to be parse
			try {
				Scanner in = new Scanner(obj.get("qServiceTypes").toString());
				in.useDelimiter("\\[|\\]|,|\" ");
				String temp;
				while (in.hasNext()) {
					temp = (String) in.next();
					temp = temp.substring(1, temp.length() - 1); // current type code
					Pair<String,String> tempPair = null;
					if(pairSet.get(temp) == null) // if the current service type code is not in the local file
						tempPair = new Pair<String,String>(temp,temp+" complete name not found");
					else  // if the current service type code is in the local file
						tempPair = new Pair<String,String>(temp,pairSet.get(temp));
					if(!serviceTypes.contains(tempPair)) // only pairs not already processed
						serviceTypes.add(tempPair);
				}
				in.close();
			} catch (Exception e) {
				System.err.println("error 3 " + e);
				// e.printStackTrace();
			}
		}
		return serviceTypes;
	}

	/**
	 * Static method that parse the countryData array and extract the data to create
	 * a class Country object
	 * 
	 * @param countryCode The code that identifies each country
	 * @return the Country object just created
	 */
	public static Country retriveCountryData(String countryCode) {
		if (countriesData == null || countriesName == null) {
			initCountriesName();
			initCountriesData();
		}

		Country tempCountry = null;

		// Build the country object with only name and country as attribute
		Iterator<Object> itCountry = countriesName.iterator();
		while (itCountry.hasNext()) {
			JSONObject countryObject = (JSONObject) itCountry.next();
			String tempCode = countryObject.getString("countryCode");
			if (tempCode.equals(countryCode)) {
				tempCountry = new Country(tempCode, countryObject.getString("countryName"));
				break;
			}
		}

		// Add the providers with the relative services
		Iterator<Object> itProviders = countriesData.iterator();

		while (itProviders.hasNext()) {
			JSONObject obj = (JSONObject) itProviders.next();
			// look if the provider is from the current country
			if ((obj.getString("countryCode")).equals(countryCode)) {
				Provider tempProvider = new Provider((String) obj.get("name"), tempCountry);
				JSONArray services = obj.getJSONArray("services");
				// Build each service object with status, name service and country code. After
				// that it is put provider's Multimap
				for (int i = 0; i < services.length(); i++) { // loop for every service
					JSONObject temp = services.getJSONObject(i);
					String tempName = temp.getString("serviceName");
					// in the json is an array, but it actually always have 1 element
					JSONArray serviceTypes = temp.getJSONArray("qServiceTypes");
					ArrayList<ServiceType> tempServiceTypes = new ArrayList<ServiceType>();
					for(int j=0; j<serviceTypes.length(); j++) {
						String tempTypeCode = temp.getJSONArray("qServiceTypes").getString(j);
						tempServiceTypes.add(ServiceType.getInstance(tempTypeCode));
					}
					String tempStatus = temp.getString("currentStatus"); // complete string
					tempStatus = tempStatus.substring(50, tempStatus.length()); // only the status
					Service tempService = new Service(tempName,tempServiceTypes,tempStatus,tempProvider);
					tempProvider.addService(tempService);
					}
				tempCountry.addProvider(tempProvider);
			}
		}
		
		return tempCountry;
	}
}
