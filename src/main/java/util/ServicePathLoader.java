package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServicePathLoader {
    static Properties properties = new Properties();

    static {
        try (InputStream inputStream = new FileInputStream("C:\\JavaProjects\\oris\\semesterwork\\memesWebApp\\src\\main\\resources\\service.properties")){
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key){
        return properties.getProperty(key);
    }
}
