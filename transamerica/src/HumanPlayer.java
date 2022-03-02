import java.awt.Color;
import java.util.ArrayList;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, Color color, Blackboard board) {
		super(name, color, board);
	}

	@Override
	Link[] makeMove(Graph graph, Player[] players, boolean oneLinkLeft) {
		return new Link[] {new Link(new Node(-1, -1, -1), new Node(-1, -1, -1), -1)};
	}

	@Override
	Node placeStartingMarker(ArrayList<Node> takenStartingNodes) {
		return new Node(-1, -1, -1);
	}

}
