package dev.logan.engine.core;

import dev.logan.engine.input.KeyListener;
import dev.logan.engine.input.MouseListener;
import dev.logan.engine.util.Time;
import dev.logan.game.scenes.MainMenuScene;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private static Window instance = null;
    private static long glfwWindow;

    private final String TITLE;
    private final int WIDTH;
    private final int HEIGHT;

    private Window() {
        TITLE = "Platformer";
        WIDTH = 1280;
        HEIGHT = 720;
    }

    public static Window getWindow() {
        if(instance == null)
            instance = new Window();

        return instance;
    }

    private static Scene currentScene = null;
    public static void changeScene(String scene) {
        if(currentScene.name.equals(scene))
            return;


    }

    public void run() {
        System.out.println("Hello, LWJGL! (Version: " + Version.getVersion() + ")");

        init();
        loop();
    }

    public void init() {
        // Create the error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if(!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW.");

        // Configure the window
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        // Create the window
        glfwWindow = glfwCreateWindow(WIDTH, HEIGHT, TITLE, NULL, NULL);

        if(glfwWindow == NULL) throw new IllegalStateException("Failed to create the GLFW window.");

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);

        // Set the input callbacks
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Enable V-SYNC
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // Not sure exactly what this does, but it's important. DON'T REMOVE IT!
        GL.createCapabilities();
    }

    public void loop() {
        float frameStart = Time.getTime();
        float frameEnd = 0.0f;
        float dt = -1.0f;

        if(currentScene == null)
            currentScene = new MainMenuScene();

        currentScene.start();

        while(!glfwWindowShouldClose(glfwWindow)) {
            // Polls events (key presses, if the window was closed, etc...)
            glfwPollEvents();

            // Color the entire screen
            glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            // Update the current scene
            if(dt >= 0.0f) currentScene.update(dt);

            // Swap the buffers
            glfwSwapBuffers(glfwWindow);

            // Update the MouseListener and KeyListener
            MouseListener.update();
            KeyListener.update();

            // Update deltaTime
            frameEnd = Time.getTime();
            dt = frameEnd - frameStart;
            frameStart = frameEnd;
        }
    }

    public static int getWidth() {
        return getWindow().WIDTH;
    }

    public static int getHeight() {
        return getWindow().HEIGHT;
    }

    public static String getTitle() {
        return getWindow().TITLE;
    }
}
