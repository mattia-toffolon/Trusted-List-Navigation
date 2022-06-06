package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import proseccoCoding.TLN.App;
import proseccoCoding.TLN.model.APIHandler;
import proseccoCoding.TLN.model.TrustedListFacade;

/**
 * Controller of the listCountries view.
 * It has the duty to show the list of the EU countries to the user and to let him go back to the homepage.
 */
public class ListCountriesController {

	
	/**
	 * ListView object used to contain the list of the countries' names 
	 */
	@FXML
	private ListView<String> countriesList;
	
	/**
	 * Method called when ListCountriesController is loaded. 
	 * It adds the list of the countries' names to the private ListView object.
	 * The countries names are retrieved as Strings through the use of an APIHandler method.
	 */
	@FXML
	private void initialize() {
		for (String s : TrustedListFacade.getInstance().retrieveCountriesNames())
			countriesList.getItems().add(s);
	}
	
    /**
     * Switches scene to the "home" one
     * @throws IOException
     */
	@FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
}
