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
import utils.GoodsApiSteps;
import utils.ConfigParser;
import utils.RestAssuredHomeworkCheck;
import utils.SeleniumSteps;

import java.util.Random;

@Epic("Каталог товаров")
@Feature("Пользовательский интерфейс через Selenium")
public class UIHomeworkSelenium {
    private WebDriver driver;
    private final GoodsApiSteps goodsApi = new GoodsApiSteps();
    private final RestAssuredHomeworkCheck apiChecks = new RestAssuredHomeworkCheck();
    private Long productIdToDelete;

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
        SeleniumSteps.click(driver, By.xpath("/html/body/div[2]/div[1]/a"), "возврат в каталог");
        SeleniumSteps.assertElementTextEquals(
                driver,
                By.xpath("//*[@class='product-card']/h4[contains(text(), '" + productName + "')]"),
                "карточка товара",
                productName
        );
        productIdToDelete = Long.parseLong(driver.findElement(By.xpath(
                "//*[@data-action='add-to-cart' and @data-name='" + productName + "']"
        )).getAttribute("data-id"));
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
        SeleniumSteps.enterInvalidAdminUsername(driver, By.id("username"));
        SeleniumSteps.enterAdminPassword(driver, By.id("password"));
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
        SeleniumSteps.assertElementVisible(
                driver,
                By.xpath("//*[@class='cart-item']"),
                "товар в корзине"
        );
    }

    @Step("Открыть административную панель и войти")
    private void openAdminAndLogin() {
        SeleniumSteps.click(driver, By.xpath("//*[@class='btn-outline']"), "открыть вход");
        SeleniumSteps.enterAdminUsername(driver, By.id("username"));
        SeleniumSteps.enterAdminPassword(driver, By.id("password"));
        SeleniumSteps.click(driver, By.xpath("//*[@type='submit']"), "кнопка входа");
    }

    @AfterEach
    void tearDown() {
        try {
            if (productIdToDelete != null) {
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
        Response response = goodsApi.deleteProduct(productIdToDelete);
        apiChecks.assertStatusCode(response, 200);
        productIdToDelete = null;
    }
}
