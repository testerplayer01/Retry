package org.example.config;

import org.aeonbits.owner.Config;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
            "system:properties",
            "system:env",
            "classpath:config.properties"
    })
    public interface ProjectConfig extends Config {

        @Key("base.uri")
        String baseUrl();

        @Key("admin.login")
        String adminLogin();

        @Key("admin.password")
        String adminPassword();

        @Key("browser")
        @DefaultValue("chrome")
        String browser();

        @Key("browserSize")
        @DefaultValue("1980x1020")
        String browserSize();

        @Key("browser.headless")
        @DefaultValue("false")
        boolean headless();

        @Key("timeout")
        @DefaultValue("6000")
        long timeout();

        @Key("newProduct.name")
        @DefaultValue("Товар")
        String productName();

        @Key("newProduct.price")
        @DefaultValue("100")
        int productPrice();

        @Key("order.total.limit")
        @DefaultValue("300")
        int orderTotalLimit();

        @Key("api.goods-list.page")
        @DefaultValue("0")
        int goodsListPage();

        @Key("api.goods-list.page-size")
        @DefaultValue("100")
        int goodsListPageSize();
    }
