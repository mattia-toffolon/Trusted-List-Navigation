package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import proseccoCoding.TLN.App;

/**
 * 
 * Controller of the "queryResults" section
 *
 */
public class QueryResultsController {

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
     * Switches scene to the "home" one
     * @throws IOException
     */
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
}