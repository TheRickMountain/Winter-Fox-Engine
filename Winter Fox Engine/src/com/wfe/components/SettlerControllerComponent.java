package com.wfe.components;

import java.util.ArrayList;
import java.util.List;

import com.wfe.audio.AudioMaster;
import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.game.World;
import com.wfe.jobSystem.Job;
import com.wfe.pathfinding.PathAStar;
import com.wfe.tileEngine.Tile;
import com.wfe.utils.MathUtils;
import com.wfe.utils.TimeUtil;

public class SettlerControllerComponent extends Component {
	
	private World world;
	
	private PlayerAnimationComponent animation;
	
	private Tile currTile;
	private Tile nextTile;
	private Tile destTile;
	
	private PathAStar pathAStar;
	
	private float movementPerc;	
	private float speed = 2f;
	
	private Job currentJob;
	private TimeUtil time;
	
	private boolean moving = false;
	
	private Entity cargo = null;
	
	public SettlerControllerComponent(Tile tile, PlayerAnimationComponent animation) {
		world = World.getWorld();
		
		currTile = destTile = nextTile = tile;
		this.animation = animation;
		
		time = new TimeUtil(); 
	}
	
	@Override
	public void update(float dt) {
		if(currentJob == null) {
			chooseJob();
		} else {
			if(moving) {
				if(cargo != null) {
					Transformation transform = getParent().getTransform();
					cargo.getTransform().setPosition(transform.getX(), 0.65f, transform.getZ());
				}
				
				animation.walkAnim(dt);
				if(move(dt)) {
					animation.idleAnim();
					moving = false;
				}
			} else {
				switch(currentJob.getJobMode()) {
				case DEVELOPMENT:
					// If animation has reached a target number than play "development" sound
					if(animation.hitAnim(dt)) {
						AudioMaster.defaultSource.play(currentJob.getSound());
					}
					
					if(time.getTime() >= currentJob.getTime()) {
						Tile tile = currentJob.getTile();
						tile.removeEntityPermanently();
						tile.addEntity(currentJob.getEntity(tile.getX() + 0.5f, 0, tile.getY() + 0.5f));
						world.addEntity(tile.getEntity());
						
						animation.idleAnim();
						currentJob = null;
						time.reset();
					}
					break;
				case GATHERING:
					if(cargo != null) {
						currentJob.getStockpile().addEntity(cargo);
						currentJob = null;
						cargo = null;
					} else {
						cargo = currentJob.getTile().removeEntity();
						Tile tile = choosePath(currentJob.getStockpile());
						if(tile != null) {
							destTile = tile;
							moving = true;
						}
					}
					break;
				case PLOWING:
					if(animation.hitAnim(dt)) {
						AudioMaster.defaultSource.play(currentJob.getSound());
					}
					
					if(time.getTime() >= currentJob.getTime()) {
						Tile tile = currentJob.getTile();
						tile.setId(11);
						
						animation.idleAnim();
						currentJob = null;
						time.reset();
					}
				}
			}
		}
	}
	
	private boolean move(float dt) {		
		if(currTile.equals(destTile)) {
			pathAStar = null;
			return true;
		}
		
		if(nextTile.equals(currTile)) {
			nextTile = pathAStar.getNextTile();
			
			// Rotate character to the destination tile
			float rotation = MathUtils.getRotation(currTile.getX() + 0.5f, currTile.getY() + 0.5f, 
					nextTile.getX() + 0.5f, nextTile.getY() + 0.5f);
			getParent().getTransform().setRotY(-(rotation) + 90);
		}
		
		float distToTravel = MathUtils.getDistance(
				currTile.getX(), currTile.getY(), 
				nextTile.getX(), nextTile.getY());
		
		float distThisFrame = speed * dt;
		
		float percThisFrame = distThisFrame / distToTravel;
		
		movementPerc += percThisFrame;
		
		if(movementPerc >= 1) {			
			currTile = nextTile;
			movementPerc = 0;
		}
		
		getParent().getTransform().setPosition(
				MathUtils.getLerp(currTile.getX(), nextTile.getX(), movementPerc) + 0.5f, 0.65f, 
						MathUtils.getLerp(currTile.getY(), nextTile.getY(), movementPerc) + 0.5f);
		
		return false;
	}
	
	private void chooseJob() {
		for(Job job : world.getJobList()) {
			Tile tile = choosePath(job.getTile());
			if(tile != null) {
				currentJob = job;
				destTile = tile;
				moving = true;
			}
		}
		
		if(currentJob != null) {
			world.getJobList().remove(currentJob);
		}
	}
	
	private Tile choosePath(Tile tile) {
		if(tile.getMovementCost() == 1.0f) {
			pathAStar = new PathAStar(world, currTile, tile);
			if(pathAStar.getLength() != -1) {
				return tile;
			}
		} else {
			return findNearestTile(tile.getNeighbours(false));
		}
		
		return null;
	}
	
	private Tile findNearestTile(List<Tile> tiles) {
		Tile tile = null;
		
		List<Tile> openTiles = new ArrayList<>();
		
		for(Tile t : tiles) {
			if(t.getMovementCost() == 1.0f) {
				openTiles.add(t);
			}
		}
		
		if(openTiles.size() > 0) {
			tile = null;
			float distance = Float.MAX_VALUE;
			
			for(int i = 0; i < openTiles.size(); i++) {
				PathAStar tempPathAStar = new PathAStar(world, currTile, openTiles.get(i));
				float tempDistance = tempPathAStar.getLength();
				
				if(tempDistance != -1 && tempDistance < distance) {
					tile = openTiles.get(i);
					distance = tempDistance;
					pathAStar = tempPathAStar;
				}
			}
		}
		
		return tile;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.SETTLER_CONTROLLER;
	}

	@Override
	public Component getInstance() {
		return null;
	}

}
