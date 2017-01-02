package com.wfe.graph;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.wfe.utils.OpenglUtils;

public class Mesh {
	
	private static final int BYTES_PER_FLOAT = 4;
	
	private int VAO, VBO, EBO;
	private int indexCount;
	private int vertexCount;
	
	public Mesh(float[] vertices, float[] textureCoords, float[] normals, int[] indices) {
		this.indexCount = indices.length;
		int vertexByteCount = BYTES_PER_FLOAT * (3 + 2 + 3);
		
		VAO = GL30.glGenVertexArrays();
		bind();
		
		VBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, OpenglUtils.toFloatBuffer(vertices, textureCoords, normals), GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, vertexByteCount, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, vertexByteCount, BYTES_PER_FLOAT * 3);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, vertexByteCount, BYTES_PER_FLOAT * (3 + 2));
		
		EBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, EBO);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, OpenglUtils.toIntBuffer(indices), GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		unbind();
	}
	
	public Mesh(float[] vertices, float[] textureCoords) {
		int vertexByteCount = BYTES_PER_FLOAT * (2 + 2);
		
		VAO = GL30.glGenVertexArrays();
		bind();
		
		VBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, OpenglUtils.toFloatBuffer(vertices, textureCoords), GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, vertexByteCount, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, vertexByteCount, BYTES_PER_FLOAT * 2);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		unbind();
	}
	
	public Mesh(float[] vertices, int dimension) {
		this.vertexCount = vertices.length / dimension;
		int vertexByteCount = BYTES_PER_FLOAT * dimension;
		
		VAO = GL30.glGenVertexArrays();
		bind();

        VBO = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, OpenglUtils.toFloatBuffer(vertices), GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, dimension, GL11.GL_FLOAT, false, vertexByteCount, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        unbind();
	}
	
	public int getVAO() {
		return VAO;
	}
	
	public int getIndexCount() {
		return indexCount;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
	public void bind(int... attributes){
		GL30.glBindVertexArray(VAO);
		for (int i : attributes) {
			GL20.glEnableVertexAttribArray(i);
		}
	}

	public void unbind(int... attributes){
		for (int i : attributes) {
			GL20.glDisableVertexAttribArray(i);
		}
		GL30.glBindVertexArray(0);
	}
	
	public void delete() {
		GL30.glDeleteVertexArrays(VAO);
		GL15.glDeleteBuffers(VBO);
		GL15.glDeleteBuffers(EBO);
	}

}
