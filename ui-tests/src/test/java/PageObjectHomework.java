import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.Configuration;
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

import java.util.List;

@Epic("Каталог товаров")
@Feature("Пользовательский интерфейс через Page Object")
public class PageObjectHomework  extends AllureSelenideListener {
    private MainPage mainPage;
    private LoginPage loginPage;
    private CartPage cartPage;
    private AdminPage adminPage;
    private MainPageAssert mainPageAssert;
    private LoginPageAssert loginPageAssert;
    private CartPageAssert cartPageAssert;
    private AdminPageAssert adminPageAssert;
    private final GoodsApiSteps goodsApi = new GoodsApiSteps();
    private final RestAssuredHomeworkCheck apiChecks = new RestAssuredHomeworkCheck();
    private Long productIdToDelete;
    private Long productIdToRestore;
    private String originalProductName;
    private int originalProductPrice;


    @BeforeEach
    void setup(){
        Configuration.browser = ConfigParser.getBrowser();
        Configuration.browserSize = ConfigParser.getBrowserSize();
        Configuration.headless = ConfigParser.isHeadless();
        Configuration.timeout = ConfigParser.getTimeout();
        Selenide.open(ConfigParser.getBaseUrl());
        productIdToDelete = null;
        productIdToRestore = null;
        mainPage = new MainPage();
        loginPage = new LoginPage();
        cartPage = new CartPage();
        adminPage = new AdminPage();
        mainPageAssert = new MainPageAssert(mainPage);
        loginPageAssert = new LoginPageAssert(loginPage);
        cartPageAssert = new CartPageAssert(cartPage);
        adminPageAssert = new AdminPageAssert(adminPage);
    }

    //Задание 1. Лекция
    @Tag("PageObject")
    @Tag("smoke")
    @Test
    void testMainPage(){
        mainPage.waitForPageToLoad();
        mainPageAssert.addToCartButtonIsVisible()
                .cartButtonIsVisible()
                .addCountIsVisible()
                .reduceCountIsVisible()
                .productCardsAreVisible()
                .openAdminButtonIsVisible();
    }

    @Tag("PageObject")
    @Test
    void testLoginPage(){
        mainPage.clickOpenAdminButton();
        loginPage.waitForPageLoaded();
        loginPageAssert.usernameFieldVisible()
                .passwordFieldVisible()
                .loginButtonVisible()
                .loginButtonEnabled();
    }

    @Tag("PageObject")
    @Test
    void testingLogin(){
        mainPage.clickOpenAdminButton();
        loginPage.waitForPageLoaded()
                .enterUsername()
                .enterPassword()
                .clickLoginButton();
    }

    //Задача 2. Вебинар
    @Tag("PageObject")
    @Test
    void task1(){
        mainPage.waitForPageToLoad();
        mainPageAssert.addToCartButtonIsVisible();

        mainPage.clickAddToCartButton();
        mainPage.clickAddToCartButton();
        mainPage.clickAddToCartButton();

        mainPage.clickCartButton();
        cartPage.waitForPageToLoad();
        cartPageAssert.cartItemsAreVisible();

        cartPageAssert.totalPriceDoesNotExceed(300);

        cartPage.clickMakeOrder();
        mainPageAssert.checkToaster();

    }

    @Tag("PageObject")
    @Test
    void task2(){
        mainPage.waitForPageToLoad();

        mainPage.addProductToCart(1);
        mainPage.addProductToCart(2);
        mainPage.addProductToCart(3);

        mainPage.clickCartButton();
        cartPage.waitForPageToLoad();

        List<Double> prices = getListPrices();
        double expectedTotal = prices.get(1) + prices.get(2) + prices.get(3);

        cartPageAssert.totalPriceIs((int) expectedTotal);
    }

    @Tag("PageObject")
    @Test
    void task3(){
        mainPage.waitForPageToLoad();
        mainPage.clickOpenAdminButton();
        
        loginPage.waitForPageLoaded();
        loginPageAssert.usernameFieldVisible();
        
        loginAsAdmin();
        
        adminPage.waitForPageLoaded(); 
        adminPageAssert.nameFieldIsVisible();

        adminPage
                .setName(adminPage.name)
                .setPrice(adminPage.price)
                .clickAddButton();
        
        productIdToDelete = adminPage.getProductIdByName(adminPage.name);
    }
    
    @Tag("PageObject")
    @Test
    void task4(){
        mainPage.waitForPageToLoad();
        mainPage.clickOpenAdminButton();
        
        loginPage.waitForPageLoaded();
        loginAsAdmin();
        
        originalProductName = adminPage.getFirstProductName();
        originalProductPrice = adminPage.getFirstProductPrice();
        productIdToRestore = adminPage.getProductIdByName(originalProductName);
        String updatedName = ConfigParser.getProductName() + "-" + java.util.UUID.randomUUID();

        adminPage.updateProductName(originalProductName, updatedName);
        Selenide.open(ConfigParser.getBaseUrl());
        mainPage.waitForPageToLoad();
        mainPageAssert.productIsVisible(updatedName);
    }

    @Step("Войти в административную панель")
    private void loginAsAdmin() {
        loginPage
                .enterUsername()
                .enterPassword()
                .clickLoginButton();
    }

    @Step("Получаем список цен товаров через API")
    private List<Double> getListPrices(){
        return goodsApi.getGoodsList()
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("goods.price", Double.class);
    }

    @AfterEach
    void tearDown(){
        try {
            if (productIdToRestore != null) {
                apiChecks.assertStatusCode(
                        goodsApi.updateProduct(
                                productIdToRestore,
                                originalProductName,
                                originalProductPrice
                        ),
                        200
                );
            }
        } finally {
            try {
                if (productIdToDelete != null) {
                    deleteCreatedProduct();
                }
            } finally {
                Selenide.closeWebDriver();
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