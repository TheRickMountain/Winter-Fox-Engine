package com.wfe.gui;

import com.wfe.utils.Color;
import com.wfe.utils.Rect;
import com.wfe.utils.TimeUtil;

public class PlayerStats {
	
	private ProgressBar healthBar;
	private ProgressBar hungerBar;
	private ProgressBar thirstBar;
	
	private TimeUtil timer;
	
	private int healthSpeed = 1;
	private int hungerSpeed = 1;
	private int thirstSpeed = 1;

	public PlayerStats() {
		healthBar = new ProgressBar(new Rect(10, 20, 100, 15), new Color(251, 53, 54, 255).convert());
		healthBar.setCurrentValue(100);
		hungerBar = new ProgressBar(new Rect(10, 50, 100, 15), new Color(255, 128, 21, 255).convert());
		hungerBar.setCurrentValue(100);
		thirstBar = new ProgressBar(new Rect(10, 80, 100, 15), new Color(49, 51, 255, 255).convert());
		thirstBar.setCurrentValue(100);
		
		timer = new TimeUtil();
	}
	
	public void update() {
		if(timer.getTime() >= hungerSpeed) {
			setHunger(getHunger() - 1);
			setThirst(getThirst() - 1);
			timer.reset();
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
