package utils;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;

public abstract class AllureSelenideListener {
    @BeforeAll
    protected static void configurationAllureSelenide() {
        if (!SelenideLogger.hasListener("allure")) {
            SelenideLogger.addListener("allure", new AllureSelenide()
                    .screenshots(true)
                    .savePageSource(true));
        }
    }
}
