package com.makemytrip.tests;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

/**
 * This test includes a Selenium 4 feature to capture the screenshot of the complete webpage.
 * NOTE: This feature is available only for Firefox browser.
 */
public class T06_FullPageScreenshotTest {

    @Test
    public void tc001_captureFullPageScreenshotTest() throws IOException {

        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://tutorialsninja.com/demo/");

        // Capture the full-page screenshot
        File fullPageScreenshot = ((FirefoxDriver) driver).getFullPageScreenshotAs(OutputType.FILE);
        FileHandler.copy(fullPageScreenshot, new File("src/test/resources/screenshots/full-page.png"));

        Assertions.assertThat(fullPageScreenshot)
                .withFailMessage("Full page screenshot is not captured")
                .exists();

        driver.quit();
    }
}
