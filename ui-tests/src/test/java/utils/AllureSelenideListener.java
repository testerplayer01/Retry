package utils;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;

public abstract class AllureSelenideListener {
    @BeforeAll
    protected static void configurationAllureSelenide() {
        if (!SelenideLogger.hasListener("allure")) {
            SelenideLogger.addListener("allure", createListener());
        }
    }

    public static void setSensitiveValue(SelenideElement element, String value) {
        boolean restoreListener = SelenideLogger.hasListener("allure");
        if (restoreListener) {
            SelenideLogger.removeListener("allure");
        }
        try {
            element.setValue(value);
        } finally {
            if (restoreListener) {
                SelenideLogger.addListener("allure", createListener());
            }
        }
    }

    private static AllureSelenide createListener() {
        return new AllureSelenide()
                .screenshots(true)
                .savePageSource(true);
    }
}
