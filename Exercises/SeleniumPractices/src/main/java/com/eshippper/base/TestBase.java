package com.eshippper.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import in.lifeofacoder.commons.CoreUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestBase {
    protected final String DEFAULT_CONFIG_PATH = "src/main/resources/eshipper-config/application.properties";
    protected final int MAX_RETRY = 100;
    protected WebDriver driver = null;
    protected WebDriverWait wait = null;
    protected ExtentReports extent = null;

    public void loginWithValidCredentials() {
        ExtentTest test = extent.createTest("Login to EShipper with valid credentials")
                .assignAuthor("deepjyoti-barman")
                .assignCategory("Smoke")
                .assignCategory("Regression")
                .assignDevice("macos-big-sur-11.4_chrome-92.0.4515.107");

        try {
            driver.findElement(By.cssSelector("#username")).sendKeys(CoreUtils.getProperty("username", DEFAULT_CONFIG_PATH));
            test.info("Entered '" + CoreUtils.getProperty("username", DEFAULT_CONFIG_PATH) + "' into the username text field");

            driver.findElement(By.cssSelector("#password")).sendKeys(CoreUtils.getProperty("password", DEFAULT_CONFIG_PATH));
            test.info("Entered '" + CoreUtils.getProperty("password", DEFAULT_CONFIG_PATH) + "' into the password text field");

            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Login']")))).click();
            test.info("Clicked on the 'Login' button");
            test.info("Login to the application with valid credentials is successful");
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

    @BeforeTest
    public void init() {
        try {
            final File CONF = new File("src/main/resources/lifeofacoder-config/spark-config.json");
            ExtentSparkReporter spark = new ExtentSparkReporter("target/spark-reports/test-results_" + CoreUtils.getCurrentTimestamp() + ".html");
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
            WebDriverManager.getInstance(DriverManagerType.CHROME).setup();

            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(CoreUtils.getProperty("appUrl", DEFAULT_CONFIG_PATH));

            wait = new WebDriverWait(driver, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void logout() {
        ExtentTest test = extent.createTest("Logout from the user logged into the EShipper application")
                .assignAuthor("deepjyoti-barman")
                .assignCategory("Smoke")
                .assignCategory("Regression")
                .assignDevice("macos-big-sur-11.4_chrome-92.0.4515.107");

        try {
            WebElement logoutBtn = CoreUtils.focus(driver, driver.findElement(By.xpath("//button[@ng-reflect-router-link='/logout']")));
            ((WebElement) wait.until(ExpectedConditions.elementToBeClickable(logoutBtn))).click();
            test.info("Clicked on the 'Logout' button");

            wait.until(ExpectedConditions.titleIs("Sign in"));
            test.info("Successfully logged out of the active user");
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

    @AfterTest
    public void tearDown() {
        extent.flush();

        if (driver != null)
            driver.quit();

        CoreUtils.killChromeDriver();
    }
}
