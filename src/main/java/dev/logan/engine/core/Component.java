package dev.logan.engine.core;

public abstract class Component {
    public final GameObject gameObject;
    public Component(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public abstract void start();
    public abstract void update(final float dt);
}
