package com.wfe.colladaLoader;

import com.wfe.math.Matrix4f;

public class JointTransformData {

	public final String jointNameId;
	public final Matrix4f jointLocalTransform;
	
	protected JointTransformData(String jointNameId, Matrix4f jointLocalTransform){
		this.jointNameId = jointNameId;
		this.jointLocalTransform = jointLocalTransform;
	}
}
