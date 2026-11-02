import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.AllureSelenideListener;
import utils.ConfigParser;
import utils.GoodsApiSteps;
import utils.RestAssuredHomeworkCheck;
import utils.SelenideSteps;

import java.util.OptionalLong;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Каталог товаров")
@Feature("Сценарии UI и очистка через API")
public class UIHomeworkWeb extends AllureSelenideListener {
    private final GoodsApiSteps goodsApi = new GoodsApiSteps();
    private final RestAssuredHomeworkCheck apiChecks = new RestAssuredHomeworkCheck();
    private Long productIdToDelete;

    @BeforeEach
    void setup() {
        Configuration.browser = ConfigParser.getBrowser();
        Configuration.browserSize = ConfigParser.getBrowserSize();
        Configuration.headless = ConfigParser.isHeadless();
        Configuration.timeout = ConfigParser.getTimeout();
        SelenideSteps.open(ConfigParser.getBaseUrl());
        productIdToDelete = null;
    }

    @Tag("UIHomework")
    @Test
    void task1() {
        for (int i = 0; i < 3; i++) {
            SelenideSteps.click("//*[@data-action='add-to-cart']");
        }
        SelenideSteps.click("#open-cart-btn");

        int totalPrice = readTotalPrice();
        assertTotalPriceDoesNotExceed(totalPrice, 300);
        SelenideSteps.click("#makeOrder");
        SelenideSteps.shouldHaveText(
                "//*[contains(text(), 'Заказ принят в обработку!')]",
                "Заказ принят в обработку!"
        );
        SelenideSteps.shouldHaveText(
                "//*[@id='main-title']",
                "Заказ успешно оформлен!"
        );
    }

    @Tag("UIHomework")
    @Test
    void task2() {
        int expectedTotalPrice = 0;
        for (int productIndex : new int[]{0, 2, 3}) {
            String selector = "//*[@data-action='add-to-cart']";
            expectedTotalPrice += Integer.parseInt(
                    SelenideSteps.getAttribute(
                            "(" + selector + ")[" + (productIndex + 1) + "]",
                            "data-price"
                    )
            );
            SelenideSteps.addProductToCart(productIndex);
        }

        SelenideSteps.click("//*[@id='open-cart-btn']");
        assertTotalPriceEquals(readTotalPrice(), expectedTotalPrice);
    }

    @Tag("UIHomework")
    @Test
    void task3() {
        openAdminAndLogin();
        String name = ConfigParser.getProductName() + " " + new Random().nextInt(1, 999);
        SelenideSteps.setValue("//*[@id='n-name']", name);
        SelenideSteps.setValue("//*[@id='n-price']", ConfigParser.getProductPrice());
        SelenideSteps.click("//*[@class='btn btn-add']");
        SelenideSteps.shouldHaveText(
                "//*[contains(text(), 'Товар успешно добавлен!')]",
                "Товар успешно добавлен!"
        );
        SelenideSteps.click("/html/body/div[2]/div[1]/a");
        productIdToDelete = Long.parseLong(SelenideSteps.getAttribute(
                "//*[@data-action='add-to-cart' and @data-name='" + name + "']",
                "data-id"
        ));
    }

    @Tag("UIHomework")
    @Test
    void task4() {
        openAdminAndLogin();
        String newName = new Random().nextInt(1, 999) + "Товар ";
        SelenideSteps.setValue(
                "//*[@id='tbody']/tr[1]/td[2]//input[@type='text']",
                newName
        );
        SelenideSteps.click("//*[@id='tbody']/tr[1]/td[4]//*[@class='btn btn-upd']");
        SelenideSteps.click("/html/body/div[2]/div[1]/a");
        SelenideSteps.shouldBeVisible("//h4[contains(text(), '" + newName + "')]");
    }

    @Step("Открыть административную панель и войти")
    private void openAdminAndLogin() {
        SelenideSteps.click("//*[@class='btn-outline']");
        SelenideSteps.enterAdminUsername("//*[@id='username']");
        SelenideSteps.enterAdminPassword("//*[@id='password']");
        SelenideSteps.click("//*[@type='submit']");
    }

    @Step("Получить отображаемую сумму корзины")
    private int readTotalPrice() {
        String priceText = SelenideSteps.getText("//*[@id='total-price']");
        return Integer.parseInt(priceText.replaceAll("\\D+", "").trim());
    }

    @Step("Проверить, что сумма корзины не превышает {limit}")
    private void assertTotalPriceDoesNotExceed(int totalPrice, int limit) {
        assertThat(totalPrice)
                .as("Сумма в корзине не должна быть выше " + limit)
                .isLessThanOrEqualTo(limit);
    }

    @Step("Проверить, что сумма корзины равна ожидаемой")
    private void assertTotalPriceEquals(int actualPrice, int expectedPrice) {
        assertThat(actualPrice)
                .as("Сумма корзины должна совпадать с суммой выбранных товаров")
                .isEqualTo(expectedPrice);
    }

    @Step("Удалить созданный тестом товар")
    private void deleteCreatedProduct() {
        Response response = goodsApi.deleteProduct(productIdToDelete);
        apiChecks.assertStatusCode(response, 200);
        productIdToDelete = null;
    }

    @AfterEach
    void tearDown() {
        try {
            if (productIdToDelete != null) {
                deleteCreatedProduct();
            }
        } finally {
            Selenide.closeWebDriver();
        }
    }
}
