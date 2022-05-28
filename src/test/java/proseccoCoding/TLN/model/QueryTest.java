package proseccoCoding.TLN.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

class QueryTest {

	@BeforeAll
	static void setUpBeforeAll() throws Exception {
		// 2 tipi possibili e 2 stati possibili
		// 3 paesi
		// 2 provider per paese
		// 2 servizi per provider
		ServiceType[] type = {ServiceType.getInstance("T1"),ServiceType.getInstance("T2")};

		Country[] country= {new Country("AA", "aa"), new Country("BB", "bb"), new Country("CC", "cc")};
		
		// populate county 0
		Provider[] provider0 = {new Provider("PR1", country[0]), new Provider("PR2", country[0])};
		// 2 services foreach provider
		Service[] service0 = {
				new Service("SR01", type[0], "STATUS1", provider0[0]),
				new Service("SR02", type[1], "STATUS2", provider0[0]),
				new Service("SR03", type[0], "STATUS1", provider0[1]),
				new Service("SR04", type[1], "STATUS2", provider0[1])
				};
		for (Provider provider : provider0) {
			country[0].addProvider(provider);
			provider
		}
		// populate country 1
		Provider[] provider1 = {new Provider("PR1", country[1]), new Provider("PR2", country[1])};
		for (Provider provider : provider1) {
			country[1].addProvider(provider);
		}
		// populate country 2
		Provider[] provider2 = {new Provider("PR1", country[2]), new Provider("PR2", country[2])};
		for (Provider provider : provider2) {
			country[2].addProvider(provider);
		}
		
		for (Provider provider : provider0) {
			
		}
	}
	
	@BeforeEach
	static void setUp() throws Exception {
		Query q = new Query();
	}

	@DisplayName("AddSelectedCountries fail testing: if input is null or empty throws an exception")
	@ParameterizedTest
	@EmptySource
	@NullSource
	void testAddSelectedCountries(ArrayList<Country> list) {
		assertThrows(null, null)
	}

	@Test
	void testGetAvailableProviders() {
		fail("Not yet implemented");
	}

	@Test
	void testSetSelectedProviders() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAvailableServiceTypes() {
		fail("Not yet implemented");
	}

	@Test
	void testSetSelectedServiceTypes() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAvailableServiceStatus() {
		fail("Not yet implemented");
	}

	@Test
	void testSetSelectedServiceStatus() {
		fail("Not yet implemented");
	}

	@Test
	void testGetResults() {
		fail("Not yet implemented");
	}

}
