#version 330 core

in vec2 TextureCoords;
in vec3 Normal;
in vec3 ToLightVector;

out vec4 out_Color;

uniform sampler2D diffuseMap;
uniform vec4 color;
uniform vec3 lightColor;
uniform float hasFakeLighting;

void main()
{
	vec4 diffuseColor = texture(diffuseMap, TextureCoords);
	if(diffuseColor.a <= 0.5f){
		discard;
	}
	
	vec3 unitNormal = vec3(0.0f, 1.0f, 0.0f);
	if(hasFakeLighting != 1) {
		unitNormal = normalize(Normal);
	}
	vec3 unitLightVector = normalize(ToLightVector);
	
	float nDotl = dot(unitNormal, unitLightVector);
	float brightness = max(nDotl, 0.5f);
	vec3 diffuse = brightness * lightColor + vec3(0.3f, 0.3f, 0.3f);
	
	out_Color = vec4(diffuse, 1.0f) * diffuseColor;
}