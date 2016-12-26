package com.wfe.colladaLoader;

public class AnimatedModelData {

	private final JointsData joints;
	private final MeshData mesh;
	
	public AnimatedModelData(MeshData mesh, JointsData joints){
		this.joints = joints;
		this.mesh = mesh;
	}
	
	public JointsData getJointsData(){
		return joints;
	}
	
	public MeshData getMeshData(){
		return mesh;
	}
	
}
