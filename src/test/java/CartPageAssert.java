import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;

public class CartPageAssert extends AbstractAssert<CartPageAssert, CartPage> {
    public CartPageAssert(CartPage actual) {
        super(actual, CartPageAssert.class);
    }

    public CartPageAssert cartItemsAreVisible() {
        actual.cartItems.shouldHave(sizeGreaterThan(0));
        return this;
    }
    public CartPageAssert totalPriceIsVisible() {
        actual.totalPrice.shouldBe(visible);
        return this;
    }
    public CartPageAssert makeOrderButtonIsVisible() {
        actual.makeOrderButton.shouldBe(visible);
        return this;
    }
    public CartPageAssert cartItemsNotEmpty(){
        actual.cartItems.shouldHave(sizeGreaterThan(0));
        return this;
    }

}
