#version 150

in vec2 pass_textureCoords;
in vec3 pass_normal;

out vec4 out_colour;

uniform sampler2D diffuseMap;

uniform vec3 lightDirection;
uniform vec3 lightColor;
uniform vec3 ambientLight;

const vec2 lightBias = vec2(0.7, 0.6);

void main(void){
	
	vec4 diffuseColour = texture(diffuseMap, pass_textureCoords);		
	vec3 unitNormal = normalize(pass_normal);
	float brightness = max(dot(-lightDirection, unitNormal), 0.0) * lightBias.x + lightBias.y;
	vec3 diffuseLight = brightness * lightColor + ambientLight;
	out_colour = diffuseColour * vec4(diffuseLight, 1.0f);
}