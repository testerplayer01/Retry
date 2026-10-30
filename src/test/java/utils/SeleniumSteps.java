package utils;

import io.qameta.allure.Step;
import io.qameta.allure.Param;
import io.qameta.allure.model.Parameter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public final class SeleniumSteps {
    private SeleniumSteps() {
    }

    @Step("Открыть страницу {url}")
    public static void open(WebDriver driver, String url) {
        driver.get(url);
    }

    @Step("Нажать элемент {locatorDescription}")
    public static void click(WebDriver driver, By locator, String locatorDescription) {
        wait(driver).until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    @Step("Ввести имя пользователя")
    public static void enterUsername(
            WebDriver driver,
            By locator,
            @Param(mode = Parameter.Mode.MASKED) String username
    ) {
        enterText(driver, locator, username);
    }

    @Step("Ввести пароль")
    public static void enterPassword(
            WebDriver driver,
            By locator,
            @Param(mode = Parameter.Mode.MASKED) String password
    ) {
        enterText(driver, locator, password);
    }

    @Step("Ввести текст в поле {locatorDescription}")
    public static void enterText(
            WebDriver driver,
            By locator,
            @Param(mode = Parameter.Mode.MASKED) String value,
            String locatorDescription
    ) {
        WebElement element = wait(driver).until(ExpectedConditions.elementToBeClickable(locator));
        element.clear();
        element.sendKeys(value);
    }

    @Step("Проверить текст элемента {locatorDescription}")
    public static void assertElementText(
            WebDriver driver,
            By locator,
            String locatorDescription,
            String expectedText
    ) {
        WebElement element = wait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        assertThat(element.getText()).contains(expectedText);
    }

    @Step("Проверить точное значение элемента {locatorDescription}")
    public static void assertElementTextEquals(
            WebDriver driver,
            By locator,
            String locatorDescription,
            String expectedText
    ) {
        WebElement element = wait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        assertThat(element.getText()).isEqualTo(expectedText);
    }

    @Step("Проверить, что числовое значение элемента {locatorDescription} больше {minimum}")
    public static void assertElementNumberGreaterThan(
            WebDriver driver,
            By locator,
            String locatorDescription,
            int minimum
    ) {
        WebElement element = wait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        assertThat(Integer.parseInt(element.getText().trim())).isGreaterThan(minimum);
    }

    @Step("Проверить значение атрибута {attributeName} элемента {locatorDescription}")
    public static void assertElementAttributeEquals(
            WebDriver driver,
            By locator,
            String locatorDescription,
            String attributeName,
            String expectedValue
    ) {
        WebElement element = wait(driver).until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        assertThat(element.getAttribute(attributeName)).isEqualTo(expectedValue);
    }

    @Step("Прочитать атрибут {attributeName} элемента {locatorDescription}")
    public static String getElementAttribute(
            WebDriver driver,
            By locator,
            String locatorDescription,
            String attributeName
    ) {
        return wait(driver)
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getAttribute(attributeName);
    }

    @Step("Проверить, что элемент {locatorDescription} отображается")
    public static void assertElementVisible(
            WebDriver driver,
            By locator,
            String locatorDescription
    ) {
        assertThat(wait(driver)
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .isDisplayed()).isTrue();
    }

    @Step("Обновить страницу")
    public static void refresh(WebDriver driver) {
        driver.navigate().refresh();
    }

    @Step("Заполнить поле формы")
    private static void enterText(
            WebDriver driver,
            By locator,
            @Param(mode = Parameter.Mode.MASKED) String value
    ) {
        WebElement element = wait(driver).until(ExpectedConditions.elementToBeClickable(locator));
        element.clear();
        element.sendKeys(value);
    }

    private static WebDriverWait wait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }
}
