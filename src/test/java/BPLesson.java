import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.interactions.Actions;
import utils.AllureSelenideListener;
import utils.ConfigParser;
import utils.SelenideSteps;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@Epic("Каталог товаров")
@Feature("Перетаскивание и уведомления")
public class BPLesson extends AllureSelenideListener {
    SelenideElement firstCard = $x("//*[@id='card-50']");
    SelenideElement cardButton = $x("//*[@id='open-cart-btn']");

    @BeforeEach
    void setup() {
//        open();
//        sleep(30000);
//        Configuration.pageLoadTimeout = 5000;
        SelenideSteps.open(ConfigParser.getBaseUrl());
    }

    @Tag("UIHomework")
    @Test
    void simpleDnd(){
        SelenideSteps.dragProductToCart("//*[@id='card-50']", "//*[@id='open-cart-btn']");
    }

    @Tag("UIHomework")
    @Test
    void anotherDnD(){
        dragWithActions();
    }

    @Step("Перетащить карточку к кнопке корзины мышью")
    private void dragWithActions() {
        actions().moveToElement(firstCard)
                .clickAndHold()
                .pause(2000)
                .moveToElement(cardButton)
                .release()
                .pause(Duration.ofSeconds(2))
                .perform();
    }

    @Tag("UIHomework")
    @Test
    void slowDnD(){
        dragWithSlowActions();
    }

    @Step("Медленно перетащить карточку к кнопке корзины")
    private void dragWithSlowActions() {
        int firstCardX = firstCard.getCoordinates().inViewPort().getX() + firstCard.getSize().getWidth()/2;
        int firstCardY = firstCard.getCoordinates().inViewPort().getY() + firstCard.getSize().getHeight()/2;
        int cardButtonX = cardButton.getCoordinates().inViewPort().getX() + cardButton.getSize().getWidth()/2;
        int cardButtonY = cardButton.getCoordinates().inViewPort().getY() + cardButton.getSize().getHeight()/2;

        int difX = cardButtonX - firstCardX;
        int difY = cardButtonY - firstCardY;

        sleep(2000);
        Actions dndActions = actions().moveToElement(firstCard).clickAndHold();
        for (int i = 0; i < 50; i++) {
            dndActions = dndActions.moveByOffset(difX / 50, difY / 50);
        }
        dndActions
                .release()
                .perform();
        sleep(2000);
    }

    @Tag("UIHomework")
    @Test
    void hiddenTest(){
        for (int i = 0; i < 3; i++) {
            SelenideSteps.click("//*[@data-action='add-to-cart']");
        }

        String notification = "(//*[@datatest='notification-container'])[1]";
        SelenideSteps.shouldBeHidden(notification);
        SelenideSteps.shouldExist(notification);
        SelenideSteps.shouldBeVisible("(" + notification + ")/..");
    }
}
