package config.util;


import lombok.experimental.UtilityClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@UtilityClass
public class PropertyListenerUtil {

    public static String getPropertyFromFile(final String path, final String param) {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return properties.getProperty(param);
    }

}
