package proseccoCoding.TLN.model;
import java.util.HashMap;
import java.util.Objects;

import javafx.util.Pair;

public class ServiceType {
	private static HashMap<String, ServiceType> instances = null;
	
	private String code;
	private String name;
	
	/**
	 * Private constructor for a ServiceType object
	 * @param code the code of the service type
	 * @param name The name of the service type
	 * @throws IllegalArgumentException if one of the parameters is null
	 */
	private ServiceType(String code, String name) {
		if(name == null || code == null)
			throw new IllegalArgumentException("Arguments must be not null");
		this.code = code;
		this.name = name;
	}

	/**
	 * Returns the instance associated with the given typeCode
	 * @param typeCode
	 * @return the Singleton instance of ServiceType associated with typeCode
	 * @throws IllegalArgumentException if the code is null
	 */
	public static ServiceType getInstance(String typeCode) {
		if(typeCode == null)
			throw new IllegalArgumentException("Argument must be not null");
		if(instances == null)
			init();
		ServiceType ret = instances.get(typeCode);
		if(ret == null) {
			ret = new ServiceType(typeCode, typeCode+" (complete name not found)");
					instances.put(typeCode, ret);
		}
		return ret;
	}
	
	/**
	 * Initializes and fills the hashmap with all the known types complete data
	 */
	private static void init() {
		instances = new HashMap<String, ServiceType>();
		
		for (Pair<String, String> p : APIHandler.retriveServiceTypes())
			instances.put(p.getKey(), new ServiceType(p.getKey(), p.getValue()));
	}

	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	
	
	/**
	 * Hash Code generated only from the code attribute
	 */
	@Override
	public int hashCode() {
		return Objects.hash(code);
	}
	/**
	 * Equals method that compare service types only by code
	 * @param obj Second object to compare to this
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceType other = (ServiceType) obj;
		return Objects.equals(code, other.code);
	}
}
