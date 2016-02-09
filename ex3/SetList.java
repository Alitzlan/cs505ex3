package ex3;

public class SetList {
	protected Node head;

	public SetList() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}
}
