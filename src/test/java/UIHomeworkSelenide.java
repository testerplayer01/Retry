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
import utils.SelenideSteps;

import java.util.Random;

@Epic("Каталог товаров")
@Feature("Пользовательский интерфейс через Selenide")
public class UIHomeworkSelenide extends AllureSelenideListener {
    private final GoodsApiSteps goodsApi = new GoodsApiSteps();
    private final RestAssuredHomeworkCheck apiChecks = new RestAssuredHomeworkCheck();
    private String productNameToDelete;

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
        productNameToDelete = name;
        SelenideSteps.click("/html/body/div[2]/div[1]/a");
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
        SelenideSteps.setValue("//*[@id='username']", "admin1");
        SelenideSteps.setValue("//*[@id='password']", "secret1234");
        SelenideSteps.click("//*[@type='submit']");
        SelenideSteps.shouldBeVisible("//*[@class='alert alert-danger']");
    }

    @Tag("Selenide")
    @Test
    void task4() {
        SelenideSteps.click("//*[@data-action='add-to-cart']");
        SelenideSteps.click("//*[@id='open-cart-btn']");
        SelenideSteps.refreshPage();
        SelenideSteps.shouldNotHaveText("//*[@id='cart-count']", "0");
    }

    @Tag("Selenide")
    @Test
    void task5() {
        String name = "Товар " + new Random().nextInt(1, 999);
        String price = String.valueOf(new Random().nextInt(100, 200));
        addProductThroughAdmin(name, price);
        productNameToDelete = name;
        SelenideSteps.click("/html/body/div[2]/div[1]/a");

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
        SelenideSteps.setValue("//*[@id='username']", ConfigParser.getAdminLogin());
        SelenideSteps.setValue("//*[@id='password']", ConfigParser.getAdminPassword());
        SelenideSteps.click("//*[@type='submit']");
        SelenideSteps.setValue("//*[@id='n-name']", name);
        SelenideSteps.setValue("//*[@id='n-price']", price);
        SelenideSteps.click("//*[@class='btn btn-add']");
    }

    @Step("Очистить созданный тестом товар")
    private void deleteCreatedProduct() {
        long productId = goodsApi.findProductIdByName(productNameToDelete)
                .orElseThrow(() -> new AssertionError(
                        "Тестовый товар не найден при очистке: " + productNameToDelete
                ));
        Response response = goodsApi.deleteProduct(productId);
        apiChecks.assertStatusCode(response, 200);
        productNameToDelete = null;
    }

    @AfterEach
    void tearDown() {
        try {
            if (productNameToDelete != null) {
                deleteCreatedProduct();
            }
        } finally {
            Selenide.closeWebDriver();
        }
    }
}
