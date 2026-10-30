import io.qameta.allure.Step;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.AllureRestAssuredFilter;
import utils.ConfigParser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@Tag("RestAssuredHomework")
@Epic("Каталог товаров")
@Feature("REST API")
public class RestAssuredHomework {
    private static final String BASE_URI = "http://localhost:8080";
    private final List<Long> createdProductIds = new ArrayList<>();
    private final RestAssuredHomeworkCheck checks = new RestAssuredHomeworkCheck();

    @AfterEach
    void deleteCreatedProducts() {
        for (Long productId : new ArrayList<>(createdProductIds)) {
            Response response = deleteProduct(productId);
            checks.assertStatusCode(response, 200);
        }
        createdProductIds.clear();
    }

    @Test
    void givenWhenThen() {
        checks.assertGoodsListAvailable(getGoodsList());
    }

    @Test
    void requestSpecification() {
        checks.assertGoodsListAvailable(getGoodsListWithSpecification());
    }

    @Test
    void createdProduct() {
        CreatedProduct product = createProduct();

        checks.assertProductNamePresent(
                getGoodsList().jsonPath().getList("goods.name", String.class),
                product.name()
        );
    }

    @Test
    void createdProduct2() {
        CreatedProduct product = createProduct();
        checks.assertProductNamePresent(
                getGoodsListNames(),
                product.name()
        );
    }

    @Test
    void getGoodsList200() {
        checks.assertGoodsListAvailable(getGoodsList());
    }

    @Test
    void add200() {
        checks.assertProductCreated(createProduct());
    }

    @Test
    void get200() {
        CreatedProduct product = createProduct();
        checks.assertProductResponse(getProduct(product.id()), product.name());
    }

    @Test
    void get404() {
        checks.assertStatusCode(getProduct(Long.MAX_VALUE), 404);
    }

    @Test
    void add400() {
        checks.assertStatusCode(addProductWithoutName(), 400);
    }

    @Test
    void patch200() {
        CreatedProduct product = createProduct();
        String updatedName = "rest-assured-updated-" + UUID.randomUUID();

        checks.assertStatusCode(updateProduct(product.id(), updatedName), 200);
        checks.assertProductResponse(getProduct(product.id()), updatedName);
    }

    @Test
    void patch400() {
        CreatedProduct product = createProduct();
        CreatedProduct duplicateNameSource = createProduct();

        checks.assertStatusCode(
                updateProduct(product.id(), duplicateNameSource.name()),
                400
        );
    }

    @Test
    void patch404() {
        String missingName = "missing-" + UUID.randomUUID();
        checks.assertStatusCode(updateProduct(Long.MAX_VALUE, missingName), 404);
    }

    @Test
    void delete200() {
        CreatedProduct product = createProduct();

        checks.assertStatusCode(deleteProduct(product.id()), 200);
        createdProductIds.remove(product.id());
        checks.assertStatusCode(getProduct(product.id()), 404);
    }

    @Test
    void delete404() {
        checks.assertStatusCode(deleteProduct(Long.MAX_VALUE), 404);
    }

    @Step("Получить список товаров")
    private Response getGoodsList() {
        return given()
                .spec(buildRequestSpecification())
                .when()
                .get("/goods/list")
                .then()
                .extract()
                .response();
    }

    @Step("Получить список товаров с отдельной спецификацией запроса")
    private Response getGoodsListWithSpecification() {
        RequestSpecification specification =
                AllureRestAssuredFilter.requestSpecification(BASE_URI);

        return given()
                .spec(specification)
                .when()
                .get("/goods/list")
                .then()
                .extract()
                .response();
    }

    @Step("Получить список имён товаров")
    private List<String> getGoodsListNames() {
        return getGoodsList().jsonPath().getList("goods.name", String.class);
    }

    @Step("Получить товар с идентификатором {productId}")
    private Response getProduct(long productId) {
        return authenticatedRequest()
                .when()
                .get("/goods/{id}", productId)
                .then()
                .extract()
                .response();
    }

    @Step("Создать товар")
    private CreatedProduct createProduct() {
        String productName = "rest-assured-" + UUID.randomUUID();
        Response response = authenticatedJsonRequest(
                "{\"name\":\"" + productName + "\",\"price\":100.0}"
        )
                .when()
                .post("/goods/add")
                .then()
                .extract()
                .response();

        checks.assertStatusCode(response, 200);
        long productId = response.jsonPath().getLong("data.id");
        CreatedProduct product = new CreatedProduct(productId, productName);
        createdProductIds.add(productId);
        checks.assertProductCreated(product);
        return product;
    }

    @Step("Создать товар без имени")
    private Response addProductWithoutName() {
        return authenticatedJsonRequest("{\"price\":100.0}")
                .when()
                .post("/goods/add")
                .then()
                .extract()
                .response();
    }

    @Step("Изменить имя товара с идентификатором {productId}")
    private Response updateProduct(long productId, String updatedName) {
        return authenticatedJsonRequest(
                "{\"name\":\"" + updatedName + "\",\"price\":100.0}"
        )
                .when()
                .patch("/goods/{id}", productId)
                .then()
                .extract()
                .response();
    }

    @Step("Удалить товар с идентификатором {productId}")
    private Response deleteProduct(long productId) {
        return authenticatedRequest()
                .when()
                .delete("/goods/{id}", productId)
                .then()
                .extract()
                .response();
    }

    private RequestSpecification buildRequestSpecification() {
        return AllureRestAssuredFilter.requestSpecification(BASE_URI);
    }

    private RequestSpecification authenticatedJsonRequest(String body) {
        return authenticatedRequest()
                .contentType(JSON)
                .body(body);
    }

    private RequestSpecification authenticatedRequest() {
        return given()
                .spec(buildRequestSpecification())
                .auth()
                .preemptive()
                .basic(
                        ConfigParser.getAdminLogin(),
                        ConfigParser.getAdminPassword()
                );
    }

    record CreatedProduct(long id, String name) {
    }
}
