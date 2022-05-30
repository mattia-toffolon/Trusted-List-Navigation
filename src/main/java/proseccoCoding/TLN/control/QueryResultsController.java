package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import proseccoCoding.TLN.App;

/**
 * 
 * Controller of the "queryResults" section
 *
 */
public class QueryResultsController {

	@FXML
	/**
	 * ListView object used to contain the list of the user's query results 
	 */
	private ListView<String> queryResultsList;
	
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