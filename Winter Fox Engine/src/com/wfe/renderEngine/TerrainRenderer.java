package com.wfe.renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.game.Game;
import com.wfe.math.Matrix4f;
import com.wfe.terrain.Terrain;
import com.wfe.terrain.TerrainBlock;
import com.wfe.textures.Texture;
import com.wfe.utils.MathUtils;
import com.wfe.utils.OpenglUtils;
import com.wfe.weather.DirectionalLight;

public class TerrainRenderer {

	 private TerrainShader shader;

	 private Matrix4f modelMatrix = new Matrix4f();

	 private Camera camera;
	 
	 private Texture texture;

	 public TerrainRenderer(Camera camera) {
		 this.camera = camera;
		 
		 shader = new TerrainShader();
		 
		 shader.start();
		 shader.projectionMatrix.loadMatrix(camera.getProjectionMatrix());
		 shader.stop();
		 
		 texture = ResourceManager.getTexture("terrain");
	 }

	 public void render(Map<Terrain, List<TerrainBlock>> terrainBatches) {
		 shader.start();
		 
		 if(Display.isResized()) {
			 shader.projectionMatrix.loadMatrix(camera.getProjectionMatrix());
		 }
		 
		 shader.viewMatrix.loadMatrix(camera.getViewMatrix());
		 shader.lightPosition.loadVec3(DirectionalLight.LIGHT_POSITION);
		 shader.lightColor.loadVec3(DirectionalLight.LIGHT_COLOR);

		 /*for (List<TerrainBlock> blocks : terrainBatches.values()) {
			 for (TerrainBlock block : blocks) {
				 block.setStitching();
				 LodCalculator.calculateTerrainLOD(block, camera);
			 }
		 }*/
		 
		 OpenglUtils.cullBackFaces(true);
		 for(Terrain terrain : terrainBatches.keySet()) {
			 prepareTerrainInstance(terrain, camera);
			 List<TerrainBlock> batch = terrainBatches.get(terrain);
			 for(TerrainBlock terrainBlock : batch) {
				 if(MathUtils.getDistance(terrainBlock.getX(), terrainBlock.getZ(), 
						 Game.player.getTransform().getX(), Game.player.getTransform().getZ()) <= 100) {
					 int[] indexInfo = terrainBlock.getIndicesVBOInfo();
					 GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexInfo[0]);
					 render(terrainBlock.getIndex(), indexInfo[1]);
					 GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);;
				 }
			 }
			 GL20.glDisableVertexAttribArray(0);
			 GL20.glDisableVertexAttribArray(1);
			 GL20.glDisableVertexAttribArray(2);
			 GL30.glBindVertexArray(0);
			 GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		 }

		 shader.stop();
	 }

	 private void prepareTerrainInstance(Terrain terrain, Camera camera) {
		 shader.modelMatrix.loadMatrix(
				 MathUtils.getModelMatrix(modelMatrix, terrain.getX(), 0, terrain.getZ(),
						 0, 0, 0, 1, 1, 1));
		 
		 texture.bind(0);
		 
		 GL30.glBindVertexArray(terrain.getVaoId());
		 GL20.glEnableVertexAttribArray(0);
		 GL20.glEnableVertexAttribArray(1);
		 GL20.glEnableVertexAttribArray(2);
	 }

	 private void render(int blockIndex, int indicesLength) {
		 int vertexOffset = blockIndex * Terrain.VERTICES_PER_NODE;
		 GL32.glDrawElementsBaseVertex(GL11.GL_TRIANGLES, indicesLength, GL11.GL_UNSIGNED_INT, 0, vertexOffset);
	 }

	 public void cleanup() {
		 shader.cleanUp();
	 }
	
}
