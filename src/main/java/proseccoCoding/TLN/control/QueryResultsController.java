package proseccoCoding.TLN.control;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import java.util.Collections;
import proseccoCoding.TLN.App;
import proseccoCoding.TLN.model.Service;
import proseccoCoding.TLN.model.ServiceType;
import proseccoCoding.TLN.model.TrustedListFacade;

/**
 * Controller of the queryResults view.
 * It has the duty to show the user the list of his query results. 
 * It also has to let the user move to the hompage and go back to the service statuses selection.
 */
public class QueryResultsController {

	/**
	 * ListView object used to contain the list of the user's query results 
	 */
	@FXML
	private ListView<String> queryResultsList;
	
	/**
	 * Method called when QueryResultsController is loaded. 
	 * It adds the list of the services selected by the user with their information to the private ListView object.
	 * These informations are retrieved as Strings through the use of TrustedListFacade's Query public methods.
	 */
	@FXML
	private void initialize() {
		ArrayList<String> results = new ArrayList<String>();
		for (Service s : TrustedListFacade.getInstance().getQuery().getResults()) {
			String serviceTypes = new String();
			for(ServiceType st : s.getTypes())
				serviceTypes += (st.getName()+"; ");
			
			String result = s.getName() +"\n﹂ Country: "+ s.getProvider().getCountry().getName()
		                    +"\n﹂ Provider: "+ s.getProvider().getName()
		                    +"\n﹂ ServiceTypes: "+ serviceTypes.substring(0, serviceTypes.length()-2)
		                    +"\n﹂ Status: "+ s.getStatus() + "\n\n";
			if(!results.contains(result))
				results.add(result);
		}
		Collections.sort(results);
		for(String s : results)
			queryResultsList.getItems().add(s);
	}
	
    /**
     * Switches scene to the "selectStatus" one
     * @throws IOException
     */
	@FXML
    private void switchToSelectStatus() throws IOException {
        App.setRoot("selectStatus");
    }
    
    /**
     * Switches scene to the "home" one
     * @throws IOException
     */
	@FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
}