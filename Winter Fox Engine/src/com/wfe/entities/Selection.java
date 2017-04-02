package com.wfe.entities;

import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.graph.Mesh;

public class Selection extends Entity {

	public Selection(Mesh mesh, Material material, Transformation transform) {
		super(mesh, material, transform);
	}

}
