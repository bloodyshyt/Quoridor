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

	public boolean hasWallMove() {
		// traverse in a clockwise fashion
		Tile t = this.adj[LEFT];
		if(t == null) return false;
		t = t.adj[UP];
		if(t == null) return false;
		t = t.adj[RIGHT];
		if(t == null) return false;
		if(t.adj[DOWN] == this) return true;
		else return false;
		
	}

	
	// TODO implement a clone method

}