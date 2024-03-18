package ru.omarov.test;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ResourceLoader {
    private static final Properties properties = new Properties();

    public static Properties getProperties() {
        try {
            properties.load(
                    new InputStreamReader(ResourceLoader.class.getResourceAsStream("/app.config"),
                                          StandardCharsets.UTF_8)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

}
