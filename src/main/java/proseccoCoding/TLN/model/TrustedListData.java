package proseccoCoding.TLN.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javafx.util.Pair;
public class TrustedListData{
	private HashMap<String,Country> countries;
	
	public TrustedListData() {
		countries = APIHandler.retrieveCountries();
	}
	/**
	 * return the Country object representing the country which the code specified as parameter
	 * @param the code of the country to return
	 * @return the Country object with the parameter code
	 * @throws IllegalArgumentException if the parameter is null
	 */
	public Country getCountry(String countryCode){
		if (countryCode == null)
			throw new IllegalArgumentException("Argument must not be null");
		Country ret = countries.get(countryCode);
		if(!ret.isRetrieved())
			ret = APIHandler.retriveCountryData(countryCode);
		return ret;
	}
	/**
	 * return a set of country objects representing the countries which the code is in the parameter set
	 * @param an ArrayList of country codes
	 * @return an ArrayList of corresponding Countries
	 * @throws IllegalArgumentException if the parameter is null
	 */
	public ArrayList<Country> getCountries(ArrayList<String> countryCodes){
		if (countryCodes == null)
			throw new IllegalArgumentException("Argument must not be null");
		ArrayList<Country> ret = new ArrayList<Country>();
		Iterator<String> i = countryCodes.iterator();
		while(i.hasNext()) {
			String tempCode = i.next();
			Country tempCountry = countries.get(tempCode);
			if(!tempCountry.isRetrieved())
				tempCountry = APIHandler.retriveCountryData(tempCode);
			ret.add(tempCountry);
		}
		// ret = countryCodes.stream().map((String s)->{return this.getCountry(s);}).collect(Collectors.toList());
		return ret;
	}
	/**
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
