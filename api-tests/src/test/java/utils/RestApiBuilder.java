package utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class RestApiBuilder {
    private RequestSpecification spec;

    private RestApiBuilder() {
        this.spec = given()
                .filter(new AllureRestAssured())
                .baseUri(ConfigParser.getBaseUrl())
                .config(RestAssuredConfig.config()
                        .logConfig(LogConfig.logConfig()
                                .blacklistHeader("Authorization")));
    }

    public static RestApiBuilder create() {
        return new RestApiBuilder();
    }

    public RestApiBuilder withBasicAuth(String username, String password) {
        this.spec = this.spec
                .auth().preemptive().basic(username, password);
        return this;
    }

    public RestApiBuilder withBasicAdminAuth() {
        this.spec = this.spec
                .auth().preemptive().basic(ConfigParser.getAdminLogin(), ConfigParser.getAdminPassword());
        return this;
    }

    public RequestSpecification build() {
        return this.spec;
    }
}
