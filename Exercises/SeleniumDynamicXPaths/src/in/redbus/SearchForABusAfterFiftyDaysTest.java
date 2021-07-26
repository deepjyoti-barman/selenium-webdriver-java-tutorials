package in.redbus;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

/**
 * Write a Selenium script to perform the following tasks in Chrome Browser:
 *  - Disable notification pop-ups
 *  - Open up 'https://www.redbus.in/'
 *  - Enter "Bangalore" in 'From' textbox and select "Majestic, Bangalore"
 *  - Enter "Mysore" in `To` textbox and select "Mysore (All Locations)"
 *  - Click on the 'Onward Date' label and select a date 50 days from current date
 *  - Click on the 'Return Date' label and select a date 15 days from 'Onward Date'
 *  - Finally click on 'Search Buses' button  
 * 
 * @author Deepjyoti Barman
 * @since March 04, 2020
 */
public class SearchForABusAfterFiftyDaysTest
{
    @Test
    public void searchForABusAfterFiftyDays() throws InterruptedException
    {
        // Disable notifications pop-ups
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.redbus.in/");
        
        // Enter "Bangalore" in 'From' textbox and select "Majestic, Bangalore"
        driver.findElement(By.id("src")).sendKeys("Bangalore");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@id='src']/parent::div/descendant::li[text()='Majestic, Bangalore']")).click();
        
        // Enter "Mysore" in `To` textbox and select "Mysore (All Locations)"
        driver.findElement(By.id("dest")).sendKeys("Mysore");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@id='dest']/parent::div/descendant::li[text()='Mysore (All Locations)']")).click();
        
        // ------------------------------------------------------------------------------------

        // Calculating the 'Onward Date' - day, month and year
        LocalDate onwardDate = LocalDate.now().plusDays(50);
        int onwardDay = onwardDate.getDayOfMonth();
        
        String onwardMonth = onwardDate.getMonth().toString();
        if (onwardMonth.length() > 3)
            onwardMonth = onwardMonth.substring(0, 3);
        
        int onwardYear = onwardDate.getYear();
        String onwardMonthAndYearEstimated = onwardMonth + " " + onwardYear;
        System.out.println(onwardDate + " " + onwardMonthAndYearEstimated);
        
        // Click on the 'Onward Date' label
        driver.findElement(By.xpath("//label[@for='onward_cal']")).click();
        
        // Retrieving month and year of 'Onward Date' from calendar 
        String onwardMonthAndYearOnCal = driver.findElement(By.xpath("//div[@id='rb-calendar_onward_cal']/descendant::td[@class='monthTitle']")).getText();
        
        // Traverse through the months present on 'Onward Date' calendar
        while (!onwardMonthAndYearOnCal.equalsIgnoreCase(onwardMonthAndYearEstimated))
        {
            driver.findElement(By.xpath("//div[@id='rb-calendar_onward_cal']/descendant::td[@class='next']/button")).click();
            onwardMonthAndYearOnCal = driver.findElement(By.xpath("//div[@id='rb-calendar_onward_cal']/descendant::td[@class='monthTitle']")).getText();
        }
        
        // Select the Day for 'Onward Date'
        String onwardDateSelectorXpath = "//div[@id='rb-calendar_onward_cal']/descendant::td[text()='" + onwardDay + "']";
        driver.findElement(By.xpath(onwardDateSelectorXpath)).click();
        
        // ------------------------------------------------------------------------------------
        
        // Calculating the 'Return Date' - day, month and year
        LocalDate returnDate = onwardDate.plusDays(15);
        int returnDay = returnDate.getDayOfMonth();
        
        String returnMonth = returnDate.getMonth().toString();
        if (returnMonth.length() > 3)
            returnMonth = returnMonth.substring(0, 3);
        
        int returnYear = returnDate.getYear();
        String returnMonthAndYearEstimated = returnMonth + " " + returnYear;
        System.out.println(returnDate + " " + returnMonthAndYearEstimated);
        
        // Click on the 'Return Date' label
        driver.findElement(By.xpath("//label[@for='return_cal']")).click();
        
        // Retrieving month and year of 'Return Date' from calendar
        String returnMonthAndYearOnCal = driver.findElement(By.xpath("//div[@id='rb-calendar_return_cal']/descendant::td[@class='monthTitle']")).getText();
        
        // Traverse through the months present on 'Return Date' calendar
        while (!returnMonthAndYearOnCal.equalsIgnoreCase(returnMonthAndYearEstimated))
        {
            driver.findElement(By.xpath("//div[@id='rb-calendar_return_cal']/descendant::td[@class='next']/button")).click();
            returnMonthAndYearOnCal = driver.findElement(By.xpath("//div[@id='rb-calendar_return_cal']/descendant::td[@class='monthTitle']")).getText();
        }
        
        // Select the Day for 'Return Date'
        String returnDateSelectorXpath = "//div[@id='rb-calendar_return_cal']/descendant::td[text()='" + returnDay + "']";
        driver.findElement(By.xpath(returnDateSelectorXpath)).click();
        
        // Click on the 'Search Buses' button
        driver.findElement(By.id("search_btn")).click();
    }
}