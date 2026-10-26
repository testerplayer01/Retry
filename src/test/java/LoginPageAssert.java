import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;

public class LoginPageAssert extends AbstractAssert<LoginPageAssert, LoginPage> {

    public LoginPageAssert(LoginPage actual) {
        super(actual, LoginPageAssert.class);
    }

    public LoginPageAssert usernameFieldVisible() {
        actual.usernameField.shouldBe(visible);
        return this;
    }

    public LoginPageAssert passwordFieldVisible() {
        actual.passwordField.shouldBe(visible);
        return this;
    }

    public LoginPageAssert loginButtonVisible() {
        actual.loginButton.shouldBe(visible);
        return this;
    }

    public LoginPageAssert loginButtonEnabled() {
        actual.loginButton.shouldBe(visible).shouldBe(enabled);
        return this;
    }
}
