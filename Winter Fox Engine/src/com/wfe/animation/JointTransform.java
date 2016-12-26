package com.wfe.animation;

import com.wfe.math.Matrix4f;
import com.wfe.math.Vector3f;

public class JointTransform {

	private final Vector3f position;
	private final Quaternion rotation;

	/**
	 * 
	 * @param position
	 *            - the position of the joint relative to the parent joint
	 *            (local-space) at a certain keyframe.
	 * @param rotation
	 *            - the rotation of the joint relative to te parent joint
	 *            (local-space) at a certain keyframe.
	 */
	public JointTransform(Vector3f position, Quaternion rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	/**
	 * @param localTransform
	 *            - the joint's local-transform at a certain keyframe of an
	 *            animation.
	 */
	public JointTransform(Matrix4f localTransform) {
		this.position = new Vector3f(localTransform.m30, localTransform.m31, localTransform.m32);
		this.rotation = new Quaternion(localTransform);
	}

	/**
	 * In this method the local-space transform matrix is constructed by
	 * translating an identity matrix using the position variable and then
	 * applying the rotation. The rotation is applied by first converting the
	 * quaternion into a rotation matrix, which is then multiplied with the
	 * transform matrix.
	 * 
	 * @return The local-space transform as a matrix.
	 */
	protected Matrix4f getLocalTransform() {
		Matrix4f matrix = new Matrix4f();
		matrix.translate(position);
		Matrix4f.mul(matrix, rotation.toRotationMatrix(), matrix);
		return matrix;
	}

	/**
	 * Interpolates between two transforms based on the progression value. The
	 * result is a new transform which is part way between the two original
	 * transforms. The translation can simply be linearly interpolated, but the
	 * rotation interpolation is slightly more complex, using a method called
	 * "SLERP" to spherically-linearly interpolate between 2 quaternions
	 * (rotations). This gives a much much better result than trying to linearly
	 * interpolate between Euler rotations.
	 * 
	 * @param frameA
	 *            - the previous transform
	 * @param frameB
	 *            - the next transform
	 * @param progression
	 *            - a number between 0 and 1 indicating how far between the two
	 *            transforms to interpolate. A progression value of 0 would
	 *            return a transform equal to "frameA", a value of 1 would
	 *            return a transform equal to "frameB". Everything else gives a
	 *            transform somewhere in-between the two.
	 * @return
	 */
	protected static JointTransform interpolate(JointTransform frameA, JointTransform frameB,
			float progression) {
		Vector3f pos = interpolate(frameA.position, frameB.position, progression);
		Quaternion rot = Quaternion.slerp(frameA.rotation, frameB.rotation, progression);
		return new JointTransform(pos, rot);
	}

	/**
	 * Linearly interpolates between two translations based on a "progression"
	 * value.
	 * 
	 * @param start
	 *            - the start translation.
	 * @param end
	 *            - the end translation.
	 * @param progression
	 *            - a value between 0 and 1 indicating how far to interpolate
	 *            between the two translations.
	 * @return
	 */
	private static Vector3f interpolate(Vector3f start, Vector3f end, float progression) {
		float x = start.x + (end.x - start.x) * progression;
		float y = start.y + (end.y - start.y) * progression;
		float z = start.z + (end.z - start.z) * progression;
		return new Vector3f(x, y, z);
	}

}