package Features;

import main.GameBoard;
import Player.AiPlayer;

public class opponentMovesToNextColumn implements Feature {
	
	GameBoard G;
	AiPlayer player;

	@Override
	public float evaluate(GameBoard G, AiPlayer player) {
		MovesToNextColumn m = new MovesToNextColumn();
		this.player = player;
		this.G = G;
		
		int sum = 0;
		for(int i = 0; i < G.players.length; i++)
			if(i != player.playerNo) sum += m.BFS(G, G.players[i]);
		return sum;
	}

}
