package com.wfe.loader;

import com.wfe.animation.AnimatedEntity;
import com.wfe.animation.Joint;
import com.wfe.animation.Vao;
import com.wfe.colladaLoader.AnimatedModelData;
import com.wfe.colladaLoader.JointData;
import com.wfe.colladaLoader.JointsData;
import com.wfe.colladaLoader.MeshData;
import com.wfe.core.GeneralSettings;
import com.wfe.textures.Texture;
import com.wfe.utils.MyFile;

public class AnimatedEntityCreator {

	/**
	 * Creates an AnimatedEntity from the data in an entity file. It loads up
	 * the collada model data, stores the extracted data in a VAO, sets up the
	 * joint heirarchy, and loads up the entity's texture.
	 * 
	 * @param modelFile - the file containing the data for the entity.
	 * @return The animated entity (no animation applied though)
	 */
	public static AnimatedEntity loadEntity(MyFile modelFile, MyFile textureFile) {

		AnimatedModelData entityData = ColladaLoader.loadColladaModel(modelFile, GeneralSettings.MAX_WEIGHTS);

		Vao model = createVao(entityData.getMeshData());
		Texture texture = loadTexture(textureFile);

		JointsData skeletonData = entityData.getJointsData();
		Joint headJoint = createJoints(skeletonData.headJoint);

		return new AnimatedEntity(model, texture, headJoint, skeletonData.jointCount);
	}

	private static Texture loadTexture(MyFile entityFile) {
		Texture diffuseTexture = Texture.newTexture(entityFile).anisotropic().create();
		return diffuseTexture;
	}

	private static Joint createJoints(JointData data) {
		Joint j = new Joint(data.index, data.nameId, data.bindLocalTransform);
		for (JointData child : data.children) {
			j.addChild(createJoints(child));
		}
		return j;
	}

	/**
	 * Stores the mesh data in a VAO.
	 * @param data - all the data about the mesh that needs to be stored in the VAO.
	 * @return The VAO containing all the mesh data for the model.
	 */
	private static Vao createVao(MeshData data) {
		Vao vao = Vao.create();
		vao.bind();
		vao.createIndexBuffer(data.getIndices());
		vao.createAttribute(0, data.getVertices(), 3);
		vao.createAttribute(1, data.getTextureCoords(), 2);
		vao.createAttribute(2, data.getNormals(), 3);
		vao.createIntAttribute(3, data.getJointIds(), 3);
		vao.createAttribute(4, data.getVertexWeights(), 3);
		vao.unbind();
		return vao;
	}

}