package com.wfe.animation.loaders;

import com.wfe.animation.colladaLoader.AnimatedModelData;
import com.wfe.animation.colladaLoader.AnimationData;
import com.wfe.animation.colladaLoader.AnimationLoader;
import com.wfe.animation.colladaLoader.GeometryLoader;
import com.wfe.animation.colladaLoader.JointsData;
import com.wfe.animation.colladaLoader.JointsLoader;
import com.wfe.animation.colladaLoader.SkinLoader;
import com.wfe.animation.colladaLoader.SkinningData;
import com.wfe.animation.xmlLoader.XmlNode;
import com.wfe.animation.xmlLoader.XmlParser;
import com.wfe.graph.MeshData;
import com.wfe.utils.MyFile;

public class ColladaLoader {

	public static AnimatedModelData loadColladaModel(MyFile colladaFile, int maxWeights) {
		XmlNode node = XmlParser.loadXmlFile(colladaFile);

		SkinLoader skinLoader = new SkinLoader(node.getChild("library_controllers"), maxWeights);
		SkinningData skinningData = skinLoader.extractSkinData();

		JointsLoader jointsLoader = new JointsLoader(node.getChild("library_visual_scenes"),
				skinningData.jointOrder);
		JointsData jointsData = jointsLoader.extractBoneData();

		GeometryLoader g = new GeometryLoader(node.getChild("library_geometries"),
				skinningData.verticesSkinData);
		MeshData meshData = g.extractModelData();
		
		return new AnimatedModelData(meshData, jointsData);
	}

	public static AnimationData loadColladaAnimation(MyFile colladaFile){
		XmlNode node = XmlParser.loadXmlFile(colladaFile);
		AnimationLoader a = new AnimationLoader(node.getChild("library_animations"));
		AnimationData animData = a.extractAnimation();
		return animData;
	}
	
}
