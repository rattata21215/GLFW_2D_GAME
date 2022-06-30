package dev.logan.engine.core;

import java.util.ArrayList;

public abstract class Scene {

    public final String name;

    public Scene(String name) {this.name = name; this.gameObjects = new ArrayList<>(); }

    private ArrayList<GameObject> gameObjects;

    public void start() {
        for(GameObject gameObject : gameObjects) {
            gameObject.start();

            for(Component component : gameObject.getAllComponents()) {
                component.start();
            }
        }
    }

    public void update(final float dt) {
        for(GameObject gameObject : gameObjects) {
            gameObject.update(dt);

            for(Component component : gameObject.getAllComponents()) {
                component.update(dt);
            }
        }
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        gameObjects.sort(GameObject::sortByZIndex);
    }
}
