package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.game.World;
import com.wfe.gui.ItemDatabase;
import com.wfe.gui.inventory.GUIManager;
import com.wfe.input.Mouse;
import com.wfe.utils.MathUtils;

public class GatherableComponent implements Component {
	
	private Transformation transform;
	private StaticEntity player;
	private int itemID;
	private BoundingBox boundingBox;
	
	public GatherableComponent(int itemID, StaticEntity player, Transformation transform, BoundingBox boundingBox) {
		this.itemID = itemID;
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
					if(GUIManager.getGUI().inventory.addItem(ItemDatabase.items.get(itemID), 1)) {
						World.getWorld().removeEntityFromTile((int)transform.getX(), (int)transform.getZ());
					}
				}
			}
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.GATHERABLE;
	}

}
