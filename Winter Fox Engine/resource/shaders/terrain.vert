#version 330 core

in vec3 in_position;
in vec2 in_textureCoords;
in vec3 in_normal;

out vec2 TextureCoords;
out vec3 Normal;
out vec3 ToLightVector;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform vec3 lightPosition;

void main()
{
	gl_Position = projectionMatrix * viewMatrix * vec4(in_position, 1.0f);
	TextureCoords = in_textureCoords;
	Normal = in_normal;
	ToLightVector = lightPosition - in_position.xyz;
}