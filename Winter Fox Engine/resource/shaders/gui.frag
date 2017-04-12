#version 330 core

in vec2 TextureCoords;

out vec4 out_Color;

uniform sampler2D image;
uniform vec4 color;
uniform int mode;

void main()
{
	if(mode == 0){
		out_Color = texture(image, TextureCoords) * color;
	} else if(mode == 1) {
		out_Color = color;
	} else {
		vec4 img = texture(image, TextureCoords);
		float value = (img.r + img.g + img.b) / 3;
		out_Color = vec4(value, value, value, img.a);
	}
}