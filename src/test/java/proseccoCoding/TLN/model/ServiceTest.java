
package proseccoCoding.TLN.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServiceTest {

	@Test
	@DisplayName("Constructor testing: in case of a null parameter must throw an exception")
	void testService() {
		assertThrows(IllegalArgumentException.class, () -> {new Service("test", null, "serv");});
		assertThrows(IllegalArgumentException.class, () -> {new Service(null, new ServiceType("code", "type"), "serv");});
		assertThrows(IllegalArgumentException.class, () -> {new Service("test", new ServiceType("code", "type"), null);});
	}
}