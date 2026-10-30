import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.time.Duration;

import utils.AllureSelenideListener;
import utils.ConfigParser;
import utils.SelenideSteps;

public class SelenideLesson extends AllureSelenideListener {
    @BeforeEach
    void setup() {
        SelenideSteps.open(ConfigParser.getBaseUrl());
    }

    @Tag("Selenide")
    @Test //просто поиск элемента и клик по нему
    void selenideTest() {
        SelenideSteps.click("//*[@id='open-cart-btn']");
        SelenideSteps.click("//*[@id='close-modal']");
    }

    @Tag("Selenide")
    @Test //коллекция элементов и вывод их текста в консоль
    void selenideTest2() {
        SelenideSteps.click("//*[@id='open-cart-btn']");
        SelenideSteps.getTexts("//*[contains(@id, card)]/h4");
    }

    @Tag("Selenide")
    @Test //коллекция элементов и ассерты на их текст
    void selenideTest3() {
        SelenideSteps.shouldBeVisible("//*[@id='open-cart-btn']");
        SelenideSteps.click("//*[@id='open-cart-btn']");
        SelenideSteps.shouldBeInteractable("//*[@id='open-cart-btn']");
        SelenideSteps.getTexts("//*[contains(@id, card)]/h4");
    }

    @Tag("Selenide")
    @Test //Алерты и коллекции
    void selenideTest4() {
        for (int i = 0; i < 4; i++) {
            SelenideSteps.click("//*[@data-action='add-to-cart']");
        }
        SelenideSteps.click("#open-cart-btn");
        SelenideSteps.click("#makeOrder");
        SelenideSteps.assertAlertContains("Денег не хватает!");
    }
}
