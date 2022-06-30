package dev.logan.engine.input;

import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public final class MouseListener {
    private static final boolean[] mouseButtonsCurrent = new boolean[5];
    private static final boolean[] mouseButtonsPrevious = new boolean[5];

    private static Vector2f mousePosCurrent = new Vector2f();
    private static Vector2f mousePosPrevious = new Vector2f();

    private static boolean isDragging = false;

    private MouseListener() {}

    public static void mousePosCallback(long glfwWindow, double mouseX, double mouseY) {
        mousePosPrevious = new Vector2f(mousePosCurrent.x, mousePosCurrent.y);
        mousePosCurrent = new Vector2f((float) mouseX, (float) mouseY);

        isDragging = mouseButtonsCurrent[0] || mouseButtonsCurrent[1] || mouseButtonsCurrent[2] || mouseButtonsCurrent[3] || mouseButtonsCurrent[4];
    }

    public static void mouseButtonCallback(long glfwWindow, int button, int action, int mods) {
        if(button < 5) {
            if(action == GLFW_PRESS) {
                mouseButtonsCurrent[button] = true;
            } else if(action == GLFW_RELEASE) {
                mouseButtonsCurrent[button] = false;
            }
        }
    }

    public static void update() {
        for(int i = 0; i < 5; i++) {
            mouseButtonsPrevious[i] = mouseButtonsCurrent[i];
        }
    }

    public static Vector2f getMousePosition() {
        return mousePosCurrent;
    }

    public static Vector2f getMouseMovement() {
        return new Vector2f(
                mousePosCurrent.x - mousePosPrevious.x,
                mousePosCurrent.y - mousePosPrevious.y
        ).absolute();
    }
}
