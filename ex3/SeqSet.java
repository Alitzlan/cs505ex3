package ex3;

public class SeqSet extends SetList implements IntSet {

	@Override
	public boolean insert(int item) {
		Node pred = head;
		Node curr = head.next;
		while (curr.key < item) {
			pred = curr;
			curr = pred.next;
		}
		if (curr.key == item)
			return false;
		else {
			Node node = new Node(item);
			node.next = curr;
			pred.next = node;
			return true;
		}
	}

	@Override
	public boolean remove(int item) {
		Node pred = head;
		Node curr = head.next;
		while (curr.key < item) {
			pred = curr;
			curr = pred.next;
		}
		if (curr.key == item) {
			pred.next = curr.next;
			return true;
		} else
			return false;
	}

	@Override
	public boolean contain(int item) {
		Node pred = head;
		Node curr = head.next;
		while (curr.key < item) {
			pred = curr;
			curr = pred.next;
		}
		return curr.key == item;
	}
}
