import io.qameta.allure.Step;
import io.qameta.allure.Param;
import io.qameta.allure.model.Parameter;
import io.restassured.response.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class RestAssuredHomeworkCheck {

    @Step("Проверить статус ответа: ожидается {expectedStatus}")
    public void assertStatusCode(
            @Param(mode = Parameter.Mode.HIDDEN) Response response,
            int expectedStatus
    ) {
        assertThat(response.statusCode()).isEqualTo(expectedStatus);
    }

    @Step("Проверить, что список товаров получен")
    public void assertGoodsListAvailable(
            @Param(mode = Parameter.Mode.HIDDEN) Response response
    ) {
        assertStatusCode(response, 200);
        response.then().body("goods", notNullValue());
    }

    @Step("Проверить, что товар создан")
    public void assertProductCreated(
            @Param(mode = Parameter.Mode.HIDDEN) RestAssuredHomework.CreatedProduct product
    ) {
        assertThat(product.id()).isPositive();
    }

    @Step("Проверить, что ответ содержит товар {expectedName}")
    public void assertProductResponse(
            @Param(mode = Parameter.Mode.HIDDEN) Response response,
            String expectedName
    ) {
        assertStatusCode(response, 200);
        assertThat(response.asString()).contains(expectedName);
    }

    @Step("Проверить, что список содержит товар {expectedName}")
    public void assertProductNamePresent(
            @Param(mode = Parameter.Mode.HIDDEN) List<String> productNames,
            String expectedName
    ) {
        assertThat(productNames).contains(expectedName);
    }
}
