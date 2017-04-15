package com.wfe.utils;

import java.util.List;

import com.wfe.core.Camera;
import com.wfe.ecs.Transformation;
import com.wfe.math.Matrix4f;
import com.wfe.math.Vector2f;
import com.wfe.math.Vector3f;
import com.wfe.math.altvecmath.Matrix4;
import com.wfe.math.altvecmath.Transform;
import com.wfe.math.altvecmath.Vector3;

public class MathUtils {

	public static final float PI = 3.1415927f;
	public static final float RADIANS_TO_DEGREES = 180f / PI;
	public static final float DEGREES_TO_RADIANS = PI / 180;
	
	public static final Vector3f AXIS_X = new Vector3f(1, 0, 0);
    public static final Vector3f AXIS_Y = new Vector3f(0, 1, 0);
    public static final Vector3f AXIS_Z = new Vector3f(0, 0, 1);
    public static final Vector3f ZERO = new Vector3f(0, 0, 0);
    public static final Vector3f IDENTITY = new Vector3f(1, 1, 1);
	
	public static Matrix4f getProjectionMatrix(Matrix4f matrix, float fov, float width, float height, float zNear, float zFar){
		float aspectRatio = (float) width/ (float)height;
		float y_scale = (float) ((1f / Math.tan((fov / 2) * DEGREES_TO_RADIANS)) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = zFar - zNear;
		
		matrix.m00 = x_scale;
		matrix.m11 = y_scale;
		matrix.m22 = -((zFar + zNear) / frustum_length);
		matrix.m23 = -1;
		matrix.m32 = -((2 * zFar * zNear) / frustum_length);
		matrix.m33 = 0;
		
		return matrix; 
	}
	
	public static Matrix4f getOrthoProjectionMatrix(Matrix4f matrix, float left, float right, float bottom, float top) {
        matrix.m00 = 2.0f / (right - left);
        matrix.m11 = 2.0f / (top - bottom);
        matrix.m22 = -1.0f;
        matrix.m30 = -(right + left) / (right - left);
        matrix.m31 = -(top + bottom) / (top - bottom);
        return matrix;
	}
	
	public static Matrix4f getViewMatrix(Matrix4f matrix, Camera camera) {
		matrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), AXIS_X, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), AXIS_Y, matrix, matrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		Matrix4f.translate(negativeCameraPos, matrix, matrix);
		return matrix;
	}
	
	static Transform transform = new Transform();
	
	public static Matrix4f getEulerModelMatrix(Matrix4f matrix, Transformation t) {
		matrix.setIdentity();
		transform.reset()
        .rotateSelf(Vector3.AXIS_X, t.rotX * DEGREES_TO_RADIANS)
        .rotateSelf(Vector3.AXIS_Z, t.rotZ * DEGREES_TO_RADIANS)
        .rotateSelf(Vector3.AXIS_Y, t.rotY * DEGREES_TO_RADIANS)
        .scaleSelf(new Vector3(t.scaleX, t.scaleY, t.scaleZ))
        .translateSelf(new Vector3(t.x, t.y, t.z));
		Matrix4 tempMatrix = transform.getMatrix();
		matrix.m00 = tempMatrix.get(0, 0);
		matrix.m01 = tempMatrix.get(0, 1);
		matrix.m02 = tempMatrix.get(0, 2);
		matrix.m03 = tempMatrix.get(0, 3);
		matrix.m10 = tempMatrix.get(1, 0);
		matrix.m11 = tempMatrix.get(1, 1);
		matrix.m12 = tempMatrix.get(1, 2);
		matrix.m13 = tempMatrix.get(1, 3);
		matrix.m20 = tempMatrix.get(2, 0);
		matrix.m21 = tempMatrix.get(2, 1);
		matrix.m22 = tempMatrix.get(2, 2);
		matrix.m23 = tempMatrix.get(2, 3);
		matrix.m30 = tempMatrix.get(3, 0);
		matrix.m31 = tempMatrix.get(3, 1);
		matrix.m32 = tempMatrix.get(3, 2);
		matrix.m33 = tempMatrix.get(3, 3);
		return matrix;
    }
	
	public static Matrix4f getModelMatrix(Matrix4f matrix, Transformation transform) {
		matrix.setIdentity();
		Matrix4f.translate(new Vector3f(transform.getX(), transform.getY(), transform.getZ()), matrix, matrix);
		Matrix4f.rotate(DEGREES_TO_RADIANS * transform.getRotX(), AXIS_X, matrix, matrix);
		Matrix4f.rotate(DEGREES_TO_RADIANS * transform.getRotY(), AXIS_Y, matrix, matrix);
		Matrix4f.rotate(DEGREES_TO_RADIANS * transform.getRotZ(), AXIS_Z, matrix, matrix);
		Matrix4f.scale(new Vector3f(transform.getScaleX(), transform.getScaleY(), transform.getScaleZ()), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f getModelMatrix(Matrix4f matrix, float x, float y, float z, float rotX, float rotY,
			float rotZ, float scaleX, float scaleY, float scaleZ) {
		matrix.setIdentity();
		Matrix4f.translate(new Vector3f(x, y, z), matrix, matrix);
		Matrix4f.rotate(DEGREES_TO_RADIANS * rotX, AXIS_X, matrix, matrix);
		Matrix4f.rotate(DEGREES_TO_RADIANS * rotY, AXIS_Y, matrix, matrix);
		Matrix4f.rotate(DEGREES_TO_RADIANS * rotZ, AXIS_Z, matrix, matrix);
		Matrix4f.scale(new Vector3f(scaleX, scaleY, scaleZ), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f getModelMatrix(Matrix4f matrix, float xPos, float yPos, float rotation, float xScale, float yScale){
		matrix.setIdentity();
		Matrix4f.translate(new Vector2f(xPos, yPos), matrix, matrix);
        Matrix4f.translate(new Vector2f(0.5f * xScale, 0.5f * yScale), matrix, matrix);
		Matrix4f.rotate(rotation * DEGREES_TO_RADIANS, AXIS_Z, matrix, matrix);
		Matrix4f.translate(new Vector2f(-0.5f * xScale, -0.5f * yScale), matrix, matrix);
		Matrix4f.scale(new Vector3f(xScale, yScale, 0.0f), matrix, matrix);
        
		return matrix;
	}
	
	public static Matrix4f getModelMatrix(Matrix4f matrix, Rect rect){
		return getModelMatrix(matrix, rect.x, rect.y, rect.rotation, rect.width, rect.height);
	}
	
	public static float getDistance(float x1, float y1, float x2, float y2) {
		float dX = x1 - x2;
		float dY = y1 - y2;
		return (float) Math.sqrt((dX * dX) + (dY * dY));
	}
	
	public static float getRotation(float x1, float y1, float x2, float y2){
		return (float) ((Math.atan2(y2 - y1, x2 - x1)) * RADIANS_TO_DEGREES);
	}
	
	public static float getNewRotation(float x1, float y1, float x2, float y2) {
		float angle = (float)Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
		
		if(angle < 0) {
			angle += 360;
		} else if(angle > 360) {
			angle -= 360;
		}
		
		return angle;
	}
	
	public static float getAverageOfList(List<Float> numbers) {
        float total = 0;
        for (Float number : numbers) {
            total += number;
        }
        return total / numbers.size();
    }
	
	public static int compareTo(int a, int b) {
		if(a > b) {
			return 1;
		} else if(a == b) {
			return 0;
		} else {
			return -1;
		}
	}
	
	public static float getLerp(float p1, float p2, float alpha) {
		return p1 + alpha * (p2 - p1);
	}
	
	public static Vector2f getPointOnCirce(int maxRadius, float xPos, float yPos) {
		double angle = Math.random() * Math.PI * 2;
		float x = (float) (Math.cos(angle) * maxRadius);
		float y = (float) (Math.sin(angle) * maxRadius);
		return new Vector2f(x + xPos, y + yPos);
	}
	
}
