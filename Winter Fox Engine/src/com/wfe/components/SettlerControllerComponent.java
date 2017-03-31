package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.game.World;
import com.wfe.input.Mouse;
import com.wfe.math.Vector3f;
import com.wfe.tileEngine.Tile;
import com.wfe.utils.MousePicker;

public class SettlerControllerComponent extends Component {

	private World world;
	
	public SettlerControllerComponent() {
		world = World.getWorld();
	}
	
	@Override
	public void update(float dt) {
		if(Mouse.isButtonDown(0)) {
		Vector3f tp = MousePicker.getCurrentTerrainPoint();
			if(tp != null) {
				Tile tile = world.getTile((int)tp.getX(), (int)tp.getZ());
				if(tile.isHasEntity()) {
					System.out.println(tile.getEntity().getTag());
				}
				System.out.println("Mouse Piker: " + tp.getX() + " " + tp.getZ());
				System.out.println("Tile:" + tile.getX() + " " + tile.getY());
			}
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.SETTLER_CONTROLLER;
	}

}
