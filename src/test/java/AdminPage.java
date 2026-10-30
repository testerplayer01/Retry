import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.Random;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class AdminPage {
    String name = "Товар " + new Random().nextInt(1, 100);
    String price = String.valueOf(new Random().nextInt(10, 300));
    SelenideElement nameField = $x("//*[@id='n-name']");
    SelenideElement priceField = $x("//*[@id='n-price']");
    SelenideElement addButton = $x("//*[@class='btn btn-add']");
    SelenideElement updateButton = $x("//*[@class='btn btn-upd']");
    SelenideElement backToProductsButton = $x("//*[contains(text(), 'Вернуться')]");
    SelenideElement successMessage = $x("//*[contains(text(), 'Товар успешно добавлен!')]");
    SelenideElement errorMessage = $x("//*[@class='alert alert-danger']");
    SelenideElement oldProduct = $x("//*[@id='tbody']/tr[1]/td[2]//input[@type='text']");

    @Step("Изменить название товара в таблице")
    public AdminPage choiceOldProduct(String newName){
        oldProduct.setValue(newName);
        return this;
    }

    @Step("Ввод наименования товара")
    public AdminPage setName(String name) {
        nameField.setValue(name);
        return this;
    }

    @Step("Ввод стоимости товара")
    public AdminPage setPrice(String price) {
        priceField.setValue(price);
        return this;
    }

    @Step("Нажать кнопку добавить товар")
    public AdminPage clickAddButton() {
        addButton.click();
        return this;
    }

    @Step("Нажать кнопку 'Вернуться'")
    public AdminPage clickBackToProducts() {
        backToProductsButton.click();
        return this;
    }

    @Step("Нажать кнопку сохранения изменений товара")
    public AdminPage clickUpdateButton() {
        updateButton.click();
        return this;
    }

    @Step("Ожидание загрузки админ-страницы")
    public AdminPage waitForPageLoaded(){
        nameField.shouldBe(visible);
        priceField.shouldBe(visible);
        addButton.shouldBe(visible);
        return this;
    }


}
