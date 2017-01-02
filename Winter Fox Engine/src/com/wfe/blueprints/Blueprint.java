package com.wfe.blueprints;

import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.graph.Mesh;

public abstract class Blueprint {
	
	protected Mesh mesh;
	protected Material material;
	
	protected Transformation transform;
	
	public Blueprint(Mesh mesh, Material material, Transformation transform) {
		this.mesh = mesh;
		this.material = material;
		this.transform = transform;
	}
	
	public abstract StaticEntity createInstance();

}
