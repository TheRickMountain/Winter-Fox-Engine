package com.wfe.gui;

import com.wfe.core.ResourceManager;
import com.wfe.font.GUIText;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class Slot implements GUIComponent {
	
	public Rect rect;
	private static final Texture DEFAULT = ResourceManager.getTexture("slot_ui");
	private static final Texture SELECTED = ResourceManager.getTexture("selected_slot_ui");
	public static int SIZE = 60;
	
	private Item item;
	private boolean hasItem = false;
	
	private GUIText countText;
	private int count = 0;
	
	private boolean selected = false;
	
	public Slot(Rect rect) {
		this.rect = rect;
		this.countText = new GUIText("", FontRenderer.ARIAL);
		this.countText.setScale(0.8f);
		addItem(ItemDatabase.getItem(Item.NULL), 0);
	}
	
	@Override
	public void render() {
		if(!selected) {
			GUIRenderer.render(DEFAULT, Color.WHITE, rect.x, rect.y, 0, rect.width, rect.height, false);
		} else {
			GUIRenderer.render(SELECTED, Color.WHITE, rect.x, rect.y, 0, rect.width, rect.height, false);
		}
		if(item.id != Item.NULL) {
			GUIRenderer.render(item.icon, Color.WHITE, rect.x, rect.y, 0, rect.width, rect.height, false);
		}
	}

	@Override
	public void renderText() {
		if(item.id != Item.NULL) {
			countText.setPosition(
					rect.x + rect.width - countText.getWidth(), 
					rect.y + rect.height - (countText.getHeight() / 2));
			FontRenderer.render(countText);
		}
	}
	
	public void addItem(Item item, int count) {
		this.item = item;
		if(this.item.id != Item.NULL) {
			hasItem = true;
		} else {
			hasItem = false;
		}
		
		if(count < 0)
			count = 0;
		
		if(count > 1)
			this.countText.setText("" + count);
		else
			this.countText.setText("");
		
		this.count = count;
	}
	
	public Item getItem() {
		return item;
	}
	
	public boolean isHasItem() {
		return hasItem;
	}

	public int getCount() {
		return count;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
