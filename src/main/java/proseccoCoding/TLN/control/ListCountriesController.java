package proseccoCoding.TLN.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import proseccoCoding.TLN.App;

/**
 * 
 * Controller of the "listCountries" section
 *
 */
public class ListCountriesController {

	@FXML
	/**
	 * ListView object used to contain the list of the countries 
	 */
	private ListView countriesList;
	
	@FXML
	/**
	 * Method called when ListCountriesController is loaded. 
	 * It adds the list of the countries to the private ListView object
	 */
	private void initialize() {
    	// add countries to the ListView
		for (String s : requestCountries())
			countriesList.getItems().add(s);
	}
	
    @FXML
    /**
     * Switches scene to the "home" one
     * @throws IOException
     */
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
    
    /**
     * Private method that connects to an API that returns a .json file containing the list of the countries. 
     * This method parses that file to create and then return an ArrayList of strings containing those names.
     * @return ArrayList<String> containing all the countries names
     */
    private static ArrayList<String> requestCountries() {
		ArrayList<String> countries = new ArrayList<String>();
		
		try {
			// try connection
			URL url;
			url = new URL("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			// create json object
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
			// iterate over parsed json and add countries names to the ArrayList
			Iterator<Object> it = jsonArr.iterator();
			while (it.hasNext()) {
				JSONObject object = (JSONObject) it.next();
				countries.add((String) object.get("countryName"));		
			}
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return countries;
	}
}