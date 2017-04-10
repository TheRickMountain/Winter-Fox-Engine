package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Transformation;
import com.wfe.gui.Item;

public class GatherableComponent extends Component {

	private Item item;
	private int count;
	private int sound;
	
	public GatherableComponent(Item item, int count, int sound) {
		this.item = item;
		this.count = count;
		this.sound = sound;
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

	@Override
	public ComponentType getType() {
		return ComponentType.GATHERABLE;
	}

	@Override
	public Component getInstance() {
		return new GatherableComponent(item, count, sound);
	}

	@Override
	public Component getInstane(Transformation transform) {
		return new GatherableComponent(item, count, sound);
	}

}
