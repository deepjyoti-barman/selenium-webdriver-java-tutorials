# Selenium 4 Features

## Overview

- Selenium is now W3C compliant
- Relative Locators
- Better Window/Tab management
- Improved Selenium Grid
- Upgraded Selenium IDE
- New APIs for CDP (Chrome DevTools Protocol)
- Deprecation of `DesiredCapabilities` class
- Modifications in the Actions class
- New ways to take screenshots

## Selenium is now W3C Compliant

- JSON wire protocol was used do communicate between the Selenium WebDriver APIs and the browser native APIs.

  - **Previously**: Selenium <-(request/response is encoded and decoded via JSON wire protocol)-> Driver Executables <--> Browser
  - **Now**: Selenium <-(request/response)-> Driver Executables <--> Browser

- With W3C compliance, the communication happens directly without any encoding and decoding required.
- Any software following W3C standard protocol can be integrated with Selenium with no compatibility issues.
- Tests will be more consistent across browsers and flakiness will be considerably reduced, thus improving stability.
- Richer Actions API support:
  - Multi-touch
  - Zoom
  - Pressing two keys simultaneously etc.
- The changes are completely backward compatible, no impact in existing framework written in Selenium 3.
- All the major browsers e.g. Chrome, IE, Firefox and Safari are already W3C compliant.

## Relative Locators

- Functions to locate nearby elements by specifying directions.
- **Existing**: id, name, linkText, partialLinkText, className, tagName, xpath and cssSelector.
- **New Relative Locators**: above, below, toLeftOf, toRightOf, near (element located near - approx. 50 px of the specific element).
- Example:

  ```java
  import static org.openqa.selenium.support.locators.RelativeLocator.with;

  WebElement emailAddressField = driver.findElement(By.id("email"));
  WebElement passwordField = driver.findElement(with(By.tagName("input")).below(emailAddressField));
  ```

## Better window/tab management

- Earlier to open a new/window we had to create a new driver session, and it was very difficult ot manage a single session of WebDriver.
- Now we can work with multiple windows or tabs in the same session.
- We can now open multiple windows/tabs without creating new driver object.
- Open a new window and switch to the window:  
  `driver.switchTo().newWindow(WindowType.WINDOW);`
- Open a new tab and switch to the tab:  
  `driver.switchTo().newWindow(WindowType.TAB);`

## Improved Selenium Grid

- Selenium Grid helps us in `Distributed Test Execution`.
- It enables test execution on different combinations of browsers, OS, machines.
- It also enables parallel execution.
- Now selenium grid is completely redesigned (all the code is being written from scratch).
- We now have docker support.
  - Enables to spin up the containers (No need to set up virtual machines).
  - Enables to deploy the grid on Kubernetes for better scaling.
  - User-friendly GUI, which lets you view all your sessions along with a VNC player plugged in to watch the videos of the tests that are running within the container.
- Support for IPv6 addresses and HTTPS protocol (along with IPv4).
- Single jar to create hub and node, which brings easier management - no need to setup and start hubs and nodes separately.
- We can run Selenium Grid in 3 ways:
  - Standalone mode
    - The new selenium-server.jar contains all the functionalities needed to run a grid.
    - Command: `java -jar selenium-server-4.0.0.jar standalone`
    - The grid automatically identifies that the WebDrivers for Chrome and Firefox are present on the system.
  - Hub and Node mode (Classic mode)
    - The classical way of using Grid for Selenium test automation that consists of two major components - Hub and Nodes.
    - **Start Hub:** `java -jar selenium-server-4.0.0.jar hub`
    - **Register Node:** `java -jar selenium-server-4.0.0.jar node --detect-drivers`
  - Distributed mode
    - Selenium Grid 4 can be started in a fully distributed manner, with each piece running in its own process.
    - Selenium Grid 4 consists of four processes:
      - Router
      - Distributor
      - Session
      - Node

## Upgrade Selenium IDE

- Selenium IDE is a record and playback tool.
- Available as an add-on for Firefox, Chrome, MS-Edge etc.
- Now we have an improved GUI.
- SIDE runner: Selenium IDE runner for command execution, grid and node projects.
- Better element locator strategy.
- More stable and reliable.

## New APIs for CDP (Chrome Dev Tools Protocol)

- Chrome DevTools - set of tools built directly into Chromium-based browsers like Chrome, Opera, Brave and Microsoft Edge to help developers debug and investigate websites.
- It helps us in:
  - Inspect elements in the DOM
  - Edit elements and CSS on the fly
  - Check and monitor the site's performance
  - Mock faster/slower network speeds
  - Mock geo-locations of the user
  - Execute and debug JavaScript
  - View the console logs
- Selenium 4 comes with native support for Chrome DevTools APIs.
- So now we can:
  - Capture and monitor network traffic
  - Intercept network requests and mock backends
  - Perform Basic Authentication
  - Simulate/throttle network performance to simulate poor network conditions
  - Perform geo-location testing
  - Change device mode to do responsive design testing
- New `ChromiumDriver()` class, which includes two methods to access Chrome DevTools:`getDevTools()` and `executeCdpCommand()`.

## Deprecation of Desired Capabilities

- **Desired Capabilities** were primarily used in the test scripts to define the test environment (Browser name, version, operating system).
- Now **Capabilities** objects are replaced with **Options**:
  - **Firefox:** `FirefoxOptions`
  - **Chrome:** `ChromeOptions`
  - **Internet Explorer (IE):** `InternetExplorerOptions`
  - **Microsoft Edge:** `EdgeOptions`
  - **Safari:** `SafariOptions`

## Modifications in the Actions Class

- **Actions** class in Selenium is used to simulate input actions from mouse and keyboard on specific web elements (e.g Left click, Right click, Double click etc.).
- New changes are:
  - Click on a web element:
    - Old: `moveToElement(WebElement).click()`
    - New: `click(WebElement)`
  - Click on the element without releasing the click:
    - Old: `moveToElement(WebElement).clickAndHold()`
    - New: `clickAndHold(WebElement)`
  - Right click:
    - Old: `moveToElement(WebElement).contextClick()`
    - New: `contextClick(WebElement)`
  - `release()` method is now a part of **Actions** class, previously it was a part of `org.openqa.selenium.interactions.ButtonReleaseAction` class.

## New ways to take screenshots

- Now in Selenium 4 we can take three type of screenshots:

  - Screenshot of a WebElement

    - Screenshot can be of an input-box or a section as well as long as we treat it as an element.
    - Code Snippet:

      ```java
      WebElement loginForm = driver.findElement(By.className("oxd-form"));
      File partialScreenshhot = loginForm.getScreenshotAs(OutputType.FILE);
      FileHandler.copy(partialScreenshhot, new File("src/test/resources/screenshots/login-form.png"));
      ```

  - Screenshot of the current visible view-port (classic and new ways)

    - Code snippet of the classic way:

      ```java
      File viewPortScreenshot1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      FileHandler.copy(viewPortScreenshot1, new File("src/test/resources/screenshots/view-port1.png"));
      ```

    - Code snippet of the new way:

      ```java
      File viewPortScreenshot2 = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
      FileHandler.copy(viewPortScreenshot2, new File("src/test/resources/screenshots/view-port2.png"));
      ```

  - Screenshot of the complete web-page

    - This functionality only exists in Firefox browser.
    - Code snippet:

      ```java
      File fullPageScreenshot = ((FirefoxDriver) driver).getFullPageScreenshotAs(OutputType.FILE);
      FileHandler.copy(fullPageScreenshot, new File("src/test/resources/screenshots/full-page.png"));
      ```
