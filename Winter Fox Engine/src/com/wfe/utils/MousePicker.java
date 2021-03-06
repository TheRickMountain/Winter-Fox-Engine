package com.wfe.utils;

import com.wfe.core.Camera;
import com.wfe.input.Mouse;
import com.wfe.math.Matrix4f;
import com.wfe.math.Vector3f;
import com.wfe.math.Vector4f;

public class MousePicker {

	private static Vector3f currentTerrainPoint;
	private static Vector3f currentRay = new Vector3f();

	private static final int RECURSION_COUNT = 21;
	private static final float RAY_RANGE = 600;

	private static Matrix4f projectionMatrix;
	private static Camera camera;


	public static void setUpMousePicker(Camera camera) {
		MousePicker.camera = camera;
		projectionMatrix = camera.getProjectionMatrix();
	}
	
	public static Vector3f getRayOrigin() {
		return camera.getPosition();
	}

	public static Vector3f getCurrentTerrainPoint() {
		return currentTerrainPoint;
	}

	public static Vector3f getCurrentRay() {
		return currentRay;
	}

	public static void update() {
		currentRay = calculateMouseRayCamera();
		if (intersectionInRange(0, RAY_RANGE, currentRay)) {
			currentTerrainPoint = binarySearch(0, 0, RAY_RANGE, currentRay);
		} else {
			currentTerrainPoint = null;
		}
	}

	private static Vector3f binarySearch(int count, float start, float finish, Vector3f ray) {
		float half = start + ((finish - start) / 2f);
		if (count >= RECURSION_COUNT) {
			Vector3f endPoint = getPointOnRay(ray, half);
			return endPoint;
		}
		if (intersectionInRange(start, half, ray)) {
			return binarySearch(count + 1, start, half, ray);
		} else {
			return binarySearch(count + 1, half, finish, ray);
		}
	}

	private static boolean intersectionInRange(float start, float finish, Vector3f ray) {
		Vector3f startPoint = getPointOnRay(ray, start);
		Vector3f endPoint = getPointOnRay(ray, finish);
		if (!isUnderGround(startPoint) && isUnderGround(endPoint)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isUnderGround(Vector3f testPoint) {
		float height = 0;
		if (testPoint.y < height) {
			return true;
		} else {
			return false;
		}
	}

	public static Vector3f getPointOnRay(Vector3f ray, float distance) {
		Vector3f start = new Vector3f(camera.getPosition().x, camera.getPosition().y, camera.getPosition().z);
		Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		return Vector3f.add(start, scaledRay, null);
	}

	private static Vector3f calculateMouseRayCamera() {
		Vector4f clipCoords = new Vector4f(Mouse.getNX(), Mouse.getNY(), -1.0f, 1.0f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3f worldRay = toWorldCoords(eyeCoords);
		return worldRay;
	}

	private static Vector3f toWorldCoords(Vector4f eyeCoords) {
		Matrix4f invertedView = new Matrix4f();
		Matrix4f.invert(camera.getViewMatrix(), invertedView);
		Vector4f rayWorld = new Vector4f();
		Matrix4f.transform(invertedView, eyeCoords, rayWorld);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalise();
		return mouseRay;
	}

	private static Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f invertedProjection = new Matrix4f();
		Matrix4f.invert(projectionMatrix, invertedProjection);
		Vector4f eyeCoords = new Vector4f();
		Matrix4f.transform(invertedProjection, clipCoords, eyeCoords);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

}
