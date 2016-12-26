package com.wfe.tileEngine;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.ecs.Entity;
import com.wfe.graph.ShaderProgram;
import com.wfe.textures.Texture;
import com.wfe.textures.TextureBuilder;
import com.wfe.utils.MyFile;

public class Terrain {

	private List<Chunk> chunks = new ArrayList<Chunk>();
	
	private int sizeX, sizeZ;
	private Camera camera;
	private Texture spriteSheet;
	private ShaderProgram shader;
	
	public Terrain(int sizeX, int sizeZ, Camera camera) throws Exception {
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
		this.camera = camera;
		init();
	}
	
	public void init() throws Exception {
		TextureBuilder texBuilder = Texture.newTexture(new MyFile("textures/terrain.png"));
		texBuilder.clampEdges().anisotropic().nearestFiltering();
		spriteSheet = texBuilder.create();
		
		for(int x = 0; x < sizeX; x++) {
			for(int y = 0; y < sizeZ; y++) {
				chunks.add(new Chunk(x, y));
			}
		}
		
		shader = new ShaderProgram();
		shader.createVertexShader("/shaders/terrain.vert");
		shader.createFragmentShader("/shaders/terrain.frag");
		shader.link();
		
		// Vertex shader
		shader.createUniform("projectionMatrix");
		shader.createUniform("viewMatrix");
		
		// Fragment shader
		shader.createUniform("image");
		
		shader.bind();
		shader.setUniform("projectionMatrix", camera.getProjectionMatrix());
		shader.setUniform("image", 0);
		shader.unbind();
	}
	
	public void render() {
		shader.bind();
		if(Display.isResized()) {
			shader.setUniform("projectionMatrix", camera.getProjectionMatrix());
		}
		
		shader.setUniform("viewMatrix", camera.getViewMatrix());
		spriteSheet.bind(0);
		for(Chunk chunk : chunks) {
			chunk.render();
		}
		shader.unbind();
	}
	
	public void setTile(int x, int y, int id) {
		int tX = (x / 16) * 16;
		int tY = (y / 16) * 16;
		
		for(Chunk chunk : chunks) {
			if(chunk.getPosX() == tX && chunk.getPosY() == tY) {
				chunk.setTile(x - tX, y - tY, id);
				return;
			}
		}
	}
	
	public boolean setTileEntity(int x, int y, Entity entity) {
		int tX = (x / 16) * 16;
		int tY = (y / 16) * 16;
		
		for(Chunk chunk : chunks) {
			if(chunk.getPosX() == tX && chunk.getPosY() == tY) {
				return chunk.setEntity(x - tX, y - tY, entity);
			}
		}
		return false;
	}
	
	public void removeTileEntity(int x, int y) {
		int tX = (x / 16) * 16;
		int tY = (y / 16) * 16;
		
		for(Chunk chunk : chunks) {
			if(chunk.getPosX() == tX && chunk.getPosY() == tY) {
				chunk.removeEntity(x - tX, y - tY);
				return;
			}
		}
	}
	
	public void cleanup() {
		for(Chunk chunk : chunks) {
			chunk.cleanup();
		}
		shader.cleanup();
	}
	
}
