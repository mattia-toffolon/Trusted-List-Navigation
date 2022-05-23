package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.TLN.App;

/**
 * 
 * Controller of the "selectServices" section
 *
 */
public class SelectServicesController {

    @FXML
    /**
     * Switches scene to the "selectStatus" one
     * @throws IOException
     */
    private void switchToSelectStatus() throws IOException {
        App.setRoot("selectStatus");
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