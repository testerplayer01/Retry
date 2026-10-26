import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
    SelenideElement usernameField = $x("//*[@id='username']");
    SelenideElement passwordField = $x("//*[@id='password']");
    SelenideElement loginButton = $x("//*[@type='submit']");

    public LoginPage enterUsername(String username) {
        usernameField.setValue(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    public LoginPage clickLoginButton() {
        loginButton.click();
        return this;
    }

    public LoginPage waitForPageLoaded(){
        usernameField.shouldBe(visible);
        passwordField.shouldBe(visible);
        loginButton.shouldBe(visible);
        return this;
    }
}
