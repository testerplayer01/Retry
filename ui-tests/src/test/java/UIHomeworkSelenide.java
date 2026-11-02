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

import java.util.Random;

@Epic("Каталог товаров")
@Feature("Пользовательский интерфейс через Selenide")
public class UIHomeworkSelenide extends AllureSelenideListener {
    private final GoodsApiSteps goodsApi = new GoodsApiSteps();
    private final RestAssuredHomeworkCheck apiChecks = new RestAssuredHomeworkCheck();
    private Long productIdToDelete;

    @BeforeEach
    void setup() {
        SelenideSteps.open(ConfigParser.getBaseUrl());
    }

    @Tag("Selenide")
    @Test
    void task1() {
        String name = "Товар " + new Random().nextInt(1, 999);
        String price = String.valueOf(new Random().nextInt(10, 200));

        addProductThroughAdmin(name, price);
        SelenideSteps.click("/html/body/div[2]/div[1]/a");
        productIdToDelete = getProductIdByName(name);
        SelenideSteps.shouldBeVisible(
                "//*[@class='product-card']/h4[contains(text(), '" + name + "')]"
        );
    }

    @Tag("Selenide")
    @Test
    void task2() {
        SelenideSteps.click("//*[@data-action='add-to-cart']");
        SelenideSteps.click("//*[@id='open-cart-btn']");
        SelenideSteps.shouldBeVisible("//*[@class='cart-item']");
    }

    @Tag("Selenide")
    @Test
    void task3() {
        SelenideSteps.click("//*[@class='btn-outline']");
        SelenideSteps.enterInvalidAdminUsername("//*[@id='username']");
        SelenideSteps.enterAdminPassword("//*[@id='password']");
        SelenideSteps.click("//*[@type='submit']");
        SelenideSteps.shouldBeVisible("//*[@class='alert alert-danger']");
    }

    @Tag("Selenide")
    @Test
    void task4() {
        SelenideSteps.click("//*[@data-action='add-to-cart']");
        SelenideSteps.click("//*[@id='open-cart-btn']");
        SelenideSteps.shouldBeVisible("//*[@class='cart-item']");
    }

    @Tag("Selenide")
    @Test
    void task5() {
        String name = "Товар " + new Random().nextInt(1, 999);
        String price = String.valueOf(new Random().nextInt(100, 200));
        addProductThroughAdmin(name, price);
        SelenideSteps.click("/html/body/div[2]/div[1]/a");
        productIdToDelete = getProductIdByName(name);

        String productButton =
                "//*[@data-action='add-to-cart' and @data-name='" + name
                        + "' and @data-price='" + price + "']";
        for (int i = 0; i < 4; i++) {
            SelenideSteps.click(productButton);
        }
        SelenideSteps.click("#open-cart-btn");
        SelenideSteps.click("#makeOrder");
        SelenideSteps.assertAlertContains("Денег не хватает!");
    }

    @Step("Создать товар в административной панели")
    private void addProductThroughAdmin(String name, String price) {
        SelenideSteps.click("//*[@class='btn-outline']");
        SelenideSteps.enterAdminUsername("//*[@id='username']");
        SelenideSteps.enterAdminPassword("//*[@id='password']");
        SelenideSteps.click("//*[@type='submit']");
        SelenideSteps.setValue("//*[@id='n-name']", name);
        SelenideSteps.setValue("//*[@id='n-price']", price);
        SelenideSteps.click("//*[@class='btn btn-add']");
    }

    @Step("Получить идентификатор товара {name}")
    private long getProductIdByName(String name) {
        String selector = "//*[@data-action='add-to-cart' and @data-name='" + name + "']";
        return Long.parseLong(SelenideSteps.getAttribute(selector, "data-id"));
    }

    @Step("Очистить созданный тестом товар")
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
