package proseccoCoding.TLN.model;

import java.util.Objects;

public class Service {
	/**
	 * Complete name of the service
	 */
	private String name;
	/**
	 * Type of the service
	 */
	private ServiceType type;
	
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
	 * @param type object representing the type of the service
	 * @param status the status relative to this service
	 * @param provider the provider that offers this service (not required)
	 */
	public Service(String name, ServiceType type, String status, Provider provider) {
		if(name == null || type == null || status == null)
			throw new IllegalArgumentException("Arguments must be not null");
		this.name = name;
		this.type = type;
		this.status = status;
		this.provider = provider;
	}

	public String getName() {
		return name;
	}
	public ServiceType getType() {
		return type;
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
		return Objects.hash(name, type);
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
		return Objects.equals(name, other.name) && Objects.equals(type, other.type);
	}
}
