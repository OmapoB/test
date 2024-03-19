package ru.omarov.test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ResourceLoader {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(
                    new InputStreamReader(ResourceLoader.class.getResourceAsStream("/app.config"),
                                          StandardCharsets.UTF_8)
            );
        } catch (IOException e) {
            throw new RuntimeException("нет файла конфига", e);
        }
    }

    public static String getProperty(String name) {
        return properties.getProperty(name);
    }

}




















