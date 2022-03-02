import java.util.*;
public class Graph {
	public static final int[][] nodeCoords = new int[][] {
		{2, 4, 6, 8, 10, 12, 14, 16, 18, 20},
		{1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 31, 33},
		{0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 30, 32, 34},
		{1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33},
		{0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32},
		{1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31},
		{0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30},
		{1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31},
		{2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32},
		{3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31},
		{4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30},
		{7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29},
		{12, 14, 16, 18, 20, 22, 24, 26, 28},
	};
	public static final int[][] doubleLinks = new int[][] {
		{0, 1}, {0, 11}, {2, 12}, {4, 14}, {4, 15}, {5, 15}, {5, 16}, {6, 16},
		{10, 11}, {10, 25}, {12, 13}, {13, 14}, {13, 27}, {14, 28}, {16, 17}, {17, 31}, {19, 20}, {20, 34},
		{24, 25}, {25, 40}, {27, 28}, {28, 29}, {29, 44}, {31, 32}, {32, 47}, {34, 35}, {35, 50},
		{40, 41}, {40, 58}, {44, 45}, {47, 48}, {48, 65}, {50, 51}, {50, 68}, {55, 73},
		{57, 58}, {58, 74}, {59, 75}, {61, 77}, {65, 66}, {66, 82}, {67, 68}, {68, 84}, {72, 73}, {72, 89},
		{74, 75}, {74, 91}, {75, 76}, {75, 92}, {77, 94}, {78, 94}, {78, 79}, {79, 95}, {82, 83}, {83, 99}, {83, 100}, {84, 85}, {84, 100}, {84, 101}, {85, 101}, {86, 103}, {87, 103}, {87, 104}, {88, 89}, {88, 105},
		{91, 92}, {91, 106}, {93, 94}, {93, 109}, {95, 96}, {95, 111}, {101, 102}, {102, 103}, {102, 117}, {102, 118}, {104, 105}, {104, 120},
		{106, 107}, {108, 109}, {110, 111}, {117, 118}, {117, 133}, {119, 120}, {119, 135},
		{122, 123}, {123, 124}, {124, 125}, {124, 139}, {125, 140}, {132, 133}, {132, 148}, {134, 135}, {134, 150},
		{138, 139}, {139, 140}, {139, 154}, {140, 141}, {141, 155}, {141, 156}, {147, 148}, {147, 162}, {149, 150},
		{153, 154}, {161, 162}, {162, 174},
		{174, 175}, {174, 184},
		{183, 184}
	};
	private Node[] nodes;
	public Graph() {
		nodes = new Node[188];
		int count = 0;
		for (int y = 0; y < nodeCoords.length; y++) {
			for (int x : nodeCoords[y]) {
				if (City.cityNames[count] == null)
					nodes[count] = new Node(x, y, count);
				else
					nodes[count] = new City(x, y, count, City.cityNames[count], City.cityColors[count]);
				count++;
			}
		}
		count = 0;
		Link newLink;
		for (int y = 0; y < nodeCoords.length; y++) {
			for (int i = 0; i < nodeCoords[y].length; i++) {
				if (i > 0 && (nodeCoords[y][i] - nodeCoords[y][i-1] == 2)) {
					newLink = new Link(nodes[count], nodes[count-1], 1);
					nodes[count].addLink(newLink);
					nodes[count-1].addLink(newLink);
				}
				if (i < (nodeCoords[y].length - 1) && (nodeCoords[y][i+1] - nodeCoords[y][i] == 2)) {
					newLink = new Link(nodes[count], nodes[count+1], 1);
					nodes[count].addLink(newLink);
					nodes[count+1].addLink(newLink);
				}
				if (y < nodeCoords.length - 1) {
					for (int j = 0; j < nodeCoords[y+1].length; j++) {
						if (Math.abs(nodeCoords[y][i] - nodeCoords[y+1][j]) == 1) {
							newLink = new Link(nodes[count], nodes[count + nodeCoords[y].length - i + j], 1);
							nodes[count].addLink(newLink);
							nodes[count + nodeCoords[y].length - i + j].addLink(newLink);
						}
					}
				}
				count++;
			}
		}
		for (int[] dl : doubleLinks) {
			addDoubleLink(nodes[dl[0]], nodes[dl[1]]);
		}
	}
	public Node[] getNodes() {
		return nodes;
	}
	public boolean isConnected(Node[] nodes) {
		Node startingNode = nodes[0];
		boolean[] found = new boolean[nodes.length-1];
		Queue<Node> testingNodes = new LinkedList<Node>();
		ArrayList<Node> testedNodes = new ArrayList<Node>();
		testingNodes.add(startingNode);
		Node temp;
		while(!testingNodes.isEmpty()) {
			temp = testingNodes.poll();
			if (!inArray(temp, testedNodes)) {
				for (int i = 1; i < nodes.length; i++) {
					if (temp == nodes[i])
						found[i - 1] = true;
				}
				for (Node node : getLinkedNodes(temp)) {
					testingNodes.add(node);
				}
			}
			testedNodes.add(temp);
		}
		boolean connected = true;
		for (boolean b : found) {
			connected = connected && b;
		}
		return connected;
	} // bfs
	public ArrayList<Link> getPossibleLinks(Node start) {
		ArrayList<Link> links = start.getLinks();
		ArrayList<Link> newLinks = new ArrayList<Link>();
		for (Link link : links) {
			if (link.getWeight() != 0) {
				newLinks.add(link);
			}
		}
		return newLinks;
	}
	
