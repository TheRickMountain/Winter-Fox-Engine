package com.wfe.gui;

import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class GUISlot implements GUIComponent {

	public Rect rect;
	private static final Color COLOR = new Color(0, 0, 0, 150).convert();
	//private static final Color TEXT_COLOR = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	private Item item;
	private int count;
	private boolean hasItem;
	private GUIText text;
	
	public GUISlot(Rect rect) {
		this.rect = rect;
		this.text = new GUIText("", 1.1f, FontRenderer.font, 0.0f, 0.0f, 1.0f, false);
		this.text.setColor(1.0f, 1.0f, 1.0f);
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		GUIRenderer.render(COLOR, rect.x, rect.y, rect.rotation, rect.width, rect.height, false);
		if(hasItem) {
			GUIRenderer.render(item.icon, rect.x, rect.y, rect.rotation, rect.width, rect.height, false);
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
	
	public boolean isHasItem() {
		return hasItem;
	}

}
