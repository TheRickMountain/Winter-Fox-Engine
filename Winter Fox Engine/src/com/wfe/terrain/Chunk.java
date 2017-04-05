package com.wfe.terrain;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import com.wfe.core.World;
import com.wfe.math.Vector2f;
import com.wfe.utils.OpenglUtils;

public class Chunk {

	private World world;
	
	private static final int BYTES_PER_FLOAT = 4;
	
	public static final int SIZE = 16;
	private Vector2f position;
	private int VAO, VBO;
	
	private float[] vertices;
	private float[] texCoords;
	private float[] normals;
	
	private boolean isVisible = true;
	
	public static final float SPRITE_SHEET = 4;
	private boolean rebuild = false;
	
	public Chunk(int x, int y) {
		world = World.getWorld();
		position = new Vector2f(x * SIZE, y * SIZE);	
		
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				world.getTile(i + (int)position.x, j + (int)position.y).setChunk(this);
			}
		}
		
		rebuild();
	}
	
	private void create() {
		List<Float> vList = new ArrayList<Float>();
		List<Float> vtList = new ArrayList<Float>();
		List<Float> nList = new ArrayList<Float>();
		
		for(int x = 0; x < SIZE; x++) {
			for(int y = 0; y < SIZE; y++) {
				int currX = x + (int)position.x;
				int currY = y + (int)position.y;
			
				float height = world.getTile(currX, currY).getHeight();
				
				top(currX, currY, height, vList, vtList, nList);
				
				if(height > 0) {
					if((currX - 1) >= 0 && world.getTile(currX - 1, currY).getHeight() == 0) {
						left(currX, currY, height, vList, vtList, nList);
					}
					
					if((currX + 1) <= 159 && world.getTile(currX + 1, currY).getHeight() == 0) {
						right(currX, currY, height, vList, vtList, nList);
					}
					
					if((currY + 1) <= 159 && world.getTile(currX, currY + 1).getHeight() == 0) {
						front(currX, currY, height, vList, vtList, nList);
					}
					
					if((currY - 1) >= 0 && world.getTile(currX, currY - 1).getHeight() == 0) {
						back(currX, currY, height, vList, vtList, nList);
					}
				}
			}
		}
		
		vertices = new float[vList.size()];
		int i = 0;
		
		for(Float f : vList) {
			vertices[i++] = (f != null ? f : Float.NaN);
		}
		
		texCoords = new float[vtList.size()];
		i = 0;
		
		for(Float f : vtList) {
			texCoords[i++] = (f != null ? f : Float.NaN);
		}
		
		normals = new float[nList.size()];
		i = 0;
		
		for(Float f : nList) {
			normals[i++] = (f != null ? f : Float.NaN);
		}
	}
	
	private void initGL() {
		int vertexByteCount = BYTES_PER_FLOAT * (3 + 2 + 3);
		
		VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(VAO);
		
		VBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
		FloatBuffer floatBuffer = OpenglUtils.toFloatBuffer(vertices, texCoords, normals);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, vertexByteCount, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, vertexByteCount, BYTES_PER_FLOAT * 3);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, vertexByteCount, BYTES_PER_FLOAT * (3 + 2));
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL30.glBindVertexArray(0);
		
		MemoryUtil.memFree(floatBuffer);
	}
	
	private void top(float x, float y, float height, List<Float> vList, List<Float> vtList, List<Float> nList) {
		float u0 = ((world.getTile((int) x, (int) y).getId() % (int)SPRITE_SHEET) / SPRITE_SHEET);
        float u1 = (u0 + (1.0f / SPRITE_SHEET));
        float v0 = ((world.getTile((int) x,(int) y).getId() / (int)SPRITE_SHEET) / SPRITE_SHEET);
        float v1 = (v0 + (1.0f / SPRITE_SHEET));
		
		// Left Top vertex
		vList.add(x); // x
		vList.add(height);
        vList.add(y); // y
        vtList.add(u0);
        vtList.add(v0);
        nList.add(0f);
        nList.add(1f);
        nList.add(0f);
        
        // Left Bottom vertex
        vList.add(x); // x
        vList.add(height);
        vList.add(y + 1.0f); // y
        vtList.add(u0);
        vtList.add(v1);
        nList.add(0f);
        nList.add(1f);
        nList.add(0f);
        
        // Right Bottom vertex
        vList.add(x + 1.0f); // x
        vList.add(height);
        vList.add(y + 1.0f); // y
        vtList.add(u1);
        vtList.add(v1);
        nList.add(0f);
        nList.add(1f);
        nList.add(0f);
        
        // Right Top vertex
        vList.add(x + 1.0f); // x
        vList.add(height);
        vList.add(y); // y
        vtList.add(u1);
        vtList.add(v0);
        nList.add(0f);
        nList.add(1f);
        nList.add(0f);
        
        // Left Top vertex
        vList.add(x); // x
        vList.add(height);
        vList.add(y); // y
        vtList.add(u0);
        vtList.add(v0);
        nList.add(0f);
        nList.add(1f);
        nList.add(0f);
        
        // Right Bottom vertex
        vList.add(x + 1.0f); // x
        vList.add(height);
        vList.add(y + 1.0f); // y
        vtList.add(u1);
        vtList.add(v1);
        nList.add(0f);
        nList.add(1f);
        nList.add(0f);
	}

	private void left(float x, float y, float height,  List<Float> vList, List<Float> vtList, List<Float> nList) {
		float u0 = ((world.getTile((int) x, (int) y).getId() % (int)SPRITE_SHEET) / SPRITE_SHEET);
        float u1 = (u0 + (1.0f / SPRITE_SHEET));
        float v0 = ((world.getTile((int) x,(int) y).getId() / (int)SPRITE_SHEET) / SPRITE_SHEET);
        float v1 = (v0 + (1.0f / SPRITE_SHEET));
		
		// Left Top vertex
		vList.add(x); // x
		vList.add(0.0f);
        vList.add(y + 1.0f); // y
        vtList.add(u0);
        vtList.add(v0);
        nList.add(-1f);
        nList.add(0f);
        nList.add(0f);
        
        // Left Bottom vertex
        vList.add(x); // x
        vList.add(height);
        vList.add(y + 1.0f); // y
        vtList.add(u0);
        vtList.add(v1);
        nList.add(-1f);
        nList.add(0f);
        nList.add(0f);
        
        // Right Bottom vertex
        vList.add(x); // x
        vList.add(height);
        vList.add(y); // y
        vtList.add(u1);
        vtList.add(v1);
        nList.add(-1f);
        nList.add(0f);
        nList.add(0f);
        
        // Right Top vertex
        vList.add(x); // x
        vList.add(0.0f);
        vList.add(y); // y
        vtList.add(u1);
        vtList.add(v0);
        nList.add(-1f);
        nList.add(0f);
        nList.add(0f);
        
        // Left Top vertex
        vList.add(x); // x
        vList.add(0.0f);
        vList.add(y + 1.0f); // y
        vtList.add(u0);
        vtList.add(v0);
        nList.add(-1f);
        nList.add(0f);
        nList.add(0f);
        
        // Right Bottom vertex
        vList.add(x); // x
        vList.add(height);
        vList.add(y); // y
        vtList.add(u1);
        vtList.add(v1);
        nList.add(-1f);
        nList.add(0f);
        nList.add(0f);
	}
	
	private void right(float x, float y, float height,  List<Float> vList, List<Float> vtList, List<Float> nList) {
		float u0 = ((world.getTile((int) x, (int) y).getId() % (int)SPRITE_SHEET) / SPRITE_SHEET);
        float u1 = (u0 + (1.0f / SPRITE_SHEET));
        float v0 = ((world.getTile((int) x,(int) y).getId() / (int)SPRITE_SHEET) / SPRITE_SHEET);
        float v1 = (v0 + (1.0f / SPRITE_SHEET));
		
		// Left Top vertex
		vList.add(x + 1.0f); // x
		vList.add(0.0f);
        vList.add(y); // y
        vtList.add(u0);
        vtList.add(v0);
        nList.add(1f);
        nList.add(0f);
        nList.add(0f);
        
        // Left Bottom vertex
        vList.add(x + 1.0f); // x
        vList.add(height);
        vList.add(y); // y
        vtList.add(u0);
        vtList.add(v1);
        nList.add(1f);
        nList.add(0f);
        nList.add(0f);
        
        // Right Bottom vertex
        vList.add(x + 1.0f); // x
        vList.add(height);
        vList.add(y + 1.0f); // y
        vtList.add(u1);
        vtList.add(v1);
        nList.add(1f);
        nList.add(0f);
        nList.add(0f);
        
        // Right Top vertex
        vList.add(x + 1.0f); // x
        vList.add(0.0f);
        vList.add(y + 1.0f); // y
        vtList.add(u1);
        vtList.add(v0);
        nList.add(1f);
        nList.add(0f);
        nList.add(0f);
        
        // Left Top vertex
        vList.add(x + 1.0f); // x
        vList.add(0.0f);
        vList.add(y); // y
        vtList.add(u0);
        vtList.add(v0);
        nList.add(1f);
        nList.add(0f);
        nList.add(0f);
        
        // Right Bottom vertex
        vList.add(x + 1.0f); // x
        vList.add(height);
        vList.add(y + 1.0f); // y
        vtList.add(u1);
        vtList.add(v1);
        nList.add(1f);
        nList.add(0f);
        nList.add(0f);
	}
	
	private void front(float x, float y, float height,  List<Float> vList, List<Float> vtList, List<Float> nList) {
		float u0 = ((world.getTile((int) x, (int) y).getId() % (int)SPRITE_SHEET) / SPRITE_SHEET);
        float u1 = (u0 + (1.0f / SPRITE_SHEET));
        float v0 = ((world.getTile((int) x,(int) y).getId() / (int)SPRITE_SHEET) / SPRITE_SHEET);
        float v1 = (v0 + (1.0f / SPRITE_SHEET));
		
		// Left Top vertex
		vList.add(x + 1.0f); // x
		vList.add(0.0f);
        vList.add(y + 1.0f); // y
        vtList.add(u0);
        vtList.add(v0);
        nList.add(0f);
        nList.add(0f);
        nList.add(1f);
        
        // Left Bottom vertex
        vList.add(x + 1.0f); // x
        vList.add(height);
        vList.add(y + 1.0f); // y
        vtList.add(u0);
        vtList.add(v1);
        nList.add(0f);
        nList.add(0f);
        nList.add(1f);
        
        // Right Bottom vertex
        vList.add(x); // x
        vList.add(height);
        vList.add(y + 1.0f); // y
        vtList.add(u1);
        vtList.add(v1);
        nList.add(0f);
        nList.add(0f);
        nList.add(1f);
        
        // Right Top vertex
        vList.add(x); // x
        vList.add(0.0f);
        vList.add(y + 1.0f); // y
        vtList.add(u1);
        vtList.add(v0);
        nList.add(0f);
        nList.add(0f);
        nList.add(1f);
        
        // Left Top vertex
        vList.add(x + 1.0f); // x
        vList.add(0.0f);
        vList.add(y + 1.0f); // y
        vtList.add(u0);
        vtList.add(v0);
        nList.add(0f);
        nList.add(0f);
        nList.add(1f);
        
        // Right Bottom vertex
        vList.add(x); // x
        vList.add(height);
        vList.add(y + 1.0f); // y
        vtList.add(u1);
        vtList.add(v1);
        nList.add(0f);
        nList.add(0f);
        nList.add(1f);
	}
	
	private void back(float x, float y, float height,  List<Float> vList, List<Float> vtList, List<Float> nList) {
		float u0 = ((world.getTile((int) x, (int) y).getId() % (int)SPRITE_SHEET) / SPRITE_SHEET);
        float u1 = (u0 + (1.0f / SPRITE_SHEET));
        float v0 = ((world.getTile((int) x,(int) y).getId() / (int)SPRITE_SHEET) / SPRITE_SHEET);
        float v1 = (v0 + (1.0f / SPRITE_SHEET));
		
		// Left Top vertex
		vList.add(x); // x
		vList.add(0.0f);
        vList.add(y); // y
        vtList.add(u0);
        vtList.add(v0);
        nList.add(0f);
        nList.add(0f);
        nList.add(-1f);
        
        // Left Bottom vertex
        vList.add(x); // x
        vList.add(height);
        vList.add(y); // y
        vtList.add(u0);
        vtList.add(v1);
        nList.add(0f);
        nList.add(0f);
        nList.add(-1f);
        
        // Right Bottom vertex
        vList.add(x + 1.0f); // x
        vList.add(height);
        vList.add(y); // y
        vtList.add(u1);
        vtList.add(v1);
        nList.add(0f);
        nList.add(0f);
        nList.add(-1f);
        
        // Right Top vertex
        vList.add(x + 1.0f); // x
        vList.add(0.0f);
        vList.add(y); // y
        vtList.add(u1);
        vtList.add(v0);
        nList.add(0f);
        nList.add(0f);
        nList.add(-1f);
        
        // Left Top vertex
        vList.add(x); // x
        vList.add(0.0f);
        vList.add(y); // y
        vtList.add(u0);
        vtList.add(v0);
        nList.add(0f);
        nList.add(0f);
        nList.add(-1f);
        
        // Right Bottom vertex
        vList.add(x + 1.0f); // x
        vList.add(height);
        vList.add(y); // y
        vtList.add(u1);
        vtList.add(v1);
        nList.add(0f);
        nList.add(0f);
        nList.add(-1f);
	}
	
	private void rebuild() {
		create();
		initGL();
	}
	
	public void render() {
		if(rebuild) {
			cleanup();
			rebuild();
			rebuild = false;
		}
		
		GL30.glBindVertexArray(VAO);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices.length / 3);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	public void cleanup() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(VBO);
		
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(VAO);
	}
	
	public int getPosX() {
		return (int) position.x;
	}
	
	public int getPosY() {
		return (int) position.y;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				Tile tile = world.getTile(i + (int)position.x, j + (int)position.y);
				if(tile.isHasEntity()) {
					tile.getEntity().setVisible(isVisible);
				}
			}
		}
		this.isVisible = isVisible;
	}
	
	protected void setRebuild(boolean value) {
		rebuild = value;
	}
	
}