package project4;

public class TurnEngine {
	public int[] results = {0,0};
	public int doubleRoll = 3;
	public int playerPosition = 0;
	public boolean jail = false;
	public Board board;

	public TurnEngine(Board board) {
		this.board = board;
	}
	
	//Roll two 6-sided-die
	public int[] roll() {
		for (int i = 0; i < 2; i++) {
			results[i] = (int)(Math.random() * (6 - 1 + 1)) + 1;
		}
		return results;
	}
	
	//Play a turn
	public void turn() {
		int[] roll = roll();
		if(roll[0] == roll[1]) {
			if(jail = true) {
				jail = false;
			}
			doubleRoll++;
		} else {
			doubleRoll = 0;
		}
		// if 3 double rolls in a row, go to jail.
		if(doubleRoll == 3) {
			jail = true;
			doubleRoll = 0;
			playerPosition = board.indexOf("Jail");
		}
	}
}
