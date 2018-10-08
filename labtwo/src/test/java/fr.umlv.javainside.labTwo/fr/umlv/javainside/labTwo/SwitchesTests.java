package fr.umlv.javainside.labTwo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;



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
	static Stream<TestData> testDataProvider() {
	    List<TestData> lst = List.of(new TestData(Switches::intSwitch, 0, "zero"), new TestData(Switches::intSwitch, 1, "one"), new TestData(Switches::intSwitch, 2, "a lot"), new TestData(Switches::intSwitch2, 0, "zero"), new TestData(Switches::intSwitch2, 10, "one"), new TestData(Switches::intSwitch2, 100, "a lot"));
	    return lst.stream();
	}
	static Stream<IntFunction> getIntFunction() {
		List<IntFunction> lst = List.of(Switches::intSwitch, Switches::intSwitch2);
		return lst.stream();
	}
}
