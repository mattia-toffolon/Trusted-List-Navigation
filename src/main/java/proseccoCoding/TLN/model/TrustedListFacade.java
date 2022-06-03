package proseccoCoding.TLN.model;

import java.util.ArrayList;
/**
 * This class is used as interface for all the business logic operations.
 * Provides access to all trusted data and some basic methods for query management and query interaction.
 */
public class TrustedListFacade {
	/**
	 * The query currently used or the latest used
	 */
	private static Query query;
	/**
	 * Trusted data from the trusted service list
	 */
	private static TrustedListData trustedListData;
	
	/**
	 * Initializer method for the facade class. It initializes the trusted list data structure.
	 */
	public static void init() {
		query = null;
		trustedListData = new TrustedListData();
	}
	
	/**
	 * Starts and initialize a new query with the given countries 
	 * @param countryCodes Country codes of the selected countries (must have size>0)
	 * @return true if the query has been initialized, false 
	 * @throws IllegalArgumentException In case of null parameter
	 */
	public static boolean startQuery(ArrayList<String> countryCodes) throws IllegalArgumentException {
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
	public static Query getQuery() {
		if(query == null)
			return null;
		return query;
	}
	
	/**
	 * Ends the current query and discards it if it isn't completed
	 * @return True if the query is complete, false if it isn't completed
	 */
	public static boolean endQuery() {
		if(query.isEnded())
			return true;
		query = null;
		return false;
	}
	
	/**
	 * Return the object to access all the trusted data for each country
	 * @return
	 */
	public static TrustedListData getData() {
		return trustedListData;
	}
}
