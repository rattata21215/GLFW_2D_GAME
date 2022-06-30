#type vertex
#version 330 core

layout (location = 0) in vec3 attribPosition;
layout (location = 0) in vec4 attribColor;

out vec4 fragColor;

void main()
{
    fragColor = attribColor;

    gl_Position = vec4(attribPosition, 1.0);
}

#type fragment
#version 330 core

out vec4 color;

in vec4 fragColor;

void main()
{
    color = fragColor;
}