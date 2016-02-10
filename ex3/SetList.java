package ex3;

public class SetList {
	protected Node head;

	public SetList() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}
	
	public void printSet() { // not thread safe!!!
		Node curr = head.next;
		int sz = 0;
		while (curr.key < Integer.MAX_VALUE) {
			sz++;
			System.out.print(Integer.toString(curr.key) + " ");
			curr = curr.next;
		}
		System.out.println("\nEnd printing set of size " + Integer.toString(sz));
	}
}
