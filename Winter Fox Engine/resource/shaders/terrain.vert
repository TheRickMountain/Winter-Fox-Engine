#version 330 core

in vec3 in_position;
in vec2 in_textureCoords;

out vec2 TextureCoords;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main()
{
	gl_Position = projectionMatrix * viewMatrix * vec4(in_position, 1.0f);
	TextureCoords = in_textureCoords;
}