package Features;

import main.GameBoard;
import Player.AiPlayer;
import Std.StdOut;

public class PositionDifference implements Feature {
	// returns difference between player and opponent(s) from respective
	// baseline
	// player * nOpponent - sumOfOpponent (more is better)
	
	PositionFromBaseline positionFromBaseline = new PositionFromBaseline();

	@Override
	public float evaluate(GameBoard G, AiPlayer player) {
		int sumOfOpponent = 0;
		for (int i = 0; i < G.players.length; i++) {
			if (i != player.playerNo) {
				sumOfOpponent += positionFromBaseline.evaluate(G, G.players[i]);
			}
		}

		return ((G.players.length - 1) * positionFromBaseline.evaluate(G, player) - sumOfOpponent);
	}

}
