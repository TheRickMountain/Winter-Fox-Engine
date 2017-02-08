package com.wfe.gui;

import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;
import com.wfe.utils.Rect;

public class Button {
	
	public Rect rect;
	private Texture texture;
	private GUIText text;
	
	public Button(Rect rect, Texture texture, String name) {
		this.rect = rect;
		this.texture = texture;
		this.text = new GUIText(name, 1.3f, FontRenderer.font, 0.0f, 0.0f, rect.width, true);
		this.text.setColor(1.0f, 1.0f, 1.0f);
	}
	
	public void render() {
		GUIRenderer.render(texture, rect.x, rect.y, 0, rect.width, rect.height, false);
	}
	
	public void renderText() {
		FontRenderer.render(text, rect.x, rect.y + (rect.height / 4));
	}

}
