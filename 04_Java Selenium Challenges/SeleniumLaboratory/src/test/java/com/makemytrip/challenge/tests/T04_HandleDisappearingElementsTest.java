package com.makemytrip.challenge.tests;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;

public class T04_HandleDisappearingElementsTest {

    /*
        Applications to practice:
        1. https://www.redbus.in/ -> Calendar pop-up
        2. https://www.flipkart.com/ -> Search-box dropdown
        3. https://www.airbnb.co.in/ -> User profile dropdown

        Ways to handle:
        1. Using `Developer Tools`:
        - Right-click on the website and select `Inspect` to open the `Developer Tools`
        - Press `Command + Shift + P`
        - Select "Emulate a focused page"

        2. Using a console command:
        - Right-click on the website and select `Inspect` to open the `Developer Tools`
        - Select the `Console` tab
        - Type `setTimeout(() => {debugger}, 5000);` and press `Enter`
        - This will pause the execution after 5 seconds and you can inspect the elements

        3. Using Extensions:
        - "Selectors Hub" Chrome extension
        - "Freeze DOM" Chrome extension

        4. Enabling mouse click to start debugger mode:
        - Right-click on the website and select `Inspect` to open the `Developer Tools`
        - Select the `Sources` tab
        - Try to find and expand the `Event Listener Breakpoints` section
        - Expand the `Mouse` section and check the `click` option
     */
    @Test
    public void tc001_handleDisappearingElementsTest() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.redbus.in/");

        // Click on the calendar icon
        driver.findElement(By.cssSelector("span.dateText")).click();

        // Get the holidays count from the calendar
        String holidaysCountText = null;
        try {
            WebElement holidaysCountElement = driver.findElement(
                    By.xpath("//div[contains(@class, 'holiday_count')]"));
            holidaysCountText = holidaysCountElement.getText();

        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (holidaysCountText == null) {
            System.out.println("No holidays are available this month");
        } else {
            System.out.println("Holidays count: " + holidaysCountText);
        }

        Assertions.assertThat(driver.getTitle()).contains("redBus");
        driver.close();
    }
}
