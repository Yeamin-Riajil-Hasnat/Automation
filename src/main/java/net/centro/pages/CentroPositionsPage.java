package net.centro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfNestedElementsLocatedBy;

public class CentroPositionsPage extends BasePage<CentroPositionsPage> {

    private final static By POSTING_GROUP_TITLE = By.cssSelector(".posting-category-title.large-category-label");
    private final static By POSITION_NAME = By.cssSelector("[data-qa=\"posting-name\"]");
    private final static By FILTER_OPTIONS = By.cssSelector(".filter-popup li");
    private final static By APPLIED_FILTER = By.className("has-selected-filter");

    @FindBy(css = "[aria-label^=\"Filter by City:\"]")
    private WebElement filterByCity;

    @FindBy(css = ".postings-group")
    private List<WebElement> positionGroups;

    public CentroPositionsPage(WebDriver driver) {
        super(driver);
    }

    public CentroPositionsPage(WebDriver driver, boolean waitUntilReady) {
        super(driver, waitUntilReady);
    }

    @Override
    protected void load() {}

    @Override
    protected void isLoaded() throws Error {
        try {
            filterByCity.isDisplayed();
        }
        catch (Exception e) {
            Assert.fail("'Filter by City' is not available");
        }
    }

    public void applyFilterByCity(String city) {
        click(filterByCity, "Filter by city");
        List<WebElement> filterOptions = waitFor(visibilityOfNestedElementsLocatedBy(filterByCity, FILTER_OPTIONS));
        WebElement cityElement = filterOptions.stream()
                .filter(o -> o.getText().contains(city))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No city " + city + " found in filter by city"));
        click(cityElement, city);
        waitFor(d -> filterByCity.findElements(APPLIED_FILTER).stream()
                .anyMatch(e -> e.getText().toLowerCase().contains(city.toLowerCase())));
    }

    public List<String> getAvailablePosition(String groupName) {
        WebElement groupElement = positionGroups.stream()
                .filter(g -> groupName.equalsIgnoreCase(g.findElement(POSTING_GROUP_TITLE).getText()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No '" + groupName + "' available in the list of groups"));
        return groupElement
                .findElements(POSITION_NAME)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
