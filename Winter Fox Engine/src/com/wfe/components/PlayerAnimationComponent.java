package com.wfe.components;

import com.wfe.audio.AudioMaster;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Player;

public class PlayerAnimationComponent extends Component {

	private Transformation rightArm;
    private Transformation leftArm;
    private Transformation rightHip;
    private Transformation leftHip;
    private Transformation leftForearm;
    private Transformation rightForearm;
    private Transformation leftShin;
    private Transformation rightShin;
    
    private boolean extremitiesState = false;

    private int animSpeed = 180;
	
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
	
	@Override
	public void update(float dt) {
		if(getParent().getTransform().isMoving) {
			walkAnim(dt);
		} else {
			resetAnim();
		}
	}
	
	public void walkAnim(float dt) {
		if(leftArm.localRotX >= 40) {
            extremitiesState = true;
            AudioMaster.defaultSource.play(ResourceManager.getSound("footstep1"));
        } else if(leftArm.localRotX <= -40){
            extremitiesState = false;
            AudioMaster.defaultSource.play(ResourceManager.getSound("footstep2"));
        }

        if(extremitiesState) {
            leftArm.localRotX -= animSpeed * dt;
            rightArm.localRotX += animSpeed * dt;

            leftHip.localRotX += animSpeed * dt;
            rightHip.localRotX -= animSpeed * dt;
        } else {
            leftArm.localRotX += animSpeed * dt;
            rightArm.localRotX -= animSpeed * dt;

            leftHip.localRotX -= animSpeed * dt;
            rightHip.localRotX += animSpeed * dt;
        }

        leftForearm.localRotX = -60;
        rightForearm.localRotX = -60;

        leftShin.localRotX = 20;
        rightShin.localRotX = 20;
    }
	
	public void resetAnim() {
		leftArm.localRotX = 0;
        rightArm.localRotX = 0;

        leftHip.localRotX = 0;
        rightHip.localRotX = 0;

        leftForearm.localRotX = 0;
        rightForearm.localRotX = 0;

        leftShin.localRotX = 0;
        rightShin.localRotX = 0;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.PLAYER_ANIMATION;
	}

}
