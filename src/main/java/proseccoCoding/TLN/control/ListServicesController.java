package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.util.Pair;
import proseccoCoding.TLN.App;
import proseccoCoding.TLN.model.APIHandler;
import proseccoCoding.TLN.model.TrustedListFacade;

/**
 * Controller of the listServices view
 * It has the duty to show the list of the Trust-Service types to the user and to let him go back to the homepage.
 */
public class ListServicesController {

	@FXML
	/**
	 * ListView object used to contain the list of the service types' codes and names 
	 */
	private ListView<String> servicesList;
	
	@FXML
	/**
	 * Method called when ListServicesController is loaded. 
	 * It adds the list of the service types codes and full names to the private ListView object. 
	 * The service types' informations are retrieved as Strings through the use of an APIHandler method.
	 */
	private void initialize() {
		for (Pair<String, String> s : TrustedListFacade.getInstance().retriveServiceTypes())
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
