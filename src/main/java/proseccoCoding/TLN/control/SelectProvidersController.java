package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.TLN.App;

/**
 * 
 * Controller of the "selectProviders" section
 *
 */
public class SelectProvidersController {

    @FXML
    /**
     * Switches scene to the "selectCountries" one
     * @throws IOException
     */
    private void switchToSelectCountries() throws IOException {
        App.setRoot("selectCountries");
    }
    
    @FXML
    /**
     * Switches scene to the "selectServices" one
     * @throws IOException
     */
    private void switchToSelectServices() throws IOException {
        App.setRoot("selectServices");
    }
}