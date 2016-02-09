package ex3;

public class HOHSet extends SetList implements IntSet {

	@Override
	public boolean insert(int item) throws InterruptedException {
		head.lock();
		Node pred = head;
		try {
			Node curr = head.next;
			curr.lock();
			try {
				while (curr.key < item) {
					pred.unlock();
					pred = curr;
					curr = pred.next;
					curr.lock();
				}
				if (curr.key == item)
					return false;
				Node node = new Node(item);
				node.next = curr;
				pred.next = node;
				return true;
			} finally {
				curr.unlock();
			}
		} finally {
			pred.unlock();
		}
	}

	@Override
	public boolean remove(int item) throws InterruptedException {
		head.lock();
		Node pred = head;
		try {
			Node curr = pred.next;
			curr.lock();
			try {
				while (curr.key < item) {
					pred.unlock();
					pred = curr;
					curr = pred.next;
					curr.lock();
				}
				if (curr.key == item) {
					pred.next = curr.next;
					return true;
				}
				return false;
			} finally {
				curr.unlock();
			}
		} finally {
			pred.unlock();
		}
	}

	@Override
	public boolean contain(int item) throws InterruptedException {
		head.lock();
		Node pred = head;
		try {
			Node curr = head.next;
			curr.lock();
			try {
				while (curr.key < item) {
					pred.unlock();
					pred = curr;
					curr = pred.next;
					curr.lock();
				}
				return (curr.key == item);
			} finally {
				curr.unlock();
			}
		} finally {
			pred.unlock();
		}
	}
}
