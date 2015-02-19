package Player;

import java.rmi.server.RemoteObjectInvocationHandler;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import main.GameBoard;
import main.Move;
import main.PlayerMove;
import main.Tile;


public abstract class AiPlayer {
	public int playerNo;
	public int x, y;
	public int remainingWalls;
	public GameBoard board;
	
	public AiPlayer(GameBoard board, int x, int y, int playerNo) {
		this.board = board;
		this.x = x;
		this.y = y;
		this.playerNo = playerNo;
		remainingWalls = 10;
	}
	
	// abstract method to get the next  move
	public abstract Move getNextMove();
	
	
}
