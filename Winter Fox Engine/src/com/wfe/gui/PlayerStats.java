package com.wfe.gui;

import com.wfe.utils.Color;
import com.wfe.utils.Rect;
import com.wfe.utils.TimeUtil;

public class PlayerStats {
	
	private ProgressBar healthBar;
	private ProgressBar hungerBar;
	private ProgressBar thirstBar;
	
	private TimeUtil healthTimer;
	private TimeUtil hungerTimer;
	private TimeUtil thirstTimer;
	
	private int healthSpeed = 1;
	private int hungerSpeed = 8;
	private int thirstSpeed = 6;

	public PlayerStats() {
		healthBar = new ProgressBar(new Rect(10, 20, 100, 15), new Color(251, 53, 54, 255).convert());
		healthBar.setCurrentValue(100);
		hungerBar = new ProgressBar(new Rect(10, 50, 100, 15), new Color(255, 128, 21, 255).convert());
		hungerBar.setCurrentValue(10);
		thirstBar = new ProgressBar(new Rect(10, 80, 100, 15), new Color(49, 51, 255, 255).convert());
		thirstBar.setCurrentValue(20);
		
		healthTimer = new TimeUtil();
		hungerTimer = new TimeUtil();
		thirstTimer = new TimeUtil();
	}
	
	public void update() {
		if(getHunger() == 0 || getThirst() == 0) {
			if(healthTimer.getTime() >= healthSpeed) {
				setHealth(getHealth() - 1);
				healthTimer.reset();
			}
		} 
		
		if(getHunger() > 0) {
			if(hungerTimer.getTime() >= hungerSpeed) {
				setHunger(getHunger() - 1);
				hungerTimer.reset();
			}
		}
		
		if(getThirst() > 0) {
			if(thirstTimer.getTime() >= thirstSpeed) {
				setThirst(getThirst() - 1);
				thirstTimer.reset();
			}
		}
	}
	
	public void render() {
		healthBar.render();
		hungerBar.render();
		thirstBar.render();
	}
	
	public void renderText() {
		healthBar.renderText();
		hungerBar.renderText();
		thirstBar.renderText();
	}
	
	private void setHealth(int value) {
		healthBar.setCurrentValue(value);
	}
	
	public int getHealth() {
		return healthBar.getCurrentVaule();
	}
	
	private void setHunger(int value) {
		hungerBar.setCurrentValue(value);
	}
	
	public int getHunger() {
		return hungerBar.getCurrentVaule();
	}
	
	private void setThirst(int value) {
		thirstBar.setCurrentValue(value);
	}
	
	public int getThirst() {
		return thirstBar.getCurrentVaule();
	}
	
	public void addHunger(int value) {
		int totalHunger = getHunger() + value;
		if(totalHunger > 100) {
			totalHunger = 100;
		} else if(totalHunger < 0) {
			totalHunger = 0;
		}
		
		setHunger(totalHunger);
	}
	
	public void addThirst(int value) {
		int totalThirst = getThirst() + value;
		if(totalThirst > 100) {
			totalThirst = 100;
		} else if(totalThirst < 0) {
			totalThirst = 0;
		}
		
		setThirst(totalThirst);
	}
	
}
