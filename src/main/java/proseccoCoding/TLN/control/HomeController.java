package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import proseccoCoding.TLN.App;
import proseccoCoding.TLN.model.APIHandler;
import proseccoCoding.TLN.model.TrustedListFacade;


/**
 * 
 * Controller of the "home" section
 *
 */
public class HomeController {
	
	@FXML
	/**
	 * Method called when HomesController is loaded. 
	 * This method initialize the JSONArrays in APIHandler if it hasn't been done yet.
	 * In case of connection errors, this method displays an error dialog pane that informs the user and then closes the application.
	 */
	private void initialize() {
		try {
			APIHandler.initCountriesData();
	    	APIHandler.initCountriesName();
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Error: " +e+ "\n\nThe error is caused by either the user's or the server's internet connection.", ButtonType.CLOSE);
			a.setHeaderText("Connection Error");
			a.setTitle("Error");
			a.showAndWait();
			System.exit(0);
		}
	}

    @FXML
    /**
     * Switches scene to the "listCountries" one
     * @throws IOException
     */
    private void switchToListCountries() throws IOException {
        App.setRoot("listCountries");
    }
    
    @FXML
    /**
     * Switches scene to the "listServices" one
     * @throws IOException
     */
    private void switchToListServices() throws IOException {
        App.setRoot("listServices");
    }
    
    @FXML
    /**
     * Switches scene to the "selectCountries" one and initialize TrustedListFacade if it hasn't been done yet
     * @throws IOException
     */
    private void switchToSelectCountries() throws IOException {
    	if(TrustedListFacade.getData()==null)
    		TrustedListFacade.init();
        App.setRoot("selectCountries");
    }
    
    @FXML
    /**
     * Switches scene to the "credits" one
     * @throws IOException
     */
    private void switchToCredits() throws IOException {
        App.setRoot("credits");
    }
}