package proseccoCoding.TLN.model;

import java.util.ArrayList;

import javax.management.Query;

public class TrustedListFacade {
	/**
	 * List containing all completely done queries
	 */
	private ArrayList<Query> queries;
	/**
	 * Trusted data from the trusted service list
	 */
	private TrustedListData trustedListData;
	
	/**
	 * Constructor for the facade object. It initializes the trusted list data structure.
	 */
	public TrustedListFacade() {
		queries = new ArrayList<Query>();
		trustedListData = new TrustedListData();
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
		
		Query q = new Query();
		q.addSelectedCountries(trustedListData.getCountries(countryCodes));
		queries.add(q);
		
		return true;
	}
	
	/**
	 * Gets the current query or null if there isn't one in use
	 * @return The Query in use or null if there isn't one
	 */
	public Query getQuery() {
		if(queries.size() <= 0 || queries.get(queries.size()-1).isEnded())
			return null;
		return queries.get(queries.size()-1);
	}
	
	
	public TrustedListData getData() {
		return trustedListData;
	}
}
