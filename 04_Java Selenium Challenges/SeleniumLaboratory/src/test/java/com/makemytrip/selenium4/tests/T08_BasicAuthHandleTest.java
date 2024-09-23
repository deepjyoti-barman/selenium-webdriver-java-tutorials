package com.makemytrip.selenium4.tests;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v127.network.Network;
import org.openqa.selenium.devtools.v127.network.model.Headers;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Browser level Basic Authentication pop-up handle test in classic and new way using Selenium 4.
 */
public class T08_BasicAuthHandleTest {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    // This classic way of handling Basic Authentication does not work in Edge browser
    @Test
    public void tc001_basicAuthHandleClassicTest() {

        WebDriver driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://" + USERNAME + ":" + PASSWORD + "@the-internet.herokuapp.com/basic_auth");

        WebElement welcomeTextElement = driver.findElement(
                By.xpath("//p[contains(text(),'Congratulations!')]"));
        Assertions.assertThat(welcomeTextElement.isDisplayed()).isTrue();

        driver.close();
    }

    @Test
    public void tc002_basicAuthHandleNew1Test() {

        WebDriver driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));

        // Send auth headers
        Map<String, Object> headers = new HashMap<>();
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((USERNAME + ":" + PASSWORD).getBytes());
        headers.put("Authorization", basicAuth);
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

        driver.get("https://the-internet.herokuapp.com/basic_auth");

        WebElement welcomeTextElement = driver.findElement(
                By.xpath("//p[contains(text(),'Congratulations!')]"));
        Assertions.assertThat(welcomeTextElement.isDisplayed()).isTrue();

        driver.close();
    }

    @Test
    public void tc003_basicAuthHandleNew2Test() {

        WebDriver driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Register basic auth for specific sites
        ((HasAuthentication) driver).register(
                uri -> uri.getHost().equals("the-internet.herokuapp.com"),
                () -> new UsernameAndPassword(USERNAME, PASSWORD));
        driver.get("https://the-internet.herokuapp.com/basic_auth");

        WebElement welcomeTextElement = driver.findElement(
                By.tagName("p"));
        Assertions.assertThat(welcomeTextElement.getText()).contains("Congratulations!");

        driver.close();
    }

    @Test
    public void tc004_basicAuthHandleNew3Test() {

        WebDriver driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Register basic auth for all sites
        ((HasAuthentication) driver).register(() -> new UsernameAndPassword(USERNAME, PASSWORD));
        driver.get("https://the-internet.herokuapp.com/basic_auth");

        WebElement welcomeTextElement = driver.findElement(
                By.tagName("p"));
        Assertions.assertThat(welcomeTextElement.getText()).contains("Congratulations!");

        driver.close();
    }
}
