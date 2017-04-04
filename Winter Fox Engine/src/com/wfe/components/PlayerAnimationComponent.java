package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Player;
import com.wfe.entities.Settler;

public class PlayerAnimationComponent extends Component {

	private enum AnimationType {
		WALK,
		HIT,
		IDLE,
		FIGHT
	};
	
	private Transformation rightArm;
    private Transformation leftArm;
    private Transformation rightHip;
    private Transformation leftHip;
    private Transformation leftForearm;
    private Transformation rightForearm;
    private Transformation leftShin;
    private Transformation rightShin;
    
    public AnimationType type = AnimationType.IDLE;
    
    private boolean extremitiesState = false;

    private int walkAnimSpeed = 180;
    private int hitAnimSpeed = 250;
    private int fightAnimSpeed = 450;
	
	public PlayerAnimationComponent(Player player) {
		rightArm = player.rightArm.getTransform();
		leftArm = player.leftArm.getTransform();
		rightHip = player.rightHip.getTransform();
		leftHip = player.leftHip.getTransform();
		leftForearm = player.leftForearm.getTransform();
		rightForearm = player.rightForearm.getTransform();
		leftShin = player.leftShin.getTransform();
		rightShin = player.rightShin.getTransform();
	}
	
	public PlayerAnimationComponent(Settler player) {
		rightArm = player.rightArm.getTransform();
		leftArm = player.leftArm.getTransform();
		rightHip = player.rightHip.getTransform();
		leftHip = player.leftHip.getTransform();
		leftForearm = player.leftForearm.getTransform();
		rightForearm = player.rightForearm.getTransform();
		leftShin = player.leftShin.getTransform();
		rightShin = player.rightShin.getTransform();
	}
	
	@Override
	public void update(float dt) {	
		
	}
	
	public void walkAnim(float dt) {
		if(leftArm.localRotX >= 40) {
            extremitiesState = true;
            //AudioMaster.defaultSource.play(ResourceManager.getSound("footstep1"));
        } else if(leftArm.localRotX <= -40){
            extremitiesState = false;
            //AudioMaster.defaultSource.play(ResourceManager.getSound("footstep2"));
        }

        if(extremitiesState) {
            leftArm.localRotX -= walkAnimSpeed * dt;
            rightArm.localRotX += walkAnimSpeed * dt;

            leftHip.localRotX += walkAnimSpeed * dt;
            rightHip.localRotX -= walkAnimSpeed * dt;
        } else {
            leftArm.localRotX += walkAnimSpeed * dt;
            rightArm.localRotX -= walkAnimSpeed * dt;

            leftHip.localRotX -= walkAnimSpeed * dt;
            rightHip.localRotX += walkAnimSpeed * dt;
        }

        leftForearm.localRotX = -60;
        rightForearm.localRotX = -60;

        leftShin.localRotX = 20;
        rightShin.localRotX = 20;
    }
	
	public boolean hitAnim(float dt) {		
		if(rightArm.localRotX >= -45) {
			rightArm.localRotX = -165;
			return true;
		}
		
		rightArm.localRotX += hitAnimSpeed * dt;
		return false;
	}
	
	public void prepareFightAnim() {
		rightArm.localRotY = 100;
		rightArm.localRotX = -40;
	}
	
	public boolean fightAnim(float dt) {
		if(rightArm.localRotY <= -50) {
			return true;
		}
	
		rightArm.localRotY -= fightAnimSpeed * dt;
		return false;
	}
	
	public void idleAnim() {
		leftArm.localRotX = 0;
        rightArm.localRotX = 0;

        leftHip.localRotX = 0;
        rightHip.localRotX = 0;

        leftForearm.localRotX = 0;
        rightForearm.localRotX = 0;

        leftShin.localRotX = 0;
        rightShin.localRotX = 0;
        
        leftArm.localRotY = 0;
        rightArm.localRotY = 0;

        leftHip.localRotY = 0;
        rightHip.localRotY = 0;

        leftForearm.localRotY = 0;
        rightForearm.localRotY = 0;

        leftShin.localRotY = 0;
        rightShin.localRotY = 0;
	}

	@Override
	public Component getInstance() {
		return null;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.PLAYER_ANIMATION;
	}

}
