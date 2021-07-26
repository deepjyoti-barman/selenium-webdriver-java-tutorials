package com.espncricinfo;

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
 *  - Open up 'https://www.espncricinfo.com/'
 *  - Hover onto the first match result posted
 *  - Click on 'Scorecard' tab
 *  - Retrieve and display the team names
 *  - Print both teams batting scorecards in console the way it is present in the website as tables retrieving each table data  
 * 
 * @author Deepjyoti Barman
 * @since March 06, 2020
 */
public class GetFirstMatchBattingTableDataTest
{
    @Test
    public void getFirstMatchBattingTableData() throws InterruptedException
    {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.espncricinfo.com/");
        
        // Creating the object of Actions class
        Actions action = new Actions(driver);
        
//        driver.findElement(By.xpath("//div[@id='overlaybg']/descendant::a")).click();
        
        // Hover onto the first match result posted
        WebElement firstMatchResultSection = driver.findElement(By.xpath("(//span[text()='Result'])[1]"));
        action.moveToElement(firstMatchResultSection).build().perform();
        
        // Click on 'Scorecard' tab
        driver.findElement(By.xpath("(//span[text()='Result'])[1]/ancestor::div[contains(@class, 'cscore cricket')]/descendant::a[contains(text(), 'Scorecard')]")).click();
        
        // Retrieve and display the team names
        List<WebElement> teamNames = driver.findElements(By.xpath("//div[@class='main-content']//span[contains(@class, 'cscore_name cscore_name--long')]"));
        System.out.println("Team A: " + teamNames.get(0).getAttribute("innerHTML"));
        System.out.println("Team B: " + teamNames.get(1).getAttribute("innerHTML") + "\n\n");
        
        // Retrieve, traverse through and print each the section header
        List<WebElement> sectionHeaders = driver.findElements(By.xpath("//div[contains(@id, 'gp-inning')]/parent::li/descendant::h2"));
        for (WebElement sectionHeader : sectionHeaders)
        {
            String sectionHeaderName = sectionHeader.getText();
            System.out.println(sectionHeaderName);
            System.out.println("=============================================================");
            
            // Retrieve, traverse through and print each scorecard header
            List<WebElement> scorecardHeaders = driver.findElements(By.xpath("//h2[text()='" + sectionHeaderName + "']/ancestor::li/descendant::div[@class='wrap header']/div[@class='cell batsmen' or @class='cell commentary' or @class='cell runs']"));
            for (WebElement scorecardHeader : scorecardHeaders)
            {
                System.out.printf("%30s", scorecardHeader.getText());
            }
            System.out.println();
            
            // Retrieve, traverse through and print each player name
            List<WebElement> players = driver.findElements(By.xpath("//h2[text()='" + sectionHeaderName + "']/ancestor::li/descendant::div[@class='wrap batsmen']/div[@class='cell batsmen']/a"));
            for (WebElement player : players)
            {
                String playerName = player.getText();
                
                // Retrieve, traverse through and print score details of each player
                List<WebElement> scoresDetails = driver.findElements(By.xpath("//h2[text()='" + sectionHeaderName + "']/ancestor::li/descendant::a[text()='" + playerName + "']/ancestor::div[@class='wrap batsmen']/div[@class='cell batsmen' or @class='cell commentary' or @class='cell runs']"));
                for (WebElement score : scoresDetails)
                {
                    System.out.printf("%30s", score.getText());
                }
                
                System.out.println();
            }
            
            System.out.println("\n---------------------------------------------------------------------------------------------------------------"
                    + "------------------------------------------------------------------------------------------------------------------------\n");
        }
    }
}