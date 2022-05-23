package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import proseccoCoding.TLN.App;

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
     * Switches scene to the "home" one
     * @throws IOException
     */
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
}