package io.engine.core;

public abstract class Component {
    private boolean enabled;
    private int priority;

    public Component() {
        this.enabled = true;
        this.priority = 0;
    }

    public abstract void update();

    public Component(boolean enabled, int priority) {
        this.enabled = enabled;
        this.priority = priority;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
