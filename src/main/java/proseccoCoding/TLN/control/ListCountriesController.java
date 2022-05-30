package proseccoCoding.TLN.control;

import java.io.IOException;
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
	private ListView<String> countriesList;
	
	@FXML
	/**
	 * Method called when ListCountriesController is loaded. 
	 * It adds the list of the countries to the private ListView object.
	 * The countries names are retrieved as Strings through the use of APIHandler class.
	 */
	private void initialize() {
    	// add countries to the ListView
		for (String s : APIHandler.retrieveCountriesNames())
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
}
