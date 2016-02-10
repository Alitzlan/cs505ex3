package ex3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node {
	public int key;
	public Node next;
	private Lock lock = new ReentrantLock();

	public Node(int item) {
		key = item;
		next = null;
	}
	
	public void lock() throws InterruptedException {
		lock.lock();
	}
	
	public void unlock() {
		lock.unlock();
	}
}
