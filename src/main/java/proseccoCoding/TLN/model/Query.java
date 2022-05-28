package proseccoCoding.TLN.model;

import java.util.ArrayList;
import java.sql.Timestamp;

public class Query{
	/**
	 * The timestamp representing the query starting time
	 */
	private Timestamp startTime;
	/**
	 * ArrayList that stores the Country objects selected by the user
	 */
	private ArrayList<Country> selectedCountries;
	/**
	 * ArrayList that stores the Provider objects selected by the user
	 */
	private ArrayList<Provider> selectedProviders;
	/**
	 * ArrayList that stores the Service objects which service type has been selected by the user
	 */
	private ArrayList<Service> selectedServicesByType;
	/**
	 * ArrayList that stores the possible service statuses among the selected services as Strings
	 */
	private ArrayList<String> selectedServiceStatus;
	
	public Query() {
		startTime = new Timestamp(System.currentTimeMillis());
	}

	/**
	 * This method sets selectedCountries with the given parameter
	 * @param ArrayList of countries objects that correspond to the ones selected by the user
	 * @return true if countries was added, false otherwise
	 * @throws IllegalArgumentException In case of null parameter
	 */
	public boolean addSelectedCountries(ArrayList<Country> countries) throws IllegalArgumentException {
		if(countries == null)
			throw new IllegalArgumentException("Argument must be not null");

		selectedCountries = countries;
		return true;
	}
	
	/**
	 * This method retrives from selectedCountries all the relative Provider objects and returns them as an ArrayList
	 * @return ArraList of Provider objects that contains all the possible providers from the selected countries
	 */
	public ArrayList<Provider> getAvailableProviders(){
		ArrayList<Provider> availableProviders = new ArrayList<Provider>();
		
		for(Country c : selectedCountries) {
			ArrayList<Provider> thisCountryProviders = c.getProviders();
			for(Provider p : thisCountryProviders)
				availableProviders.add(p);
		}
		
		return availableProviders;
	}
	/**
	 * This method adds to selectedProviders the providers object associated with the Strings contained in providerCodes
	 * @param providerCodes , ArrayList of Strings that contains all the codes of the selected providers
	 * @return true if the Provider objects were added, false otherwise
	 * @throws IllegalArgumentException In case of null parameter
	 */
	public boolean setSelectedProviders(ArrayList<String> providerCodes) throws IllegalArgumentException {
		if(providerCodes == null)
			throw new IllegalArgumentException("Argument must be not null");

		selectedProviders = new ArrayList<Provider>();
		ArrayList<Provider> availableProviders = getAvailableProviders();
		for(Provider p : availableProviders)
			if(providerCodes.contains(p.getCode()))
				selectedProviders.add(p);
		
		return true;
	}
	/**
	 * This method returns an ArrayList of all the possible service types user can select in this query
	 * @return ArrayList of ServiceType objects
	 */
	public ArrayList<ServiceType> getAvailableServiceTypes(){
		ArrayList<ServiceType> availableServiceTypes = new ArrayList<ServiceType>();
		
		for(Provider p : selectedProviders) {
			for(ServiceType st : p.getServiceTypes()) {
				if(availableServiceTypes.contains(st))
					continue;
				else
					availableServiceTypes.add(st);
			}
		}
		
		return availableServiceTypes;
	}
	/**
	 * This method adds to selectedServicesByType all the Service objects contained in selectedProviders' Provider objects 
	 * which service type is among the selected ones by the user
	 * @param typeCodes , ArrayList of Strings that indicates the selected service types
	 * @return true if the Service objects were added, false otherwise
	 * @throws IllegalArgumentException In case of null parameter
	 */
	public boolean setSelectedServiceTypes(ArrayList<String> typeCodes) throws IllegalArgumentException {
		if(typeCodes == null)
			throw new IllegalArgumentException("Argument must be not null");
		
		selectedServicesByType = new ArrayList<Service>();
		for(Provider p : selectedProviders) {
			for(ServiceType st : p.getServiceTypes()) {
				for(Service s : p.getServices(st))
					selectedServicesByType.add(s);
			}
		}
		
		return true;
	}
	/**
	 * This method saves and returns all possible service status from selectedServicesByType in an ArrayLIst as Strings
	 * @return ArrayList of Strings 
	 */
	public ArrayList<String> getAvailableServiceStatus() {
		ArrayList<String> availableServiceStatus = new ArrayList<String>();
		
		for(Service s : selectedServicesByType)
			if(!availableServiceStatus.contains(s.getStatus()))
				availableServiceStatus.add(s.getStatus());
		
		return availableServiceStatus;
	}
	/**
	 * This method adds to selectedServiceStatus all the service statuses selected by the user
	 * @param status , ArrayList of Strings
	 * @return true if selectedServiceStatus was correctly loaded, false otherwise
	 * @throws IllegalArgumentException In case of null parameter
	 */
	public boolean setSelectedServiceStatus(ArrayList<String> status) throws IllegalArgumentException {
		if(status == null)
			throw new IllegalArgumentException("Argument must be not null");
		
		selectedServiceStatus = status;
		
		return true;
	}
	/**
	 * This method returns all Service objects from selectedServiceByType 
	 * which status is among the ones contained in selectedServiceStatus
	 * @return ArrayList of Service objects
	 */
	public ArrayList<Service> getResults() {
		ArrayList<Service> results = new ArrayList<Service>();
		
		for(Service s : selectedServicesByType) {
			if(selectedServiceStatus.contains(s.getStatus()))
				results.add(s);
		}
		
		return results;
	}
	/**
	 * This method tells if the query is completed or hasn't been completed yet
	 * @return true if the query is completed, false otherwise
	 */
	public boolean isEnded() {
		return (selectedServiceStatus==null ? false : true);
	}
}
