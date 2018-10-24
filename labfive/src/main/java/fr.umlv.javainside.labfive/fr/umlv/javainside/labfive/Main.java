package fr.umlv.javainside.labfive;

import static java.util.stream.Collectors.joining;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;

public class Main {
	private static ClassValue<Method[]> cache = new ClassValue<Method[]>() {
		@Override
		protected Method[] computeValue(Class<?> arg0) {
			return arg0.getMethods();
		}
	};
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
		var alterativeName = m.getAnnotation(JSONProperty.class).alterativeName();
		return alterativeName.equals("") ? propertyName(m.getName()) : alterativeName;
	}
	public static String toJSON(Object obj) {
		return Arrays.stream(cache.get(obj.getClass()))
		    .filter(method -> method.isAnnotationPresent(JSONProperty.class))
			.map(method -> formatJSONLine(obj, method))
			.collect(joining(",\n", "{\n", "\n}"));
	}
	private static String formatJSONLine(Object obj, Method method) {
		return "\t\"" + getmethodNameOrAlternativeName(method) + "\": \"" + doInvoke(method, obj) + "\"";
	}
	
	public static void main(String[] args) {
	    var person = new Person("John", "Doe");
	    System.out.println(toJSON((Object)person));
	}
}
