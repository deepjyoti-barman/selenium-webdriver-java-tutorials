package com.makemytrip.selenium4.tests;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

/**
 * This test demonstrates the getRect() method to get the height, width, x, y of an element using Selenium 4.
 */
public class T06_GetRectTest {

    @Test
    public void tc001_getRectTest() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://bstackdemo.com/");
        driver.manage().window().maximize();

        WebElement iPhone12Image = driver.findElement(By.xpath("//img[@alt='iPhone 12']"));
        Rectangle rect = iPhone12Image.getRect();
        Dimension dimension = rect.getDimension();
        Point point = rect.getPoint();

        // Get the height, width, x, y of iPhone 12 image element
        System.out.println("Height: " + rect.getHeight());
        System.out.println("Width: " + rect.getWidth());
        System.out.println("X: " + rect.getX());
        System.out.println("Y: " + rect.getY());

        System.out.println("Height: " + dimension.height);
        System.out.println("Width: " + dimension.width);

        System.out.println("X: " + point.x);
        System.out.println("Y: " + point.y);

        Assertions.assertThat(rect.getHeight()).isEqualTo(dimension.height);
        Assertions.assertThat(rect.getX()).isEqualTo(point.x);

        driver.quit();
    }
}
