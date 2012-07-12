package nl.runnable.lims.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link PersonName}.
 * 
 * @author laurens
 * 
 */
public class PersonNameTest {

	private List<PersonName> personNames;

	private PersonName zachariasVanderAa;

	private PersonName adriaanVanderAa;

	private PersonName janJanssen;

	@Before
	public void createPersonNames() {
		zachariasVanderAa = new PersonName("Zacharias", "van der", "Aa");
		adriaanVanderAa = new PersonName("Adriaan", "van der", "Aa");
		janJanssen = new PersonName("Jan", "Janssen");

		personNames = new ArrayList<PersonName>();
		personNames.add(janJanssen);
		personNames.add(zachariasVanderAa);
		personNames.add(adriaanVanderAa);
	}

	@Test
	public void testOrdering() {
		Collections.sort(personNames);
		assertEquals(adriaanVanderAa, personNames.get(0));
		assertEquals(zachariasVanderAa, personNames.get(1));
		assertEquals(janJanssen, personNames.get(2));
	}
}
