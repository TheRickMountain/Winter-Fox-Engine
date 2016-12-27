#version 330

in vec2 TextureCoords;

out vec4 out_Color;

uniform sampler2D fontAtlas;
uniform vec3 color;


void main(void){

	out_Color = vec4(color, texture(fontAtlas, TextureCoords).a);

}