package com.wfe.tileEngine;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.World;
import com.wfe.ecs.Entity;

public class Tile {
	
	private Chunk chunk;
	private int x, y;
	private int id;
	private Entity entity;
	private boolean hasEntity = false;
	
	public Tile(Chunk chunk, int x, int y, int id) {
		this.chunk = chunk;
		this.x = x;
		this.y = y;
		this.id = id;
	}

	protected void setId(int id) {
		if(this.id != id) {
			this.id = id;
			chunk.rebuild();
		}
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
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
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getId() {
		return id;
	}
	
	public int getMovementCost() {
		if(hasEntity) 
			return entity.isWalkable() ? 1 : 0;
		
		return 1;
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
