package io.engine.component;

import io.engine.core.Component;
import io.engine.core.render.Shader;
import io.engine.core.render.ShaderType;
import io.engine.exception.ShaderException;
import io.engine.util.EngineFileUtil;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import static org.lwjgl.opengl.GL20.*;

public class ShaderComponent extends Component {
    private String vertexShaderLocation;
    private String fragmentShaderLocation;

    private int shaderProgramId;

    public ShaderComponent(final String vertexShaderLocation, final String fragmentShaderLocation) {
        this.vertexShaderLocation = vertexShaderLocation;
        this.fragmentShaderLocation = fragmentShaderLocation;

        shaderProgramId = init(vertexShaderLocation, fragmentShaderLocation);
    }

    private int init(final String vertexShaderLocation, final String fragmentShaderLocation) {
        final int shaderProgramId;
        Objects.requireNonNull(vertexShaderLocation, "Vertex shader location should not be null");
        Objects.requireNonNull(fragmentShaderLocation, "Fragment shader location should not be null");

        int vertexShader = compileShader(new Shader(vertexShaderLocation, ShaderType.VERTEX));
        int fragmentShader = compileShader(new Shader(fragmentShaderLocation, ShaderType.FRAGMENT));

        shaderProgramId = createShaderProgram(vertexShader, fragmentShader);
        cleanShaders(vertexShader, fragmentShader);
        return shaderProgramId;
    }

    private int createShaderProgram(final int vertexShader, final int fragmentShader) {
        int programId = glCreateProgram();
        glAttachShader(programId, vertexShader);
        glAttachShader(programId, fragmentShader);
        glLinkProgram(programId);
        boolean shaderLinkingFailed = glGetProgrami(programId, GL_LINK_STATUS) == 0;
        if (shaderLinkingFailed) {
            String shaderInfoLog = glGetShaderInfoLog(programId);
            throw new ShaderException("Error occurred while linking shaders, with error : [" + shaderInfoLog + "]");
        }
        return programId;
    }

    private int compileShader(final Shader shader) {
        Objects.requireNonNull(shader, "Shader should not be null");

        String shaderLocation = shader.getShaderLocation();
        int glShaderType = shader.getShaderType().getGlShaderType();

        Objects.requireNonNull(shaderLocation, "Shader location should not be null");
        Objects.requireNonNull(shader.getShaderType(), "Shader type should not be null");

        String shaderCode;
        int shaderObject;

        try {
            shaderCode = EngineFileUtil.getFileFromResourcesAsString(shaderLocation);
        } catch (IOException e) {
            throw new UncheckedIOException("Error occurred while loading shader from location " + shaderLocation, e);
        }

        shaderObject = glCreateShader(glShaderType);
        glShaderSource(shaderObject, shaderCode);
        glCompileShader(shaderObject);
        final boolean shaderCompilationFailed = glGetShaderi(shaderObject, GL_COMPILE_STATUS) == 0;
        if (shaderCompilationFailed) {
            String shaderInfoLog = glGetShaderInfoLog(shaderObject);
            throw new ShaderException("Error occurred while compiling vertex shader in location [" + shaderLocation + "], with error : [" + shaderInfoLog + "]");
        }
        return shaderObject;
    }

    private void cleanShaders(final int... shaderObjects) {
        for (int shaderObject : shaderObjects) {
            glDeleteShader(shaderObject);
        }
    }

    public void sendVariableToPipeLine(String variableName, float value) {
        Objects.requireNonNull(variableName, "Shader uniform Variable name should not be null");

        final int fromCpuUniform = glGetUniformLocation(shaderProgramId, variableName);
        glUniform1f(fromCpuUniform, value);
    }

    public void sendValue(String variableName, Vector2f value) {
        Objects.requireNonNull(variableName, "Shader uniform Variable name should not be null");

        final int fromCpuUniform = glGetUniformLocation(shaderProgramId, variableName);
        glUniform2f(fromCpuUniform, value.x(), value.y());
    }

    public void sendValue(String variableName, Vector3f value) {
        Objects.requireNonNull(variableName, "Shader uniform Variable name should not be null");

        final int fromCpuUniform = glGetUniformLocation(shaderProgramId, variableName);
        glUniform3f(fromCpuUniform, value.x(), value.y(), value.z());
    }

    public void sendValue(String variableName, Vector4f value) {
        Objects.requireNonNull(variableName, "Shader uniform Variable name should not be null");

        final int fromCpuUniform = glGetUniformLocation(shaderProgramId, variableName);
        glUniform4f(fromCpuUniform, value.x(), value.y(), value.z(), value.w);
    }

    @Override
    public void update() {
        glUseProgram(shaderProgramId);
    }

    public String getFragmentShaderLocation() {
        return fragmentShaderLocation;
    }

    public void setFragmentShaderLocation(String fragmentShaderLocation) {
        this.fragmentShaderLocation = fragmentShaderLocation;
        shaderProgramId = init(fragmentShaderLocation, vertexShaderLocation);
    }

    public String getVertexShaderLocation() {
        return vertexShaderLocation;
    }

    public void setVertexShaderLocation(String vertexShaderLocation) {
        this.vertexShaderLocation = vertexShaderLocation;
        shaderProgramId = init(fragmentShaderLocation, vertexShaderLocation);
    }

    public int getShaderProgramId() {
        return shaderProgramId;
    }
}