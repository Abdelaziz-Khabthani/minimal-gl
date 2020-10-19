package io.engine.core;

public abstract class System {
    private boolean enabled;
    private int priority;

    public System(final boolean enabled, final int priority) {
        this.enabled = enabled;
        this.priority = priority;
    }

    public System() {
        enabled = true;
        priority = 0;
    }

    public abstract void init();

    public abstract void update();

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
