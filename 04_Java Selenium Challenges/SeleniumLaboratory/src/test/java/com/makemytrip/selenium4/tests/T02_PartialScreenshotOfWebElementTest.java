package com.makemytrip.selenium4.tests;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

/**
 * This test includes a Selenium 4 feature to capture partial screenshot of a WebElement.
 */
public class T02_PartialScreenshotOfWebElementTest {

    @Test
    public void tc001_captureWebElementScreenshotTest() throws IOException {

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");

        // Classic way to capture the partial screenshot of the login form WebElement
        WebElement loginForm = driver.findElement(By.className("oxd-form"));
        File partialScreenshot1 = ((TakesScreenshot) loginForm).getScreenshotAs(OutputType.FILE);

        // New way to capture the partial screenshot of the login form WebElement
        File partialScreenshot2 = loginForm.getScreenshotAs(OutputType.FILE);

        // Save the screenshot to a file
        FileHandler.copy(partialScreenshot1, new File("src/test/resources/screenshots/login-form1.png"));
        FileHandler.copy(partialScreenshot2, new File("src/test/resources/screenshots/login-form2.png"));

        Assertions.assertThat(partialScreenshot1)
                .withFailMessage("Partial screenshot of login form is not captured in classic way")
                .exists();

        Assertions.assertThat(partialScreenshot2)
                .withFailMessage("Partial screenshot of login form is not captured in new way")
                .exists();

        driver.quit();
    }
}
