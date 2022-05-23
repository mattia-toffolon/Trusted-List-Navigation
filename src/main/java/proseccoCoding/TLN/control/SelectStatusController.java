package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.TLN.App;

/**
 * 
 * Controller of the "selectStatus" section
 *
 */
public class SelectStatusController {

    @FXML
    /**
     * Switches scene to the "selectServices" one
     * @throws IOException
     */
    private void switchToSelectServices() throws IOException {
        App.setRoot("selectServices");
    }
    
    @FXML
    /**
     * Switches scene to the "queryResults" one
     * @throws IOException
     */
    private void switchToQueryResults() throws IOException {
        App.setRoot("queryResults");
    }
}