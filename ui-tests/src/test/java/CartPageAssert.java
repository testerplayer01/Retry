import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static org.assertj.core.api.Assertions.assertThat;

public class CartPageAssert extends AbstractAssert<CartPageAssert, CartPage> {
    public CartPageAssert(CartPage actual) {
        super(actual, CartPageAssert.class);
    }

    @Step("Проверка видимости товаров в корзине")
    public CartPageAssert cartItemsAreVisible() {
        actual.cartItems.shouldHave(sizeGreaterThan(0));
        return this;
    }

    @Step("Проверка видимости итоговой суммы")
    public CartPageAssert totalPriceIsVisible() {
        actual.totalPrice.shouldBe(visible);
        return this;
    }

    @Step("Проверка видимости кнопки 'Оформить заказ'")
    public CartPageAssert makeOrderButtonIsVisible() {
        actual.makeOrderButton.shouldBe(visible);
        return this;
    }

    @Step("Проверка наличия товаров в корзине")
    public CartPageAssert cartItemsNotEmpty(){
        actual.cartItems.shouldHave(sizeGreaterThan(0));
        return this;
    }

    @Step("Проверить итоговую сумму корзины")
    public CartPageAssert totalPriceIs(int expectedPrice) {
        int actualPrice = Integer.parseInt(
                actual.getTotalPrice().replaceAll("\\D+", "").trim()
        );
        assertThat(actualPrice).isEqualTo(expectedPrice);
        return this;
    }

    @Step("Проверить, что итоговая сумма корзины не превышает {limit}")
    public CartPageAssert totalPriceDoesNotExceed(int limit) {
        int actualPrice = Integer.parseInt(
                actual.getTotalPrice().replaceAll("\\D+", "").trim()
        );
        assertThat(actualPrice).isLessThanOrEqualTo(limit);
        return this;
    }

}
