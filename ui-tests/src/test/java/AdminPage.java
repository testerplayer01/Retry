import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import utils.ConfigParser;

import java.util.Random;
import java.util.Objects;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$x;

public class AdminPage {
    String name = ConfigParser.getProductName() + " " + new Random().nextInt(1, 100);
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

    @Step("Изменить цену товара с id {productId} на {newPrice}")
    public AdminPage updateProductPrice(long productId, int newPrice){
        SelenideElement priceField = $x("//*[@id='tbody']/tr[1]/td[3]/input");
        priceField.setValue(String.valueOf(newPrice));
        updateButton.click();
        return this;
    }

    @Step("Получить ID товара {productName} в административной таблице")
    public long getProductIdByName(String productName) {
        SelenideElement nameField = $$x("//*[@id='tbody']/tr/td[2]/input[@type='text']")
                .findBy(attribute("value", productName))
                .shouldBe(visible);
        String id = nameField.$x("./ancestor::tr")
                .$x(".//*[@data-action='delete']")
                .getAttribute("data-id");
        return Long.parseLong(Objects.requireNonNull(id));
    }

    @Step("Получить название первого товара в административной таблице")
    public String getFirstProductName() {
        return oldProduct.getValue();
    }

    @Step("Получить цену первого товара в административной таблице")
    public int getFirstProductPrice() {
        return Integer.parseInt($x("//*[@id='tbody']/tr[1]/td[3]/input").getValue());
    }

    @Step("Изменить название товара {oldName} на {newName}")
    public AdminPage updateProductName(String oldName, String newName) {
        for (SelenideElement row : $$x("//*[@id='tbody']/tr")) {
            String rowName = row.$x("./td[2]//input[@type='text']").getValue();
            if (oldName.equals(rowName)) {
                row.$x("./td[2]//input[@type='text']").setValue(newName);
                row.$x(".//*[@data-action='update']").click();
                return this;
            }
        }
        throw new AssertionError("Товар не найден в административной таблице: " + oldName);
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
