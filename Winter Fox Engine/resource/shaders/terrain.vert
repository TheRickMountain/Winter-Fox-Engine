#version 330 core

in vec3 in_position;
in vec2 in_textureCoords;
in vec3 in_normal;

out vec2 TextureCoords;
out vec3 Normal;
out vec3 ToLightVector;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

uniform vec3 lightPosition;

void main()
{
	vec4 worldPosition = modelMatrix * vec4(in_position, 1.0f);
	gl_Position = projectionMatrix * viewMatrix * worldPosition;
	TextureCoords = in_textureCoords * 200;
	Normal = (modelMatrix * vec4(in_normal, 0.0f)).xyz;
	ToLightVector = lightPosition - worldPosition.xyz;
}