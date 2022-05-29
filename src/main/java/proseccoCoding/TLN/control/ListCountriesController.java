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
import proseccoCoding.TLN.model.APIHandler;

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
     * Private method that calls APIHandler.retriveCountriesNames(), which connects to an API that returns a 
     * .json file containing the list of the countries. That method parses that file to create and then 
     * return an ArrayList of strings containing those names.
     * @return ArrayList<String> containing all the countries names
     */
    private static ArrayList<String> requestCountries() {
		ArrayList<String> countries = APIHandler.retrieveCountriesNames();
		return countries;
	}
}
