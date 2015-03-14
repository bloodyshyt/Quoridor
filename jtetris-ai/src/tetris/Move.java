package tetris;

public class Move {
	int[][] mino;
	int x;
	int value;
	
	public Move(int[][] m, int _x, int v) {
		mino = m;
		x = _x;
		value = v;
	}
}
