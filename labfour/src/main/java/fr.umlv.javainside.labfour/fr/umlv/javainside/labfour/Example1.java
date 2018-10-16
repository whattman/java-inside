package fr.umlv.javainside.labfour;

public class Example1 {
	public static void main(String[] args) {
		ContinuationScope continuationScope = new ContinuationScope("hello1");
		Continuation c = new Continuation(continuationScope, () -> {
			Continuation.yield(continuationScope);
			System.out.println("hello continuation");
		});
		c.run();
		c.run();
	}
}
