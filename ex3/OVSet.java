package ex3;

public class OVSet extends SetList implements IntSet {

	private boolean validate(Node pred, Node curr) {
		Node node = head;
		while (node.key <= pred.key) {
			if (node == pred) {
				return pred.next == curr;
			}
			node = node.next;
		}
		return false;
	}

	@Override
	public boolean insert(int item) throws InterruptedException {
		while (true) {
			Node pred = head;
			Node curr = pred.next;
			while (curr.key < item) {
				pred = curr;
				curr = curr.next;
			}
			pred.lock();
			curr.lock();
			try {
				if (validate(pred, curr)) {
					if (curr.key == item) {
						return false;
					}
					Node node = new Node(item);
					node.next = curr;
					pred.next = node;
					return true;
				}
			} finally {
				pred.unlock();
				curr.unlock();
			}
		}
	}

	@Override
	public boolean remove(int item) throws InterruptedException {
		while (true) {
			Node pred = head;
			Node curr = pred.next;
			while (curr.key < item) {
				pred = curr;
				curr = curr.next;
			}
			pred.lock();
			curr.lock();
			try {
				if (validate(pred, curr)) {
					if (curr.key == item) {
						pred.next = curr.next;
						return true;
					}
					return false;
				}
			} finally {
				pred.unlock();
				curr.unlock();
			}
		}
	}

	@Override
	public boolean contain(int item) throws InterruptedException {
		while (true) {
			Node pred = head;
			Node curr = pred.next;
			while (curr.key < item) {
				pred = curr;
				curr = curr.next;
			}
			pred.lock();
			curr.lock();
			try {
				if (validate(pred, curr)) {
					return (curr.key == item);
				}
			} finally {
				pred.unlock();
				curr.unlock();
			}
		}
	}

}
