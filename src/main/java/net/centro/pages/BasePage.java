package net.centro.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

public abstract class BasePage<T extends BasePage<T>> extends LoadableComponent<T> {

    public static final int WAIT_TIMEOUT_SECONDS = 20;
    protected WebDriver driver;
    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public BasePage(WebDriver driver, boolean waitUntilReady) {
        this(driver);
        if (waitUntilReady) {
            waitUntilReady();
        }
    }

    protected void waitUntilReady() {
        Function<WebDriver, Boolean> isReady = d -> {
            try {
                isLoaded();
                return true;
            }
            catch (Exception e) {
                return false;
            }
        };
        waitFor(isReady);
    }

    protected void click(WebElement element, String... text) {
        String valueToLog =  text.length > 0 ? text[0] : element.getText();
        element.click();
        logger.info("'{}' clicked", valueToLog);
    }

    protected <V> V waitFor(Function<WebDriver, V> isTrue) {
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS).until(isTrue);
    }
}
