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
import java.util.Random;

public class UIHomeworkSelenium {
    WebDriver driver;

    @BeforeEach //task5
    void setup() {
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/");

    }
  @Tag("Selenium")
    @Test
    void task1() {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      WebElement element = wait.until(
              ExpectedConditions.elementToBeClickable(
                      By.xpath("//*[@class='btn-outline']")
              )
      );
      element.click();

      WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
      WebElement username = wait2.until(ExpectedConditions.elementToBeClickable(By.id("username")));
      username.clear();
      username.sendKeys("admin");

      WebElement password = wait2.until(ExpectedConditions.elementToBeClickable(By.id("password")));
      password.clear();
      password.sendKeys("secret123");

      driver.findElement(By.xpath("//*[@type='submit']")).click();

      String productName = "Товар " + new Random().nextInt(1, 999);
      String productPrice = String.valueOf(new Random().nextInt(10, 200));

      WebElement name = wait2.until(ExpectedConditions.elementToBeClickable(By.id("n-name")));
      name.clear();
      name.sendKeys(productName);

      WebElement price = wait2.until(ExpectedConditions.elementToBeClickable(By.id("n-price")));
      price.clear();
      price.sendKeys(productPrice);

      driver.findElement(By.xpath("//*[@class='btn btn-add']")).click();
      driver.findElement(By.xpath("/html/body/div[2]/div[1]/a")).click();
      WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(5));

      driver.navigate().refresh();
      wait3.until(ExpectedConditions.presenceOfElementLocated(By.className("product-card")));

      WebElement productCard = wait3.until(
              ExpectedConditions.visibilityOfElementLocated(
                      By.xpath("//*[@class='product-card']/h4[contains(text(), '" + productName + "')]"))
      );
      Assertions.assertThat(productCard.getText())
              .as("Название товара должно быть - " + productName)
              .isEqualTo(productName);
    }
  @Tag("Selenium")
    @Test
    void task2() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@data-action='add-to-cart']")
                )
        );
        element.click();

         WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(5));
         WebElement cart = wait4.until(ExpectedConditions.elementToBeClickable(
                 By.id("open-cart-btn")
                 )
         );
         cart.click();

         WebElement cartItem = wait4.until(
                 ExpectedConditions.visibilityOfElementLocated(
                         By.xpath("//*[@class='cart-item']"))
         );
         Assertions.assertThat(cartItem.isDisplayed())
                 .as("Элемент в корзине должен быть отображён")
                 .isTrue();

    }

   @Tag("Selenium")
   @Test
    void task3(){
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
       WebElement element = wait.until(
               ExpectedConditions.elementToBeClickable(
                       By.xpath("//*[@class='btn-outline']")
               )
       );
       element.click();

       WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
       WebElement username = wait2.until(ExpectedConditions.elementToBeClickable(By.id("username")));
       username.clear();
       username.sendKeys("admin2");

       WebElement password = wait2.until(ExpectedConditions.elementToBeClickable(By.id("password")));
       password.clear();
       password.sendKeys("secret1234");

       driver.findElement(By.xpath("//*[@type='submit']")).click();

       WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(5));
       WebElement message = wait5.until(
               ExpectedConditions.visibilityOfElementLocated(
                       By.xpath("//*[@class='alert alert-danger']")
               )
       );
       Assertions.assertThat(message.isDisplayed())
               .as("Сообщение об ошибке должно быть отображено")
               .isTrue();
   }

  @Tag("Selenium")
  @Test
    void task4(){
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
      WebElement element = wait.until(
              ExpectedConditions.elementToBeClickable(
                      By.xpath("//*[@data-action='add-to-cart']")
              )
      );
      element.click();

      WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(5));
      WebElement cart = wait4.until(ExpectedConditions.elementToBeClickable(
                      By.id("open-cart-btn")
              )
      );
      cart.click();

      driver.navigate().refresh();

      WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(5));
      WebElement cartCount = wait6.until(
              ExpectedConditions.visibilityOfElementLocated(
                      By.xpath("//*[@id='cart-count']")
              )
      );
      int count = Integer.parseInt(cartCount.getText().trim());
      Assertions.assertThat(count)
              .as("Количество товаров в корзине должно быть больше 0")
              .isGreaterThan(0);


  }
    @AfterEach //task5
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
