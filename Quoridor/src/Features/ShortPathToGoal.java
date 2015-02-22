package Features;

import main.GameBoard;
import Player.AiPlayer;

public class ShortPathToGoal implements Feature {

	@Override
	public float evaluate(GameBoard G, AiPlayer player) {
		return (float) Math.pow(G.BFS(player)[2], -1);
	}

}
