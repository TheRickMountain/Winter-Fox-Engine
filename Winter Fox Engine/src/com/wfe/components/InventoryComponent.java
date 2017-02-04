package com.wfe.components;

import com.wfe.audio.AudioMaster;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Player;
import com.wfe.game.World;
import com.wfe.gui.GUIManager;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.gui.ItemType;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Scroll;

public class InventoryComponent extends Component {
	
	private Entity rightForearm;
	
	private final int[] slots = new int[6];
	private final int[] counts = new int[6];
	
	private int selected = 0;
	private int lastSelected = 0;
	
	private Entity equippedEntity;
	
	public InventoryComponent(Player player) {
		this.rightForearm = player.rightForearm;
		for(int i = 0; i < slots.length; i++) {
			this.slots[i] = -1;
			this.counts[i] = 0;
		}
		
		GUIManager.inventory.setSelected(selected);
	}
	
	@Override
	public void update(float dt) {
		if(Keyboard.isKeyDown(Key.KEY_1)) selected = 0;
		else if(Keyboard.isKeyDown(Key.KEY_2)) selected = 1;
		else if(Keyboard.isKeyDown(Key.KEY_3)) selected = 2;
		else if(Keyboard.isKeyDown(Key.KEY_4)) selected = 3;
		else if(Keyboard.isKeyDown(Key.KEY_5)) selected = 4;
		else if(Keyboard.isKeyDown(Key.KEY_6)) selected = 5;
		
		selected -= Scroll.getScroll();
		
		if(selected != lastSelected) {
			if(selected >= slots.length) selected = 0;
			else if(selected < 0) selected = slots.length - 1;
			
			lastSelected = selected;
			
			GUIManager.inventory.setSelected(selected);
			
			AudioMaster.defaultSource.play(ResourceManager.getSound("tick"));
			
			/* If player has been equipped than delete current equipped one */
			if(equippedEntity != null) {
				rightForearm.removeChild(equippedEntity);
				equippedEntity.remove();
				equippedEntity = null;
			}
			
			/* If selected item has entity than equip it to the player */
			int slot = slots[selected];
			if(slot != -1) {
				Item item = ItemDatabase.getItem(slots[selected]);
				if(item.type.equals(ItemType.TOOL)) {					
					equippedEntity = item.blueprint.createInstanceWithComponents(new Transformation());
					rightForearm.addChild(equippedEntity);
					World.getWorld().addEntity(equippedEntity);
				}
			}
		}
	}
	
	private int hasItem(int item) {
		for(int i = 0; i < this.slots.length; i++) {
			if(item != this.slots[i]) continue;
			return i;
		}
		return -1;
	}
	
	private int canStackWithSuchItem(int item) {
		for(int i = 0; i < this.slots.length; i++) {
			if(item == this.slots[i]) {
				if(this.counts[i] < ItemDatabase.getItem(item).stackSize) {
					return i;
				}
			}
		}
		return -1;
	}
	
	private void addItem(int slot, int item, int count) {
		int stack = ItemDatabase.getItem(item).stackSize;
		if(count <= stack) {
			counts[slot] = count;
		} else {
			counts[slot] = stack;
			count -= stack;
			addItem(item, count);
		}
	}
	
	public boolean addItem(int item, int count) {
		int slot = hasItem(item);
		if(slot == -1) {
			slot = hasItem(slot);
			if(slot == -1) {
				return false;
			}
			
			slots[slot] = item;
			addItem(slot, item, count);
		} else {
			slot = canStackWithSuchItem(item);
			if(slot == -1) {
				slot = hasItem(slot);
				if(slot == -1) {
					return false;
				}
				
				slots[slot] = item;
				addItem(slot, item, count);
			} else {
				count = this.counts[slot] + count;
				addItem(slot, item, count);
			}
		}
		GUIManager.inventory.update(slots, counts);
		return true;
	}
	
	public boolean removeItem(int item) {
		GUIManager.inventory.update(slots, counts);
		return true;
	}
	
	public int getSelected() {
		return slots[selected];
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.INVENTORY;
	}

}
