#version 330

in vec2 outTexCoord;
in vec3 mvPos;
out vec4 fragColor;

uniform sampler2D fontAtlas;
uniform vec4 colour;
uniform float hasTexture;

void main()
{
    if ( hasTexture == 1 )
    {
        fragColor = vec4(colour.rgb, texture(fontAtlas, outTexCoord).a);
    }
    else
    {
        fragColor = colour;
    }
}