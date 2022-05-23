package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.TLN.App;

/**
 * 
 * Controller of the "selectCountries" section
 *
 */
public class SelectCountriesController {

    @FXML
    /**
     * Switches scene to the "home" one
     * @throws IOException
     */
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
    
    @FXML
    /**
     * Switches scene to the "selectProviders" one
     * @throws IOException
     */
    private void switchToSelectProviders() throws IOException {
        App.setRoot("selectProviders");
    }
}