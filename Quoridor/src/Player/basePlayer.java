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

public class basePlayer extends AiPlayer {

	Feature[] features;
	float[] weights;
	int depth;

	private AiPlayer[] players;
	private GameBoard board;
	private int thisPlayerIndex;

	private static Random random;

	public basePlayer(int playerNo, int depth, int[] features) {
		super(4, (playerNo == 0) ? 8 : 0, playerNo);
		this.depth = depth;

		PositionFromBaseline f1 = new PositionFromBaseline();
		PositionDifference f2 = new PositionDifference();
		MovesToNextColumn f3 = new MovesToNextColumn();
		opponentMovesToNextColumn f4 = new opponentMovesToNextColumn();
		Feature[] f = new Feature[] {null, f1, f2, f3, f4};
		float[] w = new float[] {0f, 0.6f, 0.6001f,14.45f, 6.52f};
		
		random = new Random(System.currentTimeMillis());

		this.features = new Feature[features.length];
		this.weights = new float[features.length];
		for(int i = 0; i < features.length; i++)  {
			this.features[i] = f[features[i]];
			this.weights[i] = w[features[i]];
		}

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
