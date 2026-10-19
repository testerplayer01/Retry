import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class UIHomeworkWeb {
    private String productNameToDelete;

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        Selenide.open("http://localhost:8080/");
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
        $$x("//*[@data-action='add-to-cart']").get(0).click();
        $$x("//*[@data-action='add-to-cart']").get(2).click();
        $$x("//*[@data-action='add-to-cart']").get(3).click();

        $x("//*[@id='open-cart-btn']").click();

        int totalPrice = Integer.parseInt($x("//*[@id='total-price']")
                .getText().replaceAll("\\D+", "")
                .trim());
        System.out.println("Общая сумма: " + totalPrice);
    }

    @Tag("UIHomework")
    @Test
    void task3() {
        $x("//*[@class='btn-outline']").click();

        $x("//*[@id='username']").setValue("admin");
        $x("//*[@id='password']").setValue("secret123");
        $x("//*[@type='submit']").click();

        String name = "Товар " + new Random().nextInt(1, 999);
        String price = String.valueOf(new Random().nextInt(10, 200));

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
                                .baseUri("http://localhost:8080")
                                .auth().preemptive().basic("admin", "secret123")
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
    }
}

