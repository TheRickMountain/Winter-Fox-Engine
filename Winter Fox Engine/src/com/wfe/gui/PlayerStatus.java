package com.wfe.gui;

import com.wfe.core.ResourceManager;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.math.Vector3f;
import com.wfe.utils.TimeUtil;

public class PlayerStatus {
	
	private TimeUtil time1, time2;
	public StatusBar healthBar;
	public StatusBar hungerBar;
	
	public PlayerStatus() {
		healthBar = new StatusBar(
				ResourceManager.getTexture("health_icon_ui"), new Vector3f(1.0f, 0.2f, 0.2f), 15, 10);
		hungerBar = new StatusBar(
				ResourceManager.getTexture("hunger_icon_ui"), new Vector3f(1.0f, 0.5f, 0.1f), 15, 35);
		time1 = new TimeUtil();
		time2 = new TimeUtil();
	}
	
	public void update(float dt) {
		if((int)time1.getTime() >= 10) {
			hungerBar.decrease(1);
			time1.reset();
			System.out.println("Hunger");
		}
		
		if(!healthBar.isFull()) {
			if(hungerBar.getCurrentValue() >= 90) {
				if((int)time2.getTime() >= 1) {
					healthBar.increase(1);
					time2.reset();
				}
			}
		}
		
		healthBar.update();
		hungerBar.update();
		
		if(Keyboard.isKeyDown(Key.KEY_H)) {
			healthBar.decrease(50);
			hungerBar.decrease(25);
		}
	}
	
	public void render() {
		healthBar.render();
		hungerBar.render();
	}

}
