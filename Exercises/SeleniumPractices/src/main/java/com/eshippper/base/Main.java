package com.eshippper.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static in.lifeofacoder.commons.CoreUtils.getCurrentTimestamp;
import static in.lifeofacoder.commons.CoreUtils.killChromeDriver;
import static io.github.bonigarcia.wdm.config.DriverManagerType.CHROME;

public class Main {
    protected final String BASE_URL = "https://v2test.eshipper.com";
    protected final int MAX_RETRY = 100;
    protected WebDriver driver = null;
    protected WebDriverWait wait = null;
    protected ExtentReports extent = null;

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
            driver.get(BASE_URL);

            wait = new WebDriverWait(driver, 20);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        extent.flush();

        if (driver != null)
//            driver.quit();

        killChromeDriver();
    }
}
