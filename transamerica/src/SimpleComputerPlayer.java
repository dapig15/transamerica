import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class SimpleComputerPlayer extends Player{
	
	public SimpleComputerPlayer(String name, Color color, Blackboard board) {
		super(name, color, board);
	}

	public Link[] makeMove(Graph graph, Player[] players, boolean oneLinkLeft) {
		Node start = getStartingNode();
		City[] cities = getCities();
		ArrayList<ArrayList<Link>> paths = new ArrayList<>();
		int[] lengths = new int[5];
		int count;
		for (int i = 0; i < 5; i++) {
			paths.add(linkTwoNodes(start, cities[i], graph.getNodes()));
			count = 0;
			for (Link l : paths.get(i)) {
				count += l.getWeight();
			}
			lengths[i] = count;
		}
		ArrayList<Link> temp1;
		int temp2;
		for (int i = 0; i < 5; i++) {
			for (int j = i + 1; j < 5; j++) {
				if (lengths[i] > lengths[j]) {
					temp2 = lengths[i];
					lengths[i] = lengths[j];
					lengths[j] = temp2;
					temp1 = paths.get(i);
					paths.set(i, paths.get(j));
					paths.set(j, temp1);
				}
			}
		}
		for (int i = 0; i < 5; i++) {
			Iterator<Link> iter = paths.get(i).iterator();
			while (iter.hasNext()) {
				Link l = iter.next();
				if (l.getWeight() == 0) {
					iter.remove();
				}
			}
		}
		
		for (int i = 0; i < paths.size(); i++) {
			if (paths.get(i).size() == 0) {
				paths.remove(i);
				i--;
			}
		}
		Link[] returnLinks = new Link[2];
		boolean looping = true;
		if (oneLinkLeft) {
			for (int i = 0; i < 5 && looping; i++) {
				if (paths.get(i).get(0).getWeight() == 1) {
					returnLinks[0] = paths.get(i).get(0);
					looping = false;
				}
			}
		} else {
			Node[] nodes = new Node[cities.length+1];
			for (int i = 0; i < cities.length; i++) {
				nodes[i] = getCities()[i];
			}
			nodes[nodes.length-1] = getStartingNode();
			returnLinks[0] = paths.get(0).remove(0);
			if (returnLinks[0].getWeight() == 1) {
				if (paths.get(0).size() == 0) {
					paths.remove(0);
				}
				for (int i = 0; i < paths.size() && looping; i++) {
					if (paths.get(i).get(0).getWeight() == 1) {
						returnLinks[1] = paths.get(i).get(0);
						looping = false;
					}
				}
			}
		}
//		if (returnLinks[1] != null) {
//			System.out.println("brain used");
//		}
		return returnLinks;
	}

	
	public Node placeStartingMarker (ArrayList<Node> takenStartingNodes) {
		// find yellow city and give it that
		Node marker = new City(0,0,0," ",Color.black);
		for (int i=0; i<5; i++) {
			if (getCities()[i].getColor() == Color.yellow) {
				marker = getCities()[i];
			}
		}
		// Checks five times if the node it is returning is equal to any taken nodes,
		// one for each player.
		// Will move it 1 to the right if the node is taken
		for (int j=0; j<5; j++) {
			for (int i=0; i<takenStartingNodes.size(); i++) {
				if (marker.getId()==takenStartingNodes.get(i).getId()) {
					marker = new Node(marker.getX()+2,marker.getY(),marker.getId()+1);
				}
			}
		}
		return (marker);
	}
	
	private ArrayList<Link> linkTwoNodes(Node start, Node end, Node[] nodes) {
		final int[] minDists = new int[nodes.length];
		Arrays.fill(minDists, 1000000);
		minDists[start.getId()] = 0;
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return minDists[o1] - minDists[o2];
			}
		});
		Link[] links = new Link[nodes.length];
		pq.add(start.getId());
		while (!pq.isEmpty()) {
			int nextId = pq.poll();
			for (Link link : nodes[nextId].getLinks()) {
				Node n1 = link.getNodes()[0];
				Node n2 = link.getNodes()[1];
				Node selected = n1;
				if (n1.getId() == nextId) {
					selected = n2;
				}
				if (minDists[selected.getId()] > minDists[nextId]+link.getWeight()) {
					minDists[selected.getId()] = minDists[nextId]+link.getWeight();
					pq.add(selected.getId());
					links[selected.getId()] = link;
				}
			}
		}
		ArrayList<Link> returnLinks = new ArrayList<Link>();
		Node currNode = end;
		while (currNode.getId() != start.getId()) {
			returnLinks.add(links[currNode.getId()]);
			Link l1 = links[currNode.getId()];
			Node[] n1arr = l1.getNodes();
			Node n1 = n1arr[0];
			Node n2 = links[currNode.getId()].getNodes()[1];
			if (n1 == currNode) {
				currNode = n2;
			} else {
				currNode = n1;
			}
		}
		Collections.reverse(returnLinks);
		return returnLinks;
	}
}
