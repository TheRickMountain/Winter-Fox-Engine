#version 330

in vec4 in_data;

out vec2 TextureCoords;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;

void main(void){

	gl_Position = modelMatrix * vec4(in_data.xy, 0.0f, 1.0f);
	TextureCoords = in_data.zw;

}