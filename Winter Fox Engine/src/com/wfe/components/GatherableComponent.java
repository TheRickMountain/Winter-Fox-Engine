package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;

public class GatherableComponent extends Component {

	private int item;
	private int count;
	private int sound;
	
	public GatherableComponent(int item, int count, int sound) {
		this.item = item;
		this.count = count;
		this.sound = sound;
	}
	
	@Override
	public void update(float dt) {
		
	}

	public int getItem() {
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

}
