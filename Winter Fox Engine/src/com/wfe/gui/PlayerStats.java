package com.wfe.gui;

import com.wfe.core.ResourceManager;
import com.wfe.font.GUIText;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
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
	
	private GUITexture cowryTexture;
	private GUIText cowryText;
	private int cowry = 0;
	
	public PlayerStats() {
		healthBar = new ProgressBar(new Rect(10, 20, 100, 15), new Color(251, 53, 54, 255).convert());
		healthBar.setCurrentValue(100);
		hungerBar = new ProgressBar(new Rect(10, 50, 100, 15), new Color(255, 128, 21, 255).convert());
		hungerBar.setCurrentValue(100);
		thirstBar = new ProgressBar(new Rect(10, 80, 100, 15), new Color(49, 51, 255, 255).convert());
		thirstBar.setCurrentValue(100);
		
		healthTimer = new TimeUtil();
		hungerTimer = new TimeUtil();
		thirstTimer = new TimeUtil();
		
		cowryTexture = new GUITexture(ResourceManager.getTexture("cowry_ui"), new Rect(10, 105, 20, 20), false);
		cowryText = new GUIText("0", FontRenderer.ARIAL);
		cowryText.setScale(0.8f);
		cowryText.setPosition(cowryTexture.rect.width + 10, 105);
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
				addHunger(-1);
				hungerTimer.reset();
			}
		}
		
		if(getThirst() > 0) {
			if(thirstTimer.getTime() >= thirstSpeed) {
				addThirst(-1);
				thirstTimer.reset();
			}
		}
	}
	
	public void render() {
		healthBar.render();
		hungerBar.render();
		thirstBar.render();
		
		GUIRenderer.render(cowryTexture);
	}
	
	public void renderText() {
		healthBar.renderText();
		hungerBar.renderText();
		thirstBar.renderText();
		
		FontRenderer.render(cowryText);
	}
	
	public int getHealth() {
		return healthBar.getCurrentVaule();
	}
	
	public void setHealth(int value) {
		healthBar.setCurrentValue(value);
	}

	public int getCowry() {
		return cowry;
	}
	
	public void setCowry(int value) {
		cowry = value;
		cowryText.setText(String.valueOf(cowry));
	}
	
	public boolean addCowry(int value) {
		if(cowry + value < 0) {
			return false;
		}
		
		setCowry(cowry += value);
		return true;
	}
	
	public int getHunger() {
		return hungerBar.getCurrentVaule();
	}
	
	public void setHunger(int value) {
		hungerBar.setCurrentValue(value);
	}
	
	public void addHunger(int value) {
		int totalHunger = getHunger() + value;
		if(totalHunger > 100) {
			totalHunger = 100;
		} else if(totalHunger < 0) {
			totalHunger = 0;
		}
		
		hungerBar.setCurrentValue(totalHunger);
	}
	
	public int getThirst() {
		return thirstBar.getCurrentVaule();
	}
	
	public void setThirst(int value) {
		thirstBar.setCurrentValue(value);
	}
	
	public void addThirst(int value) {
		int totalThirst = getThirst() + value;
		if(totalThirst > 100) {
			totalThirst = 100;
		} else if(totalThirst < 0) {
			totalThirst = 0;
		}
		
		thirstBar.setCurrentValue(totalThirst);
	}
	
}
