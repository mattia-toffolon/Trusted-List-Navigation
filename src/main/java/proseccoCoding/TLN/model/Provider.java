package proseccoCoding.TLN.model;

import java.util.ArrayList;
import java.util.Objects;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

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
	 * The code of the country in which the provider is
	 */
	private String countryCode;
	/**
	 * Multimap that stores the services as value and the related type as key
	 */
	private Multimap<ServiceType, Service> services;
	
	/**
	 * Contructor for class Provider
	 * @param name Complete name of the provider
	 * @param countryCode Code that identify the provider country
	 */
	public Provider(String name, String countryCode) throws IllegalArgumentException {
		if(name == null || countryCode == null)
			throw new IllegalArgumentException("Arguments must be not null");
		this.name = name;
		this.countryCode = countryCode;
		this.code = countryCode + counter;
		counter++;
		this.services = ArrayListMultimap.create();
	}
	
	/**
	 * Adds the service to the provider 
	 * @param service The service to add
	 * @return True if the services has been added successfully, false otherwise 
	 */
	public boolean addService(Service service) {
		return services.put(service.getType(), service);
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

	/**
	 * Generate an hash code based only on the code of the provider
	 */
	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

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
	public String getCountryCode() {
		return countryCode;
	}
}
