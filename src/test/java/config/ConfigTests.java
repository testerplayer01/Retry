package config;

import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.ConfigParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigTests {

    @Tag("UIHomework")
    @Test
    void firstTest(){
        System.out.println(ConfigParser.getProperty("base.uri"));
//        ConfigParser.getProperty("base.uri");
    }

    @Tag("UIHomework")
    @Test
    void firstPropertiesTest(){
        Properties props = new Properties();
        try(InputStream in = getClass().getClassLoader().getResourceAsStream("configTest.properties")){
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String baseUrl = props.getProperty("base.uri");

        System.out.println(baseUrl);
    }

    @Tag("UIHomework")
    @Test
    void secondParsing(){
        Properties props = new Properties();
        try(FileInputStream in = new FileInputStream("src/test/resources/configTest.properties")){
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String baseUrl = props.getProperty("base.uri");

        System.out.println(baseUrl);
    }

    @Tag("UIHomework")
    @Test
    void envTest(){
        System.out.println(System.getenv("admin_password"));
    }

    @Tag("UIHomework")
    @Test
    void ownerTest(){
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class);

        System.out.println(config.adminLogin());
        System.out.println(config.adminPassword());
        System.out.println(config.baseUrl());
        System.out.println(config.browserSize());
        System.out.println(config.timeout());
    }
}
