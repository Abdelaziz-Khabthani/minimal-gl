package io.engine.core.render;

public class Shader {
    private String shaderLocation;
    private ShaderType shaderType;

    public Shader(String shaderLocation, ShaderType shaderType) {
        this.shaderLocation = shaderLocation;
        this.shaderType = shaderType;
    }

    public String getShaderLocation() {
        return shaderLocation;
    }

    public void setShaderLocation(String shaderLocation) {
        this.shaderLocation = shaderLocation;
    }

    public ShaderType getShaderType() {
        return shaderType;
    }

    public void setShaderType(ShaderType shaderType) {
        this.shaderType = shaderType;
    }
}
