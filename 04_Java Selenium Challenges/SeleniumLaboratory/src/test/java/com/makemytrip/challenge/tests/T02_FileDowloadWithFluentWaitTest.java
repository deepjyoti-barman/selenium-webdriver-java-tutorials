package com.makemytrip.challenge.tests;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Map;

public class T02_FileDowloadWithFluentWaitTest {

    @Test
    public void tc001_downloadFileTest() {

        String downloadLink = "https://get.jenkins.io/windows-stable/2.462.2/jenkins.msi";
        String fileName = downloadLink.substring(downloadLink.lastIndexOf("/") + 1);
        String downloadPath = Paths.get("src/test/resources/fileDownloads").toAbsolutePath().toString();
        File file = new File(downloadPath, fileName);

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = Map.of(
                "download.default_directory", downloadPath,
                "download.prompt_for_download", false,
                "download.directory_upgrade", true,
                "safebrowsing.enabled", true
        );
        options.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(options);
        driver.get(downloadLink);

        FluentWait<File> wait = new FluentWait<>(file)
                .withTimeout(Duration.ofMinutes(2))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(Exception.class)
                .withMessage("File download failed");
        boolean isDownloaded = wait.until(f -> f.exists() && f.canRead());

        Assertions.assertThat(isDownloaded)
                .withFailMessage("File download failed")
                .isTrue();

        System.out.println("File has been downloaded successfully");
        driver.quit();
    }
}
