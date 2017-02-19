package com.wfe.components;

import java.util.List;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.game.Game;
import com.wfe.game.World;
import com.wfe.physics.AABB;
import com.wfe.utils.MathUtils;

public class MobComponent extends Component {
	
	private Transformation transform;
	private float yRot;
	private float xd, zd;
	private float speed;
	
	private AABB bb;
	
	public MobComponent(Transformation transform) {
		this.transform = transform;
		this.bb = new AABB(transform.x - 0.4f, 0, transform.z - 0.4f, transform.x + 0.4f, 0 + 1, transform.z + 0.4f);
		this.speed = 2.0f;
	}
	
	@Override
	public void update(float dt) {
		move(dt);
	}
	
	public void hurt(Entity entity, int damage) {
		float f2 = entity.getTransform().x - transform.x;
		float f3 = entity.getTransform().z - transform.z;
		
		float distance = MathUtils.getDistance(entity.getTransform().x, entity.getTransform().z, 
				transform.x, transform.z);
		
		float px = entity.getTransform().x;
		float pz = entity.getTransform().z;
		
		float mx = transform.x;
		float mz = transform.z;
		
		float tx = px - mx;
		float tz = pz - mz;
		
		float a = (float) Math.abs(Math.sqrt((tx * tx) + (tz * tz)));
		
		float dirX = tx / a;
		float dirZ = tz / a;
		
		PlayerControllerComponent pcc = (PlayerControllerComponent)entity.getComponent(ComponentType.PLAYER_CONTROLLER);
		float direction = dirX * pcc.getXF() + dirZ * pcc.getZF();
		
		if(distance < 2 && direction > 0) {
			knockback(f2, f3);
			System.out.println(damage);
		}
	}
	
	private void knockback(float f2, float f3) {
		float f4 = MathUtils.getDistance(Game.player.getTransform().x, Game.player.getTransform().z, 
				transform.x, transform.z);
		float f5 = 0.4f;
		this.xd /= 2.0f;
		this.zd /= 2.0f;
		this.xd -= f2 / f4 * f5;
		this.zd -= f3 / f4 * f5;
	}
	
	private void move(float dt) {
		transform.isMoving = false;
		float xa = 0.0f;
		float za = 0.0f;
		
		moveRelative(xa, za, speed, dt);
		move(xd, zd);
		
		this.xd *= 0.91f;
        this.zd *= 0.91f;
        this.xd *= 0.7f;
        this.zd *= 0.7f;
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
		return ComponentType.MOB;
	}
	
	public AABB getAABB() {
		return bb;
	}

}
