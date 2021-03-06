package com.wfe.components;

import com.wfe.core.World;
import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Transformation;
import com.wfe.math.Vector2f;
import com.wfe.pathfinding.PathAStar;
import com.wfe.tileEngine.Tile;
import com.wfe.utils.MathUtils;
import com.wfe.utils.TimeUtil;

public class NeutralMobComponent extends Component {

	private int health = 20;
	
	private Tile currTile;
	private Tile nextTile;
	private Tile destTile;
	
	private PathAStar pathAStar;
	
	private float movementPerc;	
	private float speed = 2f;
	
	private int idleTime = 10;
	private TimeUtil timer;
	
	@Override
	public void init() {
		Transformation transform = getParent().getTransform();
		World world = World.getWorld();
		currTile = world.getTile((int)transform.x, (int)transform.z);
		destTile = nextTile = currTile;
		
		timer = new TimeUtil();
	}

	@Override
	public void update(float dt) {		
		if(pathAStar != null) {
			move(dt);
		} else {
			if(timer.getTime() >= idleTime) {
				timer.reset();
				
				World world = World.getWorld();
				Vector2f point = MathUtils.getPointOnCirce(16, currTile.getX(), currTile.getY());
				destTile = world.getTile((int)point.x, (int)point.y);
				pathAStar = new PathAStar(world, currTile, destTile);
				if(pathAStar.getLength() == -1) {
					pathAStar = null;
					destTile = currTile;
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
				MathUtils.getLerp(currTile.getX(), nextTile.getX(), movementPerc) + 0.5f, 0, 
						MathUtils.getLerp(currTile.getY(), nextTile.getY(), movementPerc) + 0.5f);
		
		return false;
	}
	
	public void hurt() {
		
	}
	
	public void heal() {
		
	}
	
	private void knockback() {
		
	}
	
	public void die() {
		
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.MOB;
	}
	
	@Override
	public Component getInstance() {
		return null;
	}

}
