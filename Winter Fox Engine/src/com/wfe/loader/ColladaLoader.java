package com.wfe.loader;

import com.wfe.colladaLoader.AnimatedModelData;
import com.wfe.colladaLoader.AnimationData;
import com.wfe.colladaLoader.AnimationLoader;
import com.wfe.colladaLoader.GeometryLoader;
import com.wfe.colladaLoader.JointsData;
import com.wfe.colladaLoader.JointsLoader;
import com.wfe.colladaLoader.MeshData;
import com.wfe.colladaLoader.SkinLoader;
import com.wfe.colladaLoader.SkinningData;
import com.wfe.utils.MyFile;
import com.wfe.xmlLoader.XmlNode;
import com.wfe.xmlLoader.XmlParser;

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
