package net.centro.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class CentroMainPage extends BasePage<CentroMainPage> {

    public static final String CENTRO_URL = "https://www.centro.net/";
    private static final String COMPANY_TEXT = "Company";
    private static final String CAREERS_TEXT = "Careers";

    @FindBy(css = "#menu-main-menu > li")
    private List<WebElement> menuLinks;

    @FindBy(css = ".sub-menu > li")
    private List<WebElement> subMenuLinks;

    public CentroMainPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void load() {
        driver.get(CENTRO_URL);
        logger.info("Opened '{}'", CENTRO_URL);
    }

    @Override
    protected void isLoaded() throws Error {
        if (menuLinks.isEmpty()) {
            throw new AssertionError("No menu items are available on page");
        }
    }

    public void openMenu(String name) {
        selectItemInMenu(name, menuLinks);
    }

    public void openSubmenu(String name) {
        selectItemInMenu(name, subMenuLinks);
    }

    public CentroCareersPage openCareers() {
        openMenu(COMPANY_TEXT);
        openSubmenu(CAREERS_TEXT);
        return new CentroCareersPage(driver, true);
    }

    private void selectItemInMenu(String name, List<WebElement> items) {
        WebElement companyLink = items.stream()
                .filter(item -> name.equals(item.getText()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No " + name + " menu item available"));

        click(companyLink, name);
    }
}