	// TODO TEST ME
	public int distance(Node start, Node end) {
		final int[] minDists = new int[nodes.length];
		Arrays.fill(minDists, 1000000); // maybe bigger
		minDists[start.getId()] = 0;
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return minDists[o1] - minDists[o2];
			}
		});
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
				}
			}
		}
		return minDists[end.getId()];
	}
	
	public void addLink(Node start, Node end) {
		ArrayList<Link> links = start.getLinks();
		Link myLink = links.get(0);
		for (Link link : links) {
			if (link.getNodes()[0] == end || link.getNodes()[1] == end)
				myLink = link;
		}
		myLink.setWeight(0);
	}
	public void addLink(Link l) {
		addLink(l.getNodes()[0], l.getNodes()[1]);
	}
	
	// TODO TEST ME THOROUGHLY
	public int calculateScore(Node startNode, Node[] cities) {
		
		// generate all links between startnode, cities
		// int[] {node1, node2, weight}
		// remember node index 0 is startNode, everything else (1-cities.length) is cities
		PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[2] - o2[2];
			}
		});
		for (int i = 0; i < cities.length; i++) {
			pq.add(new int[] {0, i+1, distance(startNode, cities[i])});
		}
		for (int i = 0; i < cities.length; i++) {
			for (int j = i+1; j < cities.length; j++) {
				pq.add(new int[] {i+1, j+1, distance(cities[i], cities[j])});
			}
		}
		
		// run kruskals to make MST for startnode, cities
		int[] parent = new int[cities.length+1];
		int[] rank = new int[cities.length+1];
		for (int i = 0; i < rank.length; i++) {
			parent[i] = i;
			rank[i] = 1;
		}
		int cost = 0;
		while (!pq.isEmpty()) {
			int[] next = pq.poll();
			int p1 = findParent(parent, next[0]);
			int p2 = findParent(parent, next[1]);
			if (p1 == p2)
				continue;
			cost += next[2];
			merge(parent, rank, p1, p2);
		}
		return cost;
		
	}
	
	// TODO uh oh test me
	private int findParent(int[] parent, int target) {
		if (parent[target] == target)
			return target;
		else {
			parent[target] = findParent(parent, parent[target]);
			return parent[target];
		}
	}
	private void merge(int[] parent, int[] rank, int a, int b) {
		a = findParent(parent, a);
		b = findParent(parent, b); // these two should be unnecessary
		if (a == b)
			return;
		if (rank[a] > rank[b])
			parent[b] = a;
		else
			parent[a] = b;
	}
	
	private ArrayList<Node> getLinkedNodes(Node start) {
		ArrayList<Link> links = start.getLinks();
		ArrayList<Node> returnArr = new ArrayList<Node>();
		for (Link link : links) {
			if (link.getWeight() == 0) {
				if (link.getNodes()[0] == start)
					returnArr.add(link.getNodes()[1]);
				else
					returnArr.add(link.getNodes()[0]);
			}
		}
		return returnArr;
	}
	private boolean inArray(Node node, ArrayList<Node> nodes) {
		boolean found = false;
		for (Node cNode : nodes) {
			if (node == cNode)
				found = true;
		}
		return found;
	}
	private void addDoubleLink(Node start, Node end) {
		ArrayList<Link> links = start.getLinks();
		Link myLink = links.get(0);
		for (Link link : links) {
			if (link.getNodes()[0] == end || link.getNodes()[1] == end)
				myLink = link;
		}
		myLink.setWeight(2);
	}
}
