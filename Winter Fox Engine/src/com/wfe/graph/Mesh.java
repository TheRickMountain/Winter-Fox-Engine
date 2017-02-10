package com.wfe.graph;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import com.wfe.utils.OpenglUtils;

public class Mesh {
	
	private static final int BYTES_PER_FLOAT = 4;
	
	private int VAO, VBO, EBO;
	private int vertexCount;
	
	public Mesh(float[] vertices, float[] textureCoords, float[] normals, int[] indices) {
		this.vertexCount = indices.length;
		int vertexByteCount = BYTES_PER_FLOAT * (3 + 2 + 3);
		
		VAO = GL30.glGenVertexArrays();
		bind();
		
		VBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
		FloatBuffer floatBuffer = OpenglUtils.toFloatBuffer(vertices, textureCoords, normals);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, vertexByteCount, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, vertexByteCount, BYTES_PER_FLOAT * 3);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, vertexByteCount, BYTES_PER_FLOAT * (3 + 2));
		
		EBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, EBO);
		IntBuffer intBuffer = OpenglUtils.toIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, intBuffer, GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		unbind();
		
		MemoryUtil.memFree(floatBuffer);
		MemoryUtil.memFree(intBuffer);
	}
	
	 public Mesh(float[] positions, float[] textCoords, int[] indices) {
	        FloatBuffer posBuffer = null;
	        FloatBuffer textCoordsBuffer = null;
	        IntBuffer indicesBuffer = null;
	        try {
	            vertexCount = indices.length;

	            VAO = glGenVertexArrays();
	            glBindVertexArray(VAO);

	            // Position VBO
	            VBO = glGenBuffers();
	            posBuffer = MemoryUtil.memAllocFloat(positions.length);
	            posBuffer.put(positions).flip();
	            glBindBuffer(GL_ARRAY_BUFFER, VBO);
	            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
	            glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

	            // Texture coordinates VBO
	            VBO = glGenBuffers();
	            textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
	            textCoordsBuffer.put(textCoords).flip();
	            glBindBuffer(GL_ARRAY_BUFFER, VBO);
	            glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
	            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

	            // Index VBO
	            EBO = glGenBuffers();
	            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
	            indicesBuffer.put(indices).flip();
	            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
	            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

	            glBindBuffer(GL_ARRAY_BUFFER, 0);
	            glBindVertexArray(0);
	        } finally {
	            if (posBuffer != null) {
	                MemoryUtil.memFree(posBuffer);
	            }
	            if (textCoordsBuffer != null) {
	                MemoryUtil.memFree(textCoordsBuffer);
	            }
	            if (indicesBuffer != null) {
	                MemoryUtil.memFree(indicesBuffer);
	            }
	        }
	    }
	
	public Mesh(float[] vertices, float[] textureCoords) {
		int vertexByteCount = BYTES_PER_FLOAT * (2 + 2);
		
		VAO = GL30.glGenVertexArrays();
		bind();
		
		VBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
		FloatBuffer floatBuffer = OpenglUtils.toFloatBuffer(vertices, textureCoords);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, vertexByteCount, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, vertexByteCount, BYTES_PER_FLOAT * 2);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		unbind();
		
		MemoryUtil.memFree(floatBuffer);
	}
	
	public Mesh(float[] vertices, int dimension) {
		this.vertexCount = vertices.length / dimension;
		int vertexByteCount = BYTES_PER_FLOAT * dimension;
		
		VAO = GL30.glGenVertexArrays();
		bind();

        VBO = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
        FloatBuffer floatBuffer = OpenglUtils.toFloatBuffer(vertices);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, dimension, GL11.GL_FLOAT, false, vertexByteCount, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        unbind();
        
        MemoryUtil.memFree(floatBuffer);
	}
	
	public int getVAO() {
		return VAO;
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
