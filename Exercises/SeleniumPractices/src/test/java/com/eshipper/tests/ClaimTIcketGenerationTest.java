package com.eshipper.tests;

import com.aventstack.extentreports.ExtentTest;
import com.eshippper.base.TestBase;
import in.lifeofacoder.commons.CoreUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class ClaimTicketGenerationTest extends TestBase {

    @Test
    public void loginTest() {
        loginWithValidCredentials();
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void navigateToAddNewClaimTest() {
        ExtentTest test = extent.createTest("Navigate to the 'Add New Claim' page")
                .assignAuthor("deepjyoti-barman")
                .assignCategory("Regression")
                .assignDevice("macos-big-sur-11.4_chrome-92.0.4515.107");

        try {
            wait.until(ExpectedConditions.titleIs("Dashboard"));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            test.info("Landing on the 'Dashboard' page is successful");

            driver.findElement(By.xpath("//span[text()='Support']")).click();
            test.info("Clicked on the 'Support' tab");

            driver.findElement(By.xpath("//span[text()='Tickets']")).click();
            test.info("Clicked on the 'Tickets' tab");

            wait.until((ExpectedConditions.titleIs("Tickets")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            test.info("Landing on the 'Tickets' page is successful");

            driver.findElement(By.xpath("//div[text()='Claims']")).click();
            test.info("Clicked on the 'Clams' tab");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("ngx-overlay.foreground-closing")));
            driver.findElement(By.cssSelector("i.icon.icon-add")).click();
            test.info("Clicked on the 'Add New Claim' button");
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

    @Test(dependsOnMethods = {"navigateToAddNewClaimTest"})
    public void createNewClaimTest() {
        ExtentTest test = extent.createTest("Create a new ticket for Claim with details")
                .assignAuthor("deepjyoti-barman")
                .assignCategory("Regression")
                .assignDevice("macos-big-sur-11.4_chrome-92.0.4515.107");

        try {
            wait.until(ExpectedConditions.titleIs("Claim"));
            test.info("Landing on the 'Claim' page is successful");

            String fileName = "file-for-upload.txt";
            String filePath = System.getProperty("user.dir") + "/src/test/resources/upload/" + fileName;
            CoreUtils.focus(driver, driver.findElement(By.xpath("//span[contains(., 'Add Files')]")));
            driver.findElement(By.cssSelector("input[type='file'][multiple]")).sendKeys(filePath);
            WebElement fileUploadLabel = driver.findElement(By.cssSelector("span.mat-line"));
            Assert.assertEquals(fileUploadLabel.getText(), fileName, "File attachment failed");
            test.pass("Attaching file 'file-for-upload.txt' is successful");

            WebElement fileUploadSuccessIcon = driver.findElement(By.xpath("//input[@type='file' and @multiple='multiple']/../mat-list//div/i"));
            String classValue = fileUploadSuccessIcon.getAttribute("class");
            Assert.assertTrue(classValue.contains("icon-check"), "File upload failed");
            test.pass("File has been uploaded successfully");
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