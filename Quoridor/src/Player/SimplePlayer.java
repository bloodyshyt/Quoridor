package Player;

import java.util.Random;

import main.GameBoard;
import main.Move;

public class SimplePlayer extends AiPlayer {

	public SimplePlayer(GameBoard board, int x, int y, int playerNo) {
		super(board, x, y, playerNo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Move getNextMove() {
		Move[] m = board.generateMoves(this);
		shuffleArray(m);
		return m[0];
	}

	// Implementing Fisher–Yates shuffle
	static void shuffleArray(Object[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			Object a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

}
