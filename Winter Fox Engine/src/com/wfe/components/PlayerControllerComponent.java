package com.wfe.components;

import java.util.List;

import com.wfe.audio.AudioMaster;
import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.game.World;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.math.Vector3f;
import com.wfe.physics.AABB;
import com.wfe.tileEngine.Tile;
import com.wfe.utils.MathUtils;
import com.wfe.utils.MousePicker;

public class PlayerControllerComponent extends Component {

	private Camera camera;
	private Transformation transform;
	private float yRot;
	private float xd, zd;
	private float speed;

	private AABB bb;
	
	private PlayerAnimationComponent animation;
	private InventoryComponent inventory;
	
	private MineableComponent mineableComponent;
	private Tile currentTile;
	private boolean mining = false;
	
	public PlayerControllerComponent(Camera camera, Transformation transform, PlayerAnimationComponent playerAnim,
			InventoryComponent inventory) {		
		this.camera = camera;
		this.transform = transform;
		this.animation = playerAnim;
		this.inventory = inventory;
		this.bb = new AABB(transform.x - 0.4f, 0, transform.z - 0.4f, transform.x + 0.4f, 0 + 1, transform.z + 0.4f);
		this.speed = 2.0f;
	}
	
	@Override
	public void update(float dt) {	
		iteractWithWorld(dt);
		move(dt);
	}
	
	private void iteractWithWorld(float dt) {
		if(mining) {
			if(animation.hitAnim(dt)) {
				mineableComponent.decreasetHealth();
				animation.idleAnim();
				AudioMaster.defaultSource.play(mineableComponent.getSound());
				
				/* Если добыча окончена, проверяем здоровье добываемого предмета */
				/* В случае если здоровье равно 0, удаляем объект */
				if(mineableComponent.getHealth() == 0) {
					inventory.addItem(mineableComponent.getItem(), mineableComponent.getCount());
					currentTile.removeEntity();
				}
				
				mining = false;
				mineableComponent = null;
			}
		}
		
		/*** Получение Entity по расположению курсора на Terrain ***/
		/* Пока идет добыча чего-либо данный код бездействует в ожидании окончания добычи */
		if(!mining) {
			Vector3f tp = MousePicker.getCurrentTerrainPoint();
			if(tp != null) {
				currentTile = World.getWorld().getTile((int)tp.x, (int)tp.z);
				if(currentTile.isHasEntity()) {
					Entity entity = currentTile.getEntity();
					if(entity.hasComponent(ComponentType.GATHERABLE)) {
						Display.setCursor(Display.takeCursor);
						if(Mouse.isButtonDown(1)) {
							if(checkDistance(tp.x, tp.z)) {
								turnTo((int)tp.x, (int)tp.z);
								
								GatherableComponent gc = (GatherableComponent)entity.getComponent(ComponentType.GATHERABLE);
								
								InventoryComponent inv = (InventoryComponent) getParent().getComponent(ComponentType.INVENTORY);
								inv.addItem(gc.getItem(), gc.getCount());
								currentTile.removeEntity();
								
								AudioMaster.defaultSource.play(ResourceManager.getSound("taking"));
							}
						}
					} else if(entity.hasComponent(ComponentType.MINEABLE)) {
						Display.setCursor(Display.takeCursor);
						if(Mouse.isButton(0)) {
							if(checkDistance(tp.x, tp.z)) {
								mineableComponent = (MineableComponent)entity.getComponent(ComponentType.MINEABLE);	
								
								if(inventory.getSelected() == mineableComponent.getRequiredItem()) {
									turnTo((int)tp.x, (int)tp.z);
									mining = true;
								}
							}
						}
					}
				} else {
					Display.setCursor(Display.defaultCursor);
				}
			}
		}
		/*** *** ***/
	}
	
	private boolean checkDistance(float x, float z) {
		float distance = MathUtils.getDistance(
				(int)getParent().getTransform().x, 
				(int)getParent().getTransform().z, 
				(int)x, (int)z);
		return (distance == 1.0f || distance == 1.4142135f);
	}
	
	private void turnTo(int x, int z) {
		getParent().getTransform().setRotY(
				-MathUtils.getRotation(
						getParent().getTransform().x, 
						getParent().getTransform().z, 
						x, z) + 90);
	}
	
	private void move(float dt) {	
		transform.isMoving = false;
		float xa = 0.0f;
		float za = 0.0f;
		
		if(!mining) {
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
		
		if(!mining) {
			if(xa != 0 || za != 0) {
				transform.isMoving = true;
				animation.walkAnim(dt);
			} else {
				animation.idleAnim();
			}
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
