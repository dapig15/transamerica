import java.awt.Color;

public class GameRunner {
	public static void main(String[] args) throws InterruptedException {
		Blackboard b = new Blackboard();
		Game g = new Game(new Player[] {
				new SimpleComputerPlayer("abc", new Color(255, 0, 0), b),
				new SimpleComputerPlayer("def", new Color(255, 255, 0), b),
				new SimpleComputerPlayer("ghi", new Color(255, 0, 255), b),
				new SimpleComputerPlayer("jkl", new Color(255, 255, 255), b),
		}, true);
		g.setBlackboard(b);
		g.play();
	}
}
