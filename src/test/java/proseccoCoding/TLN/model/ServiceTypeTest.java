package proseccoCoding.TLN.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ServiceTypeTest {
	
	@DisplayName("GetInstance fail testing: if a null or blanck code is passed must throw an excepiton")
	@ParameterizedTest
	@NullSource
	@EmptySource
	void testGetInstanceFail(String code) {
		assertThrows(IllegalArgumentException.class, () -> {ServiceType.getInstance(code);});
	}
	
	@DisplayName("GetInstance testing: must return an instance for the code")
	@ParameterizedTest
	@ValueSource(strings = {"type1", "type2", "type3"})
	void testGetInstance(String code) {
		assertEquals(code, ServiceType.getInstance(code).getCode());
	}
	
	@DisplayName("GetInstance testing: the instance for a given code must be a singleton: for the same code must return the same istance")
	@ParameterizedTest
	@ValueSource(strings = {"type1", "type2", "type3"})
	void testGetInstance2(String code) {
		ServiceType a = ServiceType.getInstance(code);
		ServiceType b = ServiceType.getInstance(code);
		// check the references to objects, not equals() method
		assertTrue(a == b);
	}
}
