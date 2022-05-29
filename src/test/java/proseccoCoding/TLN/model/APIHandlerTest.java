package proseccoCoding.TLN.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javafx.util.Pair;

class APIHandlerTest {
	
	static String name1 = "Italy";
	static String name2 = "Mexico";
	static String code1 = "IT";
	static String code2 = "ME";
	static Country country1 = new Country(code1, name1);
	static String typeServiceCode1 = "QCertESig";
	static String typeServiceName1 = "Qualified certificate for electronic signature";
	static Pair<String,String> pair1 = new Pair(typeServiceCode1,typeServiceName1);
	static String typeServiceCode2 = "AAA";
	static String typeServiceName2 = "BBB";
	static Pair<String,String> pair2 = new Pair(typeServiceCode2,typeServiceName2);
	static String providerName1 = "Actalis S.p.A.";

	@Disabled
	@Test
	void testAPIHandler() {
		
		fail("Not yet implemented");
	}
	
	@Test
	@DisplayName("retrieveCountriesNames fail testing: test fail if the method return a null pointer")
	void testRetrieveCountriesNamesFail() {
		assertNotNull(APIHandler.retrieveCountriesNames());
	}
	
	@Test
	@DisplayName("retrieveCountriesNames testing: test return true if the ArrayList contains name1"
			+ " and and false for name2")
	void testRetrieveCountriesNames() {
		assertTrue(APIHandler.retrieveCountriesNames().contains(name1));
		assertFalse(APIHandler.retrieveCountriesNames().contains(name2));
	}
	
	@Test
	@DisplayName("retrieveCountries fail testing: test fail if the method return a null pointer")
	void testRetrieveCountriesFail() {
		assertNotNull(APIHandler.retrieveCountries());
	}
	
	@Test
	@DisplayName("retrieveCountries testing: test return true if the map contains the key code1 and name1 as associated value")
	void testRetrieveCountries() {
		HashMap<String, Country> map = APIHandler.retrieveCountries();
		assertTrue(map.containsKey(code1));
		assertEquals(country1.getName(), map.get(code1).getName());
		assertFalse(map.containsKey(code2));
	}
	
	@Test
	@DisplayName("retrieveServiceTypes fail testing: test fail if the method return a null pointer or an ArrayList of size 0")
	void testRetrieveServiceTypesFail() {
		assertNotNull(APIHandler.retriveServiceTypes());
		assertNotEquals(0, APIHandler.retriveServiceTypes().size());
	}
	
	@Test
	@DisplayName("retrieveServiceTypes testing: return true if the ArrayList contains pair1")
	void testRetriveServiceTypes() {
		ArrayList<Pair<String,String>> temp = APIHandler.retriveServiceTypes();
		assertTrue(temp.contains(pair1));
		assertFalse(temp.contains(pair2));
	}

	@Test
	@DisplayName("retrieveServiceTypes fail testing: test is passed if the method do not return a null pointer with a reasonable parameter")
	void testRetriveCountryDataFail() {
		assertNotNull(APIHandler.retriveCountryData(code1));
		assertNull(APIHandler.retriveCountryData(code2));
	}
	@Disabled
	@Test
	void testRetrieveCountryData() {
		HashSet<Provider> set = new HashSet();
		Provider provider1 = new Provider(providerName1, country1);
		Country country2 = APIHandler.retriveCountryData(code1);
		//asserEquals(prov)
	}

}
