import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class SeleniumLesson {
    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
    }

    @Tag("Selenium")
    @Test
    void driverTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@data-action='qty-change' and @data-step='1' and @data-id='21']")
            )
        );
        element.click();
        
        WebElement qtyField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("q-21"))
        );
        Assertions.assertThat(qtyField.getAttribute("value"))
                .as("Поле ввода количества должно быть равно 2")
                .isEqualTo("2");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
