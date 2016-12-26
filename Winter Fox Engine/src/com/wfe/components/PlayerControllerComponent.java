package com.wfe.components;

import java.util.List;

import com.wfe.core.Camera;
import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.entities.CrossWall;
import com.wfe.entities.DoorWall;
import com.wfe.entities.Wall;
import com.wfe.entities.WindowWall;
import com.wfe.game.World;
import com.wfe.input.Keyboard;
import com.wfe.input.Keys;
import com.wfe.input.Mouse;
import com.wfe.math.Vector3f;
import com.wfe.physics.AABB;
import com.wfe.tileEngine.Tile;
import com.wfe.utils.MousePicker;

public class PlayerControllerComponent implements Component {

	private Camera camera;
	private Transformation transform;
	private float yRot;
	private float xd, zd;

	private AABB bb;
	
	private StaticEntity buildEntity;
	private int buildEntityType;
	private int buildEntityRotation;
	
	public PlayerControllerComponent(Camera camera, Transformation transform) {		
		this.camera = camera;
		this.transform = transform;
		bb = new AABB(transform.x - 0.4f, 0, transform.z - 0.4f, transform.x + 0.4f, 0 + 1, transform.z + 0.4f);
	}
	
	@Override
	public void update(float dt) {	
		move(dt);
		build();
	}
	
	private void build() {
		int x = 0;
		int z = 0;
		Vector3f tPos = MousePicker.getCurrentTerrainPoint();
		if(tPos != null) {
			x = (int)tPos.x;
			z = (int)tPos.z;
		}
		
		if(Keyboard.isKeyDown(Keys.KEY_1)) {
			removeCurrentBuilding();
			buildEntity = new Wall(new Transformation(x, 0, z));
			World.getWorld().addEntity(buildEntity);
			buildEntityType = 1;
		} else if(Keyboard.isKeyDown(Keys.KEY_2)) {
			removeCurrentBuilding();
			buildEntity = new CrossWall(new Transformation(x, 0, z));
			buildEntityType = 2;
			World.getWorld().addEntity(buildEntity);
		} else if(Keyboard.isKeyDown(Keys.KEY_3)) {
			removeCurrentBuilding();
			buildEntity = new WindowWall(new Transformation(x, 0, z));
			buildEntityType = 3;
			World.getWorld().addEntity(buildEntity);
		} else if(Keyboard.isKeyDown(Keys.KEY_4)) {
			removeCurrentBuilding();
			buildEntity = new DoorWall(new Transformation(x, 0, z));
			World.getWorld().addEntity(buildEntity);
			buildEntityType = 4;
		}
		
		if(Keyboard.isKeyDown(Keys.KEY_R)) {
			buildEntityRotation += 90;
			
			if(buildEntityRotation == 360) {
				buildEntityRotation = 0;
			}
		}
		
		if(buildEntity != null) {
			buildEntity.getTransform().setPosition(x, 0, z);
			buildEntity.getTransform().setRotY(buildEntityRotation);
		}
		
		if(Mouse.isButtonDown(0)) {
			StaticEntity entity = null;
			if(buildEntityType == 1) {
				entity = new Wall(new Transformation(x, 0, z));
				entity.addComponent(new ColliderComponent(1, 1, 1, entity.getTransform()));
			} else if(buildEntityType == 2) {
				entity = new CrossWall(new Transformation(x, 0, z));
				entity.addComponent(new ColliderComponent(1, 1, 1, entity.getTransform()));
			} else if(buildEntityType == 3) {
				entity = new WindowWall(new Transformation(x, 0, z));
				entity.addComponent(new ColliderComponent(1, 1, 1, entity.getTransform()));
			} else if(buildEntityType == 4) {
				entity = new DoorWall(new Transformation(x, 0, z));
			}
			
			entity.getTransform().setRotY(buildEntityRotation);
			if(World.getWorld().setTileEntity(x, z, entity)) {
				World.getWorld().addEntity(entity);
			}
		}
		
		if(Mouse.isButtonDown(1)) {
			World.getWorld().setTile(x - 1, z + 1, Tile.DESK.getId());
			World.getWorld().setTile(x, z + 1, Tile.DESK.getId());
			World.getWorld().setTile(x + 1, z + 1, Tile.DESK.getId());
			World.getWorld().setTile(x - 1, z, Tile.DESK.getId());
			World.getWorld().setTile(x + 1, z, Tile.DESK.getId());
			World.getWorld().setTile(x - 1, z - 1, Tile.DESK.getId());
			World.getWorld().setTile(x, z - 1, Tile.DESK.getId());
			World.getWorld().setTile(x + 1, z - 1, Tile.DESK.getId());
			World.getWorld().setTile(x, z, Tile.DESK.getId());		
		}
	}
	
	private void move(float dt) {	
		transform.isMoving = false;
		float xa = 0.0f;
		float za = 0.0f;
		
		if(Keyboard.isKey(Keys.KEY_A) || Keyboard.isKey(Keys.KEY_LEFT)) {
			xa = -1.0f;
		} else if(Keyboard.isKey(Keys.KEY_D) || Keyboard.isKey(Keys.KEY_RIGHT)) {
			xa = 1.0f;
		}
		
		if(Keyboard.isKey(Keys.KEY_W) || Keyboard.isKey(Keys.KEY_UP)) {
			za = -1.0f;
		} else if(Keyboard.isKey(Keys.KEY_S) || Keyboard.isKey(Keys.KEY_DOWN)) {
			za = 1.0f;
		}
		
		yRot = camera.getYaw();
		
		if(xa < 0) {
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
		}
		
		if(xa != 0 || za != 0) {
			transform.isMoving = true;
		}
		
		moveRelative(xa, za, 1.8f, dt);
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
	
	public void removeCurrentBuilding() {
		if(buildEntity != null) {
			buildEntity.remove();
			buildEntity = null;
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.PLAYER_CONTROLLER;
	}

}
