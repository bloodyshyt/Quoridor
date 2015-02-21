package Player;

import java.util.Random;

import main.GameBoard;
import main.Move;

public class SimplePlayer extends AiPlayer {

	public SimplePlayer(int x, int y, int playerNo) {
		super(x, y, playerNo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Move getNextMove(GameBoard G) {
		Move[] m = G.generateMoves(this);
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

	@Override
	public AiPlayer clone() {
		return new SimplePlayer(x, y, playerNo);
	}

}
