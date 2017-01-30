package com.wfe.userInterfaces;

import com.wfe.core.ResourceManager;
import com.wfe.gui.GUIElement;
import com.wfe.gui.StatusBar;
import com.wfe.utils.Color;
import com.wfe.utils.TimeUtil;

public class PlayerStatus implements GUIElement {
	
	private TimeUtil time1, time2;
	public StatusBar healthBar;
	public StatusBar hungerBar;
	public StatusBar thirstBar;
	
	public PlayerStatus() {
		healthBar = new StatusBar(
				ResourceManager.getTexture("health_icon_ui"), new Color(1.0f, 0.2f, 0.2f), 15, 10);
		hungerBar = new StatusBar(
				ResourceManager.getTexture("hunger_icon_ui"), new Color(1.0f, 0.5f, 0.1f), 15, 35);
		thirstBar = new StatusBar(
				ResourceManager.getTexture("thirst_icon_ui"), new Color(0.2f, 0.2f, 1.0f), 15, 60);
		time1 = new TimeUtil();
		time2 = new TimeUtil();
	}
	
	public void update(float dt) {
		if((int)time1.getTime() >= 10) {
			hungerBar.decrease(1);
			time1.reset();
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
		thirstBar.update();
	}
	
	public void render() {
		healthBar.render();
		hungerBar.render();
		thirstBar.render();
	}
	
	@Override
	public void renderText() {
		
	}

}
