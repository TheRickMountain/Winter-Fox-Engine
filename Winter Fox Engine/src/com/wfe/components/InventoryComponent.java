package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.gui.GUIManager;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;

public class InventoryComponent extends Component {
	
	private int[] slots = new int[24];
	private int[] counts = new int[24];
	
	public InventoryComponent() {
		for(int i = 0; i < 24; i++) {
			this.slots[i] = -1;
			this.counts[i] = 0;
		}
		
		addItem(Item.APPLE, 100);
		addItem(Item.APPLE, 35);
		addItem(Item.FLINT, 87);
		addItem(Item.AXE, 5);
		
		GUIManager.inventory.update(slots, counts);
	}
	
	@Override
	public void update(float dt) {
		
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
				if(this.counts[i] < ItemDatabase.getItem(item).stack) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public boolean addItem(int item, int count) {
		int slot = hasItem(item);
		if(slot == -1) {
			slot = hasItem(slot);
			slots[slot] = item;
			int stack = ItemDatabase.getItem(item).stack;
			if(count <= stack) {
				counts[slot] = count;
			} else {
				counts[slot] = stack;
				count -= stack;
				addItem(item, count);
			}
		} else {
			slot = canStackWithSuchItem(item);
			if(slot == -1) {
				slot = hasItem(slot);
				slots[slot] = item;
				int stack = ItemDatabase.getItem(item).stack;
				if(count <= stack) {
					counts[slot] = count;
				} else {
					counts[slot] = stack;
					count -= stack;
					addItem(item, count);
				}
			} else {
				count = this.counts[slot] + count;
				int stack = ItemDatabase.getItem(item).stack;
				if(count <= stack) {
					counts[slot] = count;
				} else {
					counts[slot] = stack;
					count -= stack;
					addItem(item, count);
				}
			}
		}
		
		return true;
	}
	
	public boolean removeItem(int item) {
		return true;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.INVENTORY;
	}

}
