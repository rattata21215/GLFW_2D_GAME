package dev.logan.game.components;

import dev.logan.engine.core.Component;
import dev.logan.engine.core.GameObject;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transform extends Component {

    private Vector2f position;
    private Vector2f scale;

    private int zIndex;

    private float rotation;

    public Transform(GameObject gameObject) {
        super(gameObject);
        this.position = new Vector2f();
        this.scale = new Vector2f();
        this.rotation = 0f;
    }

    public Transform(GameObject gameObject, Vector2f position, float rotation) {
        super(gameObject);
        this.position = position;
        this.scale = new Vector2f();
        this.rotation = rotation;
    }

    public Transform(GameObject gameObject, Vector2f position, Vector2f scale, float rotation) {
        super(gameObject);
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    @Override
    public void start() {}

    @Override
    public void update(float dt) {}

    public int getZIndex() {
        return this.zIndex;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Vector2f getScale() {
        return this.scale;
    }
}
