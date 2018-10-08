package fr.umlv.javainside.labTwo;

public class Switches {
	public static String intSwitch(int nombre) {
		switch(nombre) {
			case 0 : return "zero";
			case 1 : return "one";
			case 2 : return "a lot";
			default : throw new IllegalArgumentException("number should be 0, 1 or 2. Having : " + nombre);
		}
	}
	public static String intSwitch2(int nombre) {
		switch(nombre) {
			case 0 : return "zero";
			case 10 : return "one";
			case 100 : return "a lot";
			default : throw new IllegalArgumentException("number should be 0, 1 or 2. Having : " + nombre);
		}
	}
}
