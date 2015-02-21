package Features;

import main.GameBoard;
import Player.AiPlayer;

public class PositionFromBaseline implements Feature {

	@Override
	public float evaluate(GameBoard G, AiPlayer player) {

		
		if(player.win_y == GameBoard.DIM - 1) // go up
			return player.y;
		if(player.win_y == 0) // go down
			return GameBoard.DIM - 1 - player.y;
		return 0.0f;
	}

}
