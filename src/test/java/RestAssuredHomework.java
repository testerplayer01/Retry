import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

@Tag("RestAssuredHomework")
public class RestAssuredHomework {
    private static final String baseURI = "http://localhost:8080";
    private final List<Long> createdProductIds = new ArrayList<>();

    @AfterEach
    void deleteCreatedProducts() {
        if (createdProductIds.isEmpty()) {
            return;
        }

        String username = requiredEnvironmentVariable("GOODS_API_USERNAME");
        String password = requiredEnvironmentVariable("GOODS_API_PASSWORD");

        for (Long productId : createdProductIds) {
            given()
                    .baseUri(baseURI)
                    .auth()
                    .preemptive()
                    .basic(username, password)
            .when()
                    .delete("/goods/{id}", productId)
            .then()
                    .statusCode(200);
        }
    }

    //Задание 1
    @Test
    void givenWhenThen() {
        given()
                .baseUri(baseURI)
                .log().all()
        .when()
                .get("/goods/list")
        .then()
                .log().all()
                .statusCode(200)
                .body("goods", empty());
    }

    @Test
    void requestSpecification() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .build();

        given()
                .spec(requestSpecification)
                .log().all()
        .when()
                .get("/goods/list")
        .then()
                .log().all()
                .statusCode(200)
                .body("goods", empty());
    }

    @Test
    void createdProduct() {
        CreatedProduct product = createProduct();

        given()
                .baseUri(baseURI)
                .log().all()
        .when()
                .get("/goods/list")
        .then()
                .log().all()
                .statusCode(200)
                .body("goods.name", hasItem(product.name()));
    }

    @Test
    void createdProduct2() {
        CreatedProduct product = createProduct();

        List<String> productNames = given()
                .baseUri(baseURI)
                .log().all()
        .when()
                .get("/goods/list")
        .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("goods.name", String.class);

        assertThat(productNames).contains(product.name());
    }

    //Задание 2
    @Test
    void getGoodsList200() {
        given()
                .baseUri(baseURI)
        .when()
                .get("/goods/list")
        .then()
                .statusCode(200)
                .body("goods", notNullValue());
    }

    @Test
    void add200() {
        CreatedProduct product = createProduct();

        assertThat(product.id()).isPositive();
    }

    @Test
    void get200() {
        CreatedProduct product = createProduct();

        authenticatedRequest()
        .when()
                .get("/goods/{id}", product.id())
        .then()
                .statusCode(200)
                .body(containsString(product.name()));
    }

    @Test
    void get404() {
        Response response = authenticatedRequest()
        .when()
                .get("/goods/{id}", Long.MAX_VALUE)
        .then()
                .extract()
                .response();

        assertThat(response.statusCode()).isEqualTo(404);
    }

    @Test
    void add400() {
        Response response = authenticatedJsonRequest("{\"price\":100.0}")
        .when()
                .post("/goods/add")
        .then()
                .extract()
                .response();

        assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    void patch200() {
        CreatedProduct product = createProduct();
        String updatedName = "rest-assured-updated-" + UUID.randomUUID();

        authenticatedJsonRequest("{\"name\":\"" + updatedName + "\",\"price\":100.0}")
        .when()
                .patch("/goods/{id}", product.id())
        .then()
                .statusCode(200);

        authenticatedRequest()
        .when()
                .get("/goods/{id}", product.id())
        .then()
                .statusCode(200)
                .body(containsString(updatedName));
    }

    @Test
    void patch400() {
        CreatedProduct product = createProduct();
        CreatedProduct duplicateNameSource = createProduct();
        String duplicateName = duplicateNameSource.name();

        Response response = authenticatedJsonRequest(
                "{\"name\":\"" + duplicateName + "\",\"price\":100.0}"
        )
        .when()
                .patch("/goods/{id}", product.id())
        .then()
                .extract()
                .response();

        assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    void patch404() {
        Response response = authenticatedJsonRequest(
                "{\"name\":\"missing-" + UUID.randomUUID() + "\",\"price\":100.0}"
        )
        .when()
                .patch("/goods/{id}", Long.MAX_VALUE)
        .then()
                .extract()
                .response();

        assertThat(response.statusCode()).isEqualTo(404);
    }

    @Test
    void delete200() {
        CreatedProduct product = createProduct();

        Response response = authenticatedRequest()
        .when()
                .delete("/goods/{id}", product.id())
        .then()
                .extract()
                .response();
        assertThat(response.statusCode()).isEqualTo(200);
        createdProductIds.remove(product.id());

        authenticatedRequest()
        .when()
                .get("/goods/{id}", product.id())
        .then()
                .statusCode(404);
    }

    @Test
    void delete404() {
        Response response = authenticatedRequest()
        .when()
                .delete("/goods/{id}", Long.MAX_VALUE)
        .then()
                .extract()
                .response();

        assertThat(response.statusCode()).isEqualTo(404);
    }

    private CreatedProduct createProduct() {
        String productName = "rest-assured-" + UUID.randomUUID();

        Response response = authenticatedJsonRequest(
                "{\"name\":\"" + productName + "\",\"price\":100.0}"
        )
        .when()
                .post("/goods/add")
        .then()
                .statusCode(200)
                .extract()
                .response();

        CreatedProduct product = new CreatedProduct(
                response.jsonPath().getLong("data.id"),
                productName
        );
        createdProductIds.add(product.id());

        return product;
    }

    private RequestSpecification authenticatedJsonRequest(String body) {
        return authenticatedRequest()
                .contentType(JSON)
                .body(body);
    }

    private RequestSpecification authenticatedRequest() {
        return given()
                .baseUri(baseURI)
                .auth()
                .preemptive()
                .basic(requiredEnvironmentVariable("GOODS_API_USERNAME"),
                        requiredEnvironmentVariable("GOODS_API_PASSWORD"));
    }

    private String requiredEnvironmentVariable(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Environment variable " + name + " must be set");
        }
        return value;
    }

    private record CreatedProduct(long id, String name) {
    }
}
