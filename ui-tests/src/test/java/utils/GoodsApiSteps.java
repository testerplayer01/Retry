package utils;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;
import java.util.OptionalLong;

import static io.restassured.RestAssured.given;

public class GoodsApiSteps {

    private RequestSpecification request() {
        return given()
                .filter(new AllureRestAssured())
                .baseUri(ConfigParser.getBaseUrl())
                .config(RestAssuredConfig.config()
                        .logConfig(LogConfig.logConfig().blacklistHeader("Authorization")));
    }

    @Step("Получить список товаров")
    public Response getGoodsList() {
        return request()
                .when()
                .get("/goods/list")
                .then()
                .extract()
                .response();
    }

    @Step("Найти товар с именем {productName}")
    public OptionalLong findProductIdByName(String productName) {
        Response response = getGoodsList();
        List<Long> ids = response.jsonPath().getList("goods.id", Long.class);
        List<String> names = response.jsonPath().getList("goods.name", String.class);

        for (int index = 0; index < names.size(); index++) {
            if (productName.equals(names.get(index))) {
                return OptionalLong.of(ids.get(index));
            }
        }
        return OptionalLong.empty();
    }

    @Step("Удалить товар с идентификатором {productId}")
    public Response deleteProduct(long productId) {
        return request()
                .auth()
                .preemptive()
                .basic(ConfigParser.getAdminLogin(), ConfigParser.getAdminPassword())
                .when()
                .delete("/goods/{id}", productId)
                .then()
                .extract()
                .response();
    }

    @Step("Восстановить товар с идентификатором {productId}")
    public Response updateProduct(long productId, String name, int price) {
        return request()
                .auth()
                .preemptive()
                .basic(ConfigParser.getAdminLogin(), ConfigParser.getAdminPassword())
                .contentType("application/json")
                .body(Map.of("name", name, "price", price))
                .when()
                .patch("/goods/{id}", productId)
                .then()
                .extract()
                .response();
    }
}
