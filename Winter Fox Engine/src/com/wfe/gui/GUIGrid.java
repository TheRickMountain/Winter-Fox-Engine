package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class GUIGrid implements GUIComponent {
	
	private int numColumns;
	private int numRows;
	
	public Rect rect;
	private static final Color BAKCGROUND_COLOR = new Color(25, 25, 25, 150).convert();
	public final static int SLOT_SIZE = 50;
	private final static int OFFSET_BETWEEN_SLOTS = 5;
	
	private List<Slot> slots = new ArrayList<Slot>();
	
	public GUIGrid(int numColumns, int numRows) {
		this.numColumns = numColumns;
		this.numRows = numRows;
		
		this.rect = new Rect(0, 0, 0, 0);
		
		setUpGridValues();
		updatePositions();
	}

	@Override
	public void update() {
		if(Display.isResized()) {
			updatePositions();
		}
	}

	@Override
	public void render() {
		GUIRenderer.render(BAKCGROUND_COLOR, 
				rect.x - OFFSET_BETWEEN_SLOTS, 
				rect.y - OFFSET_BETWEEN_SLOTS, 
				rect.rotation, 
				rect.width + (OFFSET_BETWEEN_SLOTS * 2), 
				rect.height + (OFFSET_BETWEEN_SLOTS * 2), 
				false);
		
		for(Slot slot : slots) {
			slot.render();
		}
	}

	@Override
	public void renderText() {
		for(Slot slot : slots) {
			slot.renderText();
		}
	}

	@Override
	public void renderPopUp() {
		
	}

	@Override
	public void renderPopUpText() {
		
	}
	
	private void setUpGridValues() {
		for(int i = 0; i < numRows * numColumns; i++) {
			slots.add(new Slot(new Rect(0, 0, SLOT_SIZE, SLOT_SIZE)));
		}
		
		rect.width = (numColumns * SLOT_SIZE) + ((numColumns - 1) * OFFSET_BETWEEN_SLOTS);
		rect.height = (numRows * SLOT_SIZE) + ((numRows - 1) * OFFSET_BETWEEN_SLOTS);
	}
	
	private void updatePositions() {
		int countX = 0;
		int countY = 0;
		for(int i = 0; i < numRows * numColumns; i++) {
			slots.get(i).rect.setPosition(
					rect.x + (countX * (SLOT_SIZE + OFFSET_BETWEEN_SLOTS)), 
					rect.y + (countY * (SLOT_SIZE + OFFSET_BETWEEN_SLOTS)));
			
			countX++;
			if(countX == numColumns) {
				countX = 0;
				countY++;
			}
		}
	}
	
	public void setPosition(float x, float y) {
		this.rect.x = x;
		this.rect.y = y;
		updatePositions();
	}
	
	public List<Slot> getSlots() {
		return slots;
	}

}
