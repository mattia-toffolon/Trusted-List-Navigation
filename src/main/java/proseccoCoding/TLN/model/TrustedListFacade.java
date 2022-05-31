package proseccoCoding.TLN.model;

import java.util.ArrayList;

public class TrustedListFacade {
	/**
	 * List containing all completely done queries
	 */
	private static ArrayList<Query> queries;
	/**
	 * Trusted data from the trusted service list
	 */
	private static TrustedListData trustedListData;
	
	/**
	 * Initializer method for the facade class. It initializes the trusted list data structure.
	 */
	public static void init() {
		queries = new ArrayList<Query>();
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
		
		Query q = new Query();
		q.addSelectedCountries(trustedListData.getCountries(countryCodes));
		queries.add(q);
		return true;
	}
	
	/**
	 * Gets the current query or null if there isn't one
	 * @return The Query in use or null if there isn't one
	 */
	public static Query getQuery() {
		if(queries.size() <= 0)
			return null;
		return queries.get(queries.size()-1);
	}
	
	/**
	 * Ends the current query and discards it if it isn't completed
	 * @return True if the query is complete, false if it isn't completed
	 */
	public static boolean endQuery() {
		if(queries.get(queries.size()-1).isEnded())
			return true;
		queries.remove(queries.size()-1);
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
