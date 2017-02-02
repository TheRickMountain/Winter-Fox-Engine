package com.wfe.components;

import java.util.List;

import com.wfe.core.Camera;
import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Transformation;
import com.wfe.game.World;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.math.Vector3f;
import com.wfe.physics.AABB;
import com.wfe.utils.MathUtils;
import com.wfe.utils.MousePicker;

public class PlayerComponent extends Component {

	private Camera camera;
	private Transformation transform;
	private float yRot;
	private float xd, zd;
	private float speed;

	private AABB bb;
	
	public PlayerComponent(Camera camera, Transformation transform) {		
		this.camera = camera;
		this.transform = transform;
		this.bb = new AABB(transform.x - 0.4f, 0, transform.z - 0.4f, transform.x + 0.4f, 0 + 1, transform.z + 0.4f);
		this.speed = 2.0f;
	}
	
	@Override
	public void update(float dt) {	
		rotate();
		move(dt);
	}
	
	private void rotate() {
		Vector3f tp = MousePicker.getCurrentTerrainPoint();
		if(tp != null) {
			transform.setRotY(-MathUtils.getRotation(
					transform.getX(), transform.getZ(), 
					tp.x, tp.z) + 90);
		}
	}
	
	private void move(float dt) {	
		transform.isMoving = false;
		float xa = 0.0f;
		float za = 0.0f;
		
		if(Keyboard.isKey(Key.KEY_A) || Keyboard.isKey(Key.KEY_LEFT)) {
			xa = -1.0f;
		} else if(Keyboard.isKey(Key.KEY_D) || Keyboard.isKey(Key.KEY_RIGHT)) {
			xa = 1.0f;
		}
		
		if(Keyboard.isKey(Key.KEY_W) || Keyboard.isKey(Key.KEY_UP)) {
			za = -1.0f;
		} else if(Keyboard.isKey(Key.KEY_S) || Keyboard.isKey(Key.KEY_DOWN)) {
			za = 1.0f;
		}
		
		yRot = camera.getYaw();
		
		/*if(xa < 0) {
			transform.rotY = (-yRot) - 90;
		} else if(xa > 0) {
			transform.rotY = (-yRot) + 90;
		}
		
		if(za < 0) {
			transform.rotY = (-yRot) + 180;
		} else if(za > 0) {
			transform.rotY = (-yRot);
		}
		
		if(za > 0 && xa > 0) {
			transform.rotY = (-yRot) + 45;
		} else if(za < 0 && xa > 0) {
			transform.rotY = (-yRot) + 135;
		} else if(za > 0 && xa < 0) {
			transform.rotY = (-yRot) - 45;
		} else if(za < 0 && xa < 0) {
			transform.rotY = (-yRot) - 135;
		}*/
		
		if(xa != 0 || za != 0) {
			transform.isMoving = true;
		}
		
		moveRelative(xa, za, speed, dt);
		move(xd, zd);
		
		this.xd *= 0.91f;
        this.zd *= 0.91f;
        this.xd *= 0.7f;
        this.zd *= 0.7f;
    	
    	camera.playerPosition.x = transform.x;
    	camera.playerPosition.z = transform.z;
    	camera.playerPosition.y = 1.1f;
	}
	
	private void moveRelative(float xa, float za, float speed, float dt) {
        float dist = xa * xa + za * za;
        if (dist < 0.01f) {
            return;
        }
        dist = speed / (float)Math.sqrt(dist);
        float sin = (float)Math.sin((double)this.yRot * 3.141592653589793 / 180.0);
        float cos = (float)Math.cos((double)this.yRot * 3.141592653589793 / 180.0);
        this.xd += ((xa *= dist) * cos - (za *= dist) * sin) * dt;
        this.zd += (za * cos + xa * sin) * dt;
    }
	
	private void move(float xa, float za) {
		 float xaOrg = xa;
	     float zaOrg = za;
	     
	     List<AABB> aABBs = World.getWorld().getColliders();
	     
	     int i = 0;
	     while (i < aABBs.size()) {
	    	 xa = aABBs.get(i).clipXCollide(this.bb, xa);
	    	 ++i;
	     }
	     this.bb.move(xa, 0.0f, 0.0f);
	     
	     i = 0;
	     while (i < aABBs.size()) {
	    	 za = aABBs.get(i).clipZCollide(this.bb, za);
	    	 ++i;
	     }
	     this.bb.move(0.0f, 0.0f, za);
	     
	     if (xaOrg != xa) {
	         this.xd = 0.0f;
	     }
	     if (zaOrg != za) {
	    	 this.zd = 0.0f;
	     }
	     this.transform.x = (this.bb.x0 + this.bb.x1) / 2.0f;
	     this.transform.z = (this.bb.z0 + this.bb.z1) / 2.0f;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.PLAYER_CONTROLLER;
	}
	
}
