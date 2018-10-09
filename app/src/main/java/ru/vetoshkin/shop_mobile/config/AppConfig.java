package ru.vetoshkin.shop_mobile.config;
import java.util.Properties;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class AppConfig {
    public static final String APP_CONFIG = "app_config";
    public static final String FAVORITES  = "favorite";
    public static final String SESSION_KEY = "SESSION";
    private static Properties properties;


    public static void init(Properties properties) {
        AppConfig.properties = properties;
    }


    public static String getServerHost() {
        return properties.getProperty("serverHost");
    }

}
