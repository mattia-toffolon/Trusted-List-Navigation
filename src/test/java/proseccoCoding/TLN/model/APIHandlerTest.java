package proseccoCoding.TLN.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class APIHandlerTest {

	@Disabled
	@Test
	void testAPIHandler() {
		fail("Not yet implemented");
	}
	@Disabled
	@Test
	void testRetrieveCountriesNames() {
		fail("Not yet implemented");
	}

	@Disabled
	@Test
	void testRetrieveCountries() {
		fail("Not yet implemented");
	}

	@Disabled
	@Test
	void testRetriveServiceTypes() {
		fail("Not yet implemented");
	}

	@Test
	void testRetriveCountryData() {
		assertNotNull(APIHandler.retriveCountryData("IT"));
	}

}
