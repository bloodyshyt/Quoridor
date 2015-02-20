package Features;

import main.GameBoard;
import Player.AiPlayer;

public class PositionDifference implements Feature {
	// returns difference between player and opponent(s) from respective
	// baseline
	// player * nOpponent - sumOfOpponent (more is better)
	
	PositionFromBaseline positionFromBaseline = new PositionFromBaseline();

	@Override
	public float evaluate(GameBoard G, AiPlayer player) {
		int sumOfOpponent = 0;
		for (AiPlayer p : G.players) {
			if (p != player)
				sumOfOpponent += positionFromBaseline.evaluate(G, player);
		}

		return (sumOfOpponent - (G.players.length - 1)
				* positionFromBaseline.evaluate(G, player));
	}

}
