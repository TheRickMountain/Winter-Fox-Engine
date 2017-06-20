#version 330 core

in vec2 TexCoords;

out vec4 out_color;

uniform sampler2D image;
uniform vec4 color;

void main()
{
	out_color = vec4(color.rgb, texture(image, TexCoords).a);
}