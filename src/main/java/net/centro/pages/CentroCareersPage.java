package net.centro.pages;


import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class CentroCareersPage extends BasePage<CentroCareersPage> {

    public static final int EXPECTED_NUMBER_OF_TABS = 2;

    @FindBy(xpath = "//a[text()='View all current openings']")
    private WebElement viewAllCurrentOpeningsLink;

    public CentroCareersPage(WebDriver driver) {
        super(driver);
    }

    public CentroCareersPage(WebDriver driver, boolean waitUntilReady) {
        super(driver, waitUntilReady);
    }

    public CentroPositionsPage clickAllCurrentOpeningsLink() {
        String handler = driver.getWindowHandle();
        click(viewAllCurrentOpeningsLink);
        waitFor(ExpectedConditions.numberOfWindowsToBe(EXPECTED_NUMBER_OF_TABS));
        String newHandler = driver.getWindowHandles().stream()
                .filter(h -> !h.equals(handler))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No new tab found"));
        driver.switchTo().window(newHandler);
        return new CentroPositionsPage(driver, true);
    }

    @Override
    protected void isLoaded() throws Error {
        try {
            viewAllCurrentOpeningsLink.isDisplayed();
        }
        catch (Exception e) {
            Assert.fail("'View all current openings' link is not available");
        }
    }

    @Override
    protected void load() {}


}
