package com.wfe.tileEngine;

import java.util.ArrayList;
import java.util.List;

import com.wfe.ecs.Entity;
import com.wfe.game.World;

public class Tile {
	
	private Chunk chunk;
	private int x, y;
	private int id;
	private Entity entity;
	private boolean hasEntity = false;
	private float movementCost = 1.0f;
	
	public Tile(Chunk chunk, int x, int y, int id) {
		this.chunk = chunk;
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public void setId(int id) {
		this.id = id;
		chunk.setRebuild(true);
	}

	public Entity getEntity() {
		return entity;
	}

	public void addEntity(Entity entity) {
		this.entity = entity;
		this.entity.getTransform().setPosition(x + 0.5f, 0, y + 0.5f);
		this.hasEntity = true;
		
		this.movementCost = entity.isWalkable() ? 1.0f : 0.0f;
	}
	
	public Entity removeEntity() {
		Entity temp = entity;
		
		entity = null;
		hasEntity = false;
		movementCost = 1.0f;
		
		return temp;
	}
	
	public void removeEntityPermanently() {
		entity.remove();
		entity = null;
		hasEntity = false;
		
		movementCost = 1.0f;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isHasEntity() {
		return hasEntity;
	}
	
	public float getMovementCost() {
		return movementCost;
	}

	public List<Tile> getNeighbours(boolean diags) {
		List<Tile> neighbours = new ArrayList<>();
		World world = World.getWorld();
		
		neighbours.add(world.getTile(x, y + 1));
		neighbours.add(world.getTile(x + 1, y));
		neighbours.add(world.getTile(x, y - 1));
		neighbours.add(world.getTile(x - 1, y));
		
		if(diags) {
			neighbours.add(world.getTile(x + 1, y + 1));
			neighbours.add(world.getTile(x + 1, y - 1));
			neighbours.add(world.getTile(x - 1, y - 1));
			neighbours.add(world.getTile(x - 1, y + 1));
		}
		
		return neighbours;
	}
}
