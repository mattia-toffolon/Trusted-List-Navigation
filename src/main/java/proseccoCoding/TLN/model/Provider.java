package proseccoCoding.TLN.model;

import java.util.ArrayList;
import java.util.Objects;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

public class Provider {
	/**
	 * Provider complete name
	 */
	private String name;
	/**
	 * Custom code made as code = countryCode-counter
	 */
	private String code;
	/**
	 * Static counter to implement the incremental assignment of codes
	 */
	private static int counter = 0;
	/**
	 * The country in wich this provider is located
	 */
	private Country country;
	/**
	 * Multimap that stores the services as value and the related type as key
	 */
	private SetMultimap<ServiceType, Service> services;
	
	/**
	 * Contructor for class Provider
	 * @param name Complete name of the provider
	 * @param countryCode Code that identify the provider country, must be of length 2 
	 */
	public Provider(String name, Country country) throws IllegalArgumentException {
		if(name == null || name.isBlank() || country == null)
			throw new IllegalArgumentException("Arguments must be not null");
		this.name = name;
		this.country = country;
		this.code = country.getCode() + "-" + counter;
		counter++;
		this.services = HashMultimap.create();
	}
	
	/**
	 * Adds the service to the provider 
	 * @param service The service to add
	 * @return True if the service is been added successfully, false otherwise
	 * @throws IllegalArgumentException If the service is null
	 */
	public boolean addService(Service service) throws IllegalArgumentException{
		if(service == null)
			throw new IllegalArgumentException("Argument must be not null");
		for(ServiceType type : service.getTypes()) {
			if(!services.put(type, service))
				return false;
		}
			
		return true;
	}
	
	/**
	 * Returns all the services provided by this provider that have type value 
	 * equals to the one given as parameter.
	 * @param type The type to search for
	 * @return ArrayList with services if there are, or empty (not null) if there aren't
	 */
	public ArrayList<Service> getServices(ServiceType type) {
		return new ArrayList<Service>(services.get(type)); 
	}
	
	/**
	 * Returns all the service types provided by this provider. If this provider has no services
	 * the ArrayList would be empty (not null)
	 * @return An ArrayList containing all service types without duplicates
	 */
	public ArrayList<ServiceType> getServiceTypes() {
		return new ArrayList<ServiceType>(services.keySet());
	}

//	/**
//	 * Generate an hash code based only on the code of the provider
//	 */
//	@Override
//	public int hashCode() {
//		return Objects.hash(code);
//	}
//
	/**
	 * Checks if the object passed as argument is equal to this object only by their codes
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Provider other = (Provider) obj;
		return Objects.equals(code, other.code);
	}

	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	public Country getCountry() {
		return country;
	}
	
	/**
	 * Static method that returns the country code associated to the given provider code.
	 * The provider code must be as [A-Z]{2}-[0-9]{1,}
	 * @param providerCode The code of the provider
	 * @return The code of the country in which the provider is located
	 * @throws IllegalArgumentException if the given code is not in a legal format
	 */
	public static String getCountryCodeBy(String providerCode) throws IllegalArgumentException {
		if(!isProviderCodeLegal(providerCode))
			throw new IllegalArgumentException("providerCode must be in format [A-Z][A-Z]-**");
		
		return providerCode.substring(0, 2);
	}
	
	/**
	 * Static method that checks if the provider code is legal.
	 * The provider code must be as [A-Z]{2}-[0-9]{1,}
	 * @param providerCode The code of the provider
	 * @return True if the code is correctly formatted
	 * @throws IllegalArgumentException if the given code is null
	 */
	public static boolean isProviderCodeLegal(String providerCode) throws IllegalArgumentException {
		if(providerCode == null)
			throw new IllegalArgumentException("providerCode must be not null");
		if(providerCode.matches("[A-Z]{2}-[0-9]{1,}"))
			return true;
		return false;
	}
}
