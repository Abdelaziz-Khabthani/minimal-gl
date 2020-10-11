package io.engine;

import io.engine.util.Color;
import org.apache.commons.io.FileUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 800;
    public static final String WINDOW_TITLE = "Minimal GL";
    public static final int SWAP_INTERVAL = 1;
    public static final int OPEN_GL_VERSION_MAJOR = 4;
    public static final int OPEN_GL_VERSION_MINOR = 6;
    public static final String SHADER_VERTEX_GLSL_FILE_PATH = "shaders/Vertex.glsl";
    public static final long CHANGE_VAO = Duration.ofSeconds(3).toSeconds();
    private static final String FRAGMENT_VERTEX_GLSL_FILE_PATH = "shaders/Fragment.glsl";
    public static final int FLOAT_SIZE_IN_BYTES = Float.SIZE / 8;
    public static final int POSITION_VERTEX_ATTRIBUTE_SIZE = 3;
    public static final int POSITION_VERTEX_ATTRIBUTE_STRIDE_SIZE = POSITION_VERTEX_ATTRIBUTE_SIZE * FLOAT_SIZE_IN_BYTES;
    public static final int POSITION_VERTEX_ATTRIBUTE_LOCATION = 0;
    public static final int GL_RENDER_START_FROM = 0;

    private long window;
    private int vao;
    private int vbo;
    private int ebo;
    private int vertexShader;
    private int fragmentShader;
    private int shaderProgram;
    private String vertexShaderCode;
    private String fragmentShaderCode;

    private float vertices[] = {
            0.5f, 0.5f, 0.0f,       //  Top     Right   Front   0
            0.5f, -0.5f, 0.0f,      //  Bottom  Right   Front   1
            -0.5f, -0.5f, 0.0f,     //  Bottom  Left    Front   2
            -0.5f, 0.5f, 0.0f,      //  Top     Left    Front   3
            0.5f, 0.5f, -0.5f,      //  Top     Right   Back    4
            0.5f, -0.5f, -0.5f,     //  Bottom  Right   Back    5
            -0.5f, -0.5f, -0.5f,    //  Bottom  Left    Back    6
            -0.5f, 0.5f, -0.5f      //  Top     Left    Back    7
    };
    private int indices[] = {
            0, 1, 3,
            1, 2, 3,
            4, 0, 7,
            0, 3, 7,
            4, 5, 7,
            5, 6, 7,
            5, 1, 6,
            1, 2, 6,
            4, 5, 0,
            5, 1, 0,
            7, 6, 2,
            6, 2, 3
    };

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    public void run() throws IOException {
        initGlfw();
        initGl();
        createModel();
        loadShaders();
        loop();
        terminate();
    }

    private void initGlfw() {
        GLFWErrorCallback.createPrint(System.err).set();
        glfwInit();

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, OPEN_GL_VERSION_MAJOR);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, OPEN_GL_VERSION_MINOR);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, NULL, NULL);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(SWAP_INTERVAL);
        glfwShowWindow(window);
    }

    private void initGl() {
        GL.createCapabilities();
        glViewport(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glClearColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha());
    }

    private void createModel() {
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        ebo = glGenBuffers();
        glBindVertexArray(vao);

        // VBO
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        // EBO
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        // Vertex Attribute Configuration
        glVertexAttribPointer(POSITION_VERTEX_ATTRIBUTE_LOCATION, POSITION_VERTEX_ATTRIBUTE_SIZE, GL_FLOAT, false, POSITION_VERTEX_ATTRIBUTE_STRIDE_SIZE, NULL);
        glEnableVertexAttribArray(POSITION_VERTEX_ATTRIBUTE_LOCATION);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private void loadShaders() throws IOException {
        vertexShaderCode = FileUtils.readFileToString(
                new File(
                        getClass()
                                .getClassLoader()
                                .getResource(SHADER_VERTEX_GLSL_FILE_PATH)
                                .getFile()
                ),
                StandardCharsets.UTF_8
        );
        fragmentShaderCode = FileUtils.readFileToString(
                new File(
                        getClass()
                                .getClassLoader()
                                .getResource(FRAGMENT_VERTEX_GLSL_FILE_PATH)
                                .getFile()
                ),
                StandardCharsets.UTF_8
        );
        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderCode);
        glCompileShader(vertexShader);
        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderCode);
        glCompileShader(fragmentShader);
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT);

            glUseProgram(shaderProgram);
            glBindVertexArray(vao);
            glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
            glBindVertexArray(0);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void terminate() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
