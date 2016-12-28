#version 330 core

in vec2 TextureCoords;
in vec3 Normal;

out vec4 out_Color;

uniform sampler2D diffuseMap;
uniform vec4 color;
uniform vec3 lightDirection;
uniform vec3 lightColor;

const vec2 lightBias = vec2(0.7, 0.6);

void main()
{
	vec4 diffuseColor = color * texture(diffuseMap, TextureCoords);
	if(diffuseColor.a <= 0.5f){
		discard;
	}
	
	vec3 unitNormal = normalize(Normal);
	float brightness = max(dot(-lightDirection, unitNormal), 0.0) * lightBias.x + lightBias.y;
	vec3 diffuseLight = brightness * lightColor;
	out_Color = diffuseColor * vec4(diffuseLight, 1.0f);
}