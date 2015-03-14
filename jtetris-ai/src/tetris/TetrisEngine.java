package tetris;

import java.util.LinkedList;
import java.util.Random;

import tetris.Shape.Tetrominoes;

// handles the game play of tetris and eventually graphics

public class TetrisEngine {
	Board board;
	int score = 0;
	int linesCleared = 0;
	AI ai;
	Shape curr, next;

	Random random = new Random(System.currentTimeMillis());

	LinkedList<Integer> indices = new LinkedList<>();

	public int startGame(double[] weights) {
		ai = new AI(weights);
		linesCleared = 0;
		board = new Board();
		curr = new Shape();
		curr.setShape(Tetrominoes.values()[getNextIndice()]);
		
		while(!ai.gameOver(board, curr)) {
			Move m = ai.getNextMove(board, curr, next);
			playMove(m);
			//linesCleared += ai.aiMove(board, curr);
			//System.out.println(linesCleared + " lines cleared");
			curr.setShape(Tetrominoes.values()[getNextIndice()]);
		}
		
		
		
		System.out.println("Game ended with "
				+ linesCleared + " lines cleared");
		return linesCleared;
	}

	public void playMove(Move m) {
		board.dropDown(m.x, m.mino, m.value);
		// update stuff
		int cleared = board.updateBoard();
		if (cleared == 4)
			score += 800;
		else
			score += 100 * cleared;
		linesCleared += cleared;

		// print stuff out
		board.printTrucBoard();
		System.out.println("score of " + score + " with " + linesCleared
				+ " lines cleared");
	}

	public static void main(String[] str) {
		TetrisEngine eng = new TetrisEngine();
		eng.startGame(new double[] {20, 1, 1, 1, 1});
	} 

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
}
