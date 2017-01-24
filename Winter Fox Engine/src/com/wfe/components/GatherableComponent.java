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
import com.wfe.utils.TimeUtil;

public class GatherableComponent implements Component {
	
	private Transformation transform;
	private StaticEntity player;
	private int itemID;
	private BoundingBox boundingBox;
	private float gatheringTime;
	
	private static final TimeUtil time = new TimeUtil();
	private boolean start = false;
	
	public GatherableComponent(int itemID, StaticEntity player, Transformation transform, BoundingBox boundingBox,
			float gatheringTime) {
		this.itemID = itemID;
		this.player = player;
		this.transform = transform;
		this.boundingBox = boundingBox;
		this.gatheringTime = gatheringTime;
	}
	
	@Override
	public void update(float dt) {
		if(Mouse.isButtonDown(1)) {
			if(MathUtils.getDistance(player.getTransform().x, player.getTransform().z, 
					transform.x, transform.z) <= 2.5f) {
				if(boundingBox.intersects()) {
					start = true;
				}
			}
		}
		
		if(start) {
			float currentTime = (float)time.getTime();
			if (currentTime <= gatheringTime) {
				System.out.println(currentTime);
			} else {
				if(GUIManager.getGUI().inventory.addItem(ItemDatabase.items.get(itemID), 1)) {
					World.getWorld().removeEntityFromTile((int)transform.getX(), (int)transform.getZ());
				}
				time.reset();
				start = false;
			}
		}
		
		if(Mouse.isButtonUp(1)) {
			time.reset();
			start = false;
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.GATHERABLE;
	}

}
