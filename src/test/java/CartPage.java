import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

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

    @Step("Нажать на кнопку 'Оформить заказ'")
    public CartPage clickMakeOrder() {
        makeOrderButton.click();
        return this;
    }

    @Step("Нажать на кнопку удаления товара из корзины")
    public CartPage clickRemoveButton() {
        removeButtons.click();
        return this;
    }

    @Step("Получить общую стоимость товаров в корзине")
    public String getTotalPrice() {
        return totalPrice.getText();
    }

    @Step("Получить количество товаров в корзине")
    public String getCartCount() {
        return cartCount.getText();
    }

    @Step("Ожидание загрузки корзины")
    public CartPage waitForPageToLoad() {
        cartItems.shouldHave(sizeGreaterThan(0));
        totalPrice.shouldBe(visible);
        makeOrderButton.shouldBe(visible);
        return this;
    }
}
