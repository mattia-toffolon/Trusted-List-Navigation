package proseccoCoding.TLN.model;

import java.util.ArrayList;
import java.util.Objects;
/**
 * This class represents a Trusted Service with his name, type, status and provider.
 * It is recommended to not instantiate the Provider object in the constructor of the Service, but to
 * pass the effective Provider object that contains this service. This parameter is only for having
 * additional info for this provider, not for deep business logic purposes.
 */
public class Service {
	/**
	 * Complete name of the service
	 */
	private String name;
	/**
	 * Type of the service
	 */
	private ArrayList<ServiceType> types;
	
	/**
	 * Status of the service
	 */
	private String status;
	
	/**
	 * Provider that provides this service
	 */
	private Provider provider;
	
	/**
	 * Constructor for a service object
	 * @param name complete name of the service
	 * @param types ArrayList containing all ServiceType objects representing the service
	 * @param status the status relative to this service
	 * @param provider the provider that offers this service (not required)
	 * @throws IllegalArgumentException if at least one required parameter is null or types is empty
	 */
	public Service(String name, ArrayList<ServiceType> types, String status, Provider provider) {
		if(name == null || types == null || types.isEmpty() || status == null)
			throw new IllegalArgumentException("Arguments must be not null");
		this.name = name;
		this.types = types;
		this.status = status;
		this.provider = provider;
	}

	/**
	 * Getter for service name
	 * @return The service name string
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for service type
	 * @return The ServiceType object
	 */
	public ArrayList<ServiceType> getTypes() {
		return types;
	}
	/**
	 * Getter for service status
	 * @return The service status string
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Getter for the service provider, could be null if it is not known
	 * @return The Provider object
	 */
	public Provider getProvider() {
		return provider;
	}

	/**
	 * Gets the hash code for this service calculated with both name and type
	 */
	@Override
	public int hashCode() {
		return Objects.hash(name, types);
	}

	/**
	 * Compares the given Service object to this one by comparing both name and type
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Service other = (Service) obj;
		return Objects.equals(name, other.name) && Objects.equals(types, other.types);
	}
}
