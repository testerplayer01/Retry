import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class MainPageAssert extends AbstractAssert<MainPageAssert, MainPage> {
    public MainPageAssert(MainPage actual) {
        super(actual, MainPageAssert.class);
    }

    @Step("Проверка видимости кнопка 'Добавить в корзину'")
    public MainPageAssert addToCartButtonIsVisible() {
        actual.addToCartButtons.shouldHave(sizeGreaterThan(0));
        return this;
    }

    @Step("Проверка видимости кнопки корзины")
    public MainPageAssert cartButtonIsVisible() {
        actual.cartButton.shouldBe(visible);
        return this;
    }

    @Step("Проверка видимости кнопки увеличения количества товаров")
    public MainPageAssert addCountIsVisible() {
        actual.addCount.shouldBe(visible);
        return this;
    }

    @Step("Проверка видимости кнопки уменьшения количества товаров")
    public MainPageAssert reduceCountIsVisible() {
        actual.reduceCount.shouldBe(visible);
        return this;
    }

    @Step("Проверка видимости карточки товаров")
    public MainPageAssert productCardsAreVisible() {
        actual.productCards.shouldBe(visible);
        return this;
    }

    @Step("Проверка видимости кнопки 'Администрирование'")
    public MainPageAssert openAdminButtonIsVisible() {
        actual.openAdminButton.shouldBe(visible);
        return this;
    }

    @Step("Проверка появления тостера")
    public MainPageAssert checkToaster(){
        actual.checkToast.shouldBe(visible);
        actual.checkToast.shouldHave(text("Заказ принят в обработку!"));
        return this;
    }

    @Step("Проверка, что измененный товар виден на странице")
    public MainPageAssert checkChange(){
        actual.checkChange.shouldBe(visible);
        return this;
    }

}
