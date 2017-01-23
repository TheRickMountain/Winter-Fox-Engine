package com.wfe.tileEngine;

import com.wfe.ecs.StaticEntity;

public class Tile {
	
	public int id;
	public StaticEntity entity;
	private boolean hasEntity = false;
	public static final Tile GRASS = new Tile(0);
	public static final Tile DRY_GRASS = new Tile(1);
	public static final Tile DRY_GROUND = new Tile(2);
	public static final Tile MOIST_GROUND = new Tile(3);
	public static final Tile SAND = new Tile(5);
	public static final Tile DESK = new Tile(6);
	
	public Tile(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StaticEntity getEntity() {
		return entity;
	}

	public void setEntity(StaticEntity entity) {
		this.entity = entity;
		this.hasEntity = true;
	}
	
	public void removeEntity() {
		this.entity.remove();
		this.entity = null;
		this.hasEntity = false;
	}
	
	public boolean isHasEntity() {
		return hasEntity;
	}

}
