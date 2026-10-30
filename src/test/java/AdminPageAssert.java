import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.visible;

public class AdminPageAssert extends AbstractAssert<AdminPageAssert, AdminPage> {

    public AdminPageAssert(AdminPage actual) {
        super(actual, AdminPageAssert.class);
    }

    @Step("Проверка видимости наименования товара")
    public AdminPageAssert nameFieldIsVisible() {
        actual.nameField.shouldBe(visible);
        return this;
    }

    @Step("Проверка видимости цены товара")
    public AdminPageAssert priceFieldIsVisible() {
        actual.priceField.shouldBe(visible);
        return this;
    }

    @Step("Проверка видимости кнопки добавления товара")
    public AdminPageAssert addButtonIsVisible() {
        actual.addButton.shouldBe(visible);
        return this;
    }

    @Step("Проверка видимости кнопки сохранения изменений товара")
    public AdminPageAssert updateButtonIsVisible() {
        actual.updateButton.shouldBe(visible);
        return this;
    }

    @Step("Проверка видимости сообщения об ошибке")
    public AdminPageAssert errorMessageIsVisible() {
        actual.errorMessage.shouldBe(visible);
        return this;
    }

    @Step("Проверка видимости сообщения об успешном изменении")
    public AdminPageAssert successMessageIsVisible() {
        actual.successMessage.shouldBe(visible);
        return this;
    }

    @Step("Проверка видимости сделанных изменений")
    public AdminPageAssert oldNameVisible() {
        actual.oldProduct.shouldBe(visible);
        return this;
    }

}
