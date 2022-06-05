package proseccoCoding.TLN.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javafx.util.Pair;

class TrustedListDataTest<E> {

	private static TrustedListData tld = new TrustedListData();

	@DisplayName("GetCountry fail testing: if parameter is null must throw exception")
	@Test
	void testGetCountryFail() {
		assertThrows(IllegalArgumentException.class,()->{tld.getCountry(null);});
	}
	@DisplayName("GetCountry testing: if parameter is blanck or not found must return null")
	@Test
	void testGetCountry() {
		assertNull(tld.getCountry(new String()));
		assertNull(tld.getCountry(new String("ww")));
	}
	@DisplayName("GetCountry testing: if parameter is ok must return the associated retrived object")
	@ParameterizedTest
	@ValueSource(strings = {"IT", "at", "fr", "DE"})
	void testGetCountry2(String countryCode) {
		Country test = tld.getCountry(countryCode);
		assertNotNull(test);
		assertTrue(test.isRetrieved());
	}

	@DisplayName("GetCountries fail testing: if null parameter is passed, must throw exception")
	@Test
	void testGetCountriesFail() {
		assertThrows(IllegalArgumentException.class,()->{tld.getCountries(null);});
	}
	@DisplayName("GetCountries fail testing: if empty parameter is passed or one of the strings isn't a valid country code, must return null")
	@Test
	void testGetCountriesFail2() {
		ArrayList<String> input = new ArrayList<String>();
		assertNull(tld.getCountries(input));
		input.add("IT");
		input.add("de");
		input.add("ww");
		assertNull(tld.getCountries(input));
	}
	@DisplayName("GetCountries testing: if ok parameter is passed, must return a list of retrieved countries")
	@Test
	void testGetCountries() {
		ArrayList<String> input = new ArrayList<String>();
		input.add("IT");
		input.add("de");
		input.add("fr");
		ArrayList<Country> output = tld.getCountries(input);
		assertNotNull(output);
		for(Country c : output)
			assertTrue(c.isRetrieved());
		
	}

	@DisplayName("PrintCountries testing: must return a list with all countries code and name")
	@Test
	void testPrintCountries() {
		ArrayList<Pair<String, String>> output = tld.printCountries();
		ArrayList<String> countryNames = APIHandler.retrieveCountriesNames();
		assertNotNull(output);
		
		assertEquals(output.size(), countryNames.size());
		for(Pair<String, String> p : output)
			assertTrue(countryNames.contains(p.getValue()));
	}

}
