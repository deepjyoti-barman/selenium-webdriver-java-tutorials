package com.makemytrip.selenium4.tests;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

/**
 * This test class demonstrates how to handle multiple tabs in a classic way.
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
}
