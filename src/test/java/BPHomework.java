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

import java.util.Random;

import utils.ConfigParser;
import utils.GoodsApiSteps;
import utils.SelenideSteps;

@Epic("Каталог товаров")
@Feature("Бизнес-сценарии")
public class BPHomework extends AllureSelenideListener {
    private String name = "Товар " + new Random().nextInt(1, 999);
    private String price = String.valueOf(new Random().nextInt(10, 200));
    private final GoodsApiSteps goodsApi = new GoodsApiSteps();
    private final RestAssuredHomeworkCheck apiChecks = new RestAssuredHomeworkCheck();
    private String productNameToDelete;
    @BeforeEach
    void setup() {
        SelenideSteps.open(ConfigParser.getBaseUrl());
    }

    @Tag("UIHomework")
    @Test
    void task1() {
        addProductThroughAdmin();
        productNameToDelete = name;
        SelenideSteps.click("//*[contains(text(), 'Вернуться на сайт')]");
        String productCard = "//*[@class='product-card']/h4[contains(text(), '" + name + "')]";
        SelenideSteps.shouldBeVisible(productCard);
        SelenideSteps.dragProductToCart("//*[@data-name='" + name + "']", "//*[@id='open-cart-btn']");
        SelenideSteps.click("//*[@id='open-cart-btn']");
        SelenideSteps.shouldBeVisible("//*[contains(text(), '" + name + "')]");
    }

    @Tag("UIHomework")
    @Test
    void task2() {
        SelenideSteps.dragProductToCart(
                "(//*[@class='product-card'])[1]",
                "//*[@id='open-cart-btn']"
        );
        SelenideSteps.click("//*[@id='open-cart-btn']");
        SelenideSteps.click("//*[@data-action='remove']");
        SelenideSteps.shouldBeHidden("//*[@class='cart-item']");
    }

    @Step("Войти в административную панель и добавить товар")
    private void addProductThroughAdmin() {
        SelenideSteps.click("//*[@class='btn-outline']");
        SelenideSteps.setValue("//*[@id='username']", ConfigParser.getAdminLogin());
        SelenideSteps.setValue("//*[@id='password']", ConfigParser.getAdminPassword());
        SelenideSteps.click("//*[@type='submit']");
        SelenideSteps.setValue("//*[@id='n-name']", name);
        SelenideSteps.setValue("//*[@id='n-price']", price);
        SelenideSteps.click("//*[@class='btn btn-add']");
    }

    @Step("Удалить созданный тестом товар")
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
    void tearDown(){
        try {
            if (productNameToDelete != null) {
                deleteCreatedProduct();
            }
        } finally {
            Selenide.closeWebDriver();
        }
    }
}
