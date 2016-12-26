package com.wfe.core;

public interface IGameLogic {
	
	public void loadResources() throws Exception;
	
	public void onEnter(Display display) throws Exception;
	
	public void update(float dt);
	
	public void render();
	
	public void onExit();

}
