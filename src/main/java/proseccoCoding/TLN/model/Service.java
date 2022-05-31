package proseccoCoding.TLN.model;

import java.util.ArrayList;
import java.util.Objects;

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
	 * Constructor for a service object with only one service type
	 * @param name complete name of the service
	 * @param type object representing the type of the service
	 * @param status the status relative to this service
	 * @param provider the provider that offers this service (not required)
	 * @throws IllegalArgumentException if at least one required parameter is null
	 
	public Service(String name, ServiceType type, String status, Provider provider) {
		if(name == null || type == null || status == null)
			throw new IllegalArgumentException("Arguments must be not null");
		this.name = name;
		this.types = new ArrayList<ServiceType>();
		types.add(type);
		this.status = status;
		this.provider = provider;
	}
	*/

	public String getName() {
		return name;
	}
	public ArrayList<ServiceType> getTypes() {
		return types;
	}
	public String getStatus() {
		return status;
	}
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
	 * Compares two objects of service calculated with both name and type
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
