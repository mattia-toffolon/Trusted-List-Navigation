package proseccoCoding.TLN.control;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.util.Pair;
import proseccoCoding.TLN.App;
import proseccoCoding.TLN.model.APIHandler;

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
	 * It adds the list of the service type to the private ListView object
	 */
	private void initialize() {
    	// add countries to the ListView
		for (String s : requestServiceTypes())
			servicesList.getItems().add(s);
	}
    @FXML
    /**
     * Switches scene to the "home" one
     * @throws IOException
     */
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
    
    /**
     * Private method that calls APIHandler.retriveServiceTypes(), which connects to an API that returns a 
     * .json file containing the list of the service types. That method parses that file to create and then return
     *  an ArrayList of strings containing those types.
     * @return ArrayList<String> containing all the service types
     */
    private static ArrayList<String> requestServiceTypes() {
    	ArrayList<String> ret = new ArrayList<String>();
		ArrayList<Pair<String,String>> list = APIHandler.retriveServiceTypes();
		for(Pair<String,String> p : list){
			ret.add(p.getValue());
		}
		return ret;
	}
}
