package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;

public class NeutralMobComponent extends Component {

	private int health = 20;
	
	@Override
	public void init() {
		
	}

	@Override
	public void update(float dt) {
		
	}

	public void move() {
		
	}
	
	public void hurt() {
		
	}
	
	public void heal() {
		
	}
	
	private void knockback() {
		
	}
	
	public void die() {
		
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.MOB;
	}
	
	@Override
	public Component getInstance() {
		return null;
	}

}
