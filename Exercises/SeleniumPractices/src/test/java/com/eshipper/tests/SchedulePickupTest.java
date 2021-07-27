package com.eshipper.tests;

import com.aventstack.extentreports.ExtentTest;
import com.eshippper.base.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static in.lifeofacoder.commons.CoreUtils.*;

public class SchedulePickupTest extends Main {

    @Test
    public void loginTest() {
        ExtentTest test = extent.createTest("Login to EShipper with valid credentials")
                .assignAuthor("deepjyoti-barman")
                .assignCategory("Smoke")
                .assignCategory("Regression")
                .assignDevice("macos-big-sur-11.4_chrome-92.0.4515.107");
        try {
            driver.findElement(By.cssSelector("#username")).sendKeys("user");
            test.info("Entered 'user' into the username text field");

            driver.findElement(By.cssSelector("#password")).sendKeys("user");
            test.info("Entered 'user' into the password text field");

            driver.findElement(By.cssSelector("button[type='submit']")).click();
            test.info("Clicked on the 'Login' button");
            test.info("Login to the application with valid credentials is successful");
        } catch (Exception e) {
            test.fail(e.getClass().getName() + ": " + e.getMessage());

            try {
                test.addScreenCaptureFromPath(captureSnapshot(driver));
                e.printStackTrace();
                Assert.fail(e.getClass().getName() + ": " + e.getMessage());
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void navigateToQuickQuoteTest() {
        ExtentTest test = extent.createTest("Navigate to the 'Quick Quote' page")
                .assignAuthor("deepjyoti-barman")
                .assignCategory("Regression")
                .assignDevice("macos-big-sur-11.4_chrome-92.0.4515.107");

        try {
            wait.until(ExpectedConditions.titleIs("Dashboard"));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            test.info("Landing on the 'Dashboard' page is successful");

            driver.findElement(By.xpath("//span[text()='Shipping']")).click();
            test.info("Clicked on the 'Shipping' tab");

            driver.findElement(By.xpath("//span[text()='Quick Quote']")).click();
            test.info("Clicked on the 'Quick Quote' tab");
        } catch (Exception e) {
            test.fail(e.getClass().getName() + ": " + e.getMessage());

            try {
                test.addScreenCaptureFromPath(captureSnapshot(driver));
                e.printStackTrace();
                Assert.fail(e.getClass().getName() + ": " + e.getMessage());
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    @Test(dependsOnMethods = {"navigateToQuickQuoteTest"})
    public void navigateToOrderSummaryTest() {
        ExtentTest test = extent.createTest("Navigate to the 'Order Confirmed' page filling all the necessary fields")
                .assignAuthor("deepjyoti-barman")
                .assignCategory("Regression")
                .assignDevice("macos-big-sur-11.4_chrome-92.0.4515.107");

        try {
            wait.until((ExpectedConditions.titleIs("Quick Quote")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            test.info("Landing on the 'Quick Quote' page is successful");

            driver.findElement(By.xpath("//mat-card-title[text()='From']/ancestor::mat-card/descendant::input[@placeholder='Company']")).sendKeys("CA", Keys.TAB);
            driver.findElement(By.xpath("//mat-card-title[text()='From']/ancestor::mat-card/descendant::input[@placeholder='Postal/Zip Code']")).sendKeys("L4T3T1", Keys.TAB);

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            driver.findElement(By.xpath("//mat-card-title[text()='To']/ancestor::mat-card/descendant::input[@placeholder='Company']")).sendKeys("US", Keys.TAB);
            driver.findElement(By.xpath("//mat-card-title[text()='To']/ancestor::mat-card/descendant::input[@placeholder='Postal/Zip Code']")).sendKeys("10009", Keys.TAB);

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            WebElement shipmentSectionHeader = driver.findElement(By.xpath("//mat-card-title[text()='Shipment']"));
            focus(driver, shipmentSectionHeader);

            WebElement shipmentDate = driver.findElement(By.xpath("//mat-card-title[text()='Shipment']/ancestor::mat-card/descendant::input[@placeholder='MM/DD/YY']"));
            Actions action = new Actions(driver);
            if (System.getProperty("os.name").startsWith("Mac")) {
                action.click(shipmentDate)
                        .keyDown(Keys.COMMAND)
                        .sendKeys("a")
                        .keyUp(Keys.COMMAND)
                        .keyDown(Keys.COMMAND)
                        .sendKeys("a")
                        .keyUp(Keys.COMMAND)
                        .sendKeys(Keys.BACK_SPACE)
                        .sendKeys("07/29/2021")
                        .perform();
            } else {
                action.click(shipmentDate)
                        .keyDown(Keys.CONTROL)
                        .sendKeys("a")
                        .keyUp(Keys.CONTROL)
                        .keyDown(Keys.CONTROL)
                        .sendKeys("a")
                        .keyUp(Keys.CONTROL)
                        .sendKeys(Keys.BACK_SPACE)
                        .sendKeys("07/29/2021")
                        .perform();
            }

            String packagingType = "Pallet";
            driver.findElement(By.xpath("//mat-select[@aria-label='Packaging Type']")).click();
            driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + packagingType + "')]")).click();

            WebElement quantityTF = driver.findElement(By.xpath("//input[@aria-label='Quantity']"));
            quantityTF.clear();
            quantityTF.sendKeys("1");

            String stackable = "Yes";
            driver.findElement(By.xpath("//mat-select[@aria-label='Stackable']")).click();
            driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + stackable + "')]")).click();

            driver.findElement(By.xpath("//input[@placeholder='L(in)']")).sendKeys("1");
            driver.findElement(By.xpath("//input[@placeholder='W(in)']")).sendKeys("2");
            driver.findElement(By.xpath("//input[@placeholder='H(in)']")).sendKeys("3");
            driver.findElement(By.xpath("//input[@placeholder='W(lbs)']")).sendKeys("24");

            String freightClass = "60";
            driver.findElement(By.xpath("//mat-select[@aria-label='Select Freight']")).click();
            driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + freightClass + "')]")).click();

            String shipmentType = "Pipes";
            driver.findElement(By.xpath("//mat-select[@aria-label='Select Shipment']")).click();
            driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + shipmentType + "')]")).click();

            driver.findElement(By.xpath("//input[@placeholder='Description']")).sendKeys("This is a test description");

            WebElement palletScheduleSectionHeader = driver.findElement(By.xpath("//mat-card-title[text()='Pallet Schedule']"));
            focus(driver, palletScheduleSectionHeader);

            String departureReadyTime = "00:00";
            driver.findElement(By.xpath("//mat-select[@aria-label='Departure Close Time']")).click();
            driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + departureReadyTime + "')]")).click();

