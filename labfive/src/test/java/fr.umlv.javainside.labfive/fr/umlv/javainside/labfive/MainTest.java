package fr.umlv.javainside.labfive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MainTest {

	@Test
	void testPersonToJson() {
		var person = new Person("John", "Doe");
		assertEquals("{\n" + 
				"	\"firstName\": \"John\",\n" + 
				"	\"lastName\": \"Doe\",\n" + 
				"	\"class\": \"class fr.umlv.javainside.labfive.Person\"\n" + 
				"}", Main.toJSON(person));
	}
	
	@Test
	void testAlienToJson() {
		var alien = new Alien("terre", 333);
		assertEquals("{\n" + 
				"	\"planet\": \"terre\",\n" + 
				"	\"age\": \"333\",\n" + 
				"	\"class\": \"class fr.umlv.javainside.labfive.Alien\"\n" + 
				"}", Main.toJSON(alien));
	}

}
