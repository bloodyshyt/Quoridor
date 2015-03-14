package tetris;

import java.util.Random;

public class Shape {

	enum Tetrominoes {
		I, J, L, O, S, T, Z
	};

	private Tetrominoes pieceShape;
	public int pMinos[][][]; // all possible orientations
	private static int[][][][] coordsTables;
	
	

	public void setShape(Tetrominoes shape) {
		int[][][] I = new int[][][] {
				{ { -2, 0 }, { -1, 0 }, { 0, 0 }, { 1, 0 } },
				{ { 0, 1 }, { 0, 0 }, { 0, -1 }, { 0, -2 } } };
		int[][][] J = new int[][][] {
				{ { -1, 1 }, { -1, 0 }, { 0, 0 }, { 0, 1 } },
				{ { 1, 1 }, { 0, 1 }, { 0, 0 }, { 0, -1 } },
				{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 1, -1 } },
				{ { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } } };
		int[][][] L = new int[][][] {
				{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },
				{ { 0, 1 }, { 0, 0 }, { 0, -1 }, { 1, -1 } },
				{ { -1, -1 }, { -1, 0 }, { 0, 0 }, { 1, 0 } },
				{ { -1, 1 }, { 0, 1 }, { 0, 0 }, { 0, -1 } } };
		int[][][] O = new int[][][] { { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 1, 0 } } };
		int[][][] S = new int[][][] {
				{ { -1, 0 }, { 0, 0 }, { 0, 1 }, { 1, 1 } },
				{ { 0, 1 }, { 0, 0 }, { 1, 0 }, { 1, -1 } },
				{ { -1, -1 }, { 0, -1 }, { 0, 0 }, { 1, 0 } },
				{ { -1, -1 }, { -1, 0 }, { 0, 0 }, { 0, -1 } } };
		int[][][] T = new int[][][] {
				{ { -1, 0 }, { 0, 0 }, { 0, 1 }, { 1, 0 } },
				{ { 0, 1 }, { 0, 0 }, { 0, -1 }, { 1, 0 } },
				{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, -1 } },
				{ { -1, 0 }, { 0, 0 }, { 0, 1 }, { 0, -1 } } };
		int[][][] Z = new int[][][] {
				{ { -1, 1 }, { 0, 1 }, { 0, 0 }, { 1, 0 } },
				{ { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },
				{ { -1, 0 }, { 0, 0 }, { 0, -1 }, { 1, -1 } },
				{ { -1, -1 }, { -1, 0 }, { 0, 0 }, { 0, 1 } } };
		coordsTables = new int[][][][] {I, J, L, O, S, T, Z};
		pieceShape = shape;
		pMinos = coordsTables[shape.ordinal()];
	}
	
	public int getValue() {return pieceShape.ordinal() + 1;}
	public int getOrdinal(){return pieceShape.ordinal(); }
	public Tetrominoes getShape() {return pieceShape;}
	
	public void getRandomShape() {
		Random r = new Random(System.currentTimeMillis());
		Tetrominoes values[] = Tetrominoes.values();
		setShape(values[r.nextInt(7)]);
	}
	

	public static void main(String[] args) {
		// ensure that all the minos are encoded correctly
	
		int[][][] I = new int[][][] {
				{ { -2, 0 }, { -1, 0 }, { 0, 0 }, { 1, 0 } },
				{ { 0, 1 }, { 0, 0 }, { 0, -1 }, { 0, -2 } } };
		int[][][] J = new int[][][] {
				{ { -1, 1 }, { -1, 0 }, { 0, 0 }, { 0, 1 } },
				{ { 1, 1 }, { 0, 1 }, { 0, 0 }, { 0, -1 } },
				{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 1, -1 } },
				{ { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } } };
		int[][][] L = new int[][][] {
				{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },
				{ { 0, 1 }, { 0, 0 }, { 0, -1 }, { 1, -1 } },
				{ { -1, -1 }, { -1, 0 }, { 0, 0 }, { 1, 0 } },
				{ { -1, 1 }, { 0, 1 }, { 0, 0 }, { 0, -1 } } };
		int[][][] O = new int[][][] { { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 1, 0 } } };
		int[][][] S = new int[][][] {
				{ { -1, 0 }, { 0, 0 }, { 0, 1 }, { 1, 1 } },
				{ { 0, 1 }, { 0, 0 }, { 1, 0 }, { 1, -1 } },
				{ { -1, -1 }, { 0, -1 }, { 0, 0 }, { 1, 0 } },
				{ { -1, -1 }, { -1, 0 }, { 0, 0 }, { 0, -1 } } };
		int[][][] T = new int[][][] {
				{ { -1, 0 }, { 0, 0 }, { 0, 1 }, { 1, 0 } },
				{ { 0, 1 }, { 0, 0 }, { 0, -1 }, { 1, 0 } },
				{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, -1 } },
				{ { -1, 0 }, { 0, 0 }, { 0, 1 }, { 0, -1 } } };
		int[][][] Z = new int[][][] {
				{ { -1, 1 }, { 0, 1 }, { 0, 0 }, { 1, 0 } },
				{ { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },
				{ { -1, 0 }, { 0, 0 }, { 0, -1 }, { 1, -1 } },
				{ { -1, -1 }, { -1, 0 }, { 0, 0 }, { 0, 1 } } };
		coordsTables = new int[][][][] {I, J, L, O, S, T, Z};
	
		for(int[][][] shape : coordsTables) {
			for(int[][] mino : shape) {
				int[][] grid = new int[4][4];
				int offset = 2;
				for(int[] coords : mino) {
					grid[coords[0] + offset][coords[1] + offset] = 8;
				}
				for(int x = 0; x < 4; x++) {
					for(int y = 0; y < 4; y++) {
						System.out.print(grid[x][y]);
					}
					System.out.println();
				}
				System.out.println();
			}
		}
	}
}
