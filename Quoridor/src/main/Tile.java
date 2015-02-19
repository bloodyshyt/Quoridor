package main;

import Player.AiPlayer;

public class Tile {

	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;

	public int x, y;
	public Tile[] adj = new Tile[4];

	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		for (Tile e : adj)
			e = null;
	}

	public void addNeighbour(int direction, Tile neighbour) {
		this.adj[direction] = neighbour;
	}

	public boolean hasNeighbour(int direction) {
		if (adj[direction] != null)
			return true;
		return false;
	}

	public boolean hasVerticalMove() {
		if (adj[UP] != null && adj[DOWN] != null)
			return true;
		return false;
	}

	public boolean hasHorizontalMove() {
		if (adj[LEFT] != null && adj[RIGHT] != null)
			return true;
		return false;
	}

	// TODO implement a clone method

}