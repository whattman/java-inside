package fr.umlv.javainside.labfour;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Scheduler {
	private final ArrayList<Continuation> queue;
	private final Politique politique;

	static enum Politique{
		STACK, FIFO, RANDOM;
		static int getPos(Politique p, int size) {
			switch(p) {
				case STACK: 
					return size - 1;
				case FIFO:
					return 0;
				case RANDOM:
					return ThreadLocalRandom.current().nextInt(0, size);
				default:
					throw new IllegalArgumentException();
			}
		}
	}
	public Scheduler(Politique politique) {
		this.politique = politique;
		this.queue = new ArrayList<>();
	}
	public void enqueue(ContinuationScope cs) {
		Continuation c = Continuation.getCurrentContinuation(cs);
		if (null == c) throw new IllegalStateException("No current continuation");
		queue.add(c);
		Continuation.yield(cs);
	}
	public void runLoop() {
		while(!queue.isEmpty()) {
			queue.remove(Politique.getPos(politique, queue.size())).run();
		}
	}
	public Scheduler add(Continuation c) {
		queue.add(c);
		return this;
	}
	public static void main(String[] args) {
		Scheduler scheduler = new Scheduler(Politique.RANDOM);
		ContinuationScope continuationScope = new ContinuationScope("hello1");
		Continuation c = new Continuation(continuationScope, () -> {
			scheduler.enqueue(continuationScope);		
			System.out.println("hello1 continuation Debut");
			
			scheduler.enqueue(continuationScope);	
			System.out.println("hello1 continuation Milieu");
			
			scheduler.enqueue(continuationScope);		
			System.out.println("hello1 continuation Fin");
		});
		
		Continuation c2 = new Continuation(continuationScope, () -> {
			scheduler.enqueue(continuationScope);		
			System.out.println("hello2 continuation Debut");
			
			scheduler.enqueue(continuationScope);		
			System.out.println("hello2 continuation Fin");
		});
		
		Continuation c3 = new Continuation(continuationScope, () -> {
			scheduler.enqueue(continuationScope);		
			System.out.println("hello3 continuation Debut");
			
			scheduler.enqueue(continuationScope);		
			System.out.println("hello3 continuation Fin");
			scheduler.enqueue(continuationScope);		
			System.out.println("hello3 continuation Fin");
			scheduler.enqueue(continuationScope);		
			System.out.println("hello3 continuation Fin");
			scheduler.enqueue(continuationScope);		
			System.out.println("hello3 continuation Fin");
		});
		scheduler.add(c).add(c2).add(c3).runLoop();
	}
}
