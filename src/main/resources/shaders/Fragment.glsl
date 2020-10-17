#version 460 core
in vec3 outputFromShader;
uniform vec4 fromCpu;
out vec4 FragColor;

void main()
{
    FragColor = fromCpu;
}