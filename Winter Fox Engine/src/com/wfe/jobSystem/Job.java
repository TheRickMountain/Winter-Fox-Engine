package com.wfe.jobSystem;

import com.wfe.ecs.Entity;
import com.wfe.terrain.Tile;

public class Job {
	
	private JobType jobType;
	private Tile tile;
	private float time;
	private Entity entity;
	private Tile stockpile;
	private int sound;
	
	public Job(JobType jobType, Tile tile, float time, Entity entity, Tile stockpile, int sound) {
		this.jobType = jobType;
		this.tile = tile;
		this.time = time;
		this.entity = entity;
		this.stockpile = stockpile;
		this.sound = sound;
	}
	
	public JobType getJobType() {
		return jobType;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public float getTime() {
		return time;
	}
	
	public Tile getStockpile() {
		return stockpile;
	}
	
	public void setStockpile(Tile tile) {
		this.stockpile = tile;
	}
	
	public int getSound() {
		return sound;
	}

	public Entity getEntity(float x, float y, float z) {
		Entity e = entity.getInstance();
		e.getTransform().setPosition(x, y, z);
		return e;
	}
	
}
