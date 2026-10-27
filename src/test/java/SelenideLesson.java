import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*; //можно не использовать Selenide.
import static com.codeborne.selenide.Condition.*; //можно не использовать Condition.


import static java.lang.Thread.sleep;

public class SelenideLesson {
    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:8080/");

    }

    @Tag("Selenide")
    @Test //просто поиск элемента и клик по нему
    void selenideTest() {
        SelenideElement cartButton = $x("//*[@id='open-cart-btn']");
        cartButton.click();
        Selenide.sleep(3000);
        cartButton.click();
    }

    @Tag("Selenide")
    @Test //коллекция элементов и вывод их текста в консоль
    void selenideTest2() {
        SelenideElement cartButton = Selenide.$x("//*[@id='open-cart-btn']");
        cartButton.click();
        ElementsCollection cardTitleList = Selenide.$$x("//*[contains(@id, card)]/h4");
        cardTitleList.forEach(cardTitle -> {
            System.out.println(cardTitle.text());
        });
    }

    @Tag("Selenide")
    @Test //коллекция элементов и ассерты на их текст
    void selenideTest3() {
        SelenideElement cartButton = $x("//*[@id='open-cart-btn']");
        cartButton.should(visible, Duration.ofSeconds(7));
        cartButton.click();
        cartButton.should(interactable, Duration.ofSeconds(1));
        ElementsCollection cardTitleList = $$x("//*[contains(@id, card)]/h4");
        cardTitleList.forEach(cardTitle -> {
            System.out.println(cardTitle.text());
        });
    }

    @Tag("Selenide")
    @Test //Алерты и коллекции
    void selenideTest4() {
        SelenideElement addToCartButton = $x("//*[@data-action='add-to-cart']");
        addToCartButton.click();
        addToCartButton.click();
        addToCartButton.click();
        addToCartButton.click();
        $("#open-cart-btn").click();
        $("#makeOrder").click();
        Alert activeAlert = Selenide.switchTo().alert();
        System.out.println(activeAlert.getText());
        Selenide.sleep(3000);
        activeAlert.accept();
    }
}
