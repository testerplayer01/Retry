package utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;
import java.util.OptionalLong;

import static io.restassured.RestAssured.given;

public class GoodsApiSteps {

    @Step("Получить список товаров")
    public Response getGoodsList() {
        return given()
                .spec(AllureRestAssuredFilter.requestSpecification(ConfigParser.getBaseUrl()))
                .when()
                .get(ConfigParser.getAPI())
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
        return given()
                .spec(AllureRestAssuredFilter.requestSpecification(ConfigParser.getBaseUrl()))
                .auth()
                .preemptive()
                .basic(ConfigParser.getAdminLogin(), ConfigParser.getAdminPassword())
                .when()
                .delete("/goods/{id}", productId)
                .then()
                .extract()
                .response();
    }
}
