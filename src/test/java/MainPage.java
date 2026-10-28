import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

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


    public MainPage clickAddToCartButton() {
        addToCartButtons.first().click();
        return this;
    }
    public MainPage clickCartButton() {
        cartButton.click();
        return this;
    }
    public MainPage clickAddCount() {
        addCount.click();
        return this;
    }
    public MainPage clickReduceCount() {
        reduceCount.click();
        return this;
    }
    public MainPage clickOpenAdminButton() {
        openAdminButton.click();
        return this;
    }
    public MainPage clickProductCard() {
        productCards.click();
        return this;
    }
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
