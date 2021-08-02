package com.eshipper.tests;

import com.aventstack.extentreports.ExtentTest;
import com.eshippper.base.TestBase;
import in.lifeofacoder.commons.CoreUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

public class BookShipmentFromQQTest extends TestBase {

    @Test
    public void loginTest() {
        loginWithValidCredentials();
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
                test.addScreenCaptureFromPath(CoreUtils.captureSnapshot(driver));
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

            driver.findElement(By.xpath("//mat-card-title[text()='From']/ancestor::mat-card/descendant::button[@aria-label='Add New Location Button']")).click();
            driver.findElement(By.xpath("//mat-dialog-container/descendant::input[@placeholder='Company']")).sendKeys("CAN");
            driver.findElement(By.xpath("//mat-dialog-container/descendant::input[@placeholder='Postal/Zip Code']")).sendKeys("L4T3T1");
            driver.findElement(By.xpath("//input[@name='address1']")).sendKeys("2184 Emerson Avenue");
            driver.findElement(By.xpath("//input[@name='attention']")).sendKeys("John Smith");
            driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("6133726854");
            driver.findElement(By.xpath("//input[@name='email']")).sendKeys("john.smith@outlook.com");
            test.info("Filled up all the necessary fields in the 'From' section");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Apply Button']")))).click();
            test.info("Clicked on the 'Add' button under the 'From' section");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            driver.findElement(By.xpath("//mat-card-title[text()='To']/ancestor::mat-card/descendant::button[@aria-label='Add New Location Button']")).click();
            driver.findElement(By.xpath("//mat-dialog-container/descendant::input[@placeholder='Company']")).sendKeys("USA", Keys.TAB);
            driver.findElement(By.xpath("//mat-dialog-container/descendant::input[@placeholder='Postal/Zip Code']")).sendKeys("10009", Keys.TAB);
            driver.findElement(By.xpath("//input[@name='address1']")).sendKeys("882 Lighthouse Drive");
            driver.findElement(By.xpath("//input[@name='attention']")).sendKeys("Kyle Wills");
            driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("4166699342");
            driver.findElement(By.xpath("//input[@name='email']")).sendKeys("kyle.wills@yahoo.co.in");
            test.info("Filled up all the necessary fields in the 'To' section");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Apply Button']")))).click();
            test.info("Clicked on the 'Add' button under the 'To' section");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            CoreUtils.focus(driver, driver.findElement(By.xpath("//mat-card-title[text()='Shipment']")));

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
                        .sendKeys(CoreUtils.getDate("MM/dd/yyyy", 1))
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
                        .sendKeys(CoreUtils.getDate("MM/dd/yyyy", 1))
                        .perform();
            }
            test.info("Entered tomorrow's date in the 'Ship Date' field");

