package proseccoCoding.TLN.model;

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
}
