package proseccoCoding.TLN.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.util.Pair;

public class ServiceType {
	private static HashMap<String, ServiceType> instances = null;

	private static boolean autoFetch = true;

	private String code;
	private String name;

	/**
	 * Private constructor for a ServiceType object
	 * 
	 * @param code the code of the service type
	 * @param name The name of the service type
	 * @throws IllegalArgumentException if one of the parameters is null
	 */
	private ServiceType(String code, String name) {
		if (name == null || code == null)
			throw new IllegalArgumentException("Arguments must be not null");
		this.code = code;
		this.name = name;
	}

	/**
	 * Returns the instance associated with the given typeCode, if not present in all types
	 * the instance will be added
	 * 
	 * @param typeCode The code of the type to get the instance
	 * @return the Singleton instance of ServiceType associated with typeCode
	 * @throws IllegalArgumentException if the code is null
	 */
	public static ServiceType getInstance(String typeCode) {
		if (typeCode == null || typeCode.isBlank())
			throw new IllegalArgumentException("Argument must be not null");
		if (instances == null)
			init();
		ServiceType ret = instances.get(typeCode);
		if (ret == null && !instances.containsKey(typeCode)) {
			ret = new ServiceType(typeCode, typeCode + " (complete name not found)");
			instances.putIfAbsent(typeCode, ret);
		}
		return ret;
	}

	/**
	 * Initializes and fills the hashmap with all the known types complete data
	 */
	public static void init() {
		instances = new HashMap<String, ServiceType>();
		if (autoFetch)
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
	 * Return the list of Pairs that represents all the known service types
	 * @return An ArrayList of Pairs containing as Key the type code, and as value the type name
	 */
	public static ArrayList<Pair<String, String>> printServiceTypes() {
		if (instances == null)
			init();
		return new ArrayList<Pair<String, String>>(
				instances.values().stream()
				.map((ServiceType s) -> {
					return new Pair<String, String>(s.getCode(), s.getName());
				}).collect(Collectors.toList())
			);
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
	 * 
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
