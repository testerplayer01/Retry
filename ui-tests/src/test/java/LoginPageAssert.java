import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;

public class LoginPageAssert extends AbstractAssert<LoginPageAssert, LoginPage> {

    public LoginPageAssert(LoginPage actual) {
        super(actual, LoginPageAssert.class);
    }

    @Step("Проверка видимости поля 'Имя пользователя'")
    public LoginPageAssert usernameFieldVisible() {
        actual.usernameField.shouldBe(visible);
        return this;
    }

    @Step("Проверка видимости поля 'Пароль'")
    public LoginPageAssert passwordFieldVisible() {
        actual.passwordField.shouldBe(visible);
        return this;
    }

    @Step("Проверка видимости кнопки 'Войти'")
    public LoginPageAssert loginButtonVisible() {
        actual.loginButton.shouldBe(visible);
        return this;
    }

    @Step("Проверка доступности кнопки 'Войти'")
    public LoginPageAssert loginButtonEnabled() {
        actual.loginButton.shouldBe(visible).shouldBe(enabled);
        return this;
    }
}
