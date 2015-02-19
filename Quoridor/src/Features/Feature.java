package Features;

import Player.AiPlayer;
import main.GameBoard;

public abstract interface Feature {
	// evaluates the GameBoard based on a feature respective to the current player
	public abstract float evaluate(GameBoard G, AiPlayer player);
}
