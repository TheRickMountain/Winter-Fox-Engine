package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.gui.ItemDatabase;

public class InventoryComponent extends Component {
	
	public final int[] slots = new int[6];
	public final int[] counts = new int[6];
	
	public InventoryComponent() {
		for(int i = 0; i < slots.length; i++) {
			this.slots[i] = -1;
			this.counts[i] = 0;
		}
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
		//GUIManager.inventory.update(slots, counts);
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
