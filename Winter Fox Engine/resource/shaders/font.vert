#version 330

in vec2 in_position;
in vec2 in_textureCoords;

out vec2 TextureCoords;

uniform vec2 translation;
uniform mat4 modelMatrix;

void main(void){

	gl_Position = modelMatrix * vec4(in_position, 0.0f, 1.0f);
	TextureCoords = in_textureCoords;

}