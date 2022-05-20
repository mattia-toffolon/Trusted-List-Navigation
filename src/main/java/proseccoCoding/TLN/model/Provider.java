package proseccoCoding.TLN.model;

import java.util.ArrayList;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class Provider {
	/**
	 * Provider complete name
	 */
	private String name;
	/**
	 * Custom code made as code = countyCode-counter
	 */
	private String code;
	private static int counter = 0;
	private String countryCode;
	private Multimap<ServiceType, Service> services;
	
	/**
	 * Contructor for class Provider
	 * @param name complete name of the provider
	 * @param countryCode code that identify the provider country
	 */
	public Provider(String name, String countryCode) {
		this.name = name;
		this.countryCode = countryCode;
		this.code = countryCode + counter;
		counter++;
		this.services = ArrayListMultimap.create();
	}
	
	/**
	 * Adds the service to the provider 
	 * @param service the service to add
	 * @return true if the services is been added succesfully, false otherwise 
	 */
	public boolean addService(Service service) {
		return services.put(service.getType(), service);
	}
	
	/**
	 * Returns all the services provided by this provider that have type value 
	 * equals to the one given as parameter.
	 * @param type the type to search for
	 * @return ArrayList with services if there are, or empty (not null) if there aren't
	 */
	public ArrayList<Service> getServices(ServiceType type) {
		return new ArrayList<Service>(services.get(type)); 
	}
}
