package config;

import org.aeonbits.owner.Config;
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:configTest.properties",
        "system:properties",
        "system:env"
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
    String browserSize();

    @Key("timeout")
    String timeout();
}
