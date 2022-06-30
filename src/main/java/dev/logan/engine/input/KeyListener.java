package dev.logan.engine.input;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public final class KeyListener {
    private static final boolean[] keysCurrent = new boolean[350];
    private static final boolean[] keysPrevious = new boolean[350];

    private KeyListener() {}

    public static void keyCallback(long glfwWindow, int key, int scancode, int action, int mods) {
        if(action == GLFW_PRESS) {
            keysCurrent[key] = true;
        } else if(action == GLFW_RELEASE) {
            keysCurrent[key] = false;
        }
    }

    public static boolean KeyPressed(int key) {
        return keysCurrent[key] && !keysPrevious[key];
    }

    public static boolean KeyHeld(int key) {
        return keysCurrent[key];
    }

    public static boolean KeyReleased(int key) {
        return !keysCurrent[key] && keysPrevious[key];
    }

    public static void update() {
        for(int i = 0; i < 350; i++) {
            keysPrevious[i] = keysCurrent[i];
        }
    }
}
