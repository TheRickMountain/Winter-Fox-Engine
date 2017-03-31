package com.wfe.jobSystem;

import com.wfe.tileEngine.Tile;

public class Job {
	
	private Tile tile;
	private float time;
	
	public Job(Tile tile, float time) {
		this.tile = tile;
		this.time = time;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public float getTime() {
		return time;
	}

}
