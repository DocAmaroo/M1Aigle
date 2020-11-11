
public class Edge {
	public String color = "none";
	public String head = "none";
	public Node nodeLeft = new Node();
	public Node nodeRight = new Node();

	public Edge() {
	}

	public Edge(Node nodeLeft, Node nodeRight) {
		super();
		this.nodeLeft = nodeLeft;
		this.nodeRight = nodeRight;
	}

	public Edge(String head, Node nodeLeft, Node nodeRight) {
		super();
		this.head = head;
		this.nodeLeft = nodeLeft;
		this.nodeRight = nodeRight;
	}

	public Edge(String color, String head, Node nodeLeft, Node nodeRight) {
		super();
		this.color = color;
		this.head = head;
		this.nodeLeft = nodeLeft;
		this.nodeRight = nodeRight;
	}

	@Override
	public String toString() {
		return "\"" + nodeLeft.getLabel() + "\" -> \""  + nodeRight.getLabel() + "\"" + " [arrowhead=\"" + head + "\", color=\"" + color + "\"];";
	}
	
}