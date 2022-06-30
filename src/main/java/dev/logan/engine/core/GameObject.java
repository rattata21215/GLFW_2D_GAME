package dev.logan.engine.core;

import dev.logan.game.components.Transform;

import java.util.ArrayList;

public abstract class GameObject {
    public final String name;
    public final int ID;
    private static int NEXT_ID = 1;

    private ArrayList<Component> components;

    public GameObject(String name) {
        this.name = name;
        this.ID = NEXT_ID;

        this.components = new ArrayList<>();

        NEXT_ID++;
    }

    public GameObject() {
        this.name = "Unnamed (ID: " + NEXT_ID + ")";
        this.ID = NEXT_ID;
        NEXT_ID++;

        this.components = new ArrayList<>();
    }

    public abstract void start();
    public abstract void update(final float dt);

    public ArrayList<Component> getAllComponents() {
        return this.components;
    }

    public void addComponent(Component c) {
        this.components.add(c);
    }

    public <T extends Component> T getComponent(Class<T> requested) {
        for(Component index : components) {
            if(requested.isAssignableFrom(index.getClass())) {
                return requested.cast(index);
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent(Class<T> requested) {
        for(Component index : components) {
            if(requested.isAssignableFrom(index.getClass())) {
                components.remove(index);
                return;
            }
        }
    }

    public static int sortByZIndex(GameObject a, GameObject b) {
        int z1 = a.getComponent(Transform.class).getZIndex();
        int z2 = b.getComponent(Transform.class).getZIndex();
        return (z1 - z2) / (z1 - Math.abs(z2));
    }
}
