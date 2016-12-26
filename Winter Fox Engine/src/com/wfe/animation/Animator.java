package com.wfe.animation;

import java.util.HashMap;
import java.util.Map;

import com.wfe.core.Display;
import com.wfe.math.Matrix4f;
import com.wfe.math.Vector3f;

public class Animator {

	private final AnimatedEntity entity;

	private float animationTime = 0;
	private Animation currentAnimation;

	/**
	 * @param entity
	 *            - the entity which will by animated by this animator.
	 */
	public Animator(AnimatedEntity entity) {
		this.entity = entity;
	}

	/**
	 * Indicates that the entity should carry out the given animation. Resets
	 * the animation time so that the new animation starts from the beginning.
	 * 
	 * @param animation
	 *            - the new animation to carry out.
	 */
	public void doAnimation(Animation animation) {
		this.animationTime = 0;
		this.currentAnimation = animation;
	}

	/**
	 * This method should be called each frame to update the animation currently
	 * being played. This increases the animation time (and loops it back to
	 * zero if necessary), finds the pose that the entity should be in at that
	 * time of the animation, and then applied that pose to all the entity's
	 * joints.
	 */
	public void update() {
		if (currentAnimation == null) {
			return;
		}
		increaseAnimationTime();
		Map<String, Matrix4f> currentPose = getCurrentAnimationPose();
		applyPoseToJoints(currentPose, entity.getRootJoint(),
				new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(1, 0, 0)));

	}

	/**
	 * This method returns the current animation pose of the entity. It returns
	 * the desired local-space transforms for all the joints in a map, indexed
	 * by the name of the joint that they correspond to.
	 * 
	 * The pose is calculated based on the previous and next keyframes in the
	 * current animation. Each keyframe provides the desired pose at a certain
	 * time in the animation, so the animated pose for the current time can be
	 * calculated by interpolating between the previous and next keyframe.
	 * 
	 * This method first finds the preious and next keyframe, calculates how far
	 * between the two the current animation is, and then calculated the pose
	 * for the current animation time by interpolating between the transforms at
	 * those keyframes.
	 * 
	 * @return The current pose as a map of the desired local-space transforms
	 *         for all the joints. The transforms are indexed by the name ID of
	 *         the joint that they should be applied to.
	 */
	private Map<String, Matrix4f> getCurrentAnimationPose() {
		KeyFrame[] frames = getPreviousAndNextFrames();
		float progression = calculateProgression(frames[0], frames[1]);
		return calculateCurrentPose(frames[0], frames[1], progression);
	}

	/**
	 * This method applies the current pose to a given joint, and all of its
	 * descendants. It does this by getting the desired local-transform for the
	 * current joint, before applying it to the joint. Before applying the
	 * transformations it needs to be converted from local-space to model-space
	 * (so that they are relative to the model's origin, rather than relative to
	 * the parent joint). This can be done by multiplying the local-transform of
	 * the joint with the model-space transform of the parent joint.
	 * 
	 * The same thing is then done to all the child joints.
	 * 
	 * Finally the inverse of the joint's bind transform is multiplied with the
	 * model-space transform of the joint. This basically "subtracts" the
	 * joint's original bind (no animation applied) transform from the desired
	 * pose transform. The result of this is then the transform required to move
	 * the joint from its original model-space transform to it's desired
	 * model-space posed transform. This is the transform that needs to be
	 * loaded up to the vertex shader and used to transform the vertices into
	 * the current pose.
	 * 
	 * @param currentPose
	 *            - a map of the local-space transforms for all the joints for
	 *            the desired pose. The map is indexed by the name of the joint
	 *            which the transform corresponds to.
	 * @param joint
	 *            - the current joint which the pose should be applied to.
	 * @param parentTransform
	 *            - the desired model-space transform of the parent joint for
	 *            the pose.
	 */
	private void applyPoseToJoints(Map<String, Matrix4f> currentPose, Joint joint,
			Matrix4f parentTransform) {
		Matrix4f currentLocalTransform = currentPose.get(joint.name);
		Matrix4f currentTransform = Matrix4f.mul(parentTransform, currentLocalTransform, null);
		for (Joint childJoint : joint.children) {
			applyPoseToJoints(currentPose, childJoint, currentTransform);
		}
		Matrix4f.mul(currentTransform, joint.getInverseBindTransform(), currentTransform);
		joint.setAnimationTransform(currentTransform);
	}

	/**
	 * Finds the previous keyframe in the animation and the next keyframe in the
	 * animation, and returns them in an array of length 2. If there is no
	 * previous frame (perhaps current animation time is 0.5 and the first
	 * keyframe is at time 1.5) then the next keyframe is used as both the
	 * previous and next keyframe. The reverse happens if there is no next
	 * keyframe.
	 * 
	 * @return The previous and next keyframes, in an array which therefore will
	 *         always have a length of 2.
	 */
	private KeyFrame[] getPreviousAndNextFrames() {
		KeyFrame previousFrame = null;
		KeyFrame nextFrame = null;
		for (KeyFrame frame : currentAnimation.getKeyFrames()) {
			if (frame.getTimeStamp() > animationTime) {
				nextFrame = frame;
				break;
			}
			previousFrame = frame;
		}
		if (previousFrame == null) {
			previousFrame = nextFrame;
		} else if (nextFrame == null) {
			nextFrame = previousFrame;
		}
		return new KeyFrame[] { previousFrame, nextFrame };
	}

	/**
	 * Calculates how far between the previous and next keyframe the current
	 * animation time is, and returns it as a value between 0 and 1.
	 * 
	 * @param previousFrame
	 *            - the previous keyframe in the animation.
	 * @param nextFrame
	 *            - the next keyframe in the animation.
	 * @return A number between 0 and 1 indicating how far between the two
	 *         keyframes the current animation time is.
	 */
	private float calculateProgression(KeyFrame previousFrame, KeyFrame nextFrame) {
		float timeDifference = nextFrame.getTimeStamp() - previousFrame.getTimeStamp();
		return (animationTime - previousFrame.getTimeStamp()) / timeDifference;
	}

	/**
	 * Calculates all the local-space joint transforms for the desired current
	 * pose by interpolating between the transforms at the previous and next
	 * keyframes.
	 * 
	 * @param previousFrame
	 *            - the previous keyframe in the animation.
	 * @param nextFrame
	 *            - the next keyframe in the animation.
	 * @param progression
	 *            - a number between 0 and 1 indicating how far between the
	 *            previous and next keyframes the current animation time is.
	 * @return The local-space transforms for all the joints for the desired
	 *         current pose. They are returned in a map, indexed by the name of
	 *         the joint to which they should be applied.
	 */
	private Map<String, Matrix4f> calculateCurrentPose(KeyFrame previousFrame, KeyFrame nextFrame,
			float progression) {
		Map<String, Matrix4f> currentPose = new HashMap<String, Matrix4f>();
		for (String jointName : previousFrame.getJointKeyFrames().keySet()) {
			JointTransform previousPose = previousFrame.getJointKeyFrames().get(jointName);
			JointTransform nextPose = nextFrame.getJointKeyFrames().get(jointName);
			JointTransform jointPose = JointTransform.interpolate(previousPose, nextPose,
					progression);
			currentPose.put(jointName, jointPose.getLocalTransform());
		}
		return currentPose;
	}

	/**
	 * Increases the current animation time which allows the animation to
	 * progress. If the current animation has reached the end then the timer is reset,
	 * causing the animation to loop.
	 */
	private void increaseAnimationTime() {
		animationTime += Display.getDeltaInSeconds();
		if (animationTime > currentAnimation.getLength()) {
			this.animationTime %= currentAnimation.getLength();
		}
	}

}