package com.wfe.blueprints;

import java.util.ArrayList;
import java.util.List;

import com.wfe.ecs.Component;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.graph.Mesh;

public abstract class Blueprint {
	
	protected Mesh mesh;
	protected Material material;
	
	protected Transformation transform;
	
	protected List<Component> components = new ArrayList<Component>();
		
	public Blueprint(Mesh mesh, Material material, Transformation transform) {
		this.mesh = mesh;
		this.material = material;
		this.transform = transform;
	}
	
	public void addComponent(Component component) {
		this.components.add(component);
	}
	
	public abstract Entity createInstance();

	public abstract Entity createInstanceWithComponents(Transformation transform);
	
}
