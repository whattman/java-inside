package fr.umlv.javainside.labfive;

import static java.util.stream.Collectors.joining;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
	private static class CachedInfos {
		private final Method method;
		private final String propertiesName;
		
		public CachedInfos(Method method, String propertiesName) {
			this.method = method;
			this.propertiesName = propertiesName;
		}
	}
	private static ClassValue<List<CachedInfos>> cache = new ClassValue<List<CachedInfos>>() {
		@Override
		protected List<CachedInfos> computeValue(Class<?> arg0) {
			return Arrays.stream(arg0.getMethods())
					.filter(method -> method.isAnnotationPresent(JSONProperty.class))
					.map(method -> new CachedInfos(method, getmethodNameOrAlternativeName(method)))
					.collect(Collectors.toList());
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
	private static String getmethodNameOrAlternativeName(Method method) {
		var alterativeName = method.getAnnotation(JSONProperty.class).alterativeName();
		return alterativeName.equals("") ? propertyName(method.getName()) : alterativeName;
	}
	public static String toJSON(Object obj) {
		return cache.get(obj.getClass()).stream()
			.map(cachedInfo -> formatJSONLine(obj, cachedInfo))
			.collect(joining(",\n", "{\n", "\n}"));
	}
	private static String formatJSONLine(Object obj, CachedInfos ci) {
		return "\t\"" + ci.propertiesName + "\": \"" + doInvoke(ci.method, obj) + "\"";
	}
	public static void main(String[] args) {
	    var person = new Person("John", "Doe");
	    System.out.println(toJSON((Object)person));
	}
}
