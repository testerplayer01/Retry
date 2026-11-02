package utils;

public final class ConfigParser {
    private ConfigParser() {
    }

    public static String getBaseUrl() {
        return org.example.config.ConfigParser.getBaseUrl();
    }

    public static String getBaseUri() {
        return org.example.config.ConfigParser.getBaseUri();
    }

    public static String getAdminLogin() {
        return org.example.config.ConfigParser.getAdminLogin();
    }

    public static String getAdminPassword() {
        return org.example.config.ConfigParser.getAdminPassword();
    }

    public static String getProductName() {
        return org.example.config.ConfigParser.getProductName();
    }

    public static String getProductPrice() {
        return org.example.config.ConfigParser.getProductPrice();
    }

    public static double getProductPriceAsDouble() {
        return org.example.config.ConfigParser.getProductPriceAsDouble();
    }

    public static long getTimeout() {
        return org.example.config.ConfigParser.getTimeout();
    }

    public static String getBrowser() {
        return org.example.config.ConfigProvider.get().browser();
    }

    public static String getBrowserSize() {
        return org.example.config.ConfigProvider.get().browserSize();
    }

    public static boolean isHeadless() {
        return org.example.config.ConfigProvider.get().headless();
    }

    public static String getAPI() {
        return org.example.config.ConfigParser.getAPI();
    }

    public static int getGoodsListPage() {
        return org.example.config.ConfigParser.getGoodsListPage();
    }

    public static int getGoodsListPageSize() {
        return org.example.config.ConfigParser.getGoodsListPageSize();
    }
}
