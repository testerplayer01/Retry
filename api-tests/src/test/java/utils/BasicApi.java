package utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

public class BasicApi {
    private RestApiBuilder request() {
        return RestApiBuilder.create();
    }

    private RestApiBuilder adminRequest() {
        return RestApiBuilder.create().withBasicAdminAuth();
    }

    @Step("Получить список товаров")
    public Response getGoodsList() {
        return request().build()
                .when()
                .get("/goods/list?page={page}&size={size}",
                        ConfigParser.getGoodsListPage(),
                        ConfigParser.getGoodsListPageSize())
                .then()
                .extract()
                .response();
    }

    @Step("Создать товар с именем {productName} и ценой {productPrice}")
    public Response createProduct(String productName, double productPrice) {
        return adminRequest().build()
                .contentType("application/json")
                .body(Map.of("name", productName, "price", productPrice))
                .when()
                .post("/goods/add")
                .then()
                .extract()
                .response();
    }

    @Step("Получить информацию о товаре с id {id}")
    public Response getProduct(long id) {
        return adminRequest().build()
                .when()
                .get("/goods/{id}", id)
                .then()
                .extract()
                .response();
    }

    @Step("Обновить товар с id {id}")
    public Response updateProduct(long id, String productName, double productPrice) {
        return adminRequest().build()
                .contentType("application/json")
                .body(Map.of("name", productName, "price", productPrice))
                .when()
                .patch("/goods/{id}", id)
                .then()
                .extract()
                .response();
    }

    @Step("Удалить товар с id {id}")
    public Response deleteProduct(long id) {
        return adminRequest().build()
                .when()
                .delete("/goods/{id}", id)
                .then()
                .extract()
                .response();
    }
}