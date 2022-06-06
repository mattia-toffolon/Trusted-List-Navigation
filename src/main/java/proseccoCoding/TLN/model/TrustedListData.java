package proseccoCoding.TLN.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javafx.util.Pair;
/**
 * This class wraps all the objects that represents EU trusted entities (country, provider, service).
 * In fact an object of this class contains a set of countries that contains providers that contains services.
 * At the time of construction this object is automatically populated using @{link APIHandler.retrieveCountries} method
 */
public class TrustedListData {
	/**
	 * Hashmap containing all the countries objects
	 */
	private HashMap<String,Country> countries;
	
	/**
	 * Constructor for TrustedListData. Initializes the countries 
	 */
	public TrustedListData() {
		countries = APIHandler.retrieveCountries();
	}
	
	/**
	 * return the Country object representing the country which code is specified as parameter
	 * @param the code of the country to return, upper or lower case
	 * @return the Country object associated with the parameter code, or null if the code hasn't been found
	 * @throws IllegalArgumentException if the parameter is null
	 */
	public Country getCountry(String countryCode) {
		if (countryCode == null)
			throw new IllegalArgumentException("Argument must not be null");
		countryCode = countryCode.toUpperCase();
		// retrieve the desired country from the map
		Country ret = countries.get(countryCode);
		// if no key is mapped with countryCode as key
		if(ret == null)
			return null;
		
		// if this country has not been retrieved, update the map and return
		if(!ret.isRetrieved()) {
			ret = APIHandler.retriveCountryData(countryCode);
			countries.replace(countryCode, ret);
		}
		return ret;
	}
	
	/**
	 * Returns a list of country objects filled with data which code is contained in the given parameter list
	 * @param countryCodes an ArrayList of Strings that contains the country codes
	 * @return an ArrayList of country objects filled with data which code is contained in the given parameter list
	 * @throws IllegalArgumentException if the parameter is null or empty
	 */
	public ArrayList<Country> getCountries(ArrayList<String> countryCodes){
		if (countryCodes == null)
			throw new IllegalArgumentException("Argument must not be null");
		if(countryCodes.isEmpty())
			return null;
		
		ArrayList<Country> ret = new ArrayList<Country>();
		for(String s : countryCodes) {
			Country temp = this.getCountry(s);
			if(temp == null)
				return null;
			ret.add(temp);
		}
		
		return ret;
	}
	
	/**
	 * Returns a list containing names and codes of each country.
	 * @return an ArrayList of Pair<String, String> composed by country code as first value and country name as second value.
	 * 		   Each pair represents a country.
	 */
	public ArrayList<Pair<String,String>> printCountries(){
		ArrayList<Pair<String,String>> ret = new ArrayList<Pair<String,String>>();
		Collection<Country> values = countries.values();
		Iterator<Country> i = values.iterator();
		while(i.hasNext()) {
			Country temp = i.next();
			ret.add(temp.print());
		}
		return ret;
	}
}
