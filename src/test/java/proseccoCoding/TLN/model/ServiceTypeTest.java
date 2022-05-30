package proseccoCoding.TLN.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import javafx.util.Pair;

class ServiceTypeTest {
	
	@DisplayName("GetInstance fail testing: if a null or blanck code is passed must throw an excepiton")
	@ParameterizedTest
	@NullSource
	@EmptySource
	void testGetInstanceFail(String code) {
		assertThrows(IllegalArgumentException.class, () -> {ServiceType.getInstance(code);});
	}
	
	@DisplayName("GetInstance testing: must return an instance for the code")
	@ParameterizedTest
	@ValueSource(strings = {"type1", "type2", "type3"})
	void testGetInstance(String code) {
		assertEquals(code, ServiceType.getInstance(code).getCode());
	}
	
	@DisplayName("GetInstance testing: the instance for a given code must be a singleton: for the same code must return the same istance")
	@ParameterizedTest
	@ValueSource(strings = {"type1", "type2", "type3"})
	void testGetInstance2(String code) {
		ServiceType a = ServiceType.getInstance(code);
		ServiceType b = ServiceType.getInstance(code);
		// check the references to objects, not equals() method
		assertTrue(a == b);
	}
	
	@DisplayName("PrintServiceTypes testing: should return the list of pairs of all known types")
	@Test
	void testPrintServiceTypes() {
		ArrayList<String> vals = new ArrayList<String>();
		vals.add("aa");
		vals.add("bb");
		vals.add("cc");
		vals.add("type1");
		vals.add("type2");
		vals.add("type3");
		vals.addAll(APIHandler.retriveServiceTypes().stream()
				.map((Pair<String, String> p)->{return p.getKey();})
				.collect(Collectors.toList()));
		ServiceType.init();
		for (String string : vals)
			ServiceType.getInstance(string);
		ArrayList<Pair<String,String>> ret = ServiceType.printServiceTypes();

		assertEquals(ret.size(), vals.size());
		
		for (Pair<String, String> pair : ret) {
			assertTrue(vals.contains(pair.getKey()));
		}
	}
}