            String departureCloseTime = "01:00";
            driver.findElement(By.xpath("//mat-select[@aria-label='Departure Close Time']")).click();
            driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + departureCloseTime + "')]")).click();

            String deliveryCloseTime  = "02:00";
            driver.findElement(By.xpath("//mat-select[@aria-label='Delivery Close Time']")).click();
            // Applying custom wait to find the element
            int retry = 0;
            while (retry < MAX_RETRY) {
                try {
                    driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + deliveryCloseTime + "')]")).click();
                    break;
                } catch (StaleElementReferenceException sere) {
                    if (retry == MAX_RETRY - 1)
                        throw sere;
                    else {
                        retry++;
                        Thread.sleep(100);
                    }
                }
            }

            WebElement getQuoteBtn = driver.findElement(By.xpath("//button[@aria-label='Get Quote Button']"));
            focus(driver, getQuoteBtn);
            getQuoteBtn.click();

            String carrierTitle = "project44";
            String carrierDesc = "UPS";
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            String totalAmtOnDialog = driver.findElement(By.xpath("//div[text()='" + carrierTitle + "']/following-sibling::div/em[text()='" + carrierDesc + "']/ancestor::div[@class='start-position']/following-sibling::div[@class='end-position']/div/strong")).getText();
            driver.findElement(By.xpath("//div[text()='" + carrierTitle + "']/following-sibling::div/em[text()='" + carrierDesc + "']")).click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            // Applying custom wait before performing assertion on the element
            retry = 0;
            String totalAmtOnQQPage = "";
            while (retry < MAX_RETRY) {
                totalAmtOnQQPage = driver.findElement(By.xpath("//jhi-rate-line[@class='quote-footer-line']/descendant::strong")).getText();

                if (!totalAmtOnQQPage.isEmpty())
                    break;

                Thread.sleep(100);
                retry++;
            }
            Assert.assertEquals(totalAmtOnDialog, totalAmtOnQQPage);

            driver.findElement(By.xpath("//button[@aria-label='Quick Quote Proceed Button']")).click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            driver.findElement(By.xpath("//mat-card-title[text()='From']/ancestor::mat-card/descendant::input[@placeholder='Address 1']")).sendKeys("2184 Emerson Avenue");
            driver.findElement(By.xpath("//mat-card-title[text()='From']/ancestor::mat-card/descendant::input[@placeholder='Attention']")).sendKeys("John Smith");
            driver.findElement(By.xpath("//mat-card-title[text()='From']/ancestor::mat-card/descendant::input[@placeholder='Phone']")).sendKeys("6133726854");
            driver.findElement(By.xpath("//mat-card-title[text()='From']/ancestor::mat-card/descendant::input[@placeholder='Email']")).sendKeys("john.smith@outlook.com");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            driver.findElement(By.xpath("//mat-card-title[text()='To']/ancestor::mat-card/descendant::input[@placeholder='Address 1']")).sendKeys("882 Lighthouse Drive");
            driver.findElement(By.xpath("//mat-card-title[text()='To']/ancestor::mat-card/descendant::input[@placeholder='Attention']")).sendKeys("Kyle Wills");
            driver.findElement(By.xpath("//mat-card-title[text()='To']/ancestor::mat-card/descendant::input[@placeholder='Phone']")).sendKeys("4166699342");
            driver.findElement(By.xpath("//mat-card-title[text()='To']/ancestor::mat-card/descendant::input[@placeholder='Email']")).sendKeys("kyle.wills@yahoo.co.in");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            WebElement nextBtn = driver.findElement(By.xpath("//button[@aria-label='Next Button']"));
            focus(driver, nextBtn);
            nextBtn.click();
        } catch (Exception e) {
            test.fail(e.getClass().getName() + ": " + e.getMessage());

            try {
                test.addScreenCaptureFromPath(captureSnapshot(driver));
                e.printStackTrace();
                Assert.fail(e.getClass().getName() + ": " + e.getMessage());
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    @Test(dependsOnMethods = {"navigateToOrderSummaryTest"})
    public void verifyDetailsInOrderSummaryTest() {
        ExtentTest test = extent.createTest("Verify the shipment details in the 'Order Confirmed' page")
                .assignAuthor("deepjyoti-barman")
                .assignCategory("Regression")
                .assignDevice("macos-big-sur-11.4_chrome-92.0.4515.107");

        try {
            wait.until(ExpectedConditions.titleIs("Order Confirmed"));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            test.info("Landing on the 'Order Confirmed' page is successful");

            String successMsg = driver.findElement(By.xpath("//div[@class='confirmed-info']/span")).getText();
            Assert.assertEquals(successMsg, "Your order is placed successfully.", "Shipment booking failed");
            String trackingId = driver.findElement(By.xpath("//div[@class='confirmed-info']/strong")).getText();
            test.pass("Shipment booking successful, order tracking id = " + trackingId);
        } catch (Exception e) {
            test.fail(e.getClass().getName() + ": " + e.getMessage());

            try {
                test.addScreenCaptureFromPath(captureSnapshot(driver));
                e.printStackTrace();
                Assert.fail(e.getClass().getName() + ": " + e.getMessage());
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }
}