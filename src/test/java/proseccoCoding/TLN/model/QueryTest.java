package proseccoCoding.TLN.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class QueryTest {
	// 2 tipi possibili e 2 stati possibili
	// 2 paesi
	// 2 provider per paese
	// 2 servizi per provider
	private static Query q;
	private static ArrayList<Country> countryArr;
	private static ServiceType[] type = {ServiceType.getInstance("T1"),ServiceType.getInstance("T2")};
	private static Country[] country= {new Country("AA", "aa"), new Country("BB", "bb")};
	// for country 0
	private static Provider[] provider0 = {new Provider("PR1", country[0]), new Provider("PR2", country[0])};
	private static Service[] service0 = {
			new Service("SR01", type[0], "STATUS1", provider0[0]),
			new Service("SR02", type[1], "STATUS2", provider0[0]),
			new Service("SR03", type[0], "STATUS1", provider0[1]),
			new Service("SR04", type[1], "STATUS2", provider0[1])
			};
	// for country 1
	private static Provider[] provider1 = {new Provider("PR1", country[1]), new Provider("PR2", country[1])};
	private static Service[] service1 = {
			new Service("SR11", type[0], "STATUS1", provider1[0]),
			new Service("SR12", type[1], "STATUS2", provider1[0]),
			new Service("SR13", type[0], "STATUS1", provider1[1]),
			};
	
	@BeforeAll
	static void setUpBeforeAll() throws Exception {
		// populate county 0
		// 2 services for each provider
		provider0[0].addService(service0[0]);
		provider0[0].addService(service0[1]);
		provider0[1].addService(service0[2]);
		provider0[1].addService(service0[3]);
		for (Provider provider : provider0) {
			country[0].addProvider(provider);
		}
		// populate country 1
		// 2 services for each provider
		provider1[0].addService(service1[0]);
		provider1[0].addService(service1[1]);
		provider1[1].addService(service1[2]);
		for (Provider provider : provider1) {
			country[1].addProvider(provider);
		}
	}
	
	@BeforeEach
	void setUp() throws Exception {
		q = new Query();
		countryArr = new ArrayList<Country>();
	}

	@DisplayName("AddSelectedCountries fail testing: if input is null throws an exception")
	@ParameterizedTest
	@NullSource
	void testAddSelectedCountriesFail(ArrayList<Country> list) {
		assertThrows(IllegalArgumentException.class, ()->{q.addSelectedCountries(list);});
	}

	@DisplayName("AddSelectedCountries testing: if input is empty return false, if country added return true")
	@Test
	void testAddSelectedCountries() {
		// empty list
		assertFalse(q.addSelectedCountries(countryArr));
		countryArr.add(country[0]);
		countryArr.add(country[1]);
		// lenght 2 list
		assertTrue(q.addSelectedCountries(countryArr));
	}
	
	@DisplayName("GetAvailableProviders testing: if countries aren't loaded return null, arraylist otherwise")
	@Test
	void testGetAvailableProviders() {
		// null if countries arent loaded
		assertNull(q.getAvailableProviders());
		// array list if there are providers
		q.addSelectedCountries(new ArrayList<Country>(Arrays.asList(country)));
		ArrayList<Provider> ret = q.getAvailableProviders();
		assertEquals(4, ret.size());
		assertTrue(ret.containsAll(Arrays.asList(provider0)));
		assertTrue(ret.containsAll(Arrays.asList(provider1)));
	}


	@DisplayName("SetSelectedProviders fail testing: if input is null with or without country loaded throws an exception")
	@ParameterizedTest
	@NullSource
	void testSetSelectedProvidersFail() {
		// null con e senza country
		assertThrows(IllegalArgumentException.class, ()->{q.setSelectedProviders(null);});
		countryArr.addAll(Arrays.asList(country));
		q.addSelectedCountries(countryArr);
		assertThrows(IllegalArgumentException.class, ()->{q.setSelectedProviders(null);});
	}
	
	@DisplayName("SetSelectedProviders testing: country not loaded or empty arr return false, otherwise true")
	@Test
	void testSetSelectedProviders() {
		ArrayList<String> input = new ArrayList<String>();
		input.add(provider0[0].getCode());
		input.add(provider1[0].getCode());
		// without countries loaded
		assertFalse(q.setSelectedProviders(input));
		
		// loading countries
		countryArr.addAll(Arrays.asList(country));
		q.addSelectedCountries(countryArr);
		
		assertTrue(q.setSelectedProviders(input));
	}

	@DisplayName("GetAvailableServiceTypes testing: without selected providers return null, list otherwise")
	@Test
	void testGetAvailableServiceTypes() {
		countryArr.addAll(Arrays.asList(country));
		q.addSelectedCountries(countryArr);
		
		// without providers
		assertNull(q.getAvailableServiceTypes());
		
		// with providers
		ArrayList<String> input = new ArrayList<String>();
		input.add(provider0[0].getCode());
		input.add(provider1[0].getCode());
		input.add(provider1[1].getCode());
		q.setSelectedProviders(input);
		
		ArrayList<ServiceType> ret = q.getAvailableServiceTypes();
		assertEquals(2, ret.size());
		assertTrue(ret.containsAll(Arrays.asList(type)));
	}
	
	@DisplayName("GetAvailableServiceTypes testing2: without selected providers return null, list otherwise")
	@Test
	void testGetAvailableServiceTypes2() {
		countryArr.addAll(Arrays.asList(country));
		q.addSelectedCountries(countryArr);
		
		// without providers
		assertNull(q.getAvailableServiceTypes());
		
		// with providers
		ArrayList<String> input = new ArrayList<String>();
		input.add(provider1[1].getCode());
		q.setSelectedProviders(input);
		
		ArrayList<ServiceType> ret = q.getAvailableServiceTypes();
		assertEquals(1, ret.size());
		assertTrue(ret.contains(type[0]));
	}

	@DisplayName("SetSelectedServiceTypes testing: returns false if providers aren't loaded or if the input is empty, true if types are loaded")
	@Test
	void testSetSelectedServiceTypes() {
		ArrayList<String> types = new ArrayList<String>();
		countryArr.addAll(Arrays.asList(country));
		q.addSelectedCountries(countryArr);
		// without providers
		assertFalse(q.setSelectedServiceTypes(types));
		ArrayList<String> input = new ArrayList<String>();
		input.add(provider0[0].getCode());
		input.add(provider1[0].getCode());
		input.add(provider1[1].getCode());
		q.setSelectedProviders(input);
		// empty input
		assertFalse(q.setSelectedServiceTypes(types));
		types.add(type[0].getCode());
		types.add(type[1].getCode());
		// not empty
		assertTrue(q.setSelectedServiceTypes(types));
	}

	@Test
	void testGetAvailableServiceStatus() {
		ArrayList<String> types = new ArrayList<String>();
		countryArr.addAll(Arrays.asList(country));
		q.addSelectedCountries(countryArr);
		ArrayList<String> input = new ArrayList<String>();
		input.add(provider0[0].getCode());
		input.add(provider1[0].getCode());
		input.add(provider1[1].getCode());
		q.setSelectedProviders(input);
		types.add(type[0].getCode());
		types.add(type[1].getCode());
		q.setSelectedServiceTypes(types);
		ArrayList<String> ret = q.getAvailableServiceStatus();
		assertTrue(ret.contains("STATUS1"));
		assertTrue(ret.contains("STATUS2"));
		assertTrue(ret.size()==2);
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
