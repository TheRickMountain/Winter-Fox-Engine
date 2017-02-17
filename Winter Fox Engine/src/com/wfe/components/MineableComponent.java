package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.gui.Item;

public class MineableComponent extends Component {

	private Item item;
	private int count;
	private int sound;
	
	private Item requiredItem;
	private int miningTime;
	
	private int health;
	
	public MineableComponent(Item item, int count, int sound, Item requiredItem, int miningTime) {
		super();
		this.item = item;
		this.count = count;
		this.sound = sound;
		this.requiredItem = requiredItem;
		this.miningTime = miningTime;
		this.health = miningTime;
	}

	@Override
	public void update(float dt) {
		
	}
	
	public Item getItem() {
		return item;
	}

	public int getCount() {
		return count;
	}

	public int getSound() {
		return sound;
	}

	public Item getRequiredItem() {
		return requiredItem;
	}
	
	public int getMiningTime() {
		return miningTime;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void decreasetHealth() {
		health--;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.MINEABLE;
	}

}
