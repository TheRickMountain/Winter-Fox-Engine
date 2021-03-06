package com.wfe.components;

import java.util.List;

import com.wfe.audio.AudioMaster;
import com.wfe.audio.Source;
import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.core.World;
import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Wheat;
import com.wfe.gui.GUIManager;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.gui.ItemType;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.math.Vector3f;
import com.wfe.physics.AABB;
import com.wfe.tileEngine.Tile;
import com.wfe.utils.MathUtils;
import com.wfe.utils.MousePicker;
import com.wfe.utils.MyRandom;
import com.wfe.utils.TimeUtil;

public class PlayerControllerComponent extends Component {

	private World world;
	
	private Camera camera;
	private Transformation transform;
	private float yRot;
	private float xd, zd;
	private float xf, zf;
	private float speed;

	private AABB bb;
	
	private PlayerAnimation animation;
	
	private Source source;
	
	private Entity hand;
	private Entity equipment;
	
	private Entity building;
	private float buildingRotation = 0;
	
	private TimeUtil timer;
	private boolean mining = false;
	private MineableComponent mc;
	
	private boolean fighting = false;
	
	public PlayerControllerComponent(Camera camera, Transformation transform, PlayerAnimation playerAnim, Entity hand) {		
		world = World.getWorld();
		
		this.camera = camera;
		this.transform = transform;
		this.animation = playerAnim;
		this.hand = hand;
		this.source = new Source();
	}
	
	public void init() {
		bb = new AABB(transform.x - 0.4f, 0, transform.z - 0.4f, transform.x + 0.4f, 0 + 1, transform.z + 0.4f);
		speed = 2.0f;
		timer = new TimeUtil();
	}
	
	@Override
	public void update(float dt) {	
		if(!Mouse.isActiveInGUI())
			iteractWithWorld(dt);
		
		if(GUIManager.state.equals(GUIManager.GUIState.GAME))
			move(dt);
		else
			animation.idleAnim();
	}
	
	public void iteractWithWorld(float dt) {
		if(!fighting) {
			if(Mouse.isButtonDown(0)) {
				if(GUIManager.inventory.getSelectedItem().type.equals(ItemType.WEAPON)) {
					animation.idleAnim();
					animation.prepareFightAnim();
					fighting = true;
					
					source.play(ResourceManager.getSound("swing" + MyRandom.nextInt(1, 3)));
					
					/*for(Entity entity : World.getWorld().getMobs()) {
						MobComponent mc = (MobComponent) entity.getComponent(ComponentType.MOB);
						mc.hurt(getParent(), 10);
					}*/
				}
				
				for(Entity entity : world.npc) {
					BoundingBoxComponent bb = (BoundingBoxComponent) entity.getComponent(ComponentType.BOUNDING_BOX);
					if(bb.intersects()) {
						NpcComponent npc = (NpcComponent) entity.getComponent(ComponentType.NPC);
						GUIManager.dialogueSystem.open(npc.node, npc.currentNode);
					}
				}
			}
		} else {
			if(animation.fightAnim(dt)) {
				fighting = false;
				animation.idleAnim();
			}
		}
		
		if(Keyboard.isKeyDown(Key.KEY_R)) {
			buildingRotation += 90;
			
			if(buildingRotation == 360) {
				buildingRotation = 0;
			}
		}
		
		Vector3f tp = MousePicker.getCurrentTerrainPoint();
		if(tp != null) {
				Tile tile = world.getTile((int)tp.x, (int)tp.z);
				
				if(tile != null) {
				if(building != null) {
					if(tile.isHasEntity()) {
						building.getMaterial().getColor().set(1.0f, 0.5f, 0.5f);
					} else {
						building.getMaterial().getColor().set(0.5f, 1.0f, 0.5f);
					}
					building.getTransform().setPosition(((int)tp.x) + 0.5f, 0, ((int)tp.z) + 0.5f);
					building.getTransform().setRotY(buildingRotation);
				}
				
				if(tile.isHasEntity()) {
					Entity entity = tile.getEntity();
					if(entity.hasComponent(ComponentType.GATHERABLE)) {
						if(checkDistance(tp.x, tp.z)) {
							Display.setCursor(Display.takeCursor);
							if(Mouse.isButtonDown(1)) {
								turnTo((int)tp.x, (int)tp.z);
								
								GatherableComponent gc = (GatherableComponent)entity.getComponent(ComponentType.GATHERABLE);
								
								if(GUIManager.inventory.addItem(gc.getItem(), gc.getCount()) == 0) {
									tile.removeEntity();
									source.play(gc.getSound());
								}
							}
						} else {
							Display.setCursor(Display.takeNonactiveCursor);
						}
					}
					
					if(entity.hasComponent(ComponentType.CHEST)) {
						if(Mouse.isButtonDown(1)) {
							if(checkDistance(tp.x, tp.z)) {
								turnTo((int)tp.x, (int)tp.z);
								GUIManager.openChest((ChestComponent) entity.getComponent(ComponentType.CHEST));
								Mouse.setActiveInGUI(true);
							}
						}
					}
					
					if(Mouse.isButtonDown(1)) {
						if(entity.getTag().equals("bed")) {
							if(checkDistance(tp.x, tp.z)) {
								turnTo((int)tp.x, (int)tp.z);
								world.addTime(8, 0);
							}
						}
					}
					
					if(entity.hasComponent(ComponentType.MINEABLE)) {
						if(Mouse.isButtonDown(0)) {
							if(checkDistance(tp.x, tp.z)) {
								animation.idleAnim();
								turnTo((int)tp.x, (int)tp.z);
								mc = (MineableComponent)entity.getComponent(ComponentType.MINEABLE);
								if(GUIManager.inventory.getSelectedItem().equals(mc.getRequiredItem())) {
									mining = true;	
								}
							}
						}
					}
					
					if(mining) {
						GUIManager.showProgressBar = true;
						
						float time = (float) timer.getTime();
						if(time >= mc.getMiningTime()) {
							GUIManager.inventory.addItem(mc.getItem(), mc.getCount());
							world.removeEntityFromTile(
									(int)mc.getParent().getTransform().x, 
									(int)mc.getParent().getTransform().z);
							mc = null;
							
							resetMiningProgress();
						} else {
							if(animation.hitAnim(dt)) {
								source.play(mc.getSound());
							}
							GUIManager.progressBar.setCurrentValue((int)((time * 100) / mc.getMiningTime()));
						}
					}
					
					if(Mouse.isButtonUp(0)) {
						resetMiningProgress();
					}
					
				} else {
					if(Mouse.isButtonDown(0)) {
				
						switch(GUIManager.inventory.getSelectedItem().id) {
						case Item.HOE:
							if(checkDistance(tp.x, tp.z)) {
								turnTo((int)tp.x, (int)tp.z);
								if(tile.getId() != 10) {
									world.setTile((int)tp.x, (int)tp.z, 0);
									source.play(ResourceManager.getSound("hoe"));
								}
							}
							break;
						case Item.WHEAT_SEEDS:
							if(checkDistance(tp.x, tp.z)) {
								turnTo((int)tp.x, (int)tp.z);
								if(tile.getId() == 10) {
									if(world.addEntityToTile(new Wheat(new Transformation(
											((int)tp.x) + 0.5f, 0, ((int)tp.z) + 0.5f), 0))) {
										GUIManager.inventory.removeItem(ItemDatabase.getItem(Item.WHEAT_SEEDS), 1);
										source.play(ResourceManager.getSound("hoe"));
									}
								}
							}
							break;
						}
						
						if(GUIManager.inventory.getSelectedItem().type.equals(ItemType.BUILDING)) {
							if(checkDistance(tp.x, tp.z)) {
								turnTo((int)tp.x, (int)tp.z);
								if(!tile.isHasEntity()) {
									tile.setEntity(GUIManager.inventory.getSelectedItem().entity.getInstance());
									tile.getEntity().getTransform().setPosition(((int)tp.x) + 0.5f, 0, ((int)tp.z) + 0.5f);
									tile.getEntity().getTransform().setRotY(buildingRotation);
									world.addEntity(tile.getEntity());
									GUIManager.inventory.removeItem(GUIManager.inventory.getSelectedItem(), 1);
									AudioMaster.defaultSource.play(ResourceManager.getSound("taking"));
									
									if(GUIManager.inventory.hasItem(GUIManager.inventory.getSelectedItem()) == 0) {
										removeBuilding();
									}
								}
							}
						}
						
					}
					Display.setCursor(Display.defaultCursor);
				}
			}
		}
	}
	
