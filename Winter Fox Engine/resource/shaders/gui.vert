#version 330 core

in vec4 in_data;

out vec2 TextureCoords;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;

void main()
{
	gl_Position = projectionMatrix * modelMatrix * vec4(in_data.xy, 0.0f, 1.0f);
	TextureCoords = in_data.xy;
}