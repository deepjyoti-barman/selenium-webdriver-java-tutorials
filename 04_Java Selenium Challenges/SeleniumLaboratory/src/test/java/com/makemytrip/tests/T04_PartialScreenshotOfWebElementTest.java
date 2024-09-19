package com.makemytrip.tests;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

/**
 * This test includes a Selenium 4 feature to capture partial screenshot of a WebElement.
 */
public class T04_PartialScreenshotOfWebElementTest {

    @Test
    public void tc001_captureWebElementScreenshotTest() throws IOException {

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");

        // Capture partial screenshot of the login form WebElement
        WebElement loginForm = driver.findElement(By.className("oxd-form"));
        File partialScreenshhot = loginForm.getScreenshotAs(OutputType.FILE);

        // Save the screenshot to a file
        FileHandler.copy(partialScreenshhot, new File("src/test/resources/screenshots/login-form.png"));

        Assertions.assertThat(partialScreenshhot)
                .withFailMessage("Partial screenshot of login form is not captured")
                .exists();

        driver.quit();
    }
}
