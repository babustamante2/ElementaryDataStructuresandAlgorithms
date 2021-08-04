
public class Node {
	int data;
	Node next;

	public Node(int data) {
		this.data = data;
		next = null;
	}

	public void append(int data) {
		Node current = this;
		while (current.next != null) {
			current = current.next;
		}
		current.next = new Node(data);
	}
}