            String packagingType = "Pallet";
            driver.findElement(By.xpath("//mat-select[@aria-label='Packaging Type']")).click();
            driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + packagingType + "')]")).click();
            test.info("Changed 'Packaging Type' to '" + packagingType + "'");

            String quantity = "1";
            WebElement quantityTF = driver.findElement(By.xpath("//input[@aria-label='Quantity']"));
            quantityTF.clear();
            quantityTF.sendKeys(quantity);
            test.info("Selected '" + quantity + "' as the 'Pickup Location'");

            String stackable = "Yes";
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@aria-label='Stackable']")))).click();
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option/span[contains(text(), '" + stackable + "')]")))).click();
            test.info("Selected '" + stackable + "' in 'Stackable' field");

            String length = "1";
            driver.findElement(By.xpath("//input[@placeholder='L(in)']")).sendKeys("1");
            test.info("Entered '" + length + "' as length in the L(in) field");

            String width = "2";
            driver.findElement(By.xpath("//input[@placeholder='W(in)']")).sendKeys("2");
            test.info("Entered '" + width + "' as width in the W(in) field");

            String height = "3";
            driver.findElement(By.xpath("//input[@placeholder='H(in)']")).sendKeys("3");
            test.info("Entered '" + height + "' as height in the H(in) field");

            String weight = "24";
            driver.findElement(By.xpath("//input[@placeholder='W(lbs)']")).sendKeys("24");
            test.info("Entered '" + weight + "' as weight in the W(lbs) field");

            String freightClass = "60";
            driver.findElement(By.xpath("//mat-select[@aria-label='Select Freight']")).click();
            driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + freightClass + "')]")).click();
            test.info("Selected '" + freightClass + "' in the 'Freight Class' field");

            String shipmentType = "Pipes";
            driver.findElement(By.xpath("//mat-select[@aria-label='Select Shipment']")).click();
            driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + shipmentType + "')]")).click();
            test.info("Selected '" + shipmentType + "' in the 'Shipment Type' field");

            String description = "This is a test description";
            driver.findElement(By.xpath("//input[@placeholder='Description']")).sendKeys(description);
            test.info("Entered '" + description + "' in the 'Description' field");

            CoreUtils.focus(driver, driver.findElement(By.xpath("//mat-card-title[text()='Pallet Schedule']")));

            String departureReadyTime = "00:00";
            driver.findElement(By.xpath("//mat-select[@aria-label='Departure Close Time']")).click();
            driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + departureReadyTime + "')]")).click();
            test.info("Selected '" + departureReadyTime + "' as the 'Departure Ready Time'");

            String departureCloseTime = "01:00";
            driver.findElement(By.xpath("//mat-select[@aria-label='Departure Close Time']")).click();
            driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + departureCloseTime + "')]")).click();
            test.info("Selected '" + departureCloseTime + "' as the 'Departure Close Time'");

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
            test.info("Selected '" + deliveryCloseTime + "' as the 'Delivery Close Time'");

            CoreUtils.focus(driver, driver.findElement(By.xpath("//button[@aria-label='Get Quote Button']"))).click();

            String carrierTitle = "project44";
            String carrierDesc = "UPS";
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            String totalAmtOnDialog = driver.findElement(By.xpath("//div[text()='" + carrierTitle + "']/following-sibling::div/em[text()='" + carrierDesc + "']/ancestor::div[@class='start-position']/following-sibling::div[@class='end-position']/div/strong")).getText();
            WebElement carrierProject44UPS = driver.findElement(By.xpath("//div[text()='" + carrierTitle + "']/following-sibling::div/em[text()='" + carrierDesc + "']"));
            wait.until(ExpectedConditions.elementToBeClickable(carrierProject44UPS));
            carrierProject44UPS.click();
            test.info("Selected carrier as '" + carrierTitle + " - " + carrierDesc + "'");

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
            Assert.assertEquals(totalAmtOnDialog, totalAmtOnQQPage, "Total amount on carrier selection dialog does not match with total amount on quick quote page");
            test.pass("Total amount on carrier selection dialog matches with total amount on quick quote page");

            driver.findElement(By.xpath("//button[@aria-label='Quick Quote Proceed Button']")).click();
            test.info("Clicked on the 'Quick Quote' button");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            CoreUtils.focus(driver, driver.findElement(By.xpath("//button[@aria-label='Next Button']"))).click();
            test.info("Clicked on the 'Next' button");
        } catch (Exception e) {
            test.fail(e.getClass().getName() + ": " + e.getMessage());

            try {
                test.addScreenCaptureFromPath(CoreUtils.captureSnapshot(driver));
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

            ArrayList<String> windows = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(windows.get(windows.size() - 1)).close();
            driver.switchTo().window(windows.get(0));

            String successMsg = driver.findElement(By.xpath("//div[@class='confirmed-info']/span")).getText();
            Assert.assertEquals(successMsg, "Your order is placed successfully.", "Shipment booking failed");
            String trackingId = driver.findElement(By.xpath("//div[@class='confirmed-info']/strong")).getText();
            test.pass("Shipment booking successful, order tracking no = " + trackingId.split("#")[1]);
        } catch (Exception e) {
            test.fail(e.getClass().getName() + ": " + e.getMessage());

            try {
                test.addScreenCaptureFromPath(CoreUtils.captureSnapshot(driver));
                e.printStackTrace();
                Assert.fail(e.getClass().getName() + ": " + e.getMessage());
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }
}