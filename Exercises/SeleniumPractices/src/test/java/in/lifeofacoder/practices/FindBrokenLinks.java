package in.lifeofacoder.practices;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FindBrokenLinks {

    public static void checkBrokenLink(String linkUrl) {
        try {
            URL url = new URL(linkUrl);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setConnectTimeout(5000);
            httpUrlConnection.connect();

            if (httpUrlConnection.getResponseCode() >= 400) {
                System.out.println(linkUrl + " ---> " + httpUrlConnection.getResponseCode() + " " + httpUrlConnection.getResponseMessage() + " - broken link found");
            } else {
                System.out.println(linkUrl + " ---> " + httpUrlConnection.getResponseCode() + " " + httpUrlConnection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://amazon.in");

        List<String> urlList = new ArrayList<>();
        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("No of links are = " + links.size());

        for (WebElement element : links) {
            String url = element.getAttribute("href");
            urlList.add(url);
        }

        long startTime = System.currentTimeMillis();
//        urlList.stream().forEach(e -> checkBrokenLink(e));                         // Time elapsed in sequential stream: 159 secs for a total of 280 links
        urlList.parallelStream().forEach(FindBrokenLinks::checkBrokenLink);          // Time elapsed in parallel stream: 18.4 secs for a total of 280 links
        long endTime = System.currentTimeMillis();
        System.out.println("Total time taken = " + (endTime - startTime) / 1000 + " secs");

        driver.quit();
    }
}