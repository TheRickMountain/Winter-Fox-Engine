package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

public class Crafting implements GUIComponent {
	
	private List<Slot> slots = new ArrayList<Slot>();
	private float offset = 5;
	
	public boolean open = false;
	
	public Crafting() {	
		updatePositions();
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		
	}

	@Override
	public void renderText() {
		
	}

	@Override
	public void renderPopUp() {
		
	}

	@Override
	public void renderPopUpText() {
		
	}
	
	public void update(int[] slots, int[] count) {
		
	}
	
	public void updatePositions() {
		int countX = 0;
		int countY = 0;
		for(int i = 0; i < slots.size(); i++) {
			slots.get(i).rect.setPosition(
					(GUIManager.mainFrame.getX() + offset) + (countX * (Slot.SLOT_SIZE + offset)), 
					(GUIManager.mainFrame.getY() + offset) + (countY * (Slot.SLOT_SIZE + offset)));
			
			countX++;
			if(countX == 4) {
				countX = 0;
				countY++;
			}
		}
	}

}
