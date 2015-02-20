package Player;

import java.util.Random;

import main.GameBoard;
import main.Move;
import Features.Feature;
import Features.MovesToNextColumn;
import Features.PositionDifference;
import Features.PositionFromBaseline;
import Features.opponentMovesToNextColumn;

public class testPlayer1 extends AiPlayer {

	Feature[] features;
	float[] weights;
	int depth;

	private AiPlayer[] players;
	private int thisPlayerIndex;
	
	private Random random;

	public testPlayer1(GameBoard board, int x, int y, int playerNo, int depth) {
		super(board, x, y, playerNo);
		this.depth = depth;
		this.players = board.players;
		
		random = new Random(System.currentTimeMillis());

		PositionFromBaseline f1 = new PositionFromBaseline();
		PositionDifference f2 = new PositionDifference();
		MovesToNextColumn f3 = new MovesToNextColumn();
		opponentMovesToNextColumn f4 = new opponentMovesToNextColumn();
		features = new Feature[]{f1, f2, f3, f4};
		weights = new float[] { 0.6f, 0.6001f, 14.45f, 6.52f};
		
		
	}

	@Override
	public Move getNextMove() {
		return (Move) minimax(board, this, depth)[0];
	}
	
	// returns an object array of {move, score}
	private Object[] minimax(GameBoard G, AiPlayer player, int depth) {
		//StdOut.println("In depth " + depth + " for player " + player.playerNo);
		
		Move[] moves  = G.generateMoves(player);
		float bestScore = (player == this) ? Float.MIN_VALUE : Float.MAX_VALUE;
		float currentScore;
		Move bestMove = null;
		
		if(moves.length == 0 || depth == 0) {
			bestScore = evaluateBoard(G, player);
		}
		
		else {
			for(Move move : moves) {
				// try move for this player
				GameBoard _G = new GameBoard(G);
				_G.playMove(move);
				currentScore = (float) minimax(_G, players[(player.playerNo + 1)
						% players.length], depth  - 1)[1];
				if(player == this) {
					if(currentScore > bestScore) {
						bestScore = currentScore;
						bestMove = move;
					}
				} else {
					if(currentScore < bestScore) {
						bestScore = currentScore;
						bestMove = move;
					}
				
				}
			}
		}
		
		return new Object[] {bestMove, bestScore};
	}

	
	private float evaluateBoard(GameBoard g, AiPlayer player) {
		float score = 0;
		for (int i = 0; i < features.length; i++) {
			score += weights[i] * features[i].evaluate(g, player);
		}
		return score + 40 * random.nextFloat();
	}

}
