package com.wfe.terrain;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.World;
import com.wfe.ecs.Entity;
import com.wfe.entities.Selection;

public class Tile {
	
	private Chunk chunk;
	private int x, y;
	private int id;
	private float height;
	private Entity entity;
	private boolean hasEntity = false;
	private float movementCost = 1.0f;
	
	private Selection selection;
	private boolean selected = false;
	
	public Tile(int x, int y, int id, float height) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.height = height;
	}

	protected Chunk getChunk() {
		return chunk;
	}
	
	public void setChunk(Chunk chunk) {
		this.chunk = chunk;
	}
	
	public Tile setId(int id) {
		if(this.id != id) {
			this.id = id;
			chunk.setRebuild(true);
		}
		
		return this;
	}
	
	public Tile setHeight(float height) {
		if(this.height != height) {
			this.height = height;
			chunk.setRebuild(true);
			
			World world = World.getWorld();
			
			if((x - 1) >= 0) {
				world.getTile(x - 1, y).getChunk().setRebuild(true);
			}
			
			if((x + 1) <= 159) {
				world.getTile(x + 1, y).getChunk().setRebuild(true);
			}
			
			if((y - 1) >= 0) {
				world.getTile(x, y - 1).getChunk().setRebuild(true);
			}
			
			if((y + 1) <= 159) {
				world.getTile(x, y + 1).getChunk().setRebuild(true);
			}
		}
		
		return this;
	}

	public Entity getEntity() {
		return entity;
	}

	public void addEntity(Entity entity) {
		this.entity = entity;
		this.entity.getTransform().setPosition(x + 0.5f, 0, y + 0.5f);
		
		hasEntity = true;
	}
	
	public Entity removeEntity() {
		Entity temp = entity;
		
		entity = null;
		hasEntity = false;
		
		return temp;
	}
	
	public void removeEntityPermanently() {
		entity.remove();
		entity = null;
		hasEntity = false;
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
	
	public float getHeight() {
		return height;
	}
	
	public boolean isHasEntity() {
		return hasEntity;
	}
	
	public float getMovementCost() {
		if(height > 0 || height < 0) {
			return 0.0f;
		}
		
		if(hasEntity) {
			return entity.isWalkable() ? 1.0f : 0.0f;
		}
		
		return movementCost;
	}
	
	public void setSelected(boolean value, int r, int g, int b, int a) {
		selected = value;
		if(selected) {
			selection = new Selection();
			selection.getTransform().setPosition(x + 0.5f, 0.05f, y + 0.5f);
			selection.getMaterial().setColor(r, g, b, a);
			World.getWorld().addEntity(selection);
		} else {
			World.getWorld().removeEntity(selection);
			selection = null;
		}
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
