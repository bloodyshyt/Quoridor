package main;

import java.awt.Color;

import Player.AiPlayer;
import Player.SimplePlayer;
import Std.StdDraw;
import Std.StdOut;

public class MainActivity {

	private final int windowDim = 1000;
	private final int padding = 100;
	private final int scale = 100;
	
	private final Color p1 = Color.BLUE;
	private final Color p2 = Color.YELLOW;
	private final Color p3 = Color.GREEN;
	private final Color p4 = Color.RED;
	private final Color[] colors = {p1, p2, p3, p4};
	
	private GameBoard board;

	public MainActivity() {

		// set the scale for drawing on screen
		StdDraw.setScale(0, windowDim);
		board = new GameBoard(2);
		board.reset();

	}

	// draw the board
	public void draw() {
		// draw the tile as dots
		for (int i = 0; i < GameBoard.DIM; i++) {
			for (int j = 0; j < GameBoard.DIM; j++) {
				// draw the dot
				StdDraw.setPenColor(StdDraw.BLACK);
				//if(i == 0 && j == 0) StdDraw.setPenColor(StdDraw.RED);
				StdDraw.setPenRadius(0.05);
				int x, y;
				x = padding + i * scale;
				y = padding + j * scale;
				StdDraw.point(x, y);
				// add the lines
				Tile t = this.board.board[i][j];
				if (t.hasNeighbour(Tile.UP)) {
					StdDraw.setPenRadius(0.001);
					StdDraw.line(x, y, x, y + scale);
				}
				if (t.hasNeighbour(Tile.RIGHT)) {
					StdDraw.setPenRadius(0.001);
					StdDraw.line(x, y, x + scale, y);
				}
				if (t.hasNeighbour(Tile.DOWN)) {
					StdDraw.setPenRadius(0.001);
					StdDraw.line(x, y, x, y - scale);
				}
				if (t.hasNeighbour(Tile.LEFT)) {
					StdDraw.setPenRadius(0.001);
					StdDraw.line(x, y, x - scale, y);
				}
			}
		}
		
		// add the numbers on the axis
		for(int x = 0; x < GameBoard.DIM; x++) {
			StdDraw.text(x * scale + padding, padding / 2, x + "");
			StdDraw.text(padding / 2, x * scale + padding, x + "");
		}
		
		// color the tiles with players 
		for(AiPlayer p : board.players) {
			drawDot(board.board[p.x][p.y], colors[p.playerNo]);
		}
		
	}
	
	private void drawDot(Tile t, Color color) {
		int x, y;
		x = padding + t.x * scale;
		y = padding + t.y * scale;
		StdDraw.setPenColor(color);
		StdDraw.setPenRadius(0.05);
		StdDraw.point(x, y);
		
	}

	// client to display board
	public static void main(String[] args) {
		MainActivity main = new MainActivity();
		//main.draw();
		StdDraw.show(10);
		main.draw();
		
		// test the move 
		//WallMove m1 = new WallMove(null, 1, 1, WallMove.HORIZONTAL);
		//main.board.playMove(m1);
		//StdDraw.clear();
		//main.draw();
		
		// ensure that the edges have been added
		for(int i = 0 ; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				StdOut.print("Tile (" + main.board.board[i][j].x + "," + main.board.board[i][j].y + "):");
				for(Tile t : main.board.board[i][j].adj) {
					if(t != null) StdOut.print(" (" + t.x + "," + t.y + ")");
				}
				StdOut.println();
			}
		}
		StdDraw.show(10);
		
		// test the generate moves function WORKS!
		//main.board.generateMoves(main.board.players[0]);
		
		// lets try for 20 turns!
		for(int i = 0; i < 20; i++) {
			main.board.playTurn();
			StdDraw.clear();
			main.draw();
			try {
			    Thread.sleep(1000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	
	}

}
