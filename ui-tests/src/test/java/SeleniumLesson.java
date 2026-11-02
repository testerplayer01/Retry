import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.ConfigParser;
import utils.SeleniumSteps;


public class SeleniumLesson {
    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        SeleniumSteps.open(driver, ConfigParser.getBaseUrl());
    }

    @Tag("Selenium")
    @Test
    void driverTest() {
        By increaseButton = By.xpath(
                "(//*[@data-action='qty-change' and @data-step='1'])[1]"
        );
        String productId = SeleniumSteps.getElementAttribute(
                driver,
                increaseButton,
                "кнопка увеличения количества",
                "data-id"
        );
        SeleniumSteps.click(
                driver,
                increaseButton,
                "увеличить количество товара"
        );
        SeleniumSteps.assertElementAttributeEquals(
                driver,
                By.id("q-" + productId),
                "поле количества товара",
                "value",
                "2"
        );
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
