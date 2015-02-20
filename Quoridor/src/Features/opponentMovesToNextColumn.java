package Features;

import main.GameBoard;
import Player.AiPlayer;

public class opponentMovesToNextColumn implements Feature {

	@Override
	public float evaluate(GameBoard G, AiPlayer player) {
		MovesToNextColumn m = new MovesToNextColumn();
		int sum = 0;
		for(AiPlayer p : G.players) 
			if(p != player) sum += m.evaluate(G, p);
		return sum;
	}

}
