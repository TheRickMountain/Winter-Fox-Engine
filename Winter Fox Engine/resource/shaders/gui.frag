#version 330 core

in vec2 TextureCoords;

out vec4 out_Color;

uniform sampler2D image;
uniform vec3 color;
uniform float solidColor;
uniform float gray;

void main()
{
	if(solidColor == 0){
		vec4 diffuseColor = texture(image, TextureCoords);		
		if(gray == 1){
			float g = (diffuseColor.r + diffuseColor.g + diffuseColor.b) / 3;
			diffuseColor = vec4(g, g, g, diffuseColor.a);
		}
		
		out_Color = diffuseColor;
	} else {
		out_Color = vec4(color, 1.0f);
	}
}