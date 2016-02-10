package ex3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CGSet extends SetList implements IntSet {
	private Lock lock = new ReentrantLock();

	@Override
	public boolean insert(int item) throws InterruptedException {
		lock.lock();
		Node pred = head;
		try {
			Node curr = head.next;
			while (curr.key < item) {
				pred = curr;
				curr = pred.next;
			}
			if (curr.key == item) {
				return false;
			}
			Node node = new Node(item);
			node.next = curr;
			pred.next = node;
			return true;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean remove(int item) throws InterruptedException {
		lock.lock();
		Node pred = head;
		try {
			Node curr = head.next;
			while (curr.key < item) {
				pred = curr;
				curr = pred.next;
			}
			if (curr.key != item) {
				return false;
			}
			pred.next = curr.next;
			return true;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean contain(int item) throws InterruptedException {
		lock.lock();
		Node pred = head;
		try {
			Node curr = head.next;
			while (curr.key < item) {
				pred = curr;
				curr = pred.next;
			}
			if (curr.key == item) {
				return true;
			}
			return false;
		} finally {
			lock.unlock();
		}
	}
}
