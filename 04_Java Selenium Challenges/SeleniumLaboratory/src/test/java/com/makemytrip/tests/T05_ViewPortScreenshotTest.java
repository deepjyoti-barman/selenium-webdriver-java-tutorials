package com.makemytrip.tests;

import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

/**
 * This test includes a Selenium 4 feature to capture the screenshot of the visible view-port in both old and new ways.
 */
public class T05_ViewPortScreenshotTest {

    @Test
    public void tc001_captureViewPortScreenshotTest() throws IOException {

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://tutorialsninja.com/demo/");

        // Classic way to capture the screenshot of the current visible view-port
        File viewPortScreenshot1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(viewPortScreenshot1, new File("src/test/resources/screenshots/view-port1.png"));

        // New way to capture the screenshot of the current visible view-port
        File viewPortScreenshot2 = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(viewPortScreenshot2, new File("src/test/resources/screenshots/view-port2.png"));

        Assertions.assertThatCode(() -> {
            Assertions.assertThat(viewPortScreenshot1)
                    .withFailMessage("Partial screenshot of the view-port is not captured in classic way")
                    .exists();

            Assertions.assertThat(viewPortScreenshot2)
                    .withFailMessage("Partial screenshot of the view-port is not captured in new way")
                    .exists();
        }).doesNotThrowAnyException();

        driver.quit();
    }
}
