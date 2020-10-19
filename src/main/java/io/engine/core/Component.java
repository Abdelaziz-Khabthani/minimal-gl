package io.engine.core;

import io.engine.util.EngineIdUtil;

public abstract class Component {
    public String id;
    private boolean enabled;

    public Component() {
        this.id = EngineIdUtil.generateUniqueId(EngineIdUtil.EngineIdType.COMPONENT);
        this.enabled = true;
    }

    public Component(final boolean enabled) {
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
