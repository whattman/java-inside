package fr.umlv.javainside.labthree;

public class ExprSwitches {
	public static enum EnumSwitch {
		DEBUG, WARNING, INFO, ERROR
	}
	
	public static String intSwitch(int nombre) {
		switch(nombre) {
			case 3:
			case 0 : return "zero";
			case 1 : return "one";
			case 2 : return "a lot";
			default : throw new IllegalArgumentException("number should be 0, 1 or 2. Having : " + nombre);
		}
	}
	public static String intSwitch2(int nombre) {
		switch(nombre) {
			case 3: 
			case 0 : return "zero";
			case 10 : return "one";
			case 100 : return "a lot";
			default : throw new IllegalArgumentException("number should be 0, 1 or 2. Having : " + nombre);
		}
	}
	public static String StringSwitch(String index) {
		switch(index) {
			case "viva zorg" :
			case "foo": return "zero";
			case "bar" : return "one";
			case "baz" : return "a lot";
			default : throw new IllegalArgumentException("number should be foo, bar, baz or viva zorg. Having : " + index);
		}
	}
	public static String enumSwitch(EnumSwitch e) {
		switch(e) {
			case ERROR :
			case DEBUG: return "zero";
			case WARNING : return "one";
			case INFO : return "a lot";
			default: throw new IllegalArgumentException("number should be foo, bar, baz or viva zorg. Having : " + e);
		}
	}
	
	public static String exprIntSwitch(int nombre) {
		return switch(nombre) {
			case 3, 0 -> "zero";
			case 1 -> "one";
			case 2 -> "a lot";
			default -> throw new IllegalArgumentException("number should be 0, 1 or 2. Having : " + nombre);
		};
	}
	public static String exprIntSwitch2(int nombre) {
		return switch(nombre) {
			case 3, 0 -> "zero";
			case 10 -> "one";
			case 100 -> "a lot";
			default -> throw new IllegalArgumentException("number should be 0, 1 or 2. Having : " + nombre);
		};
	}
	public static String exprStringSwitch(String index) {	
		return switch(index) {
			case "viva zorg", "foo" -> "zero";
			case "bar" -> "one";
			case "baz" -> "a lot";
			default -> throw new IllegalArgumentException("number should be foo, bar, baz or viva zorg. Having : " + index);
		};
	}
	public static String exprEnumSwitch(EnumSwitch e) {
		return switch(e) {
			case ERROR, DEBUG -> "zero";
			case WARNING -> "one";
			case INFO -> "a lot";
			default -> throw new IllegalArgumentException("number should be foo, bar, baz or viva zorg. Having : " + e);
		};
	}
	
}
