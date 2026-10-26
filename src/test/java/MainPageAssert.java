import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class MainPageAssert extends AbstractAssert<MainPageAssert, MainPage> {
    public MainPageAssert(MainPage actual) {
        super(actual, MainPageAssert.class);
    }

    public MainPageAssert addToCartButtonIsVisible() {
        actual.addToCartButtons.shouldHave(sizeGreaterThan(0));
        return this;
    }
    public MainPageAssert cartButtonIsVisible() {
        actual.cartButton.shouldBe(visible);
        return this;
    }
    public MainPageAssert addCountIsVisible() {
        actual.addCount.shouldBe(visible);
        return this;
    }
    public MainPageAssert reduceCountIsVisible() {
        actual.reduceCount.shouldBe(visible);
        return this;
    }
    public MainPageAssert productCardsAreVisible() {
        actual.productCards.shouldBe(visible);
        return this;
    }
    public MainPageAssert openAdminButtonIsVisible() {
        actual.openAdminButton.shouldBe(visible);
        return this;
    }
    public MainPageAssert checkToaster(){
        actual.checkToast.shouldBe(visible);
        actual.checkToast.shouldHave(text("Заказ принят в обработку!"));
        return this;
    }
    public MainPageAssert checkChange(){
        actual.checkChange.shouldBe(visible);
        return this;
    }

}
