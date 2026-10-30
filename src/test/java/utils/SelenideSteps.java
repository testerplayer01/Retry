package utils;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;

public final class SelenideSteps {
    private SelenideSteps() {
    }

    @Step("Открыть страницу {url}")
    public static void open(String url) {
        com.codeborne.selenide.Selenide.open(url);
    }

    @Step("Нажать элемент {selector}")
    public static void click(String selector) {
        element(selector).click();
    }

    @Step("Ввести значение в поле {selector}")
    public static void setValue(
            String selector,
            @Param(mode = Parameter.Mode.MASKED) String value
    ) {
        element(selector).setValue(value);
    }

    @Step("Проверить, что элемент {selector} отображается")
    public static void shouldBeVisible(String selector) {
        element(selector).shouldBe(visible);
    }

    @Step("Проверить, что элемент {selector} доступен для взаимодействия")
    public static void shouldBeInteractable(String selector) {
        element(selector).shouldBe(interactable);
    }

    @Step("Проверить, что элемент {selector} скрыт")
    public static void shouldBeHidden(String selector) {
        element(selector).shouldBe(hidden);
    }

    @Step("Проверить, что элемент {selector} существует")
    public static void shouldExist(String selector) {
        element(selector).should(exist);
    }

    @Step("Проверить текст элемента {selector}")
    public static void shouldHaveText(String selector, String expectedText) {
        element(selector).shouldBe(visible).shouldHave(text(expectedText));
    }

    @Step("Проверить, что текст элемента {selector} не равен {unexpectedText}")
    public static void shouldNotHaveText(String selector, String unexpectedText) {
        element(selector).shouldNotHave(text(unexpectedText));
    }

    @Step("Получить текст элемента {selector}")
    public static String getText(String selector) {
        return element(selector).getText();
    }

    @Step("Прочитать тексты элементов {selector}")
    public static java.util.List<String> getTexts(String selector) {
        return com.codeborne.selenide.Selenide.$$x(selector)
                .texts();
    }

    @Step("Получить атрибут {attributeName} элемента {selector}")
    public static String getAttribute(String selector, String attributeName) {
        return element(selector).getAttribute(attributeName);
    }

    @Step("Обновить страницу")
    public static void refreshPage() {
        refresh();
    }

    @Step("Добавить товар {productIndex} в корзину")
    public static void addProductToCart(int productIndex) {
        $$x("//*[@data-action='add-to-cart']").get(productIndex).click();
    }

    @Step("Проверить текст предупреждения об оплате")
    public static void assertAlertContains(String expectedText) {
        Alert alert = switchTo().alert();
        assertThat(alert.getText()).contains(expectedText);
        alert.accept();
    }

    @Step("Проверить, что сумма не превышает {limit}")
    public static void assertAmountAtMost(int actualAmount, int limit) {
        assertThat(actualAmount).isLessThanOrEqualTo(limit);
    }

    @Step("Проверить, что сумма равна {expectedAmount}")
    public static void assertAmountEquals(int actualAmount, int expectedAmount) {
        assertThat(actualAmount).isEqualTo(expectedAmount);
    }

    @Step("Перетащить карточку товара в корзину")
    public static void dragProductToCart(String productSelector, String cartSelector) {
        element(productSelector).dragAndDrop(
                com.codeborne.selenide.DragAndDropOptions.to(element(cartSelector))
        );
    }

    private static SelenideElement element(String selector) {
        if (selector.startsWith("/") || selector.startsWith("(")) {
            return $x(selector);
        }
        return $(selector);
    }
}
