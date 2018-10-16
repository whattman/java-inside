package fr.umlv.javainside.labfour;

public class Example1 {
	public static void main(String[] args) {
		ContinuationScope continuationScope = new ContinuationScope("hello1");
		Runnable r = () -> {
			Continuation.yield(continuationScope);		
			System.out.println("hello continuation Debut");
			
			Continuation.yield(continuationScope);		
			System.out.println("hello continuation Milieu");
			
			Continuation.yield(continuationScope);		
			System.out.println("hello continuation Fin");
		};
		Continuation c = new Continuation(continuationScope, r);
		
		Continuation c2 = new Continuation(continuationScope, r);
		while(!c.isDone() || !c2.isDone()) {
			if (!c.isDone()) c.run();
			if (!c2.isDone()) c2.run();
		}
	}
}
