package proseccoCoding.TLN.model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Query {
	/**
	 * Timestamp for the time of query initialization
	 */
	private Timestamp timestamp;
	/**
	 * List of all the selected countries with data
	 */
	private ArrayList<Country> selectedCountries;
	/**
	 * List of the selected providers form the selected countries
	 */
	private ArrayList<Provider> selectedProviders;
	/**
	 * List of services from selected providers that belongs to the selected types
	 */
	private ArrayList<Service> selectedServicesByType;
	/**
	 * List of the selected service status
	 */
	private ArrayList<String> selectedServiceStatus;
	
	public Query() {
		timestamp = new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * Adds the selected countries to the query. There must be at least one country.
	 * @param countries The countries list to add to the query (must have size>0)
	 * @return True if the countries have been added successfully, false otherwise
	 * @throws IllegalArgumentException In case of null parameter
	 */
	public boolean addSelectedCountries(ArrayList<Country> countries) throws IllegalArgumentException {
		if(selectedCountries == null)
			throw new IllegalArgumentException("Argument must be not null");
		if(selectedCountries.size() <= 0)
			return false;
		selectedCountries = countries;
		return true;
	}
	
	public boolean isEnded() {
		return false;
	}
}
