package io.engine.core;

import io.engine.exception.EntityException;
import io.engine.util.EngineIdUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Entity {
    private final String id;
    private String name;
    private boolean enabled;
    private final Map<String, Component> components = new HashMap<>();

    public Entity(String name) {
        this.id = EngineIdUtil.generateUniqueId(EngineIdUtil.EngineIdType.ENTITY);
        this.name = name;
        this.enabled = true;
    }

    public List<Component> getComponents() {
        return new ArrayList<>(components.values());
    }

    public void addComponent(final Component component) {
        Objects.requireNonNull(component, "Component should not be null");
        Objects.requireNonNull(component.getId(), "Component ID should not be null");

        if (this.components.get(component.getId()) != null) {
            throw new EntityException("Component with the ID [" + component.getId() + "] and the type [" + component.getClass().getTypeName() + "] already exists in the Entity with the ID [" + id + "] and the name [" + name + "]");
        }

        this.components.put(component.getId(), component);
    }

    public void addComponents(List<Component> components) {
        components.forEach(this::addComponent);
    }

    public List<Component> getComponents(final Class<?> componentType) {
        Objects.requireNonNull(componentType, "Component type should not be null");

        return getComponents()
                .stream()
                .filter(component -> componentType.isAssignableFrom(component.getClass()))
                .collect(Collectors.toList());
    }

    public Component getComponent(final String id) {
        Objects.requireNonNull(id, "Component ID should not be null");
        return this.components.get(id);
    }

    public void removeComponent(final Class<?> componentType) {
        Objects.requireNonNull(componentType, "Component type should not e null");

        List<Component> componentsToRemove = getComponents(componentType);
        componentsToRemove.forEach(component -> components.remove(component.getId()));
    }

    public void removeComponent(final String id) {
        Objects.requireNonNull(id, "Component ID should not be null");
        this.components.remove(id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Entity setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Entity setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
