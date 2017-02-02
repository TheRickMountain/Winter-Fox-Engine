package com.wfe.gui;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;
import com.wfe.utils.Rect;

public class Slot implements GUIComponent {

	public Rect rect;
	private Item item;
	private int count;
	private boolean hasItem;
	private GUIText text;
	private static final Texture SLOT_TEXTURE = ResourceManager.getTexture("slot_ui");
	public int itemSize = 50;
	public static final int SLOT_SIZE = 60;
	
	public Slot(Rect rect) {
		this.rect = rect;
		this.text = new GUIText("", 1.1f, FontRenderer.font, 0.0f, 0.0f, 
				(1.0f / Display.getWidth()) * SLOT_SIZE, true);
		this.text.setColor(1.0f, 1.0f, 1.0f);
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		GUIRenderer.render(SLOT_TEXTURE, rect.x, rect.y, rect.rotation, rect.width, rect.height, false);
		if(hasItem) {
			GUIRenderer.render(item.icon, rect.x + rect.width / 2, rect.y + rect.height / 2, 
					rect.rotation, itemSize, itemSize, true);
		}
	}

	@Override
	public void renderText() {
		if(hasItem && (count > 1)) {
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
			this.count = count;
			text.setText("" + count);
		}
	}
	
	public void removeItem() {
		this.item = null;
		this.count = 0;
		this.hasItem = false;
	}
	
	public boolean isHasItem() {
		return hasItem;
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getCount() {
		return count;
	}

}
