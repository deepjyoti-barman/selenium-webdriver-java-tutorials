package dev.selenium.factory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

    private IConfigFactory config;
    private ChromeOptions chromeOptions;
    private FirefoxOptions firefoxOptions;
    private EdgeOptions edgeOptions;

    public OptionsManager(IConfigFactory config) {
        this.config = config;
    }

    public ChromeOptions getChromeOptions() {
        chromeOptions = new ChromeOptions();

        if (config.headless()) chromeOptions.addArguments("--headless");
        if (config.incognito()) chromeOptions.addArguments("--incognito");
        chromeOptions.addArguments("--remote-allow-origins=*");     // Fix for Chrome v111.x issue

        return chromeOptions;
    }

    public FirefoxOptions getFirefoxOptions() {
        firefoxOptions = new FirefoxOptions();

        if (config.headless()) firefoxOptions.addArguments("--headless");
        if (config.incognito()) firefoxOptions.addArguments("--incognito");

        return firefoxOptions;
    }

    public EdgeOptions getEdgeOptions() {
        edgeOptions = new EdgeOptions();

        if (config.headless()) edgeOptions.addArguments("--headless");
        if (config.incognito()) edgeOptions.addArguments("--incognito");

        return edgeOptions;
    }
}
