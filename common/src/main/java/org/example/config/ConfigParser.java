package org.example.config;

public final class ConfigParser {
    private static final ProjectConfig CONFIG = ConfigProvider.get();

    private ConfigParser() {
    }

    public static String getBaseUrl() {
        return CONFIG.baseUrl();
    }

    public static String getBaseUri() {
        return CONFIG.baseUrl();
    }

    public static String getAdminLogin() {
        return CONFIG.adminLogin();
    }

    public static String getAdminPassword() {
        return CONFIG.adminPassword();
    }

    public static String getProductName() {
        return CONFIG.productName();
    }

    public static String getProductPrice() {
        return Integer.toString(CONFIG.productPrice());
    }

    public static double getProductPriceAsDouble() {
        return CONFIG.productPrice();
    }

    public static long getTimeout() {
        return CONFIG.timeout();
    }

    public static String getAPI() {
        return "/goods/list";
    }

    public static int getGoodsListPage() {
        return CONFIG.goodsListPage();
    }

    public static int getGoodsListPageSize() {
        return CONFIG.goodsListPageSize();
    }

    public static void printConfig() {
        System.out.println("URL: " + getBaseUrl());
        System.out.println("Product Name: " + getProductName());
        System.out.println("Product Price: " + getProductPrice());
        System.out.println("Timeout: " + getTimeout());
        System.out.println("Login and Password are hidden");
    }
}
