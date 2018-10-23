package fr.umlv.javainside.labfive;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;


public class Main {
//	public static String toJSON(Person person) {
//	    return
//	        "{\n" +
//	        "  \"firstName\": \"" + person.getFirstName() + "\"\n" +
//	        "  \"lastName\": \"" + person.getLastName() + "\"\n" +
//	        "}\n";
//	}
//
//	public static String toJSON(Alien alien) {
//		return 
//	        "{\n" + 
//	        "  \"planet\": \"" + alien.getPlanet() + "\"\n" + 
//	        "  \"members\": \"" + alien.getAge() + "\"\n" + 
//	        "}\n";
//	}
	
	private static String propertyName(String name) {
		return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }
	private static Object doInvoke(Method m, Object obj) {
		try {
			return m.invoke(obj);
		} catch (InvocationTargetException e) {
			var cause = e.getCause();
			if (cause instanceof RuntimeException) {
				throw (RuntimeException)cause; 
			}
			if (cause instanceof Exception) {
				throw new UndeclaredThrowableException(cause);
			}	
			else {
				throw (Error)cause; 
			}
		} catch (IllegalAccessException e) {
			throw new AssertionError(e);
		}
	}
	public static String toJSON(Object obj) {
		return Arrays.stream(obj.getClass().getMethods())
		    .filter(e -> e.getName().startsWith("get"))
			.map(e -> "\t\"" + Main.propertyName(e.getName()) + "\": \"" + Main.doInvoke(e, obj) + "\"")
			.collect(Collectors.joining(",\n", "{\n", "\n}"));
	}
	
	public static void main(String[] args) {
	    var person = new Person("John", "Doe");
	    System.out.println(toJSON((Object)person));
	}
}
