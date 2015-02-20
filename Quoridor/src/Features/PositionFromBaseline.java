package Features;

import main.GameBoard;
import Player.AiPlayer;

public class PositionFromBaseline implements Feature {

	@Override
	public float evaluate(GameBoard G, AiPlayer player) {

		if (player.win_x != GameBoard.DIM + 1)
			return Math.abs((GameBoard.DIM - 1 - player.x));
		else
			return Math.abs((GameBoard.DIM - 1 - player.y));
	}

}
