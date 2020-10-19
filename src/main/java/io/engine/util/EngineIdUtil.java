package io.engine.util;

import java.util.Objects;
import java.util.UUID;

public final class EngineIdUtil {
    private static String SEPARATOR = ":";

    private EngineIdUtil() {
    }

    public static String generateUniqueId(final EngineIdType type) {
        Objects.requireNonNull(type, "ID type should not be null");
        final UUID uuid = UUID.randomUUID();
        return type.toString() + SEPARATOR + uuid.toString();
    }

    public enum EngineIdType {
        ENTITY, COMPONENT
    }
}
