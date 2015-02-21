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

		Tile t1 = G.board[x][y];
		Tile t2 = G.board[x - 1][y];
		Tile t3 = G.board[x - 1][y + 1];
		Tile t4 = G.board[x][y + 1];

		if (wallOrientation == HORIZONTAL) {
			t1.adj[Tile.UP] = null;
			t4.adj[Tile.DOWN] = null;
			t2.adj[Tile.UP] = null;
			t3.adj[Tile.DOWN] = null;

		} else {
			t1.adj[Tile.LEFT] = null;
			t2.adj[Tile.RIGHT] = null;
			t4.adj[Tile.LEFT] = null;
			t3.adj[Tile.RIGHT] = null;
		}
	}

	@Override
	public void echo() {
		StdOut.println("Wallmove: P"
				+ player.playerNo
				+ " at ("
				+ x
				+ ","
				+ y
				+ ") "
				+ ((wallOrientation == HORIZONTAL) ? "Horizontal " : "Vetical ")
				+ player.remainingWalls + " walls left");
	}

	@Override
	public void undoMove(GameBoard G) {

		player.remainingWalls++;
		G.wallMoves.remove(this);

		Tile t1 = G.board[x][y];
		Tile t2 = G.board[x - 1][y];
		Tile t3 = G.board[x - 1][y + 1];
		Tile t4 = G.board[x][y + 1];

		if (wallOrientation == HORIZONTAL) {
			t1.adj[Tile.UP] = t4;
			t4.adj[Tile.DOWN] = t1;
			t2.adj[Tile.UP] = t3;
			t3.adj[Tile.DOWN] = t2;

		} else {
			t1.adj[Tile.LEFT] = t2;
			t2.adj[Tile.RIGHT] = t1;
			t4.adj[Tile.LEFT] = t3;
			t3.adj[Tile.RIGHT] = t4;
		}

	}

}
