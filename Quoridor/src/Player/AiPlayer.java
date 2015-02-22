package Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Std.StdOut;
import main.GameBoard;
import main.Move;
import main.PlayerMove;
import main.Tile;

public abstract class AiPlayer {
	public int playerNo;
	public int x, y;
	public int win_x, win_y;
	public int remainingWalls;

	public AiPlayer(int x, int y, int playerNo) {
		this.x = x;
		this.y = y;
		this.playerNo = playerNo;
		remainingWalls = 10;

		// add the winning condition
		win_x = GameBoard.DIM + 1;
		win_y = GameBoard.DIM + 1;
		if(y == 0) win_y = GameBoard.DIM - 1;
		if(y == GameBoard.DIM - 1) win_y = 0;
		if(x == 0) win_x = GameBoard.DIM - 1;
		if(x == GameBoard.DIM - 1) win_x = 0;
		
		//StdOut.println("Player " + playerNo + " at (" + x + "," + y + ") win at (" + win_x + "," + win_y + ")");

	}
	
	public boolean playerWon() {
		if(x == win_x || y == win_y)  {
			//StdOut.println("Winning of condition of (" + win_x + "," + win_y + ") meet at (" + x + "," + y + ")");
			return true;
		}
		return false;
	}

	// abstract method to get the next move
	public abstract Move getNextMove(GameBoard G);
	
}
