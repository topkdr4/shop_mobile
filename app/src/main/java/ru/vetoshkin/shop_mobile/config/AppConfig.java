package ru.vetoshkin.shop_mobile.config;
import java.util.Properties;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class AppConfig {
    private static Properties properties;


    public static void init(Properties properties) {
        AppConfig.properties = properties;
    }


    public static String getServerHost() {
        return properties.getProperty("serverHost");
    }

}
