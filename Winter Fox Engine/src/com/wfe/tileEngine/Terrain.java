package com.wfe.tileEngine;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.ecs.StaticEntity;
import com.wfe.textures.Texture;
import com.wfe.utils.MathUtils;
import com.wfe.utils.MyFile;
import com.wfe.weather.DirectionalLight;

public class Terrain {

	private List<Chunk> chunks = new ArrayList<Chunk>();
	
	private int sizeX, sizeZ;
	private Camera camera;
	private Texture spriteSheet;
	
	private TerrainShader shader;
	
	private HeightGenerator heightGenerator;
	
	public Terrain(int sizeX, int sizeZ, Camera camera) throws Exception {
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
		this.camera = camera;
		shader = new TerrainShader();
		heightGenerator = new HeightGenerator();
		init();
	}
	
	public void init() throws Exception {
		spriteSheet = Texture.newTexture(new MyFile("textures/terrain.png"))
				.normalMipMap().clampEdges().anisotropic().nearestFiltering().create();
		
		for(int x = 0; x < sizeX; x++) {
			for(int y = 0; y < sizeZ; y++) {
				chunks.add(new Chunk(x, y, heightGenerator));
			}
		}
		
		shader.start();
		shader.projectionMatrix.loadMatrix(camera.getProjectionMatrix());
		shader.stop();
	}
	
	public void update(float x, float z) {
		for(Chunk chunk : chunks) {
			float distance = MathUtils.getDistance(x, z, chunk.getPosX(), chunk.getPosY());
			if(distance >= 48) {
				chunk.render = false;
			} else {
				chunk.render = true;
			}
		}
	}
	
	public void render() {
		shader.start();
		if(Display.isResized()) {
			shader.projectionMatrix.loadMatrix(camera.getProjectionMatrix());
		}
		
		shader.viewMatrix.loadMatrix(camera.getViewMatrix());
		shader.lightDirection.loadVec3(DirectionalLight.LIGHT_DIR);
		shader.lightColor.loadVec3(DirectionalLight.LIGHT_COLOR);
		shader.ambientLight.loadVec3(DirectionalLight.AMBIENT_COLOR);
		
		spriteSheet.bind(0);
		
		for(Chunk chunk : chunks) {
			if(chunk.render)
				chunk.render();
		}
		shader.start();
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
	
	public boolean setTileEntity(int x, int y, StaticEntity entity) {
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
		shader.cleanUp();
	}
	
}
