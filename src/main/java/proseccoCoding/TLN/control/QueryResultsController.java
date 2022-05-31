package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import proseccoCoding.TLN.App;
import proseccoCoding.TLN.model.Service;
import proseccoCoding.TLN.model.TrustedListFacade;

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
	 * Method called when QueryResultsController is loaded. 
	 * It adds the list of the services selected by the user with their information to the private ListView object.
	 * These informations are retrieved as Strings through the use of TrustedListFacade's Query public methods.
	 */
	private void initialize() {
    	// add countries to the ListView
		for (Service s : TrustedListFacade.getQuery().getResults())
			queryResultsList.getItems().add(s.getName() +"\n﹂ Country: "+ s.getProvider().getCountry().getName()
					                                    +"\n﹂ Provider: "+ s.getProvider().getName()
					                                    +"\n﹂ ServiceType: "+ s.getType().getName()
					                                    +"\n﹂ Status: "+ s.getStatus() + "\n\n");
	}
	
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
     * Switches scene to the "home" one and ends the Query if it has been concluded
     * @throws IOException
     */
    private void switchToHome() throws IOException {
    	TrustedListFacade.endQuery();
        App.setRoot("home");
    }
}