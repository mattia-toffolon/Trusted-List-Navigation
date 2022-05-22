package proseccoCoding.TLN.model;

import java.util.ArrayList;
import java.util.HashSet;

public class Country {
	/**
	 * String code that represent the country, must be in format "CC" both upper case 
	 */
	private String code;
	/**
	 * Complete name of the country
	 */
	private String name;
	/**
	 * All the trusted providers that can be fount in that country
	 */
	private HashSet<Provider> providers;
	/**
	 * Boolean that is true if the country providers have already been retrieved
	 */
	private boolean isRetrieved;
		
	/**
	 * Constructor for a country with specified name and code but without retrieved providers data
	 * @param code 2 letter code that represents the country
	 * @param name Complete name of the country 
	 */
	public Country(String code, String name) throws IllegalArgumentException {
		if(code == null || name == null)
			throw new IllegalArgumentException("Arguments must be not null");
		this.code = code;
		this.name = name;
		this.isRetrieved = false;
		this.providers = new HashSet<Provider>();
	}
	
	/**
	 * Constructor for a country with specified name and code but with retrieved providers data
	 * @param code Code that represents the country composed by 2 letters (upper or lower case)
	 * @param name Complete name of the country 
	 * @param providers Complete collection of all providers based in this country
	 */
	public Country(String code, String name, HashSet<Provider> providers) throws IllegalArgumentException {
		if(code == null || name == null || providers == null)
			throw new IllegalArgumentException("Arguments must be not null");
		this.code = code.toUpperCase();
		this.name = name;
		this.isRetrieved = true;
		this.providers = providers;
	}
	/**
	 * Adds the providers to the country if they haven't been already added 
	 * @param providers HashSet containing all the providers of this country
	 * @return True if the providers has been added, false otherwise
	 */
	public boolean addProviders(HashSet<Provider> providers) {
		if(isRetrieved)
			return false;
		this.providers = providers;
		return true;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	/**
	 * Get the complete list of the providers based in this country
	 * @return The HashSet containing all the providers or null if they are not retrieved
	 */
	public HashSet<Provider> getProviders() {
		if(!isRetrieved)
			return null;
		return providers;
	}
}
