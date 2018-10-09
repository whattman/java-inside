package fr.umlv.javainside.labTwo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.umlv.javainside.labTwo.Switches.EnumSwitch;



class SwitchesTests {
	static class TestData {
		IntFunction function;
		int parameter;
		String expectedReturn;
		
		public TestData(IntFunction function,int parameter, String expectedReturn) {
			this.function = function;
			this.parameter = parameter;
			this.expectedReturn = expectedReturn;
		}
		public IntFunction getFunction() {
			return function;
		}
		public int getParameter() {
			return parameter;
		}
		public String getExpectedReturn() {
			return expectedReturn;
		}
	}
	
	@ParameterizedTest
	@MethodSource("testDataProvider")
	void intSwitchtest(TestData function) {
		assertEquals(function.expectedReturn, function.getFunction().apply(function.getParameter()));
	}
	@ParameterizedTest
	@MethodSource("getIntFunction")
	void intSwitchtestError(IntFunction<String> function) {
		assertThrows(IllegalArgumentException.class, () -> function.apply(-1));
	}
	
	@Test
	void StringSwitchtest() {
		assertEquals("zero", Switches.StringSwitch("foo"));
		assertEquals("one", Switches.StringSwitch("bar"));
		assertEquals("a lot", Switches.StringSwitch("baz"));
		assertEquals("zero", Switches.StringSwitch("viva zorg"));
	}
	@Test
	void StringSwitchtestError() {
		assertThrows(IllegalArgumentException.class, () -> Switches.StringSwitch("Toto"));
	}
	@Test
	void EnumSwitchtest() {
		assertEquals("zero", Switches.enumSwitch(EnumSwitch.DEBUG));
		assertEquals("one", Switches.enumSwitch(EnumSwitch.WARNING));
		assertEquals("a lot", Switches.enumSwitch(EnumSwitch.INFO));
		assertEquals("zero", Switches.enumSwitch(EnumSwitch.ERROR));
	}
	static Stream<TestData> testDataProvider() {
	    List<TestData> lst = List.of(new TestData(Switches::intSwitch, 0, "zero"), new TestData(Switches::intSwitch, 3, "zero"), new TestData(Switches::intSwitch2, 3, "zero"),new TestData(Switches::intSwitch, 1, "one"), new TestData(Switches::intSwitch, 2, "a lot"), new TestData(Switches::intSwitch2, 0, "zero"), new TestData(Switches::intSwitch2, 10, "one"), new TestData(Switches::intSwitch2, 100, "a lot"));
	    return lst.stream();
	}
	static Stream<IntFunction> getIntFunction() {
		List<IntFunction> lst = List.of(Switches::intSwitch, Switches::intSwitch2);
		return lst.stream();
	}
}
