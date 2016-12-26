#version 330 core

in vec2 TexCoords;

out vec4 out_Color;

uniform sampler2D image;
uniform int hasTexture;
uniform vec4 color;

void main()
{
	if(hasTexture == 1) {
		out_Color = color * texture(image, TexCoords);
	} else if(hasTexture == 0) {
		out_Color = color;
	}
}