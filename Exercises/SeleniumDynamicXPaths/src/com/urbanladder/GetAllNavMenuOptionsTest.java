package com.urbanladder;

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
 *  - Open up 'https://www.urbanladder.com/'
 *  - Click on the 'Close' button of the hidden division pop-up
 *  - Retrieve, traverse through and print all the item options available on top navigation bar
 *  - While traversing hover your mouse over each item option of top navigation bar to populate sub-menu headers and sub-menu item-list for each category and print them in console
 *  
 * @author Deepjyoti Barman
 * @since March 03, 2020
 */
public class GetAllNavMenuOptionsTest
{    
    @Test
    public void getAllNavMenuOptionsViaDynamicXpath() throws InterruptedException
    {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.urbanladder.com/");
        
        // Creating the object of Actions class
        Actions action = new Actions(driver);
                
        // Click on the 'Close' button of the hidden division pop-up
        driver.findElement(By.xpath("//a[contains(text(), 'Close')]")).click();
        
        // Get all the items from top navigation bar and traverse through all the items of top navigation bar
        List<WebElement> topNavItems = driver.findElements(By.xpath("//div[@id='topnav_wrapper']/descendant::span[@class='topnav_itemname']"));
        for (int i = 0; i < topNavItems.size(); i++)
        {
            // Getting the item names from top navigation bar
            String topNavItemName = topNavItems.get(i).getText();
            System.out.println("\n" + topNavItemName);
            System.out.println("-----------------------------------");
            
            // Hovering mouse on each element
            action.moveToElement(topNavItems.get(i)).build().perform();
            Thread.sleep(1000);
            
            // Building the XPath to get the sub-menu headers for each item available in top navigation bar
            String subMenuHeaderXpath = "//div[@id='topnav_wrapper']/descendant::span[contains(text(), '" + topNavItemName + "')]/parent::li/descendant::div[@class='taxontype']/a";
            
            // Retrieving all the sub-menu headers in a list and traversing through each one of them
            List<WebElement> subMenuHeaders = driver.findElements(By.xpath(subMenuHeaderXpath));
            for (int j = 0; j < subMenuHeaders.size(); j++)
            {
                String subMenuHeaderName = subMenuHeaders.get(j).getText();
                System.out.println(subMenuHeaderName);
                System.out.println("===================================");
                
                // Building the XPath to get the sub-menu items for each sub-menu header
                String subMenuItemsXpath = "//div[@id='topnav_wrapper']/descendant::span[contains(text(), '" + topNavItemName + "')]/parent::li/descendant::div[@class='taxontype']//child::a[text()='" + subMenuHeaderName + "']/ancestor::li[@class='sublist_item']/ul/li/a/span";
                
                // Retrieving all the sub-menu items in a list and traversing through each one of them
                List<WebElement> subMenuItems = driver.findElements(By.xpath(subMenuItemsXpath));
                for (int k = 0; k < subMenuItems.size(); k++)
                {
                    String subMenuItemName = subMenuItems.get(k).getText();
                    System.out.println(subMenuItemName);
                }
                
                System.out.println();
            }

            System.out.println("***********************************");
        }
    }
}
