#version 330 core

in vec2 TextureCoords;

out vec4 color;

uniform sampler2D diffuseMap;

void main()
{
	color = texture(diffuseMap, TextureCoords);
}