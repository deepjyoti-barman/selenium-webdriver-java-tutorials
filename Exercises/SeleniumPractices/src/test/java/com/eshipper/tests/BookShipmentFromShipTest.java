package com.eshipper.tests;

import com.aventstack.extentreports.ExtentTest;
import com.eshippper.base.TestBase;
import in.lifeofacoder.commons.CoreUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookShipmentFromShipTest extends TestBase {

    @Test
    public void loginTest() {
        loginWithValidCredentials();
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void navigateToShipDetailsTest() {
        ExtentTest test = extent.createTest("Navigate to the 'Ship Details' page")
                .assignAuthor("deepjyoti-barman")
                .assignCategory("Regression")
                .assignDevice("macos-big-sur-11.4_chrome-92.0.4515.107");

        try {
            wait.until(ExpectedConditions.titleIs("Dashboard"));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            test.info("Landing on the 'Dashboard' page is successful");

            driver.findElement(By.xpath("//span[text()='Shipping']")).click();
            test.info("Clicked on the 'Shipping' tab");

            driver.findElement(By.xpath("//span[text()='Ship']")).click();
            test.info("Clicked on the 'Ship' tab");
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

    @Test(dependsOnMethods = {"navigateToShipDetailsTest"})
    public void navigateToOrderSummaryTest() {

        ExtentTest test = extent.createTest("Navigate to the 'Order Confirmed' page filling all the necessary fields")
                .assignAuthor("deepjyoti-barman")
                .assignCategory("Regression")
                .assignDevice("macos-big-sur-11.4_chrome-92.0.4515.107");

        try {
            wait.until((ExpectedConditions.titleIs("Ship Details")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            test.info("Landing on the 'Ship Details' page is successful");

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

            String packagingType = "Envelope";
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@aria-label='Packaging Type']")))).click();
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option/span[contains(text(), '" + packagingType + "')]")))).click();
            test.info("Changed 'Packaging Type' to '" + packagingType + "'");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            WebElement nextBtn = CoreUtils.focus(driver, driver.findElement(By.xpath("//button[@aria-label='Open Rates Button']")));
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(nextBtn))).click();
            test.info("Clicked on the 'Next' button in 'Ship Details' page");

            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//jhi-text-dialog/descendant::button[@aria-label='Close Button']")))).click();
            test.info("Closed the carrier info popup");

            wait.until((ExpectedConditions.titleIs("Edit Shipment")));
            test.info("Landing on the 'Edit Shipment' page is successful");

            String carrierTitle = "UPS";
            String carrierDesc = "UPS Saver";
            WebElement carrier = driver.findElement(By.xpath("//jhi-rate-line/descendant::div[text()='" + carrierTitle + "']/following-sibling::div/em[text()='" + carrierDesc + "']"));
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(carrier))).click();
            test.info("Selected carrier as '" + carrierTitle + " - " + carrierDesc + "'");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            driver.findElement(By.xpath("//div[text()='Schedule Pickup']/ancestor::mat-expansion-panel/descendant::input[@placeholder='MM/DD/YY']")).sendKeys(CoreUtils.getDate("MM/dd/yyyy", 1));
            test.info("Entered tomorrow's date in the 'Pickup Date' field");

            /*
            String pickupTime = "01:00";
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@aria-label='Pickup Time']")))).click();
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option/span[contains(text(), '" + pickupTime + "')]")))).click();
            test.info("Selected '" + pickupTime + "' as the 'Pickup Time'");

            String closingTime = "05:00";
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@aria-label='Closing Time']")))).click();
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option/span[contains(text(), '" + closingTime + "')]")))).click();
            test.info("Selected '" + closingTime + "' as the 'Closing Time'");
             */

            String pickupLocation = "Reception";
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@aria-label='Select Pickup Location']")))).click();
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option/span[contains(text(), '" + pickupLocation + "')]")))).click();
            test.info("Selected '" + pickupLocation + "' as the 'Pickup Location'");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Next Button']")))).click();
            test.info("Clicked on the 'Next' button in 'Edit Shipment' page");

            wait.until((ExpectedConditions.titleIs("Shipping Invoice")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            test.info("Landing on the 'Shipping Invoice' page is successful");

            String dutiable = "Yes";
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@aria-label='Select Dutiable']")))).click();
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option/span[contains(text(), '" + dutiable + "')]")))).click();
            test.info("Selected '" + dutiable + "' in the 'Dutiable' field");

            String billTo = "SHIPPER";
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@aria-label='Select Bill To']")))).click();
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option/span[contains(text(), '" + billTo + "')]")))).click();
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//jhi-invoice-notification-dialog/descendant::button[@aria-label='Close Button']")))).click();
            test.info("Selected '" + billTo + "' in the 'Bill to' field");

            String productList = "Logitech Stereo Speakers Z120";
            CoreUtils.focus(driver,driver.findElement(By.xpath("//mat-card-title[text()='Product']")));
            driver.findElement(By.xpath("//input[@placeholder='Product List']")).sendKeys(productList);
            test.info("Entered '" + productList + "' in the 'Product List' field");

            String description = "Computer peripherals";
            driver.findElement(By.xpath("//input[@placeholder='Description']")).sendKeys(description);
            test.info("Entered '" + description + "' in the 'Description' field");

            String countryOfOrigin = "India";
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.loading-foreground")));
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-select[@aria-label='Select COO']")))).click();
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option/span[starts-with(text(), ' " + countryOfOrigin + "')]")))).click();
            test.info("Selected '" + countryOfOrigin + "' as the 'Country of Origin'");

            String hsCode = "150009372";
            driver.findElement(By.xpath("//input[@placeholder='HS Code']")).sendKeys(hsCode);
            test.info("Entered '" + hsCode + "' in the 'HS Code' field");

            String quantity = "5";
            driver.findElement(By.xpath("//input[@placeholder='Quantity']")).sendKeys(quantity);
            test.info("Entered '" + quantity + "' in the 'Quantity' field");

            String amount = "15";
            driver.findElement(By.xpath("//input[@aria-label='Amount']")).sendKeys(amount);
            test.info("Entered '" + amount + "' in the 'Amount' field");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            nextBtn = CoreUtils.focus(driver, driver.findElement(By.xpath("//button[@aria-label='Next Button']")));
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(nextBtn))).click();
            test.info("Click on the 'Next' button on the 'Shipping Invoice' page");
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
        ExtentTest test = extent.createTest("Verify the shipment details in the 'Confirmed' page")
                .assignAuthor("deepjyoti-barman")
                .assignCategory("Regression")
                .assignDevice("macos-big-sur-11.4_chrome-92.0.4515.107");

        try {
            wait.until(ExpectedConditions.titleIs("Confirmed"));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            test.info("Landing on the 'Confirmed' page is successful");

            ArrayList<String> windows = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(windows.get(windows.size() - 1)).close();
            driver.switchTo().window(windows.get(0));

            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//jhi-text-dialog/descendant::button[@aria-label='Close Button']")))).click();
            String successMsg = driver.findElement(By.xpath("//div[@class='confirmed-info']/span")).getText();
            Assert.assertEquals(successMsg, "Your order is placed successfully.", "Shipment booking failed");

            List<WebElement> shipmentInfo = driver.findElements(By.xpath("//div[@class='confirmed-info']/strong"));
            test.pass("Shipment booking successful");
            test.info("Order tracking no = " + shipmentInfo.get(0).getText().split("#")[1]);
            test.info("Order id = " + shipmentInfo.get(1).getText().split("#")[1]);
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