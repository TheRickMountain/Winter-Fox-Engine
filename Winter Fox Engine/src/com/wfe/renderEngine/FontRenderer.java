package com.wfe.renderEngine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.awt.Font;

import org.lwjgl.opengl.GL11;

import com.wfe.core.Display;
import com.wfe.font.FontTexture;
import com.wfe.font.GUIText;
import com.wfe.graph.Mesh;
import com.wfe.math.Matrix4f;
import com.wfe.utils.MathUtils;
import com.wfe.utils.OpenglUtils;

public class FontRenderer {

	private static final String CHARSET = "ISO-8859-1";
	
    private final static Matrix4f projModelMatrix = new Matrix4f();
    private final static Matrix4f orthProjMatrix = new Matrix4f();
    private final static Matrix4f modelMatrix = new Matrix4f();
    
    private static FontShader shader;
    
    public static final FontTexture ARIAL = new FontTexture(new Font("Arial", Font.PLAIN, 20), CHARSET);
    
    public FontRenderer() {
    	shader = new FontShader();
    	
    	MathUtils.getOrthoProjectionMatrix(orthProjMatrix, 0, Display.getWidth(), Display.getHeight(), 0);
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    
    public void prebare() {
    	OpenglUtils.cullBackFaces(false);
		OpenglUtils.alphaBlending(true);
		OpenglUtils.depthTest(false);
    	
    	if(Display.isResized()) {
    		MathUtils.getOrthoProjectionMatrix(orthProjMatrix, 0, Display.getWidth(), Display.getHeight(), 0);
    	}
    	
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, ARIAL.getTexture().getID());
    	
        shader.start();
    }

    public static void render(GUIText text) {        
        MathUtils.getModelMatrix(modelMatrix, text.getX(), text.getY(), 
        		text.getRotation(), text.getScaleX(), text.getScaleY());
        Matrix4f.mul(orthProjMatrix, modelMatrix, projModelMatrix);
        
        Mesh mesh = text.getMesh();
        
        shader.projModelMatrix.loadMatrix(projModelMatrix);
        shader.colour.loadColor(text.getColor());
        shader.hasTexture.loadBoolean(true);

        mesh.bind(0, 1);
        
        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

        mesh.unbind(0, 1);
    }
    
    public void finish() {
    	shader.stop();
		
		OpenglUtils.cullBackFaces(true);
		OpenglUtils.alphaBlending(false);
		OpenglUtils.depthTest(true);
    }

    public void cleanup() {
        shader.cleanUp();
    }
}
