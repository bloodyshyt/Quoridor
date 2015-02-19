package main;

public interface Move {
	
	/**
	 * @param G deep copy of the current Gameboard
	 */
	public void playMove(GameBoard G);
	
	public void echo();
	
}
