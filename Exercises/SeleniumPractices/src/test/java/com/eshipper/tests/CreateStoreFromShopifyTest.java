package com.eshipper.tests;

import in.lifeofacoder.commons.CoreUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CreateStoreFromShopifyTest {

    /**
     * Custom wait for delaying selenium code execution until the new browser windows open. It can be used primarily when the no of windows to be open is not certain.
     *
     * @param driver active driver instance in use
     * @param timeOutInSeconds maximum time to wait for the window to open
     * @return an integer value based on the number of windows currently open
     * @throws InterruptedException if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when this exception is thrown.
     */
    public int waitForNewWindowsToOpen(WebDriver driver, int timeOutInSeconds) throws InterruptedException {
        int count = 0;

        while (count < timeOutInSeconds) {
            int windowHandles = driver.getWindowHandles().size();

            if (windowHandles > 1) {
                return windowHandles;
            }

            Thread.sleep(1000);
            count++;
        }

        return 1;
    }

    @Test
    public void verifyStoreCreationTest() throws InterruptedException {
        WebDriverManager.getInstance(DriverManagerType.CHROME).setup();

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.shopify.com/partners");

        driver.findElement(By.xpath("//nav[@id='ShopifyMainNav']/descendant::a[text()='Log in']")).click();
        wait.until(ExpectedConditions.titleIs("Login - Shopify"));

        driver.findElement(By.id("account_email")).sendKeys("technology@eshipper.com");
        ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.name("commit")))).click();
        ((WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("account_password")))).sendKeys("Lockdown1$");
        ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.name("commit")))).click();
        wait.until(ExpectedConditions.titleIs("Organizations - Shopify Partners"));

        driver.findElement(By.xpath("//span[text()='Eshipper.com']")).click();
        wait.until(ExpectedConditions.titleIs("Apps - Shopify Partners"));

        driver.findElement(By.xpath("//a[text()='v2test-app-es-v2']")).click();
        wait.until(ExpectedConditions.titleIs("Shopify Partners"));

        driver.findElement(By.xpath("//div[@class='action-bar__top-links']/descendant::a[contains(., 'Test on development store')]")).click();
        wait.until(ExpectedConditions.titleIs("Test app on development store - Shopify Partners"));

        // Solution: https://www.py4u.net/discuss/173518
        WebElement v2TestStoreBtn = driver.findElement(By.xpath("//span[text()='v2test-store']"));
        wait.until(ExpectedConditions.stalenessOf(v2TestStoreBtn));     // Wait until the element becomes stale (i.e. the page / part of the page is refreshed and the element's reference is lost)
        v2TestStoreBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='v2test-store']")));      // Once the element is visible re-assign the new reference to the element
//        WebElement v2TestStoreBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='v2test-store']")));     // This one line, by directly assigning the element's reference once the element is visible would also solve this issue
        v2TestStoreBtn.click();
        wait.until(ExpectedConditions.titleIs("Shopify"));

        driver.findElement(By.xpath("//div[text()='Eshipper CWW']")).click();

        try {
            wait.until(ExpectedConditions.titleContains("v2test-store"));
            CoreUtils.focus(driver, driver.findElement(By.id("proceed_cta"))).click();
        } catch (TimeoutException ignored) {

        }

        wait.until(ExpectedConditions.titleIs("Shopify"));

        driver.switchTo().frame("app-iframe");
        driver.findElement(By.id("eshipText")).sendKeys("user");
        driver.findElement(By.id("eshipPassword")).sendKeys("user");
        driver.findElement(By.id("eshipperAuth")).click();
//        int noOfWindows = waitForNewWindowsToOpen(driver, 10);            // Alternate customized method which can be used when the no of windows to be open is uncertain
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        String childWindowHandle = new ArrayList<String>(driver.getWindowHandles()).get(1);
        driver.switchTo().window(childWindowHandle);

        try {
            wait.until(ExpectedConditions.titleIs("Sign in"));

            ((WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")))).sendKeys("user");
            ((WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")))).sendKeys("user");
            driver.findElement(By.xpath("//button[text()='Login']")).click();

            wait.until(ExpectedConditions.titleIs("Dashboard"));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));

            driver.findElement(By.xpath("//span[text()='eCommerce']")).click();
            driver.findElement(By.xpath("//span[text()='Store Settings']")).click();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        wait.until((ExpectedConditions.titleIs("Store Settings")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));

        driver.findElement(By.xpath("//button[@aria-label='Search button']")).click();
        driver.findElement(By.xpath("//input[@aria-label='Search']")).sendKeys("v2test-store");

        int noOfElements = driver.findElements(By.xpath("//span[text()='v2test-store']")).size();
        Assert.assertTrue(noOfElements > 0, "Store creation or search has been failed");
    }

    @AfterTest
    public void driverKill() {
        CoreUtils.killChromeDriver();
    }
}