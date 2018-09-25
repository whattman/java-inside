package fr.umlv.javainside.labOne;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
public class SumsTests {
	@Test
	void testStreamSums() {
		assertEquals(0, Sums.streamSum(0));
		assertEquals(6, Sums.streamSum(3));
		assertEquals(10, Sums.streamSum(4));
		assertEquals(15, Sums.streamSum(5));
		assertEquals(21, Sums.streamSum(6));
		assertEquals(28, Sums.streamSum(7));
		assertEquals(36, Sums.streamSum(8));
	}
	@Test
	void testLoopSums() {
		assertEquals(0, Sums.streamSum(0));
		assertEquals(6, Sums.loopSum(3));
		assertEquals(10, Sums.loopSum(4));
		assertEquals(15, Sums.loopSum(5));
		assertEquals(21, Sums.loopSum(6));
		assertEquals(28, Sums.loopSum(7));
		assertEquals(36, Sums.loopSum(8));
	}
}
