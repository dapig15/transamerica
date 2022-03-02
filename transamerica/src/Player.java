import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Color color;
	private City[] cities;
	private int score = 13;
	private Node startingNode = null;
	private Blackboard board;
	
	public Player(String name, Color color, Blackboard board) {
		this.name = name;
		this.color = color;
		this.board = board;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public City[] getCities() {
		return cities;
	}

	public int getScore() {
		return score;
	}

	public Node getStartingNode() {
		return startingNode;
	}
	
	public Blackboard getBoard() {
		return board;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setCities(City[] cities) {
		this.cities = cities;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setStartingNode(Node startingNode) {
		this.startingNode = startingNode;
	}

	abstract Link[] makeMove(Graph graph, Player[] players, boolean oneLinkLeft);
	abstract Node placeStartingMarker(ArrayList<Node> takenStartingNodes);
	
	public void updateBoardForMove(Graph graph, Player[] players, boolean oneLinkLeft) {
		board.setToReturnLink(makeMove(graph, players, oneLinkLeft));
		board.setNeedHumanInput(false);
	}
	public void updateBoardForStartMarker(ArrayList<Node> takenStartingNodes) {
		board.setToReturnNode(placeStartingMarker(takenStartingNodes));
		board.setNeedHumanInput(false);
	}
}
