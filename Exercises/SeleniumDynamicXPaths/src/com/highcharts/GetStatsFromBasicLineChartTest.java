package com.highcharts;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

/**
 * Write a Selenium script to perform the following tasks in Chrome Browser:
 *  - Open up 'https://www.highcharts.com/'
 *  - Hover the mouse on 'Demo' tab
 *  - Click on 'Highcharts demos' link
 *  - Click on 'Basic line' image to expand it
 *  - Handle the hidden division pop-up by clicking on 'OK' button 
 *  - Retrieve, traverse through each point present in all the line element and print the yearly statistics provided over their in console 
 * 
 * @author Deepjyoti Barman
 * @since March 05, 2020
 */
public class GetStatsFromBasicLineChartTest
{
    @Test
    public void geStatsFromBasicLineChart() throws InterruptedException
    {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.highcharts.com/");
        
        // Creating the object of Actions class
        Actions action = new Actions(driver);
        
        // Hover the mouse on 'Demo' tab
        WebElement demoTab = driver.findElement(By.xpath("//a[text()='Demo ']"));
        action.moveToElement(demoTab).build().perform();
        
        // Click on 'Highcharts demos' link
        driver.findElement(By.xpath("//a[text()='Highcharts demos']")).click();
        
        // Click on 'Basic line' image and click on 'OK' button of the hidden division pop-up
        driver.findElement(By.xpath("//img[contains(@src, 'line-basic-default.png')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("CybotCookiebotDialogBodyLevelButtonAccept")).click();
        
        // Retrieve all the line elements present on the 'Basic line' graph
        List<WebElement> lineElements = driver.findElements(By.xpath("//*[name()='g' and contains(@aria-label, 'data points')]"));
        
        //  Traverse through and print each line element
        for (WebElement lineElement : lineElements)
        {
            // Extract the line element name
            String[] lineElementArr = lineElement.getAttribute("aria-label").split(",");
            String lineElementName = lineElementArr[0];
            System.out.println(lineElementName);
            System.out.println("=========================");
            
            // Retrieve all the co-ordinate points for each line element 
            List<WebElement> coordPoints = driver.findElements(By.xpath("//*[name()='g' and contains(@aria-label, '" + lineElementName + "')]/*[name()='path']"));
            
            // Traverse through all the co-ordinate points for each line element
            for (WebElement point : coordPoints)
            {
                action.moveToElement(point).build().perform();
                String[] pointInfoArr = point.getAttribute("aria-label").split("\\s");
                System.out.println("Year: " + pointInfoArr[2].replace(",", ""));
                System.out.println(lineElementName + ": " + pointInfoArr[3].replace(".", ""));
                System.out.println("----------------------");
            }
            
            System.out.println();
        }
    }
}
