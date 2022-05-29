package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import proseccoCoding.TLN.App;
import proseccoCoding.TLN.model.APIHandler;

import javafx.util.Pair;

/**
 * 
 * Controller of the "listServices" section
 *
 */
public class ListServicesController {

	@FXML
	/**
	 * ListView object used to contain the list of the services 
	 */
	private ListView servicesList;
	
	@FXML
	/**
	 * Method called when ListServicesController is loaded. 
	 * It adds the list of the service types to the private ListView object
	 */
	private void initialize() {
    	// add countries to the ListView
		for (Pair<String, String> s : APIHandler.retriveServiceTypes())
			servicesList.getItems().add(s.getKey()+"\n﹂ "+s.getValue());
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