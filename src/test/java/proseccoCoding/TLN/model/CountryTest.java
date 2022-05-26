package proseccoCoding.TLN.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javafx.util.Pair;

class CountryTest {
	
	static Country c1;
	static Country c2;
	static Country c3;
	
	static Provider p1;
	static Provider p1_bis;
	static Provider p2;
	static Provider p1_dif;
	

	@BeforeEach
	void setUp() {
		c1 = new Country("AA", "aaaa");
		c2 = new Country("BB", "bbbb");
		c3 = new Country("CC", "cccc");
		
		p1 = new Provider("provider1", c1);
		p1_bis = new Provider("provider1", c1);
		p2 = new Provider("provider2", c2);
		
		p1_dif = new Provider("prvider2", c1);
		
		c1.addProvider(p1);
		c2.addProvider(p2);
	}

	@Test
	@DisplayName("Constructor fail testing: if one input is null must throw exception")
	void testCountryStringString() {
		assertThrows(IllegalArgumentException.class, () -> {new Country("test", null);});
		assertThrows(IllegalArgumentException.class, () -> {new Country(null, "test");});
	}

	@Test
	@DisplayName("GetProviders testing: must return an array of providers or null if they are not retrieved yet")
	void testGetProviders() {
		assertNull(c3.getProviders());
		assertEquals(1, c1.getProviders().size());
		assertEquals(1, c2.getProviders().size());
	}

	@Test
	@DisplayName("GetProvider fail testing: must return null if this provider is not found or is it illegal")
	void testGetProviderFail() {
		assertNull(c3.getProvider(p1.getCode()));
		assertNull(c1.getProvider(p2.getCode()));
		assertNull(c1.getProvider(p2.getCode()));
	}
	
	@Test
	@DisplayName("GetProvider testing: must return the provider if the code matches")
	void testGetProvider() {
		assertEquals(c1.getProvider(p1.getCode()).getCode(), p1.getCode());
		assertEquals(c2.getProvider(p2.getCode()).getCode(), p2.getCode());
	}

	@Test
	@DisplayName("GetProvider fail testing: In case in which the provider is null or has a different countryCode")
	void testAddProviderFail() {
		assertThrows(IllegalArgumentException.class, () -> {c1.addProvider(null);});
		assertThrows(IllegalArgumentException.class, () -> {c1.addProvider(p2);});
		assertThrows(IllegalArgumentException.class, () -> {c2.addProvider(p1);});
	}
	
	@Test
	@DisplayName("GetProvider fail testing: In case in which the provider is null or has a different countryCode")
	void testAddProvider() {
		assertTrue(c1.addProvider(p1_dif));
		assertFalse(c1.addProvider(p1));	
	}

	@Test
	@DisplayName("IsRetrievrd testing: must is true if the country has retrieved data")
	void testIsRetrieved() {
		assertTrue(c1.isRetrieved());
		assertFalse(c3.isRetrieved());
	}

	@Test
	@DisplayName("Print testing: must return a Pair with name and code")
	void testPrint() {
		assertEquals(new Pair<String,String>("AA", "aaaa"), c1.print());
		assertEquals(new Pair<String,String>("BB", "bbbb"), c2.print());
	}
}
