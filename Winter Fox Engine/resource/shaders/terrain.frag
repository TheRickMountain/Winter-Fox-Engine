#version 330 core

in vec2 TextureCoords;

out vec4 out_Color;

uniform sampler2D diffuseMap;
uniform vec3 lightDirection;
uniform vec3 lightColor;
uniform vec3 ambientLight;

const vec2 lightBias = vec2(0.7, 0.6);
const vec3 Normal = vec3(0.0f, 1.0f, 0.0f);

void main()
{
	vec4 diffuseColor = texture(diffuseMap, TextureCoords);
	if(diffuseColor.a < 0.5f){
		discard;
	}
	vec3 unitNormal = normalize(Normal);
	float brightness = max(dot(-lightDirection, unitNormal), 0.0) * lightBias.x + lightBias.y;
	vec3 diffuseLight = brightness * lightColor + ambientLight;
	out_Color = diffuseColor * vec4(diffuseLight, 1.0f);
}