import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class BPHomework {
    private String name = "Товар " + new Random().nextInt(1, 999);
    private String price = String.valueOf(new Random().nextInt(10, 200));
    SelenideElement firstCard = $x("//*[@data-name='" + name + "']");
    SelenideElement cardButton = $x("//*[@id='open-cart-btn']");
    SelenideElement secondCard = $x("//*[@class='product-card']");

    @BeforeEach
    void setup() {
        open("http://localhost:8080/");
    }

    @Tag("UIHomework")
    @Test
    void task1() {
        $x("//*[@class='btn-outline']").click();

        $x("//*[@id='username']").setValue("admin");
        $x("//*[@id='password']").setValue("secret123");
        $x("//*[@type='submit']").click();

        $x("//*[@id='n-name']").setValue(name);
        $x("//*[@id='n-price']").setValue(price);
        $x("//*[@class='btn btn-add']").click();

        $x("//*[contains(text(), 'Вернуться на сайт')]").click();
        sleep(2000);

        $x("//*[@class='product-card']/h4[contains(text(), '" + name + "')]").shouldBe(visible);

        sleep(2000);
        firstCard.dragAndDrop(DragAndDropOptions.to(cardButton));
        sleep(2000);

        $x("//*[@id='open-cart-btn']").click();
        $x("//*[contains(text(), '" + name + "')]").shouldBe(visible);


    }

    @Tag("UIHomework")
    @Test
    void task2() {
        sleep(2000);
        secondCard.dragAndDrop(DragAndDropOptions.to(cardButton));
        sleep(2000);

        $x("//*[@id='open-cart-btn']").click();
        $x("//*[@data-action='remove']").click();

        $x("//*[@class='cart-item']").shouldBe(hidden);
    }

    @AfterEach
    void tearDown(){
        Selenide.closeWebDriver();
    }
}
