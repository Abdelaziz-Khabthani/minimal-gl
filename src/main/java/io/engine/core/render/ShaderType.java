package io.engine.core.render;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public enum ShaderType {
    VERTEX(GL_VERTEX_SHADER),
    FRAGMENT(GL_FRAGMENT_SHADER);

    private final int glShaderType;

    public int getGlShaderType() {
        return glShaderType;
    }

    ShaderType(final int glShaderType) {
        this.glShaderType = glShaderType;
    }
}
