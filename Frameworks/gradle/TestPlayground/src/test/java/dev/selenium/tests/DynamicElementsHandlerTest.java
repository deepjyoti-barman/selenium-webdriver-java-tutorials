package dev.selenium.tests;

import dev.selenium.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

/*
Scenario:
    1. Visit https://www.worldometers.info/world-population/
    2. Keep getting the count of:
        - Current World Population
        - Today: Births, Deaths and Population Growth
        - This Year: Births, Deaths and Population Growth
    3. Print the values on console for every second till 10 seconds
 */
public class DynamicElementsHandlerTest extends BaseTest {

    public void printPopulationData(String locatorCurrentPop, String locatorTodayThisYearPop) {

        String currentPopulation = driver.findElement(By.xpath(locatorCurrentPop)).getText();
        System.out.println("Current Population: " + currentPopulation);

        List<WebElement> todayThisYearPopElements = driver.findElements(By.xpath(locatorTodayThisYearPop));
        System.out.println("Births Today: " + todayThisYearPopElements.get(0).getText());
        System.out.println("Deaths Today: " + todayThisYearPopElements.get(1).getText());
        System.out.println("Population Growth Today: " + todayThisYearPopElements.get(2).getText());

        System.out.println("Births This Year : " + todayThisYearPopElements.get(3).getText());
        System.out.println("Deaths This Year: " + todayThisYearPopElements.get(4).getText());
        System.out.println("Population Growth This Year: " + todayThisYearPopElements.get(5).getText());

        System.out.println("-------------------------------------------------------------------");
    }

    @Test
    public void testToPrintDataValuesFor20secs() throws InterruptedException {
        driver.get("https://www.worldometers.info/world-population/");

        String xpathCurrentPopulation = "//div[@id='maincounter-wrap']//span[@rel='current_population']";
        String xpathTodayThisYearPopulation = "//div[text()='Today' or text()='This year']/parent::div//span[@class='rts-counter']";

        int timer = 0;
        while (timer++ < 10) {
            System.out.println("Timer Counter: " + timer + " sec(s)");
            printPopulationData(xpathCurrentPopulation, xpathTodayThisYearPopulation);
            Thread.sleep(1000);
        }
    }
}
