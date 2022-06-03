package proseccoCoding.TLN.model;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;

/**
 * This class represents a country with all its trusted providers (that provide services).
 * Every country must be identified by its international code of two characters.
 */
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
	 * All the trusted providers that can be found in that country. Stored as (Provider.code, Provider)
	 */
	private HashMap<String, Provider> providers;
		
	
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
		this.providers = null;
	}
	
	/**
	 * Constructor for a country with specified name and code but with retrieved providers data
	 * @param code Code that represents the country composed by 2 letters (upper or lower case)
	 * @param name Complete name of the country 
	 * @param providers Complete collection of all providers based in this country
	 */
	public Country(String code, String name, HashMap<String, Provider> providers) throws IllegalArgumentException {
		if(code == null || name == null || providers == null)
			throw new IllegalArgumentException("Arguments must be not null");
		if(code.length() != 2)
			throw new IllegalArgumentException("Country code must be of length 2");
		this.code = code.toUpperCase();
		this.name = name;
		this.providers = providers;
	}
	
	/**
	 * Returns the provider with the specified providerCode if there is one in this country
	 * @param providerCode The code of the provider to get
	 * @return The provider if it's been found, null otherwise
	 */
	public Provider getProvider(String providerCode) {
		if(!isRetrieved())
			return null;
		if(!Provider.getCountryCodeBy(providerCode).equals(code))
			return null;
		return providers.get(providerCode);
	}
	
	/**
	 * Adds the given provider to this country only if it isn't already added.
	 * @param provider The provider to add to this country
	 * @return True if the provided has been added, false otherwise
	 * @throws IllegalArgumentException In case in which the provider is null or has a different countryCode 
	 */
	public boolean addProvider(Provider provider) throws IllegalArgumentException {
		if(provider == null)
			throw new IllegalArgumentException("Arguments must be not null");
		if(!provider.getCountry().getCode().equals(code))
			throw new IllegalArgumentException("Provider must be located in this country");
		if(!isRetrieved())
			providers = new HashMap<String, Provider>();
		return providers.putIfAbsent(provider.getCode(), provider) == null;
	}
	
	/**
	 * States if the complete data of this country providers has already been retrieved.
	 * The complete data consists in all the providers and their associated data.
	 * @return True if the country provider data is been retrieved
	 */
	public boolean isRetrieved() {
		return providers!=null;
	}
	
	/**
	 * Returns a Pair of two String created as: (key=CountryCode, value=CountryName)
	 * @return The pair of code and name representing this country
	 */
	public Pair<String, String> print() {
		return new Pair<String,String>(this.code, this.name);	
	}
	
	/**
	 * Get the complete list of the providers based in this country or null if they have not already been retrieved
	 * The list would be empty if the providers are been retrieved but this country has no providers
	 * @return The list containing all the providers or null if they have not already been retrieved
	 */
	public ArrayList<Provider> getProviders() {
		if(!isRetrieved())
			return null;
		return new ArrayList<Provider>(providers.values());
	}
	
	/**
	 * Getter for country code
	 * @return The country code string
	 */
	public String getCode() {
		return code;
	}
	/**
	 * Getter for country name
	 * @return The country name string
	 */
	public String getName() {
		return name;
	}
	
}
