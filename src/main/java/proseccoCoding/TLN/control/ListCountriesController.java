package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import proseccoCoding.TLN.App;
import proseccoCoding.TLN.model.APIHandler;

/**
 * Controller of the listCountries view.
 * It has the duty to show the list of the EU countries to the user and to let him go back to the homepage.
 */
public class ListCountriesController {

	@FXML
	/**
	 * ListView object used to contain the list of the countries' names 
	 */
	private ListView<String> countriesList;
	
	@FXML
	/**
	 * Method called when ListCountriesController is loaded. 
	 * It adds the list of the countries' names to the private ListView object.
	 * The countries names are retrieved as Strings through the use of an APIHandler method.
	 */
	private void initialize() {
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
