package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.font.GUIText;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;
import com.wfe.utils.Color;
import com.wfe.utils.MyRandom;
import com.wfe.utils.Rect;
import com.wfe.utils.TimeUtil;

public class Mancala {
	
	private enum Turn {
		PLAYER,
		ENEMY
	}
	
	private Rect rect;
	private Texture texture;
	
	private List<Slot> slots = new ArrayList<>();
	private List<Slot> playerSlots = new ArrayList<>();
	private List<Slot> enemySlots = new ArrayList<>();
	
	private int playerBarnId = 6;
	private int enemyBarnId = 13;
	private Slot playerBarn, enemyBarn;
	
	private Turn turn = Turn.PLAYER;
	
	private TimeUtil timer;
	
	public Mancala() {
		rect = new Rect(0, 0, 20 + Slot.SIZE * 8 + 7 * 5, 20 + Slot.SIZE * 3);
		texture = ResourceManager.getTexture("mancala_ui");
		
		for(int i = 0; i < 6; i++) {
			playerSlots.add(new Slot());
			enemySlots.add(new Slot());
			
			for(int j = 0; j < 4; j++) {
				playerSlots.get(i).addGem(new GUITexture(ResourceManager.getTexture("gem_" + j + "_ui")));
				enemySlots.get(i).addGem(new GUITexture(ResourceManager.getTexture("gem_" + j + "_ui")));
			}
		}
		
		playerBarn = new Slot();
		enemyBarn = new Slot();
		
		slots.addAll(playerSlots);
		slots.add(playerBarn);
		slots.addAll(enemySlots);
		slots.add(enemyBarn);
		
		timer = new TimeUtil();
		
		updatePositions();
	}
	
	public void update() {
		
		switch(turn) {
		case PLAYER:
			if(Mouse.isButtonDown(0)) {
				for(int i = 0; i < playerSlots.size(); i++) {
					Slot slot = playerSlots.get(i);
					if(slot.rect.isMouseOvered()) {
						
						int amount = i + slot.amount + 1;
						
						int count = 0;
						for(int j = i + 1; j < amount; j++) {
							if(j % 14 == enemyBarnId) {
								amount++;
							} else {	
								slots.get(j % 14).addGem(slot.gems.get(count));
								count++;
							}
						}
						
						if((amount - 1) != playerBarnId) {
							turn = Turn.ENEMY;
							System.out.println("Enemy turn");
						}
						
						slot.clear();
					}
				}
			}
			break;
		case ENEMY:
			if(timer.getTime() >= 2) {
				int num = MyRandom.nextInt(enemySlots.size());
				Slot slot = enemySlots.get(num);
				
				int temp = num + 7 + 1;
				
				int amount = temp + slot.amount - 1;
				
				int count = 0;
				for(int i = temp; i < temp + slot.amount; i++) {
					if(i % 14 == playerBarnId) {
						temp++;
					} else {
						slots.get(i % 14).addGem(slot.gems.get(count));
						count++;
					}
				}
				
				if(amount != enemyBarnId) {
					turn = Turn.PLAYER;
					System.out.println("Player turn");
				}
				
				slot.clear();
				
				timer.reset();
			}
			break;
		}
		
		if(Display.isResized()) {
			updatePositions();
		}
	}
	
	public void render() {
		GUIRenderer.render(texture, Color.WHITE, rect.x, rect.y, 0f, rect.width, rect.height, false);
		
		for(Slot slot : playerSlots) {
			slot.render();
		}
		
		for(Slot slot : enemySlots) {
			slot.render();
		}
		
		playerBarn.render();
		enemyBarn.render();
	}
	
	public void renderText() {
		for(Slot slot : playerSlots) {
			slot.renderText();
		}
		
		for(Slot slot : enemySlots) {
			slot.renderText();
		}
		
		playerBarn.renderText();
		enemyBarn.renderText();
	}

	private void updatePositions() {
		rect.setPosition(Display.getWidth() / 2 - rect.width / 2, 
				Display.getHeight() / 2 - rect.height / 2);
		
		playerBarn.setPosition(rect.x + rect.width - 10 - Slot.SIZE, rect.y + rect.height - 10 - Slot.SIZE);
		enemyBarn.setPosition(rect.x + 10, rect.y + 10);
		
		for(int i = 0; i < playerSlots.size(); i++) {
			Slot slot = playerSlots.get(i);
			slot.setPosition(rect.x + 10 + 60 + 5 + (65 * i), rect.y + 10 + 120);
		}
		
		int count = 0; 
		for(int i = (enemySlots.size() - 1); i >= 0; i--) {
			Slot slot = enemySlots.get(count);
			slot.setPosition(rect.x + 10 + 60 + 5 + (65 * i), rect.y + 10);
			count++;
		}
	}
	
	private class Slot {
		
		private static final int SIZE = 60;
		
		public Rect rect;
		
		private GUIText text;
		public int amount;
		
		public List<GUITexture> gems = new ArrayList<>();
		
		public Slot() {
			rect = new Rect(0, 0, SIZE, SIZE);
			text = new GUIText("", FontRenderer.ARIAL);
			amount = 0;
		}
		
		public void render() {
			GUIRenderer.render(gems);
		}

		public void renderText() {
			FontRenderer.render(text);
		}
		
		public void setPosition(float x, float y) {
			rect.setPosition(x, y);
			
			for(GUITexture gem : gems) {
				gem.rect.setPosition(x, y);
			}
			
			text.setPosition(x + SIZE / 2, y + SIZE);
		}
		
		public void addGem(GUITexture gem) {
			gem.rect.setPosition(rect.x, rect.y);
			gem.rect.setSize(25, 25);
			gems.add(gem);
			amount = gems.size();
			
			text.setText(String.valueOf(amount));
		}
		
		public void clear() {
			gems.clear();
			amount = 0;
			text.setText("");
		}
		
	}
	
}
