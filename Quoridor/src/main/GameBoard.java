package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Player.AiPlayer;
import Player.basePlayer;
import Player.testPlayer1;
import Player.testPlayer2;

public class GameBoard {

	public static final int DIM = 9;

	public Tile[][] board;
	public AiPlayer[] players;
	public int currentPlayerIndex;
	public int winningPlayer;

	public ArrayList<WallMove> wallMoves;

	// creates a new gameboard with all players being SimplePlayer
	public GameBoard(int nPlayers) {
		this();
		players = new AiPlayer[nPlayers];
		if (nPlayers == 2) {
			players[0] = new testPlayer1(DIM / 2, DIM - 1, 0, 2);
			players[1] = new testPlayer1(DIM / 2, 0, 1, 2);

		}
	}
	
	public GameBoard(int[][] playerStates) {
		this();
		players = new AiPlayer[2];
		players[0] = new basePlayer(0, 2, playerStates[0]);
		players[1] = new basePlayer(1, 2, playerStates[1]);
		
	}

	public GameBoard(AiPlayer[] players) {
		this();
		this.players = players;
	}

	public GameBoard() {
		currentPlayerIndex = 0;
		winningPlayer = Integer.MAX_VALUE;
		board = new Tile[DIM][DIM];
		wallMoves = new ArrayList<>();
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
		// move.echo();
		if (move instanceof WallMove)
			wallMoves.add((WallMove) move);
		move.playMove(this);
	}

	public void undoMove(Move move) {
		move.undoMove(this);
	}

	public void playTurn() {
		AiPlayer currentPlayer = players[currentPlayerIndex];
		Move m = currentPlayer.getNextMove(this);
		playMove(m);
		m.echo();
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

		if (player.remainingWalls > 0) {
			// generate all possible wall and player moves
			for (int i = 1; i < DIM - 1; i++) {
				for (int j = 1; j < DIM - 1; j++) {
					if (board[i][j].hasWallMove()) {
						Move h = new WallMove(player, i, j,
								WallMove.HORIZONTAL);
						Move v = new WallMove(player, i, j,
								WallMove.VERTICAL);
						if(isMoveLegal(h)) moves.add(h);
						if(isMoveLegal(v)) moves.add(v);
					}
				}
			}
			
			// generate player moves
			for(int i = 0; i < 4; i++) {
				if(currentTile.adj[i] != null) {
					Tile t = currentTile.adj[i];
					// check if there is a pawn
					if(tileHasPawn(t)) {
						// go again in the same direction
						if(t.adj[i] != null)
							possibleTiles.add(t.adj[i]);
						else {
							// check in the two side directions
							int d1 = (4 + i + 1) % 4;
							int d2 = (4 + i - 1) % 4;
							if(t.adj[d1] != null) 
								possibleTiles.add(t.adj[d1]);
							if(t.adj[d2] != null && !possibleTiles.contains(t.adj[d2])) 
								possibleTiles.add(t.adj[d2]);
						}
					} else {
						// add as per normal
						possibleTiles.add(t);
					}
				}
			}
			for (Tile t : possibleTiles) {
				moves.add(new PlayerMove(player, t.x, t.y));
				// System.out.println("Tile added, x:" + t.x + " :" + t.y);
			}
		} else {
			// player no more walls, find the shortest to goal
			int[] coords = BFS(player);
			moves.add(new PlayerMove(player, coords[0], coords[1]));

		}
		// StdOut.println("Generated " + moves.size() + " moves");

		Move[] m = new Move[moves.size()];
		for (int i = 0; i < m.length; i++)
			m[i] = moves.get(i);

		return m;
	}
	
	private boolean tileHasPawn(Tile tile) {
		for(AiPlayer p : players) {
			if(p.x == tile.x && p.y == tile.y)
				return true;
		}
		return false;
	}

	public boolean isMoveLegal(Move move) {
		this.playMove(move);
		boolean legal = true;
		for(AiPlayer player: players) {
			if(BFS(player) == null) legal = false;
		}
		this.undoMove(move);
		return legal;
	}

	public int[] BFS(AiPlayer player) {
		Tile[][] prev = new Tile[GameBoard.DIM][GameBoard.DIM];
		// StdOut.println("Goal cords (" + goalCords[0] + "," + goalCords[1] +
		// ")");
		Tile source = this.board[player.x][player.y];
		// initiaiise prev array
		for (int i = 0; i < GameBoard.DIM; i++) {
			for (int j = 0; j < GameBoard.DIM; j++) {
				prev[i][j] = null;
			}
		}

		prev[source.x][source.y] = source;
		Queue<Tile> Q = new LinkedList<>();
		Q.add(source);
		Tile v, winningTile = null;
		outerloop:
		while (!Q.isEmpty()) {
			v = Q.remove();
			for (Tile w : v.adj) {
				if (w != null && prev[w.x][w.y] == null) {
					prev[w.x][w.y] = v;
					if (w.x == player.win_x || w.y == player.win_y) {
						winningTile = w;
						break outerloop;
					}
					Q.add(w);
				}
			}
		}

		if (winningTile == null) {
			// there is no path to win, illegal move
			return null;
		} else {
			Tile t = winningTile;
			int nSteps = 1;
			while (prev[t.x][t.y] != source) {
				t = prev[t.x][t.y];
				//StdOut.println("now at: (" + t.x + "," + t.y + ")");
				nSteps++;
			}
			//StdOut.println("Took " + nSteps + " steps");
			// t is now the first tile to shortest path
			return new int[] { t.x, t.y, nSteps++ };
		}

	}
}
