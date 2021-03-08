package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

    public static Properties loadProperties(String path) {
        try (FileInputStream fs = new FileInputStream(path)) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

