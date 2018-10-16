package fr.umlv.javainside.labthree;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.umlv.javainside.labthree.ExprSwitches.EnumSwitch;



class ExprSwitchesTests {
	static class TestData {
		IntFunction function;
		int parameter;
		String expectedReturn;
		
		public TestData(IntFunction function, int parameter, String expectedReturn) {
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
		assertEquals("zero", ExprSwitches.StringSwitch("foo"));
		assertEquals("one", ExprSwitches.StringSwitch("bar"));
		assertEquals("a lot", ExprSwitches.StringSwitch("baz"));
		assertEquals("zero", ExprSwitches.StringSwitch("viva zorg"));
	}
	@Test
	void StringSwitchtestError() {
		assertThrows(IllegalArgumentException.class, () -> ExprSwitches.StringSwitch("Toto"));
	}
	@Test
	void EnumSwitchtest() {
		assertEquals("zero", ExprSwitches.enumSwitch(EnumSwitch.DEBUG));
		assertEquals("one", ExprSwitches.enumSwitch(EnumSwitch.WARNING));
		assertEquals("a lot", ExprSwitches.enumSwitch(EnumSwitch.INFO));
		assertEquals("zero", ExprSwitches.enumSwitch(EnumSwitch.ERROR));
	}

	// Nouveaux switchs
	
	@Test
	void StringSwitchNewtest() {
		assertEquals("zero", ExprSwitches.exprStringSwitch("foo"));
		assertEquals("one", ExprSwitches.exprStringSwitch("bar"));
		assertEquals("a lot", ExprSwitches.exprStringSwitch("baz"));
		assertEquals("zero", ExprSwitches.exprStringSwitch("viva zorg"));
	}
	@Test
	void StringSwitchNewtestError() {
		assertThrows(IllegalArgumentException.class, () -> ExprSwitches.StringSwitch("Toto"));
	}
	@Test
	void EnumSwitchNewtest() {
		assertEquals("zero", ExprSwitches.exprEnumSwitch(EnumSwitch.DEBUG));
		assertEquals("one", ExprSwitches.exprEnumSwitch(EnumSwitch.WARNING));
		assertEquals("a lot", ExprSwitches.exprEnumSwitch(EnumSwitch.INFO));
		assertEquals("zero", ExprSwitches.exprEnumSwitch(EnumSwitch.ERROR));
	}
	
	
	// Methodes providers
	static Stream<TestData> testDataProvider() {
	    List<TestData> lst = List.of(new TestData(ExprSwitches::intSwitch, 0, "zero"), new TestData(ExprSwitches::intSwitch, 3, "zero"), new TestData(ExprSwitches::intSwitch2, 3, "zero"),new TestData(ExprSwitches::intSwitch, 1, "one"), new TestData(ExprSwitches::intSwitch, 2, "a lot"), new TestData(ExprSwitches::intSwitch2, 0, "zero"), new TestData(ExprSwitches::intSwitch2, 10, "one"), new TestData(ExprSwitches::intSwitch2, 100, "a lot"), new TestData(ExprSwitches::exprIntSwitch, 0, "zero"), new TestData(ExprSwitches::exprIntSwitch, 3, "zero"), new TestData(ExprSwitches::exprIntSwitch2, 3, "zero"),new TestData(ExprSwitches::exprIntSwitch, 1, "one"), new TestData(ExprSwitches::exprIntSwitch, 2, "a lot"), new TestData(ExprSwitches::exprIntSwitch2, 0, "zero"), new TestData(ExprSwitches::exprIntSwitch2, 10, "one"), new TestData(ExprSwitches::exprIntSwitch2, 100, "a lot"));
	    return lst.stream();
	}
	static Stream<IntFunction> getIntFunction() {
		List<IntFunction> lst = List.of(ExprSwitches::intSwitch, ExprSwitches::intSwitch2, ExprSwitches::exprIntSwitch, ExprSwitches::exprIntSwitch2);
		return lst.stream();
	}
}
