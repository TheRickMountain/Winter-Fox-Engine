package com.wfe.components;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.game.Game;
import com.wfe.game.World;
import com.wfe.gui.ItemDatabase;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.userInterfaces.GUI;
import com.wfe.utils.MathUtils;
import com.wfe.utils.TimeUtil;

public class GatherableComponent implements Component {
	
	private Transformation transform;
	private Entity player;
	private int itemID;
	private BoundingBox boundingBox;
	private float gatheringTime;
	
	private static final TimeUtil time = new TimeUtil();
	private boolean start = false;
	
	public GatherableComponent(int itemID, Entity player, Transformation transform, BoundingBox boundingBox,
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
					
					float rot = MathUtils.getRotation(player.getTransform().x, player.getTransform().z, 
							transform.x, transform.z);
					player.getTransform().setRotY(-rot + 90);
				}
			}
		}
		
		if(start) {			
			float currentTime = (float)time.getTime();
			GUI.showProgressBar = true;
			if (currentTime <= gatheringTime) {
				int value = (int) ((currentTime * 100) / gatheringTime);
				GUI.progressBar.setCurrentValue(value);
			} else {
				if(GUI.getGUI().inventory.addItem(ItemDatabase.items.get(itemID), 1)) {
					World.getWorld().removeEntityFromTile((int)transform.getX(), (int)transform.getZ());
					GUI.soundSource.play(ResourceManager.getSound("taking"));
				}
				reset();
			}
			
			if(Keyboard.isKey(Key.KEY_W) || Keyboard.isKey(Key.KEY_S)
					|| Keyboard.isKey(Key.KEY_A) || Keyboard.isKey(Key.KEY_D))
				reset();
			
			Game.player.chopingAnimation(dt);
		}
		
		if(Mouse.isButtonUp(1)) {
			reset();
		}
	}
	
	private void reset() {
		time.reset();
		start = false;
		GUI.showProgressBar = false;
		Game.player.idleAnim();
	}

	@Override
	public ComponentType getType() {
		return ComponentType.GATHERABLE;
	}

}
