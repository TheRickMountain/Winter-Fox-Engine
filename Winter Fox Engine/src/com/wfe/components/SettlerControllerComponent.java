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
	
	private Tile currTile;
	private Tile nextTile;
	private Tile destTile;
	
	private PathAStar pathAStar;
	
	private float movementPerc;	
	private float speed = 2f;
	
	public SettlerControllerComponent(Tile tile) {
		world = World.getWorld();
		
		currTile = destTile = nextTile = tile;
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
			move(dt);
		}
	}
	
	private boolean move(float dt) {
		if(currTile.equals(destTile)) {
			pathAStar = null;
			return true;
		}
		
		if(nextTile.equals(currTile)) {
			nextTile = pathAStar.getNextTile();
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
