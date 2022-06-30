package dev.logan.engine.util;

public final class Time {
    private static final float startTime = System.nanoTime();

    public static float getTime() {
        return (System.nanoTime() - startTime) * 1E-9f;
    }
}
