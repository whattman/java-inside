package fr.umlv.javainside.labfive;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.stream.Collectors;


public class Main {
	
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
			throw (Error)cause; 
		} catch (IllegalAccessException e) {
			throw new AssertionError(e);
		}
	}
	private static String getmethodNameOrAlternativeName(Method m) {
		return (m.getAnnotation(JSONProperty.class).alterativeName().equals("") ? Main.propertyName(m.getName()) : m.getAnnotation(JSONProperty.class).alterativeName());
	}
	public static String toJSON(Object obj) {
		return Arrays.stream(obj.getClass().getMethods())
		    .filter(e -> e.getName().startsWith("get") && e.getParameterCount() == 0 && e.isAnnotationPresent(JSONProperty.class))
			.map(e -> "\t\"" + Main.getmethodNameOrAlternativeName(e) + "\": \"" + Main.doInvoke(e, obj) + "\"")
			.collect(Collectors.joining(",\n", "{\n", "\n}"));
	}
	
	public static void main(String[] args) {
	    var person = new Person("John", "Doe");
	    System.out.println(toJSON((Object)person));
	}
}
