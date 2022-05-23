package proseccoCoding.TLN.model;

import java.util.Objects;

public class Service {
	/**
	 * Complete name of the service
	 */
	private String name;
	/**
	 * Type of the service (can be omitted)
	 */
	private ServiceType type;
	
	/**
	 * Constructor for a service object
	 * @param name complete name of the service
	 * @param type object representing the type of the service
	 */
	public Service(String name, ServiceType type) {
		if(name == null || type == null)
			throw new IllegalArgumentException("Arguments must be not null");
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}
	
	public ServiceType getType() {
		return type;
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
