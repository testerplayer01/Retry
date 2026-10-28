import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.ConfigParser;

import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class UIHomeworkWeb {
    private String productNameToDelete;

    @BeforeEach
    void setup() {
        ConfigParser.printConfig();
        Configuration.holdBrowserOpen = true;
        Configuration.timeout = ConfigParser.getTimeout();
        Selenide.open(ConfigParser.getBaseUrl());
        productNameToDelete = null;
    }

    @Tag("UIHomework")
    @Test
    void task1() {
        SelenideElement addToCartButton = $x("//*[@data-action='add-to-cart']");
        addToCartButton.click();
        addToCartButton.click();
        addToCartButton.click();
        $("#open-cart-btn").click();
        int totalPrice = Integer.parseInt($("#total-price").getText().replaceAll("\\D+", "")
                .trim());
        assertThat(totalPrice).as("Сумма в корзине не должна быть выше 300")
                .isLessThanOrEqualTo(300);
        System.out.println("Сумма: " + totalPrice);
        $("#makeOrder").click();

        $x("//*[contains(text(), 'Заказ принят в обработку!')]")
                .shouldBe(visible)
                .shouldHave(text("Заказ принят в обработку!"));
        $x("//*[@id='main-title']")
                .shouldBe(visible)
                .shouldHave(text("Заказ успешно оформлен!"));

    }

    @Tag("UIHomework")
    @Test
    void task2() {
        int expectedTotalPrice = 0;
        for (int productIndex : new int[]{0, 2, 3}) {
            SelenideElement addToCartButton = $$x("//*[@data-action='add-to-cart']").get(productIndex);
            expectedTotalPrice += Integer.parseInt(addToCartButton.getAttribute("data-price"));
            addToCartButton.click();
        }

        $x("//*[@id='open-cart-btn']").click();

        int totalPrice = Integer.parseInt($x("//*[@id='total-price']")
                .getText().replaceAll("\\D+", "")
                .trim());
        assertThat(totalPrice).as("Сумма в корзине должна совпадать с суммой цен добавленных товаров")
                .isEqualTo(expectedTotalPrice);
        System.out.println("Общая сумма: " + totalPrice);
    }

    @Tag("UIHomework")
    @Test
    void task3() {
        $x("//*[@class='btn-outline']").click();

        $x("//*[@id='username']").setValue(ConfigParser.getAdminLogin());
        $x("//*[@id='password']").setValue(ConfigParser.getAdminPassword());
        $x("//*[@type='submit']").click();

        String name = ConfigParser.getProductName() +" " + new Random().nextInt(1, 999);
        String price = ConfigParser.getProductPrice();

        $x("//*[@id='n-name']").setValue(name);
        $x("//*[@id='n-price']").setValue(price);
        $x("//*[@class='btn btn-add']").click();

        $x("//*[contains(text(), 'Товар успешно добавлен!')]")
                .shouldBe(visible)
                .shouldHave(text("Товар успешно добавлен!"));
        productNameToDelete = name;
    }

    @Tag("UIHomework")
    @Test
    void task4() {
        $x("//*[@class='btn-outline']").click();

        $x("//*[@id='username']").setValue("admin");
        $x("//*[@id='password']").setValue("secret123");
        $x("//*[@type='submit']").click();

        String newName = new Random().nextInt(1, 999) + "Товар ";

        $x("//*[@id='tbody']/tr[1]/td[2]//input[@type='text']").setValue(newName);
        $x("//*[@id='tbody']/tr[1]/td[4]//*[@class='btn btn-upd']").click();


        $x("/html/body/div[2]/div[1]/a").click();
        sleep(4000);

        $x("//h4[contains(text(), '" + newName + "')]").shouldBe(visible);
    }

    @AfterEach
    void tearDown() {
        if (productNameToDelete != null) {
            try {
                Response response = given()
                        .baseUri("http://localhost:8080")
                        .auth().preemptive().basic("admin", "secret123")
                        .log().all()
                        .when()
                        .get("/goods/list")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .response();

                List<Long> productIds = response.jsonPath().getList("goods.id", Long.class);
                List<String> productNames = response.jsonPath().getList("goods.name", String.class);
                System.out.println("Товары: " + productNames);
                System.out.println("Ищем: " + productNameToDelete);

                for (int i = 0; i < productNames.size(); i++) {
                    if (productNameToDelete.equals(productNames.get(i))) {
                        System.out.println("Удаление товара ID: " + productIds.get(i));
                        given()
                                .baseUri(ConfigParser.getBaseUrl())
                                .auth().preemptive().basic(ConfigParser.getAdminLogin(), ConfigParser.getAdminPassword())
                                .log().all()
                                .when()
                                .delete("/goods/{id}", productIds.get(i))
                                .then()
                                .log().all()
                                .statusCode(200);
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка при удалении: " + e.getMessage());
            }
            productNameToDelete = null;
        }
        Selenide.closeWebDriver();

        for (int i = 1; i < 150; i++) {
            given()
                    .baseUri("http://localhost:8080")
                    .auth().preemptive().basic("admin", "secret123")
                    .contentType(ContentType.JSON)
                    .log().all()
                    .pathParam("id", i)
                    .when()
                    .delete("/goods/{id}")
                    .then()
                    .log().all()
                    .extract().response();
        }
    }
}

