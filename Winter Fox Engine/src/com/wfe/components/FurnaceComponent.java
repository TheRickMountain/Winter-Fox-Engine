package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Player;
import com.wfe.input.Mouse;
import com.wfe.utils.MathUtils;

public class FurnaceComponent implements Component {

	private Player player;
	private Transformation transform;
	private BoundingBox boundingBox;
	
	public FurnaceComponent(Player player, Transformation transform, BoundingBox boundingBox) {
		this.player = player;
		this.transform = transform;
		this.boundingBox = boundingBox;
	}
	
	@Override
	public void update(float dt) {
		if(Mouse.isButtonDown(1)) {
			if(MathUtils.getDistance(player.getTransform().x, player.getTransform().z, 
					transform.x, transform.z) <= 2.5f) {
				if(boundingBox.intersects()) {
					System.out.println("Openg Furnace Interface");
				}
			}
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.FURNACE;
	}

}
