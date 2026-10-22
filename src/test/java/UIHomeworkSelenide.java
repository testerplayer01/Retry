import com.codeborne.selenide.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;

import java.util.Random;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.*; //можно не использовать Selenide.
import static com.codeborne.selenide.Condition.*; //можно не использовать Condition.
import static org.assertj.core.api.Assertions.assertThat;


public class UIHomeworkSelenide {
    @BeforeEach
    void setup() {
//        Configuration.holdBrowserOpen = true;
        Selenide.open("http://localhost:8080/");

    }

    @Tag("Selenide")
    @Test
    void task1() {
        $x("//*[@class='btn-outline']").click();

        $x("//*[@id='username']").setValue("admin");
        $x("//*[@id='password']").setValue("secret123");
        $x("//*[@type='submit']").click();

        String name = "Товар " + new Random().nextInt(1, 999);
        String price = String.valueOf(new Random().nextInt(10, 200));

        $x("//*[@id='n-name']").setValue(name);
        $x("//*[@id='n-price']").setValue(price);
        $x("//*[@class='btn btn-add']").click();

        $x("/html/body/div[2]/div[1]/a").click();
        sleep(4000);

        $x("//*[@class='product-card']/h4[contains(text(), '" + name + "')]").shouldBe(visible);
    }

    @Tag("Selenide")
    @Test
    void task2() {
        $x("//*[@data-action='add-to-cart']").click();
        $x("//*[@id='open-cart-btn']").click();
        $x("//*[@class='cart-item']").shouldBe(visible);
        Selenide.sleep(3000);
    }

    @Tag("Selenide")
    @Test
    void task3() {
        $x("//*[@class='btn-outline']").click();;

        $x("//*[@id='username']").setValue("admin1");
        $x("//*[@id='password']").setValue("secret1234");
        $x("//*[@type='submit']").click();

        $x("//*[@class='alert alert-danger']").shouldBe(visible);
        Selenide.sleep(2000);
    }

    @Tag("Selenide")
    @Test
    void task4() {
        $x("//*[@data-action='add-to-cart']").click();
        $x("//*[@id='open-cart-btn']").click();
        refresh();
        $x("//*[@id='cart-count']").shouldNot(text("0"));
    }

    @Tag("Selenide")
    @Test
    void task5() {
        $x("//*[@class='btn-outline']").click();

        $x("//*[@id='username']").setValue("admin");
        $x("//*[@id='password']").setValue("secret123");
        $x("//*[@type='submit']").click();

        String name = "Товар " + new Random().nextInt(1, 999);
        String price = String.valueOf(new Random().nextInt(100, 200));

        $x("//*[@id='n-name']").setValue(name);
        $x("//*[@id='n-price']").setValue(price);
        $x("//*[@class='btn btn-add']").click();

        $x("/html/body/div[2]/div[1]/a").click();

        SelenideElement addToCartButton = $x("//*[@data-action='add-to-cart' and @data-name='" + name + "' and @data-price='" + price + "']");
        addToCartButton.click();
        addToCartButton.click();
        addToCartButton.click();
        addToCartButton.click();
        $("#open-cart-btn").click();
        $("#makeOrder").click();
        Alert activeAlert = switchTo().alert();
        assertThat(activeAlert.getText())
                .as("Текст алерта должен содержать 'Денег не хватает! '")
                .contains("Денег не хватает!");
                
        System.out.println(activeAlert.getText());
        sleep(3000);
        activeAlert.accept();
    }
    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }
}
