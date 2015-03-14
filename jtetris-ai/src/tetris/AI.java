package tetris;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import tetris.Shape.Tetrominoes;

public class AI {

	double[] weights = new double[] { -0.66569, 1.99275, -0.46544, -0.24077 };

	Random random = new Random(System.currentTimeMillis());

	LinkedList<Integer> indices = new LinkedList<>();

	public int getNextIndice() {

		if (indices.size() == 0) {
			for (int x = 0; x < 7; x++)
				indices.add(x);
		}

		int i = indices.get(random.nextInt(indices.size()));
		int j = i;
		indices.remove((Object) i);
		return j;
	}

	public AI(double weights[]) {
		this.weights = weights;
	}

	public AI() {
	}

	public Move getNextMove(Board bb, Shape curr, Shape next) {

		Board g = new Board(bb);

		double bestScore = Double.POSITIVE_INFINITY;
		double score;
		Move bestMove = null;
		int bestX = 0;
		int[][] bestM = null;
		Board bestBoard = null;

		// do 1 plys earch instead
		for (int[][] m : curr.pMinos) {
			for (int x = 0; x < g.BoardWidth + 1; x++) {
				Board b = new Board(g);
				if (!b.dropDown(x, m, curr.getValue()))
					continue;
				b.updateBoard();
				// b.printTrucBoard();
				score = evalaute(b);
				// System.out.println(score);
				if (score < bestScore) {
					bestBoard = new Board(b);
					bestScore = score;
					bestX = x;
					bestM = m;
				}

			}
		}
		System.out.println("****BEST*STATE**********");
		bestBoard.printTrucBoard();
		System.out.println("Score of " + bestScore);
		System.out.println("****END*OF*STATE**********");
		bestMove = new Move(bestM, bestX, curr.getValue());
		return bestMove;
		// }
	}

	// checks if there are still legal moves for the next tetrimino
	public static boolean gameOver(Board b, Shape next) {
		for (int[][] m : next.pMinos) {
			for (int x = 0; x < b.BoardWidth; x++) {
				for (int y = b.heights[x]; y < b.BoardHeight; y++) {
					if (b.tryMove(m, x, y))

						return false;
				}
			}
		}
		return true;
	}

	// evaluate functions ----------------------------------------------------
	public void testPrint(Board g) {
		double score = 0;
		System.out.print("Ag. H: " + aggregateHeight(g));
		System.out.print(" CL: " + completeLines(g));
		System.out.print(" Holes: " + holes(g));
		System.out.print(" Bp: " + bumpiness(g));
		System.out.println();
	}

	private double evalaute(Board g) {
		double score = 0;
		// score += -0.66569 * aggregateHeight(g);
		// score += 1.99275 * completeLines(g);
		// score += -0.46544 * holes(g);
		// score += -0.24077 * bumpiness(g);
		// // System.out.println("Score of: " + score);

		score += holes(g);
		score += heightOfTallestCell(g);
		score += nCells(g);
		score += bumpiness(g);
		score += weightedCells(g);
		return score;
	}

	private int aggregateHeight(Board g) {
		int sum = 0;
		for (int i : g.heights)
			sum += i;
		return sum;
	}

	private int heightOfTallestCell(Board g) {
		int h = 0;
		for (int i : g.heights)
			h = Math.max(h, i);
		return h;
	}

	private int nCells(Board b) {
		int cells = 0;
		for (int x = 0; x < b.BoardWidth; x++) {
			for (int y = 0; y < b.BoardHeight; y++) {
				if (b.grid[x][y] != 0)
					cells++;
			}
		}
		return cells;
	}

	private int weightedCells(Board b) {
		int wCells = 0;
		for (int x = 0; x < b.BoardWidth; x++) {
			for (int y = 0; y < b.BoardHeight; y++) {
				if (b.grid[x][y] != 0)
					wCells += y + 1;
			}
		}
		return wCells;
	}

	// score
	private int completeLines(Board g) {
		int completeLines = 0;
		for (int i = 0; i < g.BoardHeight; i++)
			if (g.isLineComplete(i))
				completeLines++;
		return completeLines;
	}

	// holes
	private int holes(Board g) {
		int holes = 0;
		for (int x = 0; x < g.BoardWidth; x++) {
			for (int y = 0; y < g.BoardHeight; y++) {
				if (g.grid[x][y] == 0 && y < g.heights[x])
					holes++;
			}
		}
		return holes;
	}

	int get(int x, int y, int[][] grid) {
		return grid[y][x];
	}

	private int bumpiness(Board g) {
		int bump = 0;
		for (int i = 1; i < g.heights.length; i++) {
			bump += Math.abs(g.heights[i] - g.heights[i - 1]);
		}
		return bump;
	}

	public int aiMove(Board b, Shape next) {
		Move m = getNextMove(new Board(b), next, next);
		// b.printTrucBoard();
		b.dropDown(m.x, m.mino, next.getValue());
		int lines = b.updateBoard();
		// System.out.println("best sit");
		//b.printTrucBoard();
		return lines;
	}

	
	public static void main(String[] args) {
		int[][] grid = new int[Board.BoardWidth][Board.BoardHeight];
		for (int x = 0; x < 9; x++) {
			grid[x][0] = 1;
			grid[x][1] = 1;
			grid[x][2] = 1;
			grid[x][3] = 1;
			grid[x][4] = 1;
			grid[x][5] = 1;
			grid[x][6] = 1;

		}
		Board b = new Board();
		// b.grid = grid;
		Shape s = new Shape();
		s.setShape(Tetrominoes.I);
		Shape o = new Shape();
		o.setShape(Tetrominoes.O);
		AI ai = new AI();
//		ai.test(b, s);
//		ai.test(b, s);
//		ai.test(b, o);
//		for (int i = 0; i < 40; i++) {
//			s.setShape(Tetrominoes.values()[ai.getNextIndice()]);
//			ai.test(b, s);
//		}
	}
}
