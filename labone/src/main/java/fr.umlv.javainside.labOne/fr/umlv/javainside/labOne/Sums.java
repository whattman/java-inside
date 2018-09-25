package fr.umlv.javainside.labOne;

import java.util.stream.IntStream;

public class Sums {
	public static int loopSum(int n) {
		int compteur = 0;
		for (int i = 0; i <= n; i++) {
			compteur+=i;
		}
		return compteur;
	}
	public static int streamSum(int n) {
		return IntStream.range(0, n+1).sum();
	}
}
