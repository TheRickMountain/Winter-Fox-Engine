package com.wfe.jobSystem;

import com.wfe.ecs.Entity;
import com.wfe.tileEngine.Tile;

public class Job {
	
	private Tile tile;
	private float time;
	private Entity entity;
	
	public Job(Tile tile, float time, Entity entity) {
		this.tile = tile;
		this.time = time;
		this.entity = entity;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public float getTime() {
		return time;
	}

	public Entity getEntity(float x, float y, float z) {
		Entity e = entity.getInstance();
		e.getTransform().setPosition(x, y, z);
		return e;
	}
	
}
