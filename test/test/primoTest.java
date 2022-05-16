package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class primoTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("before");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("after");
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
