package fr.umlv.javainside.labsix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.UndeclaredThrowableException;

public class Example {
	private static final MethodHandle NOT_FOUND;
	private static final MethodHandle FIND_VIRTUAL;
	static {
		try {
			FIND_VIRTUAL = MethodHandles.lookup().findVirtual(String.class, "equals", MethodType.methodType(boolean.class, Object.class));
			var tmp_not_found = MethodHandles.constant(int.class, -1);
			NOT_FOUND = MethodHandles.dropArguments(tmp_not_found, 0, String.class);
		} catch (ReflectiveOperationException e) {
			throw new UndeclaredThrowableException(e);
		}
	}
	

	private static String aStaticHello(int value) {
	    return "question " + value;
    }
	private String anInstanceHello(int value) {
		return "question " + value;
	}
	
	public static MethodHandle stringSwitch(String... matches) throws ReflectiveOperationException{
		var MethodHandleSwitch = NOT_FOUND;
		for (var i = 0; i < matches.length; i++) {
			var methodHandleFound = MethodHandles.constant(int.class, i);
			methodHandleFound = MethodHandles.dropArguments(methodHandleFound, 0, String.class);
			var MethodHandleTest = FIND_VIRTUAL;
			MethodHandleTest = MethodHandles.insertArguments(MethodHandleTest, 1, matches[i]);
			MethodHandleSwitch = MethodHandles.guardWithTest(MethodHandleTest, methodHandleFound, MethodHandleSwitch);
		}
		
		return MethodHandleSwitch;
	}
		   
}
