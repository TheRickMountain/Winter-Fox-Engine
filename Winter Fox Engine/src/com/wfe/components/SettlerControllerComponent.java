package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.game.World;
import com.wfe.input.Mouse;
import com.wfe.math.Vector3f;
import com.wfe.pathfinding.PathAStar;
import com.wfe.tileEngine.Tile;
import com.wfe.utils.MathUtils;
import com.wfe.utils.MousePicker;

public class SettlerControllerComponent extends Component {
	
	private World world;
	
	private PlayerAnimationComponent animation;
	
	private Tile currTile;
	private Tile nextTile;
	private Tile destTile;
	
	private PathAStar pathAStar;
	
	private float movementPerc;	
	private float speed = 2f;
	
	public SettlerControllerComponent(Tile tile, PlayerAnimationComponent animation) {
		world = World.getWorld();
		
		currTile = destTile = nextTile = tile;
		this.animation = animation;
	}
	
	@Override
	public void update(float dt) {
		if(Mouse.isButtonDown(0)) {
			Vector3f tp = MousePicker.getCurrentTerrainPoint();
			if(tp != null) {
				Tile tile = world.getTile((int)tp.getX(), (int)tp.getZ());
				if(tile != null) {
					pathAStar = new PathAStar(world, currTile, tile);
					if(pathAStar.getLength() != -1) {
						destTile = tile;
					}
				}
			}
		}
		
		if(pathAStar != null) {
			// While character is moving, iterate animation
			animation.walkAnim(dt);
			move(dt);
		}
	}
	
	private boolean move(float dt) {		
		if(currTile.equals(destTile)) {
			pathAStar = null;
			// Set animation to the idle
			animation.idleAnim();
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

	@Override
	public ComponentType getType() {
		return ComponentType.SETTLER_CONTROLLER;
	}

}
