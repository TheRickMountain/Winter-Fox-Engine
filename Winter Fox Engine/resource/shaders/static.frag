#version 330 core

in vec2 TextureCoords;

out vec4 out_Color;

uniform sampler2D diffuseMap;
uniform vec4 color;

void main()
{
	out_Color = color * texture(diffuseMap, TextureCoords);
}