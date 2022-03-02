public class Link {
	private Node node1, node2;
	private int weight;
	
	public Link (Node node1, Node node2, int weight) {
		this.node1 = node1;
		this.node2 = node2;
		this.weight = weight;
	}
	public Node[] getNodes() {
		return new Node[] {node1, node2};
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int newWeight) {
		weight = newWeight;
	}
	
	@Override
	public String toString() {
		return "{ "+node1.getId()+", "+node2.getId()+" }";
	}
}
