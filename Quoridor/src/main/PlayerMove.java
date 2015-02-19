package main;
import Player.AiPlayer;
import Std.StdOut;


public class PlayerMove implements Move {
	
	// move the selected player to the destination
	
	int to_x, to_y;
	AiPlayer player;
	
	public PlayerMove(AiPlayer player, int x, int y) {
		this.player = player;
		to_x = x;
		to_y = y;
	}

	@Override
	public void playMove(GameBoard G) {
		Tile nextTile = G.board[to_x][to_y];
		player.x = to_x;
		player.y = to_y;
	}

	@Override
	public void echo() {
		StdOut.println("PlayerMove: P" + player.playerNo + " to (" + to_x + "," + to_y + ")");
	}

}
