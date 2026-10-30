package utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;

public final class AllureRestAssuredFilter {
    private AllureRestAssuredFilter() {
    }

    public static AllureRestAssured create() {
        return new AllureRestAssured();
    }

    public static RequestSpecification requestSpecification(String baseUri) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setConfig(RestAssuredConfig.config()
                        .logConfig(LogConfig.logConfig().blacklistHeader("Authorization")))
                .addFilter(create())
                .build();
    }
}
