#version 330

in vec2 in_position;
in vec2 in_textureCoords;

out vec2 TextureCoords;

uniform vec2 translation;

void main(void){

	gl_Position = vec4(in_position + translation * vec2(2.0f, -2.0f), 0.0f, 1.0f);
	TextureCoords = in_textureCoords;

}