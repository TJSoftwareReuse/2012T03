package tj.reuse;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConfigComponentTest {
	
	private ConfigComponent cm;

	@Before
	public void setUp() throws Exception {
		cm = new ConfigComponent();
	}

	@After
	public void tearDown() throws Exception {
		cm = null;
	}

	@Test
	public void testReadValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadProperties() {
		fail("Not yet implemented");
	}

	@Test
	public void testWriteProperties() {
		fail("Not yet implemented");
	}

}
