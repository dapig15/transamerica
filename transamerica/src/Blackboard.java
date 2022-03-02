
public class Blackboard {
	
	private boolean needHumanInput = false;
	private Node toReturnNode;
	private Link[] toReturnLink;
	
	private boolean isFirstTurn = true;
	
	boolean needHumanInput() {
		return needHumanInput;
	}
	void setNeedHumanInput(boolean needHumanInput) {
		this.needHumanInput = needHumanInput;
	}
	Node getToReturnNode() {
		return toReturnNode;
	}
	Link[] getToReturnLink() {
		return toReturnLink;
	}
	void setToReturnNode(Node toReturnNode) {
		this.toReturnNode = toReturnNode;
	}
	void setToReturnLink(Link[] toReturnLink) {
		this.toReturnLink = toReturnLink;
	}
	boolean isFirstTurn() {
		return isFirstTurn;
	}
	void setFirstTurn(boolean isFirstTurn) {
		this.isFirstTurn = isFirstTurn;
	}
}
