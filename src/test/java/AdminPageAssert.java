import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.visible;

public class AdminPageAssert extends AbstractAssert<AdminPageAssert, AdminPage> {

    public AdminPageAssert(AdminPage actual) {
        super(actual, AdminPageAssert.class);
    }

    public AdminPageAssert nameFieldIsVisible() {
        actual.nameField.shouldBe(visible);
        return this;
    }

    public AdminPageAssert priceFieldIsVisible() {
        actual.priceField.shouldBe(visible);
        return this;
    }

    public AdminPageAssert addButtonIsVisible() {
        actual.addButton.shouldBe(visible);
        return this;
    }

    public AdminPageAssert updateButtonIsVisible() {
        actual.updateButton.shouldBe(visible);
        return this;
    }

    public AdminPageAssert errorMessageIsVisible() {
        actual.errorMessage.shouldBe(visible);
        return this;
    }

    public AdminPageAssert successMessageIsVisible() {
        actual.successMessage.shouldBe(visible);
        return this;
    }

    public AdminPageAssert oldNameVisible() {
        actual.oldProduct.shouldBe(visible);
        return this;
    }

}
