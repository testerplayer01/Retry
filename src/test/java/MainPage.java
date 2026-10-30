import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.Random;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$x;


public class MainPage {
    ElementsCollection addToCartButtons = $$x("//*[@data-action='add-to-cart']");
    SelenideElement cartButton = $x("//*[@id='open-cart-btn']");
    SelenideElement addCount = $x("//*[@data-action='qty-change' and @data-step='1']");
    SelenideElement reduceCount = $x("//*[@data-action='qty-change' and @data-step='-1']");
    SelenideElement productCards = $x("//*[@class='product-card']");
    SelenideElement openAdminButton = $x("//*[@class='btn-outline']");
    SelenideElement checkToast = $x("//*[@class='toast']");
    String newName = "Новый " + new Random().nextInt(103, 300);
    SelenideElement checkChange = $x("//*[contains(text(), '" + newName + "')]");

    @Step("Нажать на кнопку добавить в корзину")
    public MainPage clickAddToCartButton() {
        addToCartButtons.first().click();
        return this;
    }

    @Step("Добавить товар с индексом {index} в корзину")
    public MainPage addProductToCart(int index) {
        addToCartButtons.get(index).click();
        return this;
    }
    @Step("Нажать на кнопку корзина")
    public MainPage clickCartButton() {
        cartButton.click();
        return this;
    }
    @Step("Нажать на кнопку увеличения количества")
    public MainPage clickAddCount() {
        addCount.click();
        return this;
    }
    @Step("Нажать на кнопку уменьшения количества")
    public MainPage clickReduceCount() {
        reduceCount.click();
        return this;
    }
    @Step("Нажать кнопку 'Администрирование'")
    public MainPage clickOpenAdminButton() {
        openAdminButton.click();
        return this;
    }
    @Step("Нажать на карточку товара")
    public MainPage clickProductCard() {
        productCards.click();
        return this;
    }
    @Step("Ожидание загрузки главной страницы")
    public MainPage waitForPageToLoad() {
        addToCartButtons.shouldHave(sizeGreaterThan(0));
        cartButton.shouldBe(visible);
        addCount.shouldBe(visible);
        reduceCount.shouldBe(visible);
        productCards.shouldBe(visible);
        openAdminButton.shouldBe(visible);
        return this;
    }
}
