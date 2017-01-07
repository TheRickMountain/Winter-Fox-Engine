package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.game.World;
import com.wfe.gui.ItemDatabase;
import com.wfe.gui.inventory.GUIManager;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.utils.MathUtils;

public class GatherableComponent implements Component {
	
	private Transformation transform;
	private StaticEntity player;
	private int itemID;
	
	public GatherableComponent(int itemID, StaticEntity player, Transformation transform) {
		this.itemID = itemID;
		this.player = player;
		this.transform = transform;
	}
	
	@Override
	public void update(float dt) {
		if(Keyboard.isKeyDown(Key.KEY_F)) {
			if(MathUtils.getDistance(player.getTransform().x, player.getTransform().z, 
					transform.getX(), transform.getZ()) <= 2) {
				if(GUIManager.getGUI().inventory.addItem(ItemDatabase.items.get(itemID), 1)) {
					World.getWorld().removeEntityFromTile((int)transform.getX(), (int)transform.getZ());
					transform.getParent().remove();
				}
			}
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.GATHERABLE;
	}

}
