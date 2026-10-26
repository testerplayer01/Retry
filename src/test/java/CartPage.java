import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$x;


public class CartPage {
    ElementsCollection cartItems = $$x("//*[@class='cart-item']");
    SelenideElement totalPrice = $x("//*[@id='total-price']");
    SelenideElement removeButtons = $x("//*[@data-action='remove']");
    SelenideElement makeOrderButton = $x("//*[@id='makeOrder']");
    SelenideElement cartCount = $x("//*[@id='cart-count']");

    public CartPage clickMakeOrder() {
        makeOrderButton.click();
        return this;
    }
    public CartPage clickRemoveButton() {
        removeButtons.click();
        return this;
    }
    public String getTotalPrice() {
        return totalPrice.getText();
    }
    public String getCartCount() {
        return cartCount.getText();
    }
    public CartPage waitForPageToLoad() {
        cartItems.shouldHave(sizeGreaterThan(0));
        totalPrice.shouldBe(visible);
        makeOrderButton.shouldBe(visible);
        return this;
    }
}
