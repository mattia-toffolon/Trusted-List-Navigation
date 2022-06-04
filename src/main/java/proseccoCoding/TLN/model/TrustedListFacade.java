package proseccoCoding.TLN.model;

import java.util.ArrayList;
/**
 * This class is used as interface for all the business logic operations.
 * Provides access to all trusted data and some basic methods for query management and query interaction.
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
	
	public static TrustedListFacade getInstance() {
		if(instance == null)
			instance = new TrustedListFacade();
		return instance;
	}
	
	/**
	 * Starts and initialize a new query with the given countries 
	 * @param countryCodes Country codes of the selected countries (must have size>0)
	 * @return true if the query has been initialized, false 
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
	 * Gets the current query or null if there isn't one
	 * @return The Query in use or null if there isn't one
	 */
	public Query getQuery() {
		if(query == null)
			return null;
		return query;
	}
	
	/**
	 * Return the object to access all the trusted data for each country
	 * @return
	 */
	public TrustedListData getData() {
		return trustedListData;
	}
}
