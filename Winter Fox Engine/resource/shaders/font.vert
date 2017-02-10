#version 330

in vec2 in_position;
in vec2 in_texCoord;

out vec2 TextureCoords;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;

void main(void){

	gl_Position = projectionMatrix * modelMatrix * vec4(in_position, 0.0f, 1.0f);
	TextureCoords = in_texCoord;

}