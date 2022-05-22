package proseccoCoding.TLN.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServiceTypeTest {
	
	static ServiceType st1;
	static ServiceType st2;
	// Service code equal to st1 but different name
	static ServiceType st1_bis;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		st1 = new ServiceType("type1", "Service Type 1");
		st2 = new ServiceType("type2", "Service Type 2");
		st1_bis = new ServiceType("type1", "Service Type 1 bis");
	}
	
	@Test
	@DisplayName("Constructor testing: in case of a null parameter must throw an exception")
	void testServiceType() {
		assertThrows(IllegalArgumentException.class, () -> {new ServiceType("test", null);});
		assertThrows(IllegalArgumentException.class, () -> {new ServiceType(null, "test");});
	}

	@Test
	@DisplayName("Hash code testing: hash codes of two different but same typeCode objects needs to be equal")
	void testHashCodeEquals() {
		assertEquals(st1.hashCode(), st1_bis.hashCode());
	}
	@Test
	@DisplayName("Hash code testing: hash codes from two different typeCode objects needs to be different")
	void testHashCodeNotEquals() {
		assertNotEquals(st1.hashCode(), st2.hashCode());
	}

	@Test
	@DisplayName("Equals testing: different objects having same typeCode must be equal")
	void testEqualsObject() {
		assertTrue(st1.equals(st1_bis));
	}
	
	@Test
	@DisplayName("Equals testing: different objects having different typeCode must be not equal")
	void testNotEqualsObject() {
		assertFalse(st1.equals(st2));
	}
}
