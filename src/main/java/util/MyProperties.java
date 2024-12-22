package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyProperties {
    private final Map<String, String> properties = new HashMap<>();

    public void load(FileInputStream fileInputStream) throws IOException {
        byte[] buffer = fileInputStream.readAllBytes();
        String content = new String(buffer);
        String[] lines = content.split("\n");

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            String[] keyValue = line.split("=", 2);
            if (keyValue.length == 2) {
                properties.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }
}
