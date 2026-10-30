import io.qameta.allure.Step;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.ConfigParser;
import utils.GoodsApiSteps;
import utils.SeleniumSteps;

import java.util.Random;

@Epic("Каталог товаров")
@Feature("Пользовательский интерфейс через Selenium")
public class UIHomeworkSelenium {
    private WebDriver driver;
    private final GoodsApiSteps goodsApi = new GoodsApiSteps();
    private final RestAssuredHomeworkCheck apiChecks = new RestAssuredHomeworkCheck();
    private String productNameToDelete;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        SeleniumSteps.open(driver, ConfigParser.getBaseUrl());
    }

    @Tag("Selenium")
    @Test
    void task1() {
        String productName = "Товар " + new Random().nextInt(1, 999);
        String productPrice = String.valueOf(new Random().nextInt(10, 200));

        openAdminAndLogin();
        SeleniumSteps.enterText(
                driver,
                By.id("n-name"),
                productName,
                "название товара"
        );
        SeleniumSteps.enterText(
                driver,
                By.id("n-price"),
                productPrice,
                "цена товара"
        );
        SeleniumSteps.click(driver, By.xpath("//*[@class='btn btn-add']"), "кнопка добавления");
        productNameToDelete = productName;
        SeleniumSteps.click(driver, By.xpath("/html/body/div[2]/div[1]/a"), "возврат в каталог");
        SeleniumSteps.assertElementTextEquals(
                driver,
                By.xpath("//*[@class='product-card']/h4[contains(text(), '" + productName + "')]"),
                "карточка товара",
                productName
        );
    }

    @Tag("Selenium")
    @Test
    void task2() {
        SeleniumSteps.click(driver, By.xpath("//*[@data-action='add-to-cart']"), "добавить в корзину");
        SeleniumSteps.click(driver, By.id("open-cart-btn"), "открыть корзину");
        SeleniumSteps.assertElementVisible(
                driver,
                By.xpath("//*[@class='cart-item']"),
                "товар в корзине"
        );
    }

    @Tag("Selenium")
    @Test
    void task3() {
        SeleniumSteps.click(driver, By.xpath("//*[@class='btn-outline']"), "открыть вход");
        SeleniumSteps.enterUsername(driver, By.id("username"), "admin2");
        SeleniumSteps.enterPassword(driver, By.id("password"), "secret1234");
        SeleniumSteps.click(driver, By.xpath("//*[@type='submit']"), "кнопка входа");
        SeleniumSteps.assertElementVisible(
                driver,
                By.xpath("//*[@class='alert alert-danger']"),
                "сообщение об ошибке"
        );
    }

    @Tag("Selenium")
    @Test
    void task4() {
        SeleniumSteps.click(driver, By.xpath("//*[@data-action='add-to-cart']"), "добавить в корзину");
        SeleniumSteps.click(driver, By.id("open-cart-btn"), "открыть корзину");
        SeleniumSteps.refresh(driver);
        SeleniumSteps.assertElementNumberGreaterThan(
                driver,
                By.id("cart-count"),
                "счётчик корзины",
                0
        );
    }

    @Step("Открыть административную панель и войти")
    private void openAdminAndLogin() {
        SeleniumSteps.click(driver, By.xpath("//*[@class='btn-outline']"), "открыть вход");
        SeleniumSteps.enterUsername(driver, By.id("username"), ConfigParser.getAdminLogin());
        SeleniumSteps.enterPassword(driver, By.id("password"), ConfigParser.getAdminPassword());
        SeleniumSteps.click(driver, By.xpath("//*[@type='submit']"), "кнопка входа");
    }

    @AfterEach
    void tearDown() {
        try {
            if (productNameToDelete != null) {
                deleteCreatedProduct();
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
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
}
