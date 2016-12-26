package com.wfe.animation;

import java.util.ArrayList;
import java.util.List;

import com.wfe.ecs.Component;
import com.wfe.ecs.Transformation;
import com.wfe.math.Matrix4f;
import com.wfe.math.Vector3f;
import com.wfe.textures.Texture;

public class AnimatedEntity {

	private final Vao model;
	private final Texture texture;

	private final Joint rootJoint;
	private final int jointCount;

	private final Animator animator;
	
	private List<Component> components = new ArrayList<Component>();
	
	private Transformation transform;

	/**
	 * Creates a new entity capable of animation. The inverse bind transform for
	 * all joints is calculated in this constructor.
	 * 
	 * @param model
	 *            - the VAO containing the mesh data for this entity. This
	 *            includes vertex positions, normals, texture coords, IDs of
	 *            joints that affect each vertex, and their corresponding
	 *            weights.
	 * @param texture
	 *            - the diffuse texture for the entity.
	 * @param rootJoint
	 *            - the root joint of the joint hierarchy which makes up the
	 *            "skeleton" of the entity.
	 * @param jointCount
	 *            - the number of joints in the joint hierarchy for this entity.
	 * 
	 */
	public AnimatedEntity(Vao model, Texture texture, Joint rootJoint, int jointCount) {
		this.model = model;
		this.texture = texture;
		this.rootJoint = rootJoint;
		this.jointCount = jointCount;
		this.animator = new Animator(this);
		rootJoint.calcInverseBindTransform(
				new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(1, 0, 0)));
		this.transform = new Transformation(0, 0, 0);
	}

	/**
	 * @return The VAO containing all the mesh data for this entity.
	 */
	public Vao getModel() {
		return model;
	}

	/**
	 * @return The diffuse texture for this entity.
	 */
	public Texture getTexture() {
		return texture;
	}

	/**
	 * @return The root joint of the joint hierarchy. This joint has no parent,
	 *         and every other joint in the skeleton is a descendant of this
	 *         joint.
	 */
	public Joint getRootJoint() {
		return rootJoint;
	}

	/**
	 * Deletes the OpenGL objects associated with this entity, namely the model
	 * (VAO) and texture.
	 */
	public void delete() {
		model.delete();
		texture.delete();
	}

	/**
	 * Instructs this entity to carry out a given animation.
	 * 
	 * @param animation
	 *            - the animation to be carried out.
	 */
	public void doAnimation(Animation animation) {
		animator.doAnimation(animation);
	}

	/**
	 * Updates the animator for this entity, basically updating the animated
	 * pose of the entity. Must be called every frame.
	 */
	public void update(float dt) {
		for(Component component : components) {
			component.update(dt);
		}
		
		if(transform.isMoving)
			animator.update();
	}

	/**
	 * Gets an array of the model-space transforms of all the joints (with the
	 * current animation pose applied) in the entity. The joints are ordered in
	 * the array based on their joint index. The position of each joint's
	 * transform in the array is equal to the joint's index.
	 * 
	 * @return The array of model-space transforms of the joints in the current
	 *         animation pose.
	 */
	public Matrix4f[] getJointTransforms() {
		Matrix4f[] jointMatrices = new Matrix4f[jointCount];
		addJointsToArray(rootJoint, jointMatrices);
		return jointMatrices;
	}

	/**
	 * This adds the current model-space transform of a joint (and all of its
	 * descendants) into an array of transforms. The joint's transform is added
	 * into the array at the position equal to the joint's index.
	 * 
	 * @param headJoint
	 * @param jointMatrices
	 */
	private void addJointsToArray(Joint headJoint, Matrix4f[] jointMatrices) {
		jointMatrices[headJoint.index] = headJoint.getAnimatedTransform();
		for (Joint childJoint : headJoint.children) {
			addJointsToArray(childJoint, jointMatrices);
		}
	}
	
	public Transformation getTransform() {
		return transform;
	}
	
	public void addComponent(Component component) {
		components.add(component);
	}

}
