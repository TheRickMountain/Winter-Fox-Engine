package com.wfe.gui.inventory;

import com.wfe.core.Display;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Arrow;
import com.wfe.game.Game;
import com.wfe.game.World;
import com.wfe.gui.GUIElement;
import com.wfe.gui.Item;
import com.wfe.gui.ItemType;
import com.wfe.gui.Slot;
import com.wfe.input.Mouse;
import com.wfe.utils.Color;
import com.wfe.utils.MathUtils;

public class Equipment implements GUIElement {
	
	private int slotSize = 50;
	
	private Color slotColor = new Color(151, 148, 146, 125).convert();
	private Slot handSlot;
	
	public Equipment() {
		handSlot = new Slot(0, 0, slotSize, slotSize, slotColor);
		updatePositions();
	}
	
	public void update() {
		if(Mouse.isButtonDown(0)) {
			if(handSlot.isMouseOvered()) {
				Mouse.setActiveInGUI(true);
				if(GUIManager.draggedItem != null) {
					if(handSlot.isHasItem()) {
						Item temp = handSlot.getItem();
						handSlot.removeItem();
						handSlot.addItem(GUIManager.draggedItem);
						GUIManager.draggedItem = temp;
					} else {
						if(addItem(GUIManager.draggedItem)){
							GUIManager.draggedItem = null;
						}
					}
				} else {
					if(handSlot.isHasItem()) {
						GUIManager.draggedItem = handSlot.getItem();
						handSlot.removeItem();
					}
				}
			}
		}
		
		if(Mouse.isButtonUp(0)) {
			if(!Mouse.isActiveInGUI()) {
				if(Game.pickedEntity != null) {
					if(!handSlot.isMouseOvered()) {
						if(handSlot.isHasItem()) {
							if(handSlot.getItem().ID == Item.BOW) {
								Arrow arrow = new Arrow(Game.player, Game.pickedEntity,
										new Transformation(Game.player.getTransform().getX(), 1, Game.player.getTransform().getZ()));
								arrow.getTransform().setRotY(
										MathUtils.getRotation(
												Game.player.getTransform().getX(), Game.player.getTransform().getZ(), 
												Game.pickedEntity.getTransform().getX(), Game.pickedEntity.getTransform().getZ()) - 180);
								World.getWorld().addEntity(arrow);
							}
						}
					}
				}
			}
		}
		
		if(Display.isResized()) {
			updatePositions();
		}
	}
	
	public void render() {
		handSlot.render();
	}
	
	@Override
	public void renderText() {
		
	}

	private void updatePositions() {
		handSlot.setPosition(
				Display.getWidth() - slotSize - 10, 
				Display.getHeight() - slotSize);
	}
	
	public boolean addItem(Item item) {
		if(item.type.equals(ItemType.TOOL) || item.type.equals(ItemType.WEAPON)) {
			handSlot.addItem(item);
			return true;
		}
		return false;
	}
	
	public int getHandSlotItemID() {
		if(handSlot.getItem() == null) {
			return -1;
		} else {
			return handSlot.getItem().ID;
		}
	}
	
}
