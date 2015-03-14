package tetris;

public class Board {

	final static int BoardWidth = 10;
	final static int BoardHeight = 22;

	int[][] grid = new int[BoardWidth][BoardHeight];
	int[] heights = new int[BoardWidth]; // height of each column

	public Board(Board b) {
		for (int x = 0; x < BoardWidth; x++) {
			this.heights[x] = b.heights[x];
			for (int y = 0; y < BoardHeight; y++) {
				this.grid[x][y] = b.grid[x][y];
			}
		}
	}

	public Board() {
		for (int x = 0; x < BoardWidth; x++) {
			heights[x] = 0;
			for (int y = 0; y < BoardHeight; y++) {
				grid[x][y] = 0;
			}
		}

	}

	public void printBoard() {
		for (int y = BoardHeight - 1; y >= 0; y--) {
			System.out.print("|");
			for (int x = 0; x < BoardWidth; x++) {
				if (grid[x][y] == 0)
					System.out.print(" ");
				else
					System.out.print(grid[x][y]);
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.println(" ********** ");
	}
	
	public void printTrucBoard() {
		int h = 0;
		while(!isLineEmpty(h) && h < BoardHeight - 1) h++;
		for (int y = h; y >= 0; y--) {
			System.out.print("|");
			for (int x = 0; x < BoardWidth; x++) {
				if (grid[x][y] == 0)
					System.out.print(" ");
				else
					System.out.print(grid[x][y]);
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.println(" ********** ");
		System.out.println("h:" + h); 
	}

	public boolean dropDown(int x, int[][] minos, int value) {
		int y = 0;
		boolean success = false;
		while (y < BoardHeight) {
			if (tryMove(minos, x, y)) {
				success = true;
				break;
			}
			y++;
		}
		if (success) {
			for (int[] coords : minos) {
				int _x = x + coords[0];
				int _y = y + coords[1];
				grid[_x][_y] = value;
				heights[_x] = Math.max(heights[_x], _y + 1);
			}
			return true;
		} else {
			return false;
		}

	}

	public boolean tryMove(int[][] minoes, int nx, int ny) {
		for (int[] coords : minoes) {
			int x = nx + coords[0];
			int y = ny + coords[1];
			// System.out.println("Try block at (" + x + "," + y + ")");
			if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight)
				return false;
			if (grid[x][y] != 0)
				return false;
		}
		return true;
	}

	public boolean isLineComplete(int y) {
		for (int i = 0; i < BoardWidth; i++) {
			if (grid[i][y] == 0)
				return false;
		}
		return true;
	}
	
	public boolean isLineEmpty(int y) {
		for (int i = 0; i < BoardWidth; i++) {
			if (grid[i][y] != 0)
				return false;
		}
		return true;
	}
	

	public static void main(String[] ards) {

		Board b = new Board();
		int[][] grid = new int[BoardWidth][BoardHeight];
		for(int x = 0; x < 10; x++) {
			grid[x][0] = 1;
			grid[x][1] = 1;
		}
		for(int x = 0; x < 9; x++) grid[x][2] = 1;
		b.grid = grid;
		b.printTrucBoard();
		b.updateBoard();
		b.printTrucBoard();
		
	}

	public int updateBoard() {
		int lines = 0;
		for (int y = 0; y < BoardHeight; y++) {
			if (isLineComplete(y)) {
				lines++;
				for (int _y = y; _y < BoardHeight - 1; _y++) {
					for (int x = 0; x < BoardWidth; x++) {
						grid[x][_y] = grid[x][_y + 1];
					}
				}
				// add empty row on top
				for (int x = 0; x < BoardWidth; x++) {
					grid[x][BoardHeight - 1] = 0;
				}
				y--; // check the new row again
			}
		}
		return lines;
	}
}
