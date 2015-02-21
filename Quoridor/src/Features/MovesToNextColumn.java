package Features;

import java.util.LinkedList;
import java.util.Queue;

import main.GameBoard;
import main.Tile;
import Player.AiPlayer;
import Std.StdOut;

public class MovesToNextColumn implements Feature {

	GameBoard G;
	AiPlayer player;

	@Override
	public float evaluate(GameBoard G, AiPlayer player) {
		this.player = player;
		this.G = G;
		return (float) Math.pow(BFS(G, player), -1);
	}

	public int BFS(GameBoard G, AiPlayer player) {
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

		int[][] steps = new int[GameBoard.DIM][GameBoard.DIM];
		for (int i = 0; i < GameBoard.DIM; i++)
			for (int j = 0; j < GameBoard.DIM; j++) {
				steps[i][j] = -1;
			}

		//StdOut.println("Goal of (" + goal_x + "," + goal_y + ")");
		
		Queue<Tile> Q = new LinkedList<>();
		Q.add(G.board[x][y]);
		steps[x][y] = 0;
		while (!Q.isEmpty()) {
		
			Tile v = Q.remove();
			for(Tile t : v.adj) {
				if(t != null && steps[t.x][t.y] == -1) {
					steps[t.x][t.y] = steps[v.x][v.y] + 1;
					if(t.x == goal_x || t.y == goal_y) {
						//StdOut.println("Moves to next column: " + steps[t.x][t.y]);
						return steps[t.x][t.y]; 
					}
					Q.add(t);
				}
			}
		}
		
		return 0;

	}
}
