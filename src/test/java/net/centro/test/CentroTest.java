package net.centro.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.centro.pages.CentroCareersPage;
import net.centro.pages.CentroMainPage;
import net.centro.pages.CentroPositionsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class CentroTest {

    public static final String ENGINEERING_GROUP_NAME = "Engineering";

    private Logger logger = LogManager.getLogger(this.getClass().getName());
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void checkOpenedEngineeringPositionsInToronto() {
        CentroMainPage mainPage = new CentroMainPage(driver).get();
        CentroCareersPage careersPage = mainPage.openCareers();
        CentroPositionsPage positionsPage = careersPage.clickAllCurrentOpeningsLink();
        positionsPage.applyFilterByCity("Toronto");
        List<String> positionNames = positionsPage.getAvailablePosition(ENGINEERING_GROUP_NAME);
        System.out.println("Opened positions:");
        positionNames.forEach(System.out::println);
        Assert.assertFalse(positionNames.isEmpty(), "No positions discovered in " + ENGINEERING_GROUP_NAME);
        //Assert.assertEquals(positionNames.size(), 3, "Amount of opened position doesn't correspond to expected");
    }
}
