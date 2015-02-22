package Features;

import main.GameBoard;
import Player.AiPlayer;

public class OpponentShortPathToGoal implements Feature {

	@Override
	public float evaluate(GameBoard G, AiPlayer player) {
		int sum = 0;
		for(int i = 0; i < G.players.length; i++) {
			if(i != player.playerNo) sum += G.BFS(G.players[i])[2];
		}
		return sum;
	}

}