	private void resetMiningProgress() {
		mining = false;
		GUIManager.showProgressBar = false;
		timer.reset();
		GUIManager.progressBar.setCurrentValue(0);
		animation.idleAnim();
	}
	
	private boolean checkDistance(float x, float z) {
		float distance = MathUtils.getDistance(
				(int)getParent().getTransform().x, 
				(int)getParent().getTransform().z, 
				(int)x, (int)z);
		return distance <= 1.4142135f;
	}
	
	private void turnTo(int x, int z) {
		getParent().getTransform().setRotY(
				-MathUtils.getRotation(
						getParent().getTransform().x, 
						getParent().getTransform().z, 
						x + 0.5f, z + 0.5f) + 90);
	}
	
	private void move(float dt) {	
		transform.isMoving = false;
		float xa = 0.0f;
		float za = 0.0f;
		
		if(!mining && !fighting) {
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
		
		if(!mining && !fighting) {
			if(xa != 0 || za != 0) {
				/*xf = xa;
				zf = za;*/
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
        xf = ((xa *= dist) * cos - (za *= dist) * sin) * dt;
        zf = (za * cos + xa * sin) * dt;
        this.xd += xf;
        this.zd += zf;
    }
	
	private void move(float xa, float za) {
		 float xaOrg = xa;
	     float zaOrg = za;
	     
	     List<AABB> aABBs = world.getColliders();
	     
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
	
	public void addEquipment(Entity eqpm) {
		removeEquipment();
		removeBuilding();
		
		equipment = eqpm;
		hand.addChild(equipment);
		world.addEntity(equipment);
	}
	
	public void removeEquipment() {
		if(equipment != null) {
			hand.removeChild(equipment);
			equipment.remove();
			equipment = null;
		}
	}
	
	public Entity getEquipment() {
		return equipment;
	}
	
	public void addBuilding(Entity building) {
		removeBuilding();
		removeEquipment();
		
		this.building = building;
		world.addEntity(building);
		
	}
	
	public void removeBuilding() {
		if(building != null) {
			world.removeEntity(building);
			building = null;
		}
	}
	
	public Entity getBuilding() {
		return building;
	}
	
	public float getXF() {
		return -xf;
	}
	
	public float getZF() {
		return -zf;
	}

	@Override
	public Component getInstance() {
		return null;
	}
	
}
