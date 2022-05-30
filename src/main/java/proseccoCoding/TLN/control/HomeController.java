package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.TLN.App;
import proseccoCoding.TLN.model.TrustedListFacade;

/**
 * 
 * Controller of the "home" section
 *
 */
public class HomeController {

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
     * Switches scene to the "selectCountries" one and initialize TrustedListFacade
     * @throws IOException
     */
    private void switchToSelectCountries() throws IOException {
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