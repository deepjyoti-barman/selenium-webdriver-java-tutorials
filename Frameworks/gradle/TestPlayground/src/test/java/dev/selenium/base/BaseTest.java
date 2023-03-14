package dev.selenium.base;

import dev.selenium.factory.DriverFactory;
import dev.selenium.factory.IConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {

    DriverFactory driverFactory;
    protected WebDriver driver;
    protected IConfigFactory config;

    @BeforeTest
    public void init() {
        driverFactory = new DriverFactory();
        config = driverFactory.getConfig();
        driver = driverFactory.initDriver(config);
    }

    @AfterTest
    public void terminate() {
        if (config.closeBrowser() && driver != null)
            driver.quit();
    }
}
