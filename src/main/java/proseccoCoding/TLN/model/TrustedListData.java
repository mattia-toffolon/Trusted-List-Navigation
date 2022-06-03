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
	 * Contructor for TrustedListData. Initializes the countries 
	 */
	public TrustedListData() {
		countries = APIHandler.retrieveCountries();
	}
	
	/**
	 * return the Country object representing the country which the code specified as parameter
	 * @param the code of the country to return upper or lower case
	 * @return the Country object associated with the parameter code, or null if the code isn't found
	 * @throws IllegalArgumentException if the parameter is null
	 */
	public Country getCountry(String countryCode) {
		if (countryCode == null)
			throw new IllegalArgumentException("Argument must not be null");
		countryCode = countryCode.toUpperCase();
		// retrieve the country from the map
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
	 * Returns a list of country objects with data retrieved representing the countries 
	 * that have the code in the given parameter list
	 * @param an ArrayList of country codes
	 * @return an ArrayList of corresponding Countries objects with retrieved data
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
	 * Returns a list containing all names and codes for every country. It would be a facility to print them
	 * @return an ArrayList of pair, which pairs are "conutry code" and "country name" for each country
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
