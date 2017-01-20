#version 330 core

in vec2 TextureCoords;

out vec4 out_Color;

uniform sampler2D image;
uniform vec4 color;
uniform float solidColor;

void main()
{
	if(solidColor == 0){
		out_Color = texture(image, TextureCoords);
	} else {
		out_Color = color;
	}
}