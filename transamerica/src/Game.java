import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Game {
	private int barrierScore;
	private Player[] players;
	private ArrayList<Player> winners;
	private Graph graph = new Graph();
	private int currentPlayer, playerTurn, roundCount, turnCount;
	private ArrayList<Node> takenStartingNodes = new ArrayList<>();
	private Blackboard board;
	private boolean roundOver = false, gameOver = false;

	void getBlackboard(Blackboard board) {
		this.board = board;
	}

	void setBlackboard(Blackboard board) {
		this.board = board;
	}

	int getRoundCount() {
		return roundCount;
	}

	int getTurnCount() {
		return turnCount;
	}

	private static final int[][] availableCities, availableCitiesForLessPlayers;
	static {
		availableCities = new int[][] {
				{ 10, 40, 74, 90, 138, 0, 153 },
				{ 13, 17, 34, 52, 87, 20, 37 },
				{ 60, 65, 101, 126, 129, 79, 99 },
				{ 140, 148, 160, 169, 184, 164, 182 },
				{ 73, 89, 135, 166, 187, 39, 121 }
		};
		availableCitiesForLessPlayers = new int[][] {
				{ 10, 40, 74, 90, 138 },
				{ 13, 17, 34, 52, 87 },
				{ 60, 65, 101, 126, 129 },
				{ 140, 148, 160, 169, 184 },
				{ 73, 89, 135, 166, 187 }
		};
	}

	boolean isComputerOnly = false;

	public Game(Player[] players, boolean isComputerOnly) {
		this.players = players;
		this.isComputerOnly = isComputerOnly;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void play() throws InterruptedException {

		roundCount = 1;
		Collections.shuffle(Arrays.asList(players));

		// TODO this sucks lmao
		while (!gameOver) {
			startRound();
			playRound();

			// first round of clicking through
			if (!isComputerOnly) {
				board.setNeedHumanInput(true);
				waitForBlackboard();
			}

			// update score
			updateScore();
			System.out.println("round over");
			System.out.println(turnCount);
			for (Player p : players) {
				System.out.print(p.getScore() + " ");
			}
			System.out.println();
			System.out.println(gameOver);
			roundCount++;
		}

	}

	private void startRound() throws InterruptedException {

		// reset turn counter
		turnCount = 0;
		winners = null;
		playerTurn = 0;
		board.setFirstTurn(true);
		roundOver = false;
		madeLinks = new ArrayList<Link>();

		// create new graph
		graph = new Graph();

		// distribute cities to players
		int[][] citiesUsed = availableCities;
		if (players.length < 4)
			citiesUsed = availableCitiesForLessPlayers;

		for (int[] arr : citiesUsed) {
			Collections.shuffle(Arrays.asList(arr));
		}
		Collections.shuffle(Arrays.asList(citiesUsed));
		for (int i = 0; i < players.length; i++) {
			City[] newCities = new City[5];
			for (int j = 0; j < newCities.length; j++) {
				Node tempNode = graph.getNodes()[citiesUsed[j][i]];
				newCities[j] = (City) tempNode;
			}
			players[i].setCities(newCities);
		}

		// turn 0; set starting nodes for players
		for (Player p : players) {
			board.setFirstTurn(true);
			p.updateBoardForStartMarker(takenStartingNodes);
			waitForBlackboard();
			p.setStartingNode(board.getToReturnNode());
			takenStartingNodes.add(board.getToReturnNode());
		}

		turnCount = 1;

	}

	private ArrayList<Link> madeLinks = new ArrayList<Link>();

	// return necessary things
	private void playRound() throws InterruptedException {

		board.setFirstTurn(false);
		int railsLeft = 84;
		while (true) {
			board.setNeedHumanInput(true);
			players[playerTurn].updateBoardForMove(graph, players, (railsLeft <= 1));
			waitForBlackboard();
			for (Link l : board.getToReturnLink()) {
				if (l != null) {
					railsLeft -= (l.getWeight());
					graph.addLink(l);
					madeLinks.add(l);
				}
			}
			if (railsLeft <= 0) {
				return; // fix
			}

			for (Player p : players) {
				Node[] everything = new Node[1];
				boolean flag = false;
				for (City c : p.getCities()) {
					if (p.getStartingNode() == c) {
						everything = new Node[5];
						flag = true;
						break;
					}
				}
				if (!flag) {
					everything = new Node[6];
					everything[everything.length - 1] = p.getStartingNode();
				}
				for (int i = 0; i < 5; i++) {
					everything[i] = p.getCities()[i];
				}
				// temp fix
				if (graph.calculateScore(p.getStartingNode(), p.getCities()) == 0) {
					if (winners == null) {
						winners = new ArrayList<Player>();
					}
					winners.add(p);
				}
			}
			if (winners != null)
				return;
			playerTurn = (playerTurn + 1) % players.length;
			if (playerTurn == 0)
				turnCount++;

		}

	}

	private void updateScore() {
		roundOver = true;
		for (Player p : players) {
			p.setScore(p.getScore() - graph.calculateScore(p.getStartingNode(), p.getCities()));
		}
		Player[] tempPlayers = new Player[players.length];
		for (int i = 0; i < tempPlayers.length; i++) {
			tempPlayers[i] = players[i];
		}
		Arrays.sort(tempPlayers, new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				int res = o1.getScore() - o2.getScore();
				if (res == 0) {
					return o1.getName().compareTo(o2.getName());
				}
				return res;
			}

		});
		if (roundCount == 2 && tempPlayers[0].getScore() >= 4) {
			barrierScore = tempPlayers[0].getScore() - 2;
		}
		if (tempPlayers[0].getScore() < barrierScore) {
			gameOver = true;
		}
	}

	public Graph getGraph() {
		return graph;
	}

	public Player currentPlayer() {
		return players[currentPlayer];
	}

	public boolean isRoundOver() {
		return roundOver;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	void waitForBlackboard() throws InterruptedException {
		while (board.needHumanInput()) {
			Thread.sleep(100);
		}
	}

}
