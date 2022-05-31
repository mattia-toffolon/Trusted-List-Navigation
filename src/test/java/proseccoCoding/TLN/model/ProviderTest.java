package proseccoCoding.TLN.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProviderTest {
	static Provider pr1;
	static Provider pr2;
	// object representing another provider with same name to pr2
	static Provider pr2_bis;
	// provider with a service with multiple types
	static Provider pr4;

	static Service sr1;
	static Service sr2;
	static Service sr1_bis;
	static Service sr3;
	static Service sr4;
	static Service sr5;
	
	static ArrayList<ServiceType> typesSr4;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		pr1 = new Provider("provider1", new Country("AA", "AA"));
		pr2 = new Provider("provider2", new Country("BB", "BB"));
		pr2_bis = new Provider("provider2", new Country("BB", "BB"));
		
		sr1 = new Service("s1", ServiceType.getInstance("t1"), "granted", null);
		sr2 = new Service("s2", ServiceType.getInstance("t2"), "granted", null);
		sr1_bis = new Service("s1", ServiceType.getInstance("t1"), "granted", null);
		sr3 =  new Service("s3", ServiceType.getInstance("t1"), "granted", null);
		
		typesSr4 = new ArrayList<ServiceType>();
		typesSr4.add(ServiceType.getInstance("t1"));
		typesSr4.add(ServiceType.getInstance("t2"));
		
		pr4 = new Provider("provider1", new Country("DD", "dd"));
		sr4 = new Service("s1", typesSr4, "granted", null);
		sr5 = new Service("s2", ServiceType.getInstance("t2"), "granted", null);
		
		pr4.addService(sr4);
		pr4.addService(sr5);
	}

	@Test
	@DisplayName("Constructor fail testing: If inputs are illegal must throw an exception")
	void testProviderFail() {
		assertThrows(IllegalArgumentException.class, () -> {new Provider("test", null);});
		assertThrows(IllegalArgumentException.class, () -> {new Provider(null, new Country("a","a"));});
	}
	
	@Test
	@DisplayName("Constructor testing: If inputs are ok must create the object")
	void testProvider() {
		assertNotNull(pr1);
		assertNotNull(pr2);
		assertNotNull(pr2_bis);
	}
	
	@Test
	@DisplayName("Provider Code testing: Generated code must be unique, even for same name and country providers")
	void testCode() {
		assertNotEquals(pr2.getCode(), pr2_bis.getCode());
		assertNotEquals(pr1.getCode(), pr2.getCode());
		assertTrue(pr2.getCode().matches("[A-Z]{2}-[0-9]{1,}"));
	}

	@Test
	@DisplayName("AddService fail testing: If service is null must throw an exception")
	void testAddServiceFail() {
		assertThrows(IllegalArgumentException.class, () -> {pr1.addService(null);});
	}
	
	@Test
	@DisplayName("AddService testing: If service is legal must add it, if there is alreay an equal provider must return false")
	void testAddService() {
		pr1 = new Provider("provider1", new Country("AA", "AA"));
		pr2 = new Provider("provider2", new Country("BB", "BB"));
		
		assertEquals(true, pr1.addService(sr1));
		assertEquals(false, pr1.addService(sr1_bis));
		assertEquals(true, pr2.addService(sr2));
		assertEquals(true, pr2.addService(sr1));
	}

	@Test
	@DisplayName("GetServices testing: Must return all services of the type, or empty if there isn't")
	void testGetServices() {
		pr1 = new Provider("provider1", new Country("AA", "AA"));
		
		pr1.addService(sr1);
		// different type as sr1
		pr1.addService(sr2);
		// same type as sr1
		pr1.addService(sr3);
		
		assertEquals(2, pr1.getServices(sr1.getType()).size());
		assertEquals(1, pr1.getServices(sr2.getType()).size());
		assertEquals(0, pr1.getServices(ServiceType.getInstance("xx")).size());
	}
	
	@Test
	@DisplayName("GetServices with multiple types service testing: Must return the service")
	void testGetServicesMultiple() {
		for(ServiceType st : typesSr4)
			assertTrue(pr4.getServices(st).contains(sr4));		
	}

	@Test
	@DisplayName("GetServiceTypes testing: must return all service types or an empty list")
	void testGetServiceTypes() {
		pr1 = new Provider("provider1", new Country("AA", "AA"));
		pr2 = new Provider("provider2", new Country("BB", "BB"));
		
		pr1.addService(sr1);
		// different type as sr1
		pr1.addService(sr2);
		// same type as sr1
		pr1.addService(sr3);
		
		assertEquals(2, pr1.getServiceTypes().size());
		assertEquals(0, pr2.getServiceTypes().size());
		
	}

	@ParameterizedTest
	@DisplayName("GetCountryCodeByProvider fail testing: Null input or illegal input must throw exception")
	@EmptySource
	@NullSource
	@ValueSource(strings = {"wsx-21112","%564","er-44","AE-1e","-"})
	void testGetCountryCodeByProviderCodeFail(String providerCode) {
		assertThrows(IllegalArgumentException.class, ()->{Provider.getCountryCodeBy(providerCode);});
	}
	
	@ParameterizedTest
	@DisplayName("GetCountryCodeByProvider & isProviderCodeLegal not fail testing: legal input must return correct country code")
	@ValueSource(strings = {"AT-11","EE-122332","RT-0","ZZ-120303939229"})
	void testGetCountryCodeByProviderCode(String providerCode) {
		assertEquals(Provider.getCountryCodeBy(providerCode), providerCode.substring(0, 2));
	}

//	@ParameterizedTest
//	@MethodSource("stringIntAndListProvider")
//	void testWithMultiArgMethodSource(String str, int num, List<String> list) {
//	    assertEquals(5, str.length());
//	    assertTrue(num >=1 && num <=2);
//	    assertEquals(2, list.size());
//	}
//
//	static Stream<Arguments> stringIntAndListProvider() {
//	    return Stream.of(
//	        arguments("apple", 1, Arrays.asList("a", "b")),
//	        arguments("lemon", 2, Arrays.asList("x", "y"))
//	    );
//	}

}
