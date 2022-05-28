package proseccoCoding.TLN.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
		} catch (Exception e) {
			System.err.println("error 1" + e);
			// e.printStackTrace();
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
		} catch (Exception e) {
			System.err.println("error 2" + e);
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
		ArrayList<String> countries = new ArrayList<String>();
		Iterator<Object> it = countriesName.iterator();
		while (it.hasNext()) {
			JSONObject object = (JSONObject) it.next();
			countries.add((String) object.get("countryName"));
		}

		return countries;
	}

	/**
	 * Static method that populate an HashMap with country codes and Country objects
	 * from the API call
	 * 
	 * @return HashMap countries with the country code as key and the an object of
	 *         class country as value
	 */
	public static HashMap<String, Country> retrieveCountries() {
		HashMap<String, Country> countries = new HashMap<String, Country>();
		Iterator<Object> it = countriesName.iterator();
		while (it.hasNext()) {
			JSONObject obj = (JSONObject) it.next();
			countries.put((String) obj.get("countryCode"),
					new Country((String) obj.get("countryCode"), (String) obj.get("countryName")));
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
			File myObj = new File("../../../../resources/proseccoCoding/TLN/model/tipi_servizi.txt");
			s = new Scanner(myObj);
		} catch (FileNotFoundException e) {
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
		ArrayList<Pair<String,String>> serviceTypes = new ArrayList<Pair<String,String>>();
		HashMap<String,String> pairSet = APIHandler.readFromFile(); // <type code, type full name>
		Iterator<Object> it = countriesData.iterator();

		while (it.hasNext()) {
			JSONObject obj = (JSONObject) it.next();
			// Each array of services is going to be parse
			try {
				Scanner in = new Scanner((String) obj.get("qServiceTypes").toString());
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
		if (countriesData == null || countriesName == null)
			init();
		Country tempCountry = null;

		// Build the country object with only name and country as attribute
		Iterator<Object> itCountry = countriesName.iterator();
		while (itCountry.hasNext()) {
			JSONObject countryObject = (JSONObject) itCountry.next();
			String tempCode = (String)countryObject.get("countryCode");
			if (tempCode.equals(countryCode)) {
				tempCountry = new Country(tempCode,(String) countryObject.get("countryName"));
				break;
			}
		}

		// Add the providers with the relative services
		Iterator<Object> itProviders = countriesData.iterator();

		while (itProviders.hasNext()) {
			JSONObject obj = (JSONObject) itProviders.next();
			// look if the provider is from the current country
			if (((String) obj.get("countryCode")).equals(countryCode)) {
				Provider tempProvider = new Provider((String) obj.get("name"), tempCountry);
				JSONArray services = obj.getJSONArray("services");

				// Build each service object with status, name service and country code. After
				// that it is put provider's Multimap
				for (int i = 0; i < services.length(); i++) { // loop for every service
					JSONObject temp = services.getJSONObject(i);
					
					String tempName = temp.getString("serviceName");
					// in the json is an array, but it actually always have 1 element
					String tempTypeCode = obj.getJSONArray("qServiceTypes").getString(0);
					ServiceType tempServiceType = ServiceType.getInstance(tempTypeCode);
					String tempStatus = temp.getString("currentStatus"); // complete string
					tempStatus = tempStatus.substring(50, tempStatus.length()); // only the status
					
					Service tempService = new Service(tempName,tempServiceType,tempStatus,tempProvider);
					tempProvider.addService(tempService);
				}
				tempCountry.addProvider(tempProvider);
			}
		}
		return tempCountry;
	}

}
