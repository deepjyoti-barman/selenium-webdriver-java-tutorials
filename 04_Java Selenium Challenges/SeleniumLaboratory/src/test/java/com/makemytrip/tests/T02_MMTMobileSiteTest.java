package com.makemytrip.tests;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v127.emulation.Emulation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Optional;

/**
 * This test includes a Selenium 4 feature of directly accessing CDP (Chrome DevTools Protocol) commands from the WebDriver
 * and emulate a mobile device of given screen dimensions and user-agent string.
 */
public class T02_MMTMobileSiteTest {

    @Test
    public void tc001_verifyAvailabilityOfAllBookingOptionsTest() throws InterruptedException {

        // Set-up ChromeOptions
        ChromeOptions options = new ChromeOptions();

        // userAgent :: Samsung Galaxy S20 Ultra
        // String userAgent = "Mozilla/5.0 (Linux; Android 13; SM-G981B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Mobile Safari/537.36";

        // Add user-agent string
        // userAgent :: iPhone 14 Pro Max (iOS 17.4)
        String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 17_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.4 Mobile/15E148 Safari/604.1";
        options.addArguments("user-agent=" + userAgent);

        // Initialize ChromeDriver with ChromeOptions
        ChromeDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Set up DevTools to emulate mobile device
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Emulation.setDeviceMetricsOverride(
                430,
                932,
                100,
                true,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));

//        driver.get("https://www.makemytrip.com/tripideas/");
//        Thread.sleep(2000);
//
//        // Method 1: Scroll into an element
//        WebElement heading = driver.findElement(By.xpath("//h3[text()='Romantic Places']"));
//        new Actions(driver).scrollToElement(heading).perform();
//        Thread.sleep(2000);
//
//        // Method 2: Scroll into an element
//        heading = driver.findElement(By.xpath("//h3[text()='Trekking Hotspots']"));
//        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//        jsExecutor.executeScript("arguments[0].scrollIntoView(true)", heading);
//        Thread.sleep(2000);

        driver.get("https://www.makemytrip.com/tripideas/places/goa");

        WebElement bookButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Book']")));
        bookButton.click();
        Thread.sleep(1000);

        boolean isTravelVisible = driver.findElement(By.xpath("//h3[text()='Travel']")).isDisplayed();
        Assertions.assertThat(isTravelVisible)
                .withFailMessage("'Travel' option is not visible on the destination page")
                .isTrue();

        boolean isHotelsVisible = driver.findElement(By.xpath("//h3[text()='Hotels']")).isDisplayed();
        Assertions.assertThat(isHotelsVisible)
                .withFailMessage("'Hotels' option is not visible on the destination page")
                .isTrue();

        boolean isPackagesVisible = driver.findElement(By.xpath("//h3[text()='Packages']")).isDisplayed();
        Assertions.assertThat(isPackagesVisible)
                .withFailMessage("'Packages' option is not visible on the destination page")
                .isTrue();

        driver.quit();
    }
}
