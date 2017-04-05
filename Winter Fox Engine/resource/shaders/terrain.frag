#version 330 core

in vec2 TextureCoords;
in vec3 Normal;
in vec3 ToLightVector;

out vec4 out_Color;

uniform sampler2D diffuseMap;
uniform vec3 lightColor;

void main()
{
	vec4 diffuseColor = texture(diffuseMap, TextureCoords);
	
	vec3 unitNormal = normalize(Normal);
	vec3 unitLightVector = normalize(ToLightVector);
	
	float nDotl = dot(unitNormal, unitLightVector);
	float brightness = max(nDotl, 0.4f);
	vec3 diffuse = brightness * lightColor + vec3(0.3f, 0.3f, 0.3f);
	
	out_Color = vec4(diffuse, 1.0f) * diffuseColor;
}