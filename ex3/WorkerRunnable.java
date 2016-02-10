package ex3;

import java.util.Random;

public class WorkerRunnable implements Runnable {
	Random itemRnd = new Random();

	@Override
	public void run() {
		System.out.println(String.format("Thread %d starts.", Thread.currentThread().getId()));
		int action;
		int item;
		try {
			while (!Thread.currentThread().isInterrupted()) {
				action = Main.actionRnd.nextInt(100);
				item = itemRnd.nextInt(Main.itemBound);
				if (action >= Main.updThresh) // contain
					Main.set.contain(item);
				else if (action >= Main.insThresh) // remove
					Main.set.remove(item);
				else // insert
					Main.set.insert(item);

			}
		} catch (InterruptedException e) {
			System.out.println(String.format("Thread %d interrupted.", Thread.currentThread().getId()));
		} finally {
			System.out.println(String.format("Thread %d ends.", Thread.currentThread().getId()));
		}
	}
}
