package proseccoCoding.TLN.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javafx.util.Pair;

class APIHandlerTest {
	
	static String name1;
	static String name2;
	static String code1;
	static String code2;
	static Country country1;
	static String typeServiceCode1;
	static String typeServiceName1;
	static Pair<String,String> pair1;
	static String typeServiceCode2;
	static String typeServiceName2;
	static Pair<String,String> pair2;
	static Provider provider1;
	static String providerName1;
	static Service service1;
	
	@BeforeEach
	void setUp() {
		name1 = "Austria";
		name2 = "Mexico";
		code1 = "AT";
		code2 = "ME";
		country1 = new Country(code1, name1);
		typeServiceCode1 = "QCertESig";
		typeServiceName1 = "Qualified certificate for electronic signature";
		typeServiceCode2 = "AAA";
		typeServiceName2 = "BBB";
		pair1 = new Pair<String,String>(typeServiceCode1,typeServiceName1);
		pair2 = new Pair<String,String>(typeServiceCode2,typeServiceName2);
		providerName1 = "A-Trust Gesellschaft für Sicherheitssysteme im elektronischen Datenverkehr GmbH";
		
		//country1.addProvider(provider1);
		
		
				
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
	@DisplayName("retrieveCountryData fail testing: test is passed if the method do not return a null pointer with a reasonable parameter"
			+ "and if the providers pointer is not null")
	void testRetriveCountryDataFail() {
		assertNotNull(APIHandler.retriveCountryData(code1));
		assertNull(APIHandler.retriveCountryData(code2));
		assertNotNull(APIHandler.retriveCountryData(code1).getProviders());
		assertNotNull((APIHandler.retriveCountryData(code1).getProviders()).get(0).getServiceTypes());
	}
	
	@Test
	@DisplayName("retrieveCountryData testing:")
	void retrieveCountryData() {
		country1 = APIHandler.retriveCountryData(country1.getCode());
		assertEquals(providerName1, country1.getProviders().get(2).getName());
		System.out.println(country1.getProviders().get(2).getName());
		ServiceType serviceType1 = ServiceType.getInstance(typeServiceCode1);
		Service service1 = new Service("TrustSign-Sig-01 (key no. 1)",
				serviceType1, "withdraw", country1.getProviders().get(2));
		System.out.println(service1.getProvider().getName());
		ArrayList<ServiceType> tipp = country1.getProviders().get(2).getServiceTypes();
		System.out.println(tipp.size());
		//for(Service e : tipp)
			//System.out.println(e.getName());
		assertFalse(country1.getProviders().get(2).addService(service1));
	}

}

