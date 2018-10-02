package fr.umlv.javainside.labTwo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SwitchesTests {

	
	@Test
	void intSwitchtest() {
		assertEquals("one", Switches.intSwitch(1));
		assertEquals("a lot", Switches.intSwitch(2));
		assertEquals("zero", Switches.intSwitch(0));
		assertThrows(IllegalArgumentException.class, () -> Switches.intSwitch(-1));
	}

}
