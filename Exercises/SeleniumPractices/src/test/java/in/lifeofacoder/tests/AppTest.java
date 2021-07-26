package in.lifeofacoder.tests;

import static io.github.bonigarcia.wdm.config.DriverManagerType.CHROME;
import static in.lifeofacoder.core.Utils.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AppTest {
    String baseUrl = "https://v2test.eshipper.com";
    WebDriver driver = null;
    WebDriverWait wait = null;
    ExtentReports extent = null;

    @BeforeClass
    public void init() {
        try {
            final File CONF = new File("src/main/resources/config/spark-config.json");
            ExtentSparkReporter spark = new ExtentSparkReporter("target/spark-reports/test-results_" + getCurrentTimestamp() + ".html");
            spark.loadJSONConfig(CONF);

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Company Name", "MakeMyTrip India Pvt. Ltd.");
            extent.setSystemInfo("Project Name", "EShippper");
            extent.setSystemInfo("OS", "macOS Big Sur");
            extent.setSystemInfo("OS Version", "11.4");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Browser Version", "92.0.4515.107");

//            WebDriverManager.chromedriver().setup();
            WebDriverManager.getInstance(CHROME).setup();

            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(baseUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            test.info("Login to the application with valid credentials successful");
        } catch (Exception e) {
            test.fail(e.getMessage());

            try {
                test.addScreenCaptureFromPath(captureSnapshot(driver));
                Assert.fail(e.getMessage());
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void navigateToAddNewClaimTest() {
        ExtentTest test = extent.createTest("Navigate to 'Add New Claim' section")
                .assignAuthor("deepjyoti-barman")
                .assignCategory("Regression")
                .assignDevice("macos-big-sur-11.4_chrome-92.0.4515.107");

        try {
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.titleIs("Dashboard"));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            test.info("Landing on the Dashboard page successful");

            driver.findElement(By.xpath("//span[text()='Support']")).click();
            test.info("Clicked on the 'Support' tab");

            driver.findElement(By.xpath("//span[text()='Tickets']")).click();
            test.info("Clicked on the 'Tickets' tab");

            wait.until((ExpectedConditions.titleIs("Tickets")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ngx-overlay.foreground-closing")));
            test.info("Landing on the Tickets page successful");

            driver.findElement(By.xpath("//div[text()='Claims']")).click();
            test.info("Clicked on the 'Clams' tab");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("ngx-overlay.foreground-closing")));
            driver.findElement(By.cssSelector("i.icon.icon-add")).click();
            test.info("Clicked on the 'Add New Claim' button");
        } catch (Exception e) {
            test.fail(e.getMessage());

            try {
                test.addScreenCaptureFromPath(captureSnapshot(driver));
                Assert.fail(e.getMessage());
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
            test.info("Landing on the Claim page successful");

            String fileName = "file-for-upload.txt";
            String filePath = System.getProperty("user.dir") + "/src/test/resources/upload/" + fileName;
            WebElement fileUploadBtn = driver.findElement(By.xpath("//span[contains(., 'Add Files')]"));
            focus(driver, fileUploadBtn);
            driver.findElement(By.cssSelector("input[type='file'][multiple]")).sendKeys(filePath);
            WebElement fileUploadLabel = driver.findElement(By.cssSelector("span.mat-line"));
            Assert.assertEquals(fileUploadLabel.getText(), fileName, "File attachment failed");
            test.pass("Attaching file 'file-for-upload.txt' successful");

            WebElement fileUploadSuccessIcon = driver.findElement(By.xpath("//input[@type='file' and @multiple='multiple']/../mat-list//div/i"));
            String classValue = fileUploadSuccessIcon.getAttribute("class");
            Assert.assertTrue(classValue.contains("icon-check"), "File upload failed");
            test.pass("File has been uploaded successfully");
        } catch (Exception e) {
            test.fail(e.getMessage());

            try {
                test.addScreenCaptureFromPath(captureSnapshot(driver));
                Assert.fail(e.getMessage());
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    @AfterClass
    public void tearDown() {
        extent.flush();

        if (driver != null)
            driver.quit();

        killChromeDriver();
    }
}