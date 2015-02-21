package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Features.Feature;
import Features.MovesToNextColumn;
import Features.PositionDifference;
import Features.PositionFromBaseline;
import Features.opponentMovesToNextColumn;
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
		if(move instanceof WallMove) wallMoves.add((WallMove) move);
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
						moves.add(new WallMove(player, i, j,
								WallMove.HORIZONTAL));
						moves.add(new WallMove(player, i, j, WallMove.VERTICAL));
					}
				}
			}
			
			depthLimitedSearch(currentTile, 1, player, possibleTiles);
			for (Tile t : possibleTiles) {
				moves.add(new PlayerMove(player, t.x, t.y));
				// System.out.println("Tile added, x:" + t.x + " :" + t.y);
			}
		} else {
			// player no more walls, find the shortest to goal
			
		}
		// StdOut.println("Generated " + moves.size() + " moves");

		Move[] m = new Move[moves.size()];
		for (int i = 0; i < m.length; i++)
			m[i] = moves.get(i);

		return m;
	}
	
	public int[] BFS(AiPlayer player) {
		Tile[][] prev = new Tile[GameBoard.DIM][GameBoard.DIM];
		int[] goalCords = new int[2];
		goalCords = getGoalCords(player);
		StdOut.println("Goal cords (" + goalCords[0] + "," + goalCords[1] + ")");
		Tile source = this.board[player.x][player.y];
		// initiaiise prev array
		for(int i = 0; i < GameBoard.DIM; i++) {
			for(int j = 0; j < GameBoard.DIM; j++) {
				prev[i][j] = null;
			}
		} 
		
		prev[source.x][source.y] = source;
		Queue<Tile> Q = new LinkedList<>();
		Q.add(source);
		Tile v, winningTile = null;
		while(!Q.isEmpty()) {
			v = Q.remove();
			for(Tile w : v.adj) {
				if(w != null && prev[w.x][w.y] == null) {
					prev[w.x][w.y] = v;
					if(w.x == goalCords[0] || w.y == goalCords[1]) {
						winningTile = w;
						break;
					}
					Q.add(w);
				}
			}
		}
		
		if(winningTile == null) {
			// there is no path to win, illegal move
			return null;
		} else {
			Tile t = winningTile;
			while(prev[t.x][t.y] != source) {
				t = prev[t.x][t.y];
			}
			// t is now the first tile to shortest path
			return new int[] {t.x, t.y};
		}
		
	}
	
	private int[] getGoalCords(AiPlayer player) {
		int goal_x = Integer.MAX_VALUE, goal_y = Integer.MAX_VALUE;
		int x = player.x;
		int y = player.y;

		// determine direction towards goal
		int goalDirection = 0;
		if (player.win_x == 0) {
			goalDirection = Tile.LEFT;
			goal_x = x - 1;
			goal_y = Integer.MAX_VALUE;
		}
		if (player.win_x == GameBoard.DIM - 1) {
			goalDirection = Tile.RIGHT;
			goal_x = x + 1;
			goal_y = Integer.MAX_VALUE;
		}
		if (player.win_y == 0) {
			goalDirection = Tile.DOWN;
			goal_x = Integer.MAX_VALUE;
			goal_y = y - 1;
		}
		if (player.win_y == GameBoard.DIM - 1) {
			goalDirection = Tile.UP;
			goal_x = Integer.MAX_VALUE;
			goal_y = y + 1;
		}
		
		return new int[] {goal_x, goal_y};

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
