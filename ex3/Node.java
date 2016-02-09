package ex3;

import sun.misc.Lock;

public class Node {
	public int key;
	public Node next;
	private Lock lock = new Lock();

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
