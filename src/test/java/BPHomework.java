import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Selenide.*;

public class BPHomework {
    SelenideElement firstCard = $x("//*[@id='card-50']");
    SelenideElement cardButton = $x("//*[@id='open-cart-btn']");

    @BeforeEach
    void setup() {
        open("http://localhost:8080/");
    }

    @Tag("UIHomework")
    @Test
    void task1() {
        sleep(2000);
        firstCard.dragAndDrop(DragAndDropOptions.to(cardButton));
        sleep(2000);
    }

    @Tag("UIHomework")
    @Test
    void task2() {
        sleep(2000);
        firstCard.dragAndDrop(DragAndDropOptions.to(cardButton));
        sleep(2000);

        $x("//*[@id='open-cart-btn']").click();
        $x("//*[@data-action='remove']").click();

        $x("//*[@class='cart-item']").shouldBe(hidden);
    }
}
