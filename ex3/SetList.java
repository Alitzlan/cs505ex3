package ex3;

public class SetList {
	private Node head;
	public SetList() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}
}
