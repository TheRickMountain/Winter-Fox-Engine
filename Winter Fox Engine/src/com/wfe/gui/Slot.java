package com.wfe.gui;

import com.wfe.core.ResourceManager;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;
import com.wfe.utils.Rect;

public class Slot implements GUIComponent {

	private static final Texture SLOT_TEXTURE = ResourceManager.getTexture("slot_ui");
	public static final int SLOT_SIZE = 60;
	
	public Rect rect;
	private Item item;
	private GUIText text;
	
	private int itemCount;
	private boolean hasItem;
	
	public boolean selected = false;
	
	public Slot(Rect rect) {
		this.rect = rect;
		this.text = new GUIText("", 1.1f, FontRenderer.font, 0.0f, 0.0f, rect.width, true);
		this.text.setColor(1.0f, 1.0f, 1.0f);
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		if(selected) {
			GUIRenderer.render(SLOT_TEXTURE, rect.x + rect.width / 2, rect.y + rect.height / 2, 
					rect.rotation, SLOT_SIZE + 10, SLOT_SIZE + 10, true);
		} else {
			GUIRenderer.render(SLOT_TEXTURE, rect.x + rect.width / 2, rect.y + rect.height / 2, 
					rect.rotation, SLOT_SIZE, SLOT_SIZE, true);
		}
		
		if(hasItem) {
			GUIRenderer.render(item.icon, rect.x + rect.width / 2, rect.y + rect.height / 2, 
					rect.rotation, SLOT_SIZE - 10, SLOT_SIZE - 10, true);
		}
	}

	@Override
	public void renderText() {
		if(hasItem && (itemCount > 1)) {
			FontRenderer.render(text, rect.x, rect.y);
		}
	}

	@Override
	public void renderPopUp() {
		
	}

	@Override
	public void renderPopUpText() {
		
	}
	
	public void addItem(Item item, int count) {
		this.item = item;
		if(this.item != null) {
			hasItem = true;
			this.itemCount = count;
			text.setText("" + count);
		}
	}
	
	public void removeItem() {
		this.item = null;
		this.itemCount = 0;
		this.hasItem = false;
	}
	
	public boolean isHasItem() {
		return hasItem;
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getItemCount() {
		return itemCount;
	}

}
