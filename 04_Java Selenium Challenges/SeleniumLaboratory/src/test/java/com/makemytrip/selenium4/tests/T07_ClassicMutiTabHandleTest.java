package com.makemytrip.selenium4.tests;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

/**
 * This test class demonstrates how to handle multiple tabs in classic and new way using Selenium 4.
 */
public class T07_ClassicMutiTabHandleTest {

    @Test
    public void tc001_multipleTabsOpenClassicTest() {

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        String parentWindowHandle = driver.getWindowHandle();

        // Click on OrangeHRM link and switch to new tab
        driver.findElement(By.xpath("//a[starts-with(text(), 'OrangeHRM')]")).click();
        Set<String> windowHandles = driver.getWindowHandles();
        windowHandles.remove(parentWindowHandle);
//        driver.switchTo().window(windowHandles.iterator().next());
        windowHandles.stream().findFirst().ifPresent(window -> driver.switchTo().window(window));

        // Verify 30-Day Free Trial input is displayed
        Boolean is30DaysTrialInputDisplayed = driver
                .findElement(By.xpath("//input[@value='30-Day Free Trial']"))
                .isDisplayed();
        Assertions.assertThat(is30DaysTrialInputDisplayed).isTrue();

        // Close the new tab and switch back to parent tab and close it as well
        driver.close();
        driver.switchTo().window(parentWindowHandle);
        driver.close();
    }

    @Test
    public void tc002_multipleTabsOpenNewTest() {

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        String primaryWindowHandle = driver.getWindowHandle();

        // Open a new tab and switch to it with the URL from the first tab
        String url = driver.findElement(By.xpath("//a[starts-with(text(), 'OrangeHRM')]"))
                .getAttribute("href");
        driver.switchTo().newWindow(WindowType.TAB);
        String secondaryWindowHandle = driver.getWindowHandle();
        driver.get(url);

        // Verify the 30-Day Free Trial input is displayed
        Boolean is30DaysTrialInputDisplayed = driver
                .findElement(By.xpath("//input[@value='30-Day Free Trial']"))
                .isDisplayed();
        Assertions.assertThat(is30DaysTrialInputDisplayed).isTrue();

        // Switch back to the primary tab and close it, then switch to the secondary tab and close it
        driver.switchTo().window(primaryWindowHandle);
        driver.close();
        driver.switchTo().window(secondaryWindowHandle);
        driver.close();
    }
}
