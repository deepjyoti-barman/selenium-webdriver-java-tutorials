package com.makemytrip.challenge.tests;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class T03_ZoomInZoomOutTest {

    @Test
    public void tc001_zoomInAndOutTest() throws IOException {

        // Zoom-out functionality in Firefox
        ChromeDriver driverChrome = new ChromeDriver();
        driverChrome.manage().window().maximize();
        driverChrome.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driverChrome.get("https://naveenautomationlabs.com/opencart/");

        zoom(driverChrome, "50");
        FileHandler.copy(driverChrome.getScreenshotAs(OutputType.FILE),
                new File("src/test/resources/screenshots/zoomedOut.png"));


        // Zoom-in functionality in Firefox
        FirefoxDriver driverFF = new FirefoxDriver();
        driverFF.manage().window().maximize();
        driverFF.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driverFF.get("https://naveenautomationlabs.com/opencart/");

        zoom(driverFF, "150");
        FileHandler.copy(driverFF.getScreenshotAs(OutputType.FILE),
                new File("src/test/resources/screenshots/zoomedIn.png"));

        Assertions.assertThat(driverFF.getTitle()).contains("Your Store");

        driverChrome.close();
        driverFF.close();
    }

    public static void zoom(WebDriver driver, String zoomPercentage) {
        String zoom = "document.body.style.zoom='" + zoomPercentage + "%'";
        ((JavascriptExecutor) driver).executeScript(zoom);
    }
}
