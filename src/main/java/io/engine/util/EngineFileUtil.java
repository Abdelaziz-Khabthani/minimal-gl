package io.engine.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class EngineFileUtil {

    private EngineFileUtil() {
    }

    public static String getFileFromResourcesAsString(final String fileLocation) throws IOException {
        Objects.requireNonNull(fileLocation, "File location should not be null");
        URL resource = EngineFileUtil.class.getClassLoader().getResource(fileLocation);
        if (resource == null) {
            throw new NullPointerException("No file exists for location " + fileLocation);
        }
        return FileUtils.readFileToString(new File(resource.getFile()), StandardCharsets.UTF_8);
    }
}
