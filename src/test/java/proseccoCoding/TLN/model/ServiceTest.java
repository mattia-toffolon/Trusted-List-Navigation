
package proseccoCoding.TLN.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServiceTest {

	@Test
	@DisplayName("Constructor testing: in case of a null parameter must throw an exception")
	void testService() {
		ArrayList<ServiceType> types = new ArrayList<ServiceType>();
		assertThrows(IllegalArgumentException.class, () -> {new Service("test", (ServiceType)null, "serv", null);});
		assertThrows(IllegalArgumentException.class, () -> {new Service(null, ServiceType.getInstance("aa"), "serv", null);});
		types.add(ServiceType.getInstance("aa"));
		types.add(ServiceType.getInstance("bb"));
		assertDoesNotThrow(() -> {new Service("test", types, "serv", null);});
	}
}