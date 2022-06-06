package proseccoCoding.TLN.model;

import java.util.ArrayList;

import javafx.util.Pair;
/**
 * This class is used as interface for all the business logic operations.
 * Provides access to all trusted data and some basic methods for query management and query interaction.
 * It implements the Singleton pattern since there's the necessity to guarantee the uniqueness of the Facade.
 */
public class TrustedListFacade {
	
	/**
	 * Unique instance of TrustedListFacade class
	 */
	private static TrustedListFacade instance = null;
	/**
	 * The query currently used or the latest used
	 */
	private Query query;
	/**
	 * Trusted data from the trusted service list
	 */
	private TrustedListData trustedListData;
	
	/**
	 * Constructor for the facade class. It initializes the trusted list data structure.
	 */
	private TrustedListFacade() {
		query = null;
		trustedListData = new TrustedListData();
	}
	
	/**
	 * Method to get the unique instance of singleton class TrustedListFacade
	 * @return the unique TrustedListFacade object
	 */
	public static TrustedListFacade getInstance() {
		if(instance == null)
			instance = new TrustedListFacade();
		return instance;
	}
	
	/**
	 * Starts and initialize a new query with the given countries 
	 * @param countryCodes Country codes of the selected countries (must have size>0)
	 * @return true if the query has been initialized, false otherwise
	 * @throws IllegalArgumentException In case of null parameter
	 */
	public boolean startQuery(ArrayList<String> countryCodes) throws IllegalArgumentException {
		if(countryCodes == null)
			throw new IllegalArgumentException("Parameter must be not null");
		if(countryCodes.size() <= 0)
			return false;
		
		query = new Query();
		query.addSelectedCountries(trustedListData.getCountries(countryCodes));
		return true;
	}
	
	/**
	 * Tells APIHandler to request the services data to the TrustedListAPI
	 * @throws Exception
	 */
	public void requestData() throws Exception {
		APIHandler.initCountriesData();
    	APIHandler.initCountriesNames();
	}
	
	/**
	 * Retrieves from APIHandler the list of the EU countries names
	 * @return ArrayList<String> containing the names of the EU countries
	 */
	public ArrayList<String> retrieveCountriesNames() {
		return APIHandler.retrieveCountriesNames();
	}
	
	/**
	 * Retrieves from APIHandler the list of the service types names and codes
	 * @return ArrayList<Pair<String, String>> containing Pairs which first value is the service type's code and the second one is its name
	 */
	public ArrayList<Pair<String, String>> retriveServiceTypes() {
		return APIHandler.retriveServiceTypes();
	}
	
	/**
	 * Returns the current query or null if there isn't one
	 * @return The Query in use or null if there isn't one
	 */
	public Query getQuery() {
		if(query == null)
			return null;
		return query;
	}
	
	/**
	 * Returns the TrustedListData object
	 * @return trustedListData
	 */
	public TrustedListData getData() {
		return trustedListData;
	}
}
