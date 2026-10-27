import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BPLesson {
    SelenideElement firstCard = $x("//*[@id='card-50']");
    SelenideElement cardButton = $x("//*[@id='open-cart-btn']");

    @BeforeEach
    void setup() {
//        open();
//        sleep(30000);
//        Configuration.pageLoadTimeout = 5000;
        open("http://localhost:8080/");
    }

    @Tag("UIHomework")
    @Test
    void simpleDnd(){
        sleep(2000);
        firstCard.dragAndDrop(DragAndDropOptions.to(cardButton));
        sleep(2000);
    }

    @Tag("UIHomework")
    @Test
    void anotherDnD(){
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
        $x("//*[@data-action='add-to-cart']").click();
        $x("//*[@data-action='add-to-cart']").click();
        $x("//*[@data-action='add-to-cart']").click();

        SelenideElement notificationContainer = $x("(//*[@datatest='notification-container'])[1]");
        notificationContainer.shouldNot(visible);
        notificationContainer.should(exist);
        notificationContainer.$x("./..").should(visible);
    }
}
