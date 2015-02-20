package main;
import Player.AiPlayer;
import Std.StdOut;

public class WallMove implements Move {

	public final static boolean VERTICAL = true;
	public final static boolean HORIZONTAL = false;

	AiPlayer player;
	int x, y;
	boolean wallOrientation;

	public WallMove(AiPlayer player, int x, int y, boolean wallOrientation) {
		this.player = player;
		this.x = x;
		this.y = y;
		this.wallOrientation = wallOrientation;
	}

	@Override
	public void playMove(GameBoard G) {
		
		player.remainingWalls--;

		Tile t = G.board[x][y];

		if (wallOrientation == HORIZONTAL) {

			Tile left = G.board[t.x - 1][t.y];
			Tile right = G.board[t.x + 1][t.y];
			t.adj[Tile.LEFT] = null;
			t.adj[Tile.RIGHT] = null;
			left.adj[Tile.RIGHT] = null;
			right.adj[Tile.LEFT] = null;
		} else {
			Tile up = G.board[t.x][t.y + 1];
			Tile down = G.board[t.x][t.y - 1];
			t.adj[Tile.UP] = null;
			t.adj[Tile.DOWN] = null;
			up.adj[Tile.DOWN] = null;
			down.adj[Tile.UP] = null;
		}
	}

	@Override
	public void echo() {
		StdOut.println("Wallmove: P" + player.playerNo +  " at (" + x + "," + y + ") " + ((wallOrientation == HORIZONTAL) ? "Horizontal" : "Vetical") );
	}

}
