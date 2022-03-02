import java.util.ArrayList;
public class Node {
	private int x, y, id;
	private ArrayList<Link> links;
	public Node(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
		links = new ArrayList<Link>();
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getId() {
		return id;
	}
	public void addLink(Link newLink) {
		links.add(newLink);
	}
	public ArrayList<Link> getLinks() {
		return links;
	}
}
