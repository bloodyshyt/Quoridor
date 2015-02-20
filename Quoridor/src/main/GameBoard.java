package main;

import java.util.ArrayList;

import Player.AiPlayer;
import Player.SimplePlayer;
import Player.testPlayer1;
import Std.StdOut;

public class GameBoard {

	public static final int DIM = 9;

	public Tile[][] board;
	public AiPlayer[] players;
	public int currentPlayerIndex;
	public int winningPlayer;

	// creates a new gameboard with all players being SimplePlayer
	public GameBoard(int nPlayers) {
		this();
		players = new AiPlayer[nPlayers];
		if (nPlayers == 2) {
			players[0] = new testPlayer1(this, DIM / 2, DIM - 1, 0, 2);
			players[1] = new testPlayer1(this, DIM / 2, 0, 1, 2);
		}
	}

	public GameBoard(AiPlayer[] players) {
		this();
		this.players = players;
	}

	public GameBoard() {
		currentPlayerIndex = 0;
		winningPlayer = Integer.MAX_VALUE;
		board = new Tile[DIM][DIM];
		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; j++) {
				board[i][j] = new Tile(i, j);
			}
		}
	}

	// returns a deep copy of gameboard
	public GameBoard(GameBoard G) {
		this();
		this.currentPlayerIndex = G.currentPlayerIndex;
		this.players = G.players;
		// TODO decide on player implementation
		// iterate through all tiles and copy contents in
		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; j++) {
				Tile newTile = this.board[i][j];
				Tile oldTile = G.board[i][j];
				for (int a = 0; a < 4; a++) {
					if (oldTile.adj[a] == null)
						newTile.adj[a] = null;
					else
						newTile.adj[a] = this.board[oldTile.adj[a].x][oldTile.adj[a].y];
				}
			}
		}
	}

	public void reset() {
		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; j++) {
				this.board[i][j] = new Tile(i, j);
			}
		}

		// add the edges

		Tile t1, t2;

		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; j++) {
				if (i < DIM - 1) {
					t1 = this.board[i][j];
					t2 = this.board[i + 1][j];
					t1.addNeighbour(Tile.RIGHT, t2);
					t2.addNeighbour(Tile.LEFT, t1);
				}
				if (j < DIM - 1) {
					t1 = this.board[i][j];
					t2 = this.board[i][j + 1];
					t1.addNeighbour(Tile.UP, t2);
					t2.addNeighbour(Tile.DOWN, t1);
				}
			}
		}
	}

	public void playMove(Move move) {
		//move.echo();
		move.playMove(this);
	}

	public void playTurn() {
		AiPlayer currentPlayer = players[currentPlayerIndex];
		Move m = currentPlayer.getNextMove();
		m.echo();
		playMove(m);
		if (currentPlayer.playerWon())
			winningPlayer = currentPlayerIndex;
		// shift to the next player
		currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
	}

	public Move[] generateMoves(AiPlayer player) {
		ArrayList<Move> moves = new ArrayList<>();

		Tile currentTile = this.board[player.x][player.y];

		// StdOut.println("Generating moves" + currentTile.x + "," +
		// currentTile.y);

		// generate player moves change to add the moves in the DLS itself
		ArrayList<Tile> possibleTiles = new ArrayList<>();
		depthLimitedSearch(currentTile, 1, player, possibleTiles);
		for (Tile t : possibleTiles) {
			moves.add(new PlayerMove(player, t.x, t.y));
			// System.out.println("Tile added, x:" + t.x + " :" + t.y);
		}

		if (player.remainingWalls > 0) {
			// generate all possible wall moves
			for (int i = 0; i < DIM; i++) {
				for (int j = 0; j < DIM; j++) {
					if (board[i][j].hasHorizontalMove())
						moves.add(new WallMove(player, i, j,
								WallMove.HORIZONTAL));
					if (board[i][j].hasVerticalMove())
						moves.add(new WallMove(player, i, j, WallMove.VERTICAL));
				}
			}
		}
		//StdOut.println("Generated " + moves.size() + " moves");

		Move[] m = new Move[moves.size()];
		for (int i = 0; i < m.length; i++)
			m[i] = moves.get(i);

		return m;
	}

	private void depthLimitedSearch(Tile current, int depth, AiPlayer player,
			ArrayList<Tile> tiles) {
		if (depth == 0) {
			// check if there is an enemy piece
			boolean hasEnemy = false;
			for (AiPlayer p : players) {
				if (p != player && p.x == current.x && p.y == current.y)
					hasEnemy = true;
			}

			if (hasEnemy) {
				// go one more round
				depthLimitedSearch(current, 1, player, tiles);
			} else {
				if (!tiles.contains(current))
					tiles.add(current);
			}
		} else {
			for (Tile t : current.adj) {
				if (t != null) {
					depthLimitedSearch(t, depth - 1, player, tiles);
				}
			}
		}

	}

}
