import com.codeborne.selenide.Selenide;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PageObjectHomework {
    private MainPage mainPage;
    private LoginPage loginPage;
    private CartPage cartPage;
    private AdminPage adminPage;
    private MainPageAssert mainPageAssert;
    private LoginPageAssert loginPageAssert;
    private CartPageAssert cartPageAssert;
    private AdminPageAssert adminPageAssert;


    @BeforeEach
    void setup(){
        open("http://localhost:8080");
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
                .enterUsername("admin")
                .enterPassword("secret123")
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

        int totalPrice = Integer.parseInt(cartPage.getTotalPrice().replaceAll("\\D+", ""));
        assertThat(totalPrice).as("Сумма не должна превышать 300р").isLessThanOrEqualTo(300);
        System.out.println("Сумма в корзине: " + totalPrice);

        cartPage.clickMakeOrder();
        sleep(2000);

        mainPageAssert.checkToaster();

    }

    @Tag("PageObject")
    @Test
    void task2(){
        mainPage.waitForPageToLoad();

        mainPage.addToCartButtons.get(1).click();
        mainPage.addToCartButtons.get(2).click();
        mainPage.addToCartButtons.get(3).click();

        mainPage.clickCartButton();
        cartPage.waitForPageToLoad();

        Response response = given()
                .baseUri("http://localhost:8080")
                .auth().basic("admin", "secret123")
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .extract()
                .response();

        List<Double> prices = response.jsonPath().getList("goods.price", Double.class);
        double expectedTotal = prices.get(1) + prices.get(2) + prices.get(3);

        int actualTotal = Integer.parseInt(cartPage.getTotalPrice().replaceAll("\\D+", ""));
        assertThat(actualTotal)
                .as("Сумма должна быть: " + expectedTotal)
                .isEqualTo((int) expectedTotal);
        System.out.println("Сумма в корзине: " + actualTotal);
    }

    @Tag("PageObject")
    @Test
    void task3(){
        mainPage.waitForPageToLoad();
        mainPage.clickOpenAdminButton();
        
        loginPage.waitForPageLoaded();
        loginPageAssert.usernameFieldVisible();
        
        loginPage
                .enterUsername("admin")
                .enterPassword("secret123")
                .clickLoginButton();
        
        adminPage.waitForPageLoaded(); 
        adminPageAssert.nameFieldIsVisible();

        adminPage
                .setName(adminPage.name)
                .setPrice(adminPage.price)
                .clickAddButton();
        
        adminPageAssert.successMessageIsVisible();
    }
    
    @Tag("PageObject")
    @Test
    void task4(){
        mainPage.waitForPageToLoad();
        mainPage.clickOpenAdminButton();
        
        loginPage.waitForPageLoaded();
        loginPage
                .enterUsername("admin")
                .enterPassword("secret123")
                .clickLoginButton();
        
        adminPage.choiceOldProduct(mainPage.newName);
        adminPage.updateButton.click();

        adminPage.clickBackToProducts();

        mainPageAssert.checkChange();
        System.out.println("Новое наименование: " + mainPage.newName);
        
        
    }
    @AfterEach
    void tearDown(){
        Selenide.closeWebDriver();
    }
}