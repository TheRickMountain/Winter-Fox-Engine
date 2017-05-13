package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class Mancala {
	
	private Rect rect;
	private Texture texture;
	
	private List<Slot> slots = new ArrayList<>();
	private List<Slot> mySlots = new ArrayList<>();
	private List<Slot> enemySlots = new ArrayList<>();
	private Slot myBarn, enemyBarn;
	
	public Mancala() {
		rect = new Rect(0, 0, 20 + Slot.SIZE * 8 + 7 * 5, 20 + Slot.SIZE * 3);
		texture = ResourceManager.getTexture("mancala_ui");
		
		for(int i = 0; i < 6; i++) {
			mySlots.add(new Slot());
			enemySlots.add(new Slot());
			
			for(int j = 0; j < 4; j++) {
				mySlots.get(i).addGem(new GUITexture(ResourceManager.getTexture("gem_" + j + "_ui")));
				enemySlots.get(i).addGem(new GUITexture(ResourceManager.getTexture("gem_" + j + "_ui")));
			}
		}
		
		myBarn = new Slot();
		enemyBarn = new Slot();
		
		slots.addAll(mySlots);
		slots.add(myBarn);
		slots.addAll(enemySlots);
		slots.add(enemyBarn);
		
		updatePositions();
	}
	
	public void update() {
		if(Mouse.isButtonDown(0)) {
			for(int i = 0; i < mySlots.size(); i++) {
				Slot slot = mySlots.get(i);
				if(slot.rect.isMouseOvered()) {
					
					int amount = i + slot.amount + 1;
					
					int count = 0;
					for(int j = i + 1; j < amount; j++) {
						slots.get(j).addGem(slot.gems.get(count));
						count++;
					}
					
					slot.clear();
				}
			}
		}
		
		if(Display.isResized()) {
			updatePositions();
		}
	}
	
	public void render() {
		GUIRenderer.render(texture, Color.WHITE, rect.x, rect.y, 0f, rect.width, rect.height, false);
		
		for(Slot slot : mySlots) {
			slot.render();
		}
		
		for(Slot slot : enemySlots) {
			slot.render();
		}
		
		myBarn.render();
		enemyBarn.render();
	}
	
	public void renderText() {
		
	}

	private void updatePositions() {
		rect.setPosition(Display.getWidth() / 2 - rect.width / 2, 
				Display.getHeight() / 2 - rect.height / 2);
		
		myBarn.setPosition(rect.x + rect.width - 10 - Slot.SIZE, rect.y + rect.height - 10 - Slot.SIZE);
		enemyBarn.setPosition(rect.x + 10, rect.y + 10);
		
		for(int i = 0; i < mySlots.size(); i++) {
			Slot slot = mySlots.get(i);
			slot.setPosition(rect.x + 10 + 60 + 5 + (65 * i), rect.y + 10 + 120);
		}
		
		for(int i = 0; i < enemySlots.size(); i++) {
			Slot slot = enemySlots.get(i);
			slot.setPosition(rect.x + 10 + 60 + 5 + (65 * i), rect.y + 10);
		}
	}
	
	private class Slot {
		
		private static final int SIZE = 60;
		
		public Rect rect;
		
		public int amount;
		
		public List<GUITexture> gems = new ArrayList<>();
		
		public Slot() {
			rect = new Rect(0, 0, SIZE, SIZE);
			amount = 0;
		}
		
		public void render() {
			GUIRenderer.render(gems);
		}

		public void setPosition(float x, float y) {
			rect.setPosition(x, y);
			
			for(GUITexture gem : gems) {
				gem.rect.setPosition(x, y);
			}
		}
		
		public void addGem(GUITexture gem) {
			gem.rect.setPosition(rect.x, rect.y);
			gem.rect.setSize(25, 25);
			gems.add(gem);
			amount = gems.size();
		}
		
		public void clear() {
			gems.clear();
			amount = 0;
		}
		
	}
	
}
