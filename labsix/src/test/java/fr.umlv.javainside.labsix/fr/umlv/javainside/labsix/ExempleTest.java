package fr.umlv.javainside.labsix;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

import org.junit.jupiter.api.Test;

class ExempleTest {

	@Test
	void testReflectionAStaticHello() throws ReflectiveOperationException {
			var method = Example.class.getDeclaredMethod("aStaticHello", int.class);
			method.setAccessible(true);
			assertEquals("question 1", method.invoke(null, 1));
	}

	@Test
	void testReflectionAnInstanceHello() throws ReflectiveOperationException {
			var method = Example.class.getDeclaredMethod("anInstanceHello", int.class);
			method.setAccessible(true);
			var example = new Example();
			assertEquals("question 1", method.invoke(example, 1));
	}
	
	@Test
	void testLookupAStaticHello() throws Throwable{
		var methodHandle = MethodHandles.privateLookupIn(Example.class, MethodHandles.lookup())
				.findStatic(Example.class, "aStaticHello", MethodType.methodType(String.class, int.class));
		assertEquals("question 1", (String)methodHandle.invokeExact(1));
	}

	@Test
	void testLookupAnInstanceHello() throws Throwable {
		var methodHandle = MethodHandles
				.privateLookupIn(Example.class, MethodHandles.lookup())
				.findVirtual(Example.class, "anInstanceHello", MethodType.methodType(String.class, int.class));
		var example = new Example();
		assertEquals("question 1", (String)methodHandle.invokeExact(example, 1));
	}
	
	@Test
	void testLookupInsertArgumentsOnAnInstanceHello() throws Throwable {
		var methodHandle = MethodHandles.privateLookupIn(Example.class, MethodHandles.lookup())
				.findVirtual(Example.class, "anInstanceHello", MethodType.methodType(String.class, int.class));
		methodHandle = MethodHandles.insertArguments(methodHandle, 1, 8);
		var example = new Example();
		assertEquals("question 8", (String)methodHandle.invokeExact(example));
	}

	@Test
	void testLookupDropArgumentsOnAnInstanceHello() throws Throwable {
		assertThrows(WrongMethodTypeException.class, () -> {
			var methodHandle = MethodHandles.privateLookupIn(Example.class, MethodHandles.lookup())
					.findVirtual(Example.class, "anInstanceHello", MethodType.methodType(String.class, int.class));
			methodHandle = MethodHandles.insertArguments(methodHandle, 1, 8);
			var example = new Example();
			methodHandle = MethodHandles.dropArguments(methodHandle, 1, int.class);
			methodHandle.invokeExact(example);});
	}
	// diff reflexion invokation
	/*
	 * reflexion : permet de creer un objet methode sur meth private, invoke plante si on a pas fait setAccessible
	 * invokation : quand on fait find sur methode private, on plante. Quand on invokeExact, on ne refait pas les tests de securité.
	 */
	@Test
	void testLookupUnboxingAnInstanceHello() throws Throwable {
		var methodHandle = MethodHandles.privateLookupIn(Example.class, MethodHandles.lookup())
				.findVirtual(Example.class, "anInstanceHello", MethodType.methodType(String.class, int.class));
		methodHandle = methodHandle.asType(MethodType.methodType(String.class, Example.class, Integer.class));
		var example = new Example();
		assertEquals("question 1", (String)methodHandle.invokeExact(example, Integer.valueOf(1)));
	}
	
	@Test 
	void testMethodHandlesConstant() throws Throwable {
		var methodHandle = MethodHandles.constant(String.class, "question 1");
		assertEquals("question 1", (String)methodHandle.invokeExact());
	}
	
	@Test 
	void testMethodHandlesGuardWithTest() throws Throwable {
		var methodHandle1 = MethodHandles.constant(int.class, 1);
		methodHandle1 = MethodHandles.dropArguments(methodHandle1, 0, String.class);
		var methodHandle2 = MethodHandles.constant(int.class, -1);
		methodHandle2 = MethodHandles.dropArguments(methodHandle2, 0, String.class);
		
		var methodHandleTest = MethodHandles.lookup().findVirtual(String.class, "equals", MethodType.methodType(boolean.class, Object.class));
		methodHandleTest = MethodHandles.insertArguments(methodHandleTest, 1, "foo");
		var chosenMethod = MethodHandles.guardWithTest(methodHandleTest, methodHandle1, methodHandle2);
		assertEquals(1, (int)chosenMethod.invokeExact("foo"));
		assertEquals(-1, (int)chosenMethod.invokeExact("fooo"));
	}
	
	@Test 
	void testStringSwitchTest() throws Throwable {
		var mh = Example.stringSwitch("foo", "bar", "bazz");
	    assertEquals(0, (int)mh.invokeExact("foo"));
	    assertEquals(1, (int)mh.invokeExact("bar"));
	    assertEquals(-1, (int)mh.invokeExact("booze"));
	}
}
