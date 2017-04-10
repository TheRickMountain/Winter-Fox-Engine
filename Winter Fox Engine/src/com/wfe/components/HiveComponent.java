package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Transformation;
import com.wfe.gui.Item;
import com.wfe.utils.TimeUtil;

public class HiveComponent extends Component {

	private int reproductionTime;
	private Item item;
	private int countMin;
	private int countMax;
	private int sound;
	
	private TimeUtil timer;
	private boolean ready = false;
	
	public HiveComponent(int reproductionTime, Item item, int countMin, int countMax, int sound) {
		this.reproductionTime = reproductionTime;
		this.item = item;
		this.countMin = countMin;
		this.countMax = countMax;
		this.sound = sound;
		timer = new TimeUtil();
	}
	
	@Override
	public void update(float dt) {
		if(!ready) {
			if(timer.getTime() >= reproductionTime) {
				ready = true;
				timer.reset();
			}
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.HIVE;
	}
	
	public Item getItem() {
		return item;
	}

	public int getCountMin() {
		return countMin;
	}
	
	public int getCountMax() {
		return countMax;
	}
	
	public int getSound() {
		return sound;
	}
	
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public boolean isReady() {
		return ready;
	}

	@Override
	public Component getInstance() {
		return new HiveComponent(reproductionTime, item, countMin, countMax, sound);
	}

	@Override
	public Component getInstane(Transformation transform) {
		return new HiveComponent(reproductionTime, item, countMin, countMax, sound);
	}
	
}
