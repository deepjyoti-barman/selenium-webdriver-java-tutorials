package dev.selenium.tests;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.manager.SeleniumManager;
import org.testng.annotations.Test;

import java.time.Duration;

/*
Scenario:
    1. Visit https://testproject.io/
    2. Print the title of the page
    3. Scroll down to the bottom of the web-page
    4. Verify if the twitter icon is visible of not
 */
public class SVGElementHandlerTest {

    @Test
    public void testSvgElementVisibility() {

        // Selenium 4.6.0+ feature - no need to add any driver executables, added SeleniumManager as internal dependency
        // Print the path of the chromedriver
        String chromePath = SeleniumManager.getInstance().getDriverPath("chromedriver");
        System.out.println(chromePath);

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.get("https://testproject.io/");

        // Print the title of the page
        System.out.println(driver.getTitle());

        // Scroll to the bottom of the page
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Get the twitter icon element from the webpage
        WebElement twitterIcon = driver.findElement(By.xpath("//li[@class='sc-twitter']//*[local-name()='svg']//*[name()='path']"));

        // Verify if the twitter icon is visible
        Assertions.assertThat(twitterIcon.isDisplayed()).isTrue();

        driver.quit();
    }
}
