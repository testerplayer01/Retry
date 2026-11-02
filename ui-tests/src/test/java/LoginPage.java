import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import utils.ConfigParser;
import utils.AllureSelenideListener;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
    SelenideElement usernameField = $x("//*[@id='username']");
    SelenideElement passwordField = $x("//*[@id='password']");
    SelenideElement loginButton = $x("//*[@type='submit']");

    @Step("Ввожу имя пользователя")
    public LoginPage enterUsername() {
        AllureSelenideListener.setSensitiveValue(usernameField, ConfigParser.getAdminLogin());
        return this;
    }

    @Step("Ввожу пароль")
    public LoginPage enterPassword() {
        AllureSelenideListener.setSensitiveValue(passwordField, ConfigParser.getAdminPassword());
        return this;
    }

    @Step("Нажать на кнопку 'Sign in'")
    public LoginPage clickLoginButton() {
        loginButton.click();
        return this;
    }

    @Step("Ожидание загрузки страницы входа в админ-панель")
    public LoginPage waitForPageLoaded(){
        usernameField.shouldBe(visible);
        passwordField.shouldBe(visible);
        loginButton.shouldBe(visible);
        return this;
    }
}
