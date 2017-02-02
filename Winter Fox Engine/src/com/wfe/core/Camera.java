	package com.wfe.core;

import com.wfe.input.Mouse;
import com.wfe.math.Matrix4f;
import com.wfe.math.Vector3f;
import com.wfe.utils.MathUtils;

/**
 * Created by Rick on 07.10.2016.
 */
public class Camera {

    public static final float FOV = 60.0f;
    public static final float Z_NEAR = 0.1f;
    public static final float Z_FAR = 1000.f;

    private static final float MIN_DISTANCE = 10;
    private static final float MAX_DISTANCE = 25;

    private float distanceFromPlayer = 15;
    private float angleAroundPlayer = 1;
    private float zoomSpeed = 0;

    private static final float MAX_PITCH = 85;
    private static final float MIN_PITCH = 45;

    private final Vector3f position;

    private float pitch = 45;

    private float yaw = 0;

    public Vector3f playerPosition;
    
    private Matrix4f projectionMatrix, viewMatrix, projectionViewMatrix;
    
    //private float horizontal, vertical;
    //private float speed = 4.6f;

    public Camera(Vector3f playerPosition) {
        this.playerPosition = playerPosition;
        this.position = new Vector3f(0, 0, 0);
        
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        
        updateProjectionMatrix();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void invertPitch() {
    	this.pitch = -pitch;
    }
    
    public void update(float dt) {
    	rotate(dt);
    	//move(dt);
    	updateViewMatrix();
    	
    	if(Display.isResized()) {
    		updateProjectionMatrix();
    	}
    }

    private void rotate(float dt) {
    	if(Mouse.isButtonDown(2)) {
            Mouse.hide();
        }

        if(Mouse.isButtonUp(2)) {
            Mouse.show();
        }
    	
    	if(Mouse.isButton(2)) {
            calculateAngleAroundPlayer(dt);
            calculatePitch(dt);
    	} 
        //calculateZoom(dt);

        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - angleAroundPlayer;
    }

    /*private void move(float dt) {
    	// Movement
    	if(Keyboard.isKey(Keys.KEY_W)) {
            vertical = -1.0f;
        } else if(Keyboard.isKey(Keys.KEY_S)) {
            vertical = 1.0f;
        } else {
        	vertical = 0.0f;
        }

        if(Keyboard.isKey(Keys.KEY_A)) {
            horizontal = -1.0f;
        } else if(Keyboard.isKey(Keys.KEY_D)) {
            horizontal = 1.0f;
        } else {
        	horizontal = 0.0f;
        }
    	
    	float offsetX = 0;
    	float offsetZ = 0;
    	
    	if(horizontal != 0) {
    		offsetX = horizontal * speed * dt;
    		playerPosition.x += (float)Math.sin(Math.toRadians(yaw - 90)) * -1.0f * offsetX;
            playerPosition.z += (float)Math.cos(Math.toRadians(yaw - 90))* offsetX;
    	}
    	
    	if(vertical != 0) {
    		offsetZ = vertical * speed * dt;
    		playerPosition.x += (float)Math.sin(Math.toRadians(yaw)) * -1.0f * offsetZ;
            playerPosition.z += (float)Math.cos(Math.toRadians(yaw))* offsetZ;
    	}
    }*/
    
    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    private void calculateAngleAroundPlayer(float dt){
        float angleChange = Mouse.Cursor.getDX() * 20 * dt;
        angleAroundPlayer -= angleChange;
    }

    private void calculatePitch(float dt){
        float pitchChange = -Mouse.Cursor.getDY() * 10 * dt;
        pitch -= pitchChange;
        if(pitch >= MAX_PITCH){
            pitch = MAX_PITCH;
        } else if(pitch <= MIN_PITCH){
            pitch = MIN_PITCH;
        }
    }

    private void calculateCameraPosition(float horizDistance, float verticDistance){
        float theta = angleAroundPlayer;
        float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
        position.x = playerPosition.x - offsetX;
        position.z = playerPosition.z - offsetZ;
        position.y = playerPosition.y + verticDistance;
    }

    private float calculateHorizontalDistance(){
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom(float dt){
        float zoomLevel = Mouse.getScroll() * dt * zoomSpeed;
        float temp = distanceFromPlayer;
        temp -= zoomLevel;
        if(temp >= MAX_DISTANCE){
            temp = MAX_DISTANCE;
        } else if(temp <= MIN_DISTANCE){
            temp = MIN_DISTANCE;
        }
        distanceFromPlayer = temp;
    }

    public void updateProjectionMatrix() {
    	MathUtils.getProjectionMatrix(projectionMatrix, FOV, Display.getWidth(), Display.getHeight(), 
				Z_NEAR, Z_FAR);
    }
    
    public void updateViewMatrix() {
    	MathUtils.getViewMatrix(viewMatrix, this);
    }
    
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}
	
	public Matrix4f getProjectionViewMatrix() {
		return Matrix4f.mul(projectionMatrix, viewMatrix, projectionViewMatrix);
	}
    
}
