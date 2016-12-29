#version 330 core

in vec2 TextureCoords;

out vec4 out_Color;

uniform sampler2D image;

void main()
{
	out_Color = texture(image, TextureCoords);
}