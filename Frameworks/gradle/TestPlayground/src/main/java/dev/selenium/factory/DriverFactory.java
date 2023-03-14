package dev.selenium.factory;

import dev.selenium.constants.IAppConstants;
import dev.selenium.exceptions.FrameworkException;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class DriverFactory {

    public WebDriver driver;
    public IConfigFactory config;
    public OptionsManager optionsManager;
    public static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    public IConfigFactory getConfig() {
        // Run setting up system properties: mvn clean install -Denv=staging
        String envName;
        if (System.getProperty("env") != null)
            envName = System.getProperty("env");
        else if (System.getenv("env") != null)
            envName = System.getenv("env");
        else
            envName = "prod";
        System.out.println("Running tests on environment: " + envName);

        IConfigFactory config = ConfigFactory.create(IConfigFactory.class);
        return config;
    }

    public WebDriver initDriver(IConfigFactory config) {
        optionsManager = new OptionsManager(config);
        String browserName = config.browser().toLowerCase().trim();

        if (browserName.equals("chrome")) {
            threadLocalDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
        } else if (browserName.equals("firefox")) {
            threadLocalDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
        } else if (browserName.equals("edge")) {
            threadLocalDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
        } else {
            System.out.println("ERROR - Kindly pass the correct or supported browser name to proceed");
            throw new FrameworkException("Invalid or unsupported browser name found");
        }

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(IAppConstants.DEFAULT_MEDIUM_TIME_OUT));
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        return getDriver();
    }

    public synchronized static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public static String getScreenshot(WebDriver driver) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
        File destination = new File(path);
        try {
            FileHandler.copy(srcFile, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
