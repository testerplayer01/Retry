package utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigParser {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigParser.class.getClassLoader()
                .getResourceAsStream("configTest.properties")) {
            if (input == null) {
                throw new RuntimeException("configTest.properties не найден");
            }
            properties.load(new java.io.InputStreamReader(input, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки конфига", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    public static String getBaseUrl() {
        return getProperty("base.uri");
    }
    public static String getAdminLogin() {
        return getProperty("admin.login");
    }
    public static String getAdminPassword() {
        return getProperty("admin.password");
    }
    public static String getProductName() {
        return getProperty("newProduct.name");
    }
    public static String getProductPrice() {
        return getProperty("newProduct.price");
    }
    public static String getLog() {
        return getProperty("logging");
    }
    public static long getTimeout() {
        return Long.parseLong(getProperty("timeout"));
    }
    public static String getAPI(){
        return getProperty("APIget");
    }
    public static void printConfig(){
        System.out.println("URL: " + getBaseUrl());
        System.out.println("Product Name: " + getProductName());
        System.out.println("Product Price: " + getProductPrice());
        System.out.println("Timeout: " + getTimeout());
        System.out.println("Logging: " + getLog());
        System.out.println("API: " + getAPI());
        System.out.println("Login and Password are hidden");

    }





}
