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
}
