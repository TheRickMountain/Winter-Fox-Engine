package com.wfe.renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.wfe.font.FontType;
import com.wfe.font.GUIText;
import com.wfe.utils.OpenglUtils;

public class FontRenderer {
	
	private FontShader shader;
	
	protected FontRenderer() throws Exception {	
		shader = new FontShader();
	}
	
	public void render(Map<FontType, List<GUIText>> texts) {
		prepare();	
		
		for(FontType font : texts.keySet()) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
			for(GUIText text : texts.get(font)) {
				render(text);
			}
		}
		
		finish();
	}
	
	private void prepare() {
		OpenglUtils.alphaBlending(true);
		OpenglUtils.depthTest(false);
		
		shader.start();
	}
	
	private void render(GUIText text) {
		shader.translation.loadVec2(text.getX(), text.getY());
		shader.color.loadVec3(text.getColor());
		
		GL30.glBindVertexArray(text.getMesh());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	private void finish() {
		shader.stop();
		
		OpenglUtils.alphaBlending(false);
		OpenglUtils.depthTest(true);
	}
	
	public void cleanup() {
		shader.cleanUp();
	}

}
