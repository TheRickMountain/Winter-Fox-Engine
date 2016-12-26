#version 330 core

in vec3 in_position;
in vec2 in_textureCoords;

out vec2 TextureCoords;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

void main()
{
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(in_position, 1.0f);
	TextureCoords = in_textureCoords;
}