package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.gui.ItemDatabase;

public class ChestComponent extends Component {

	private int[] slots;
	private int[] counts;
	
	public ChestComponent(int column, int row) {
		slots = new int[column * row];
		counts = new int[slots.length];
		
		for(int i = 0; i < slots.length; i++) {
			this.slots[i] = -1;
			this.counts[i] = 0;
		}
	}
	
	@Override
	public void update(float dt) {
		/*if(Mouse.isButtonDown(1)) {
			GUIManager.open(this); // Send chest data to the GUIManager
		}*/
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
		return true;
	}
	
	public boolean removeItem(int item) {
		return true;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.CHEST;
	}

	@Override
	public Component getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
