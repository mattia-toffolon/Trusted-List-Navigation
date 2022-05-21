package proseccoCoding.TLN.model;

import java.util.Objects;

public class ServiceType {
	private String code;
	private String name;
	
	
	public ServiceType(String code, String name) {
		this.code = code;
		this.name = name;
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

	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}
