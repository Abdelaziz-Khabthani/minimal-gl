package io.engine;

import io.engine.component.ShaderComponent;
import io.engine.core.render.Color;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.io.IOException;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    private long window;
    private int vao;
    private int vbo;
    private int ebo;
    private float vertices[] = {
            0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f,        //  Top     Right   Front   0
            0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f,       //  Bottom  Right   Front   1
            -0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 1.0f,      //  Bottom  Left    Front   2
            -0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f,       //  Top     Left    Front   3
    };
    private int indices[] = {
            0, 1, 3,
            1, 2, 3,
    };
    private ShaderComponent shaderComponent;

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
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        window = glfwCreateWindow(800, 800, "Minimal", NULL, NULL);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    private void initGl() {
        GL.createCapabilities();
        glViewport(0, 0, 800, 800);
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glClearColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha());
    }

    private void createModel() {
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        ebo = glGenBuffers();

        // VAO
        glBindVertexArray(vao);

        // VBO
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        // EBO
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        // Vertex Attribute Configuration aPos
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * (Float.SIZE / 8), 0);
        glEnableVertexAttribArray(0);

        // Vertex Attribute Configuration aCol
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * (Float.SIZE / 8), 3 * (Float.SIZE / 8));
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private void loadShaders() {
        shaderComponent = new ShaderComponent("shaders/Vertex.glsl", "shaders/Fragment.glsl");
    }

    private void loop() {
        // Vertex Class
        // Mesh Component Class
        // Entity Object Class
        // EntityFactory Class
        // System classes
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT);
            shaderComponent.update();

            double time = glfwGetTime();
            float sin = (float) ((Math.cos(time) + 1) / 2);

            shaderComponent.sendValue("fromCpu", new Vector4f(Color.GREEN.getRed(), 1f, Color.GREEN.getBlue(), 1.0f));

            glBindVertexArray(vao);
            //glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
            glDrawArrays(GL_PATCHES , 0, vertices.length);
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
