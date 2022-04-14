package config;

import config.util.PropertyListenerUtil;

public class BaseConfig {

    private static final String configPath = "src/main/resources/petStore.properties";

    public static final String BASE_URL = getProperty("base.url");
    public static final String PET_URL = getProperty("pet.url");

    private static String getProperty(final String param) {
        return PropertyListenerUtil.getPropertyFromFile(configPath, param);
    }

}
