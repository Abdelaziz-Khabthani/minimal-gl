package io.engine.core;

import java.util.*;

public class EntityFactory {
    private static EntityFactory instance;
    private final Map<String, Entity> entities = new HashMap<>();

    private EntityFactory() {
        if (instance != null) {
            throw new IllegalStateException("Instantiation of singleton object is forbidden");
        }
    }

    public static EntityFactory getInstance() {
        if (instance == null) {
            instance = new EntityFactory();
        }

        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new IllegalStateException("Cloning of singleton object is forbidden");
    }

    public Entity addEntity(final String name, final List<Component> components) {
        Entity entity = new Entity(name);
        entity.addComponents(components);
        entities.put(entity.getId(), entity);

        return entity;
    }

    public void removeEntity(final String id) {
        Objects.requireNonNull(id, "Entity ID should not be null");

        entities.remove(id);
    }

    public void removeEntity(final Entity entity) {
        Objects.requireNonNull(entity, "Entity should not be null");
        Objects.requireNonNull(entity.getId(), "Entity ID should not be null");

        removeEntity(entity.getId());
    }

    public List<Entity> getEntities() {
        return new ArrayList<>(entities.values());
    }
}
