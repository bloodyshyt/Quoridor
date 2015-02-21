package main;
import Player.AiPlayer;
import Std.StdOut;


public class PlayerMove implements Move {
	
	// move the selected player to the destination
	
	int from_x, from_y;
	int to_x, to_y;
	AiPlayer player;
	
	public PlayerMove(AiPlayer player, int x, int y) {
		this.player = player;
		from_x = this.player.x;
		from_y = this.player.y;
		to_x = x;
		to_y = y;
	}

	@Override
	public void playMove(GameBoard G) {
		player.x = to_x;
		player.y = to_y;
	}

	@Override
	public void echo() {
		StdOut.println("PlayerMove: P" + player.playerNo + " to (" + to_x + "," + to_y + ")");
	}

	@Override
	public void undoMove(GameBoard G) {
		player.x = from_x;
		player.y = from_y;
	}

}
