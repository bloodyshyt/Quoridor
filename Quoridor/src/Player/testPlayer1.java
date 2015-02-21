package Player;

import java.util.Random;

import main.GameBoard;
import main.Move;
import Features.Feature;
import Features.MovesToNextColumn;
import Features.PositionDifference;
import Features.PositionFromBaseline;
import Features.opponentMovesToNextColumn;
import Std.StdOut;

public class testPlayer1 extends AiPlayer {

	Feature[] features;
	float[] weights;
	int depth;

	private AiPlayer[] players;
	private GameBoard board;
	private int thisPlayerIndex;

	private static Random random;

	public testPlayer1(int x, int y, int playerNo, int depth) {
		super(x, y, playerNo);
		this.depth = depth;

		random = new Random(System.currentTimeMillis());

		PositionFromBaseline f1 = new PositionFromBaseline();
		PositionDifference f2 = new PositionDifference();
		MovesToNextColumn f3 = new MovesToNextColumn();
		opponentMovesToNextColumn f4 = new opponentMovesToNextColumn();
		features = new Feature[] {f1, f2, f4};
		weights = new float[] {0.6f, 0.6001f,6.52f};
				

	}

	@Override
	public Move getNextMove(GameBoard G) {
		board = G;
		this.players = G.players;
		Object[] stuff = (Object[]) minimax(this, depth);
		StdOut.println("Final score of " + stuff[1]);
		return (Move) stuff[0];
	}

	// returns an object array of {move, score}
	private Object[] minimax(AiPlayer player, int depth) {
		//StdOut.println("In depth " + depth + " for player " + player.playerNo);

		Move[] moves = board.generateMoves(player);
		float bestScore = (player.playerNo == this.playerNo) ? Float.MIN_VALUE
				: Float.MAX_VALUE;
		float currentScore;
		Move bestMove = null;

		if (moves.length == 0 || depth == 0) {
			bestScore = evaluateBoard(player);
		}

		else {
			for (Move move : moves) {
				// try move for this player
				board.playMove(move);
				if (player.playerNo == this.playerNo) {
					currentScore = (float) minimax(
							players[(player.playerNo + 1) % players.length],
							depth - 1)[1];
					if (currentScore > bestScore) {
						bestScore = currentScore;
						bestMove = move;
					}
				} else {
					currentScore = (float) minimax(
							players[(player.playerNo + 1) % players.length],
							depth - 1)[1];
					if (currentScore < bestScore) {
						bestScore = currentScore;
						bestMove = move;
					}

				}

				board.undoMove(move);
			}
		}
		return new Object[] { bestMove, bestScore };
	}

	private float evaluateBoard(AiPlayer player) {
		float score = 0;
		for (int i = 0; i < features.length; i++) {
			score += weights[i] * features[i].evaluate(board, player);
		}
		score = (float) (score + random.nextFloat());
		//StdOut.println(score + "");
		return score;
	}

}
