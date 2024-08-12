Here is a Markdown documentation for the `DriverManager` class, outlining its responsibilities and methods, followed by an updated reference to include it in the previous file:

---

# DriverManager Class Documentation

The `DriverManager` class is responsible for managing WebDriver instances for parallel execution in the Nazeel Project. It provides methods to initialize, retrieve, and quit WebDriver instances, and ensures that each thread gets its own WebDriver instance.

## Table of Contents

- [Overview](#overview)
- [Methods](#methods)
  - [getDriver()](#getdriver)
  - [setDriver(WebDriver driver)](#setdriverwebdriver-driver)
  - [quitDriver()](#quitdriver)
  - [initializeDriver(Drivers driverName, Driver_Mode mode)](#initializedriverdrivers-drivername-driver_mode-mode)
  - [getWebDriverType(WebDriver driver)](#getwebdrivertypewebdriver-driver)
  - [edgePrintingAndDownloadOptions()](#edgeprintinganddownloadoptions)
- [Code Example](#code-example)

## Overview

The `DriverManager` class handles the instantiation and management of WebDriver instances for different browsers. It uses `ThreadLocal` to ensure that each test thread has its own WebDriver instance, which is crucial for parallel test execution.

## Methods

### `getDriver()`

Retrieves the WebDriver instance associated with the current thread.

**Return Type**: `WebDriver`

### `setDriver(WebDriver driver)`

Stores the provided WebDriver instance in the current thread's local storage.

**Parameters**:
- `driver` - The WebDriver instance to store.

### `quitDriver()`

Quits the WebDriver instance associated with the current thread and removes it from local storage.

### `initializeDriver(Drivers driverName, Driver_Mode mode)`

Initializes a WebDriver instance of the specified type and mode (headless or normal) and stores it in the current thread's local storage.

**Parameters**:
- `driverName` - Enum `Drivers` specifying the type of WebDriver to initialize (e.g., Chrome, Edge, Firefox, Safari).
- `mode` - Enum `Driver_Mode` specifying the mode (e.g., Headless, Normal).

### `getWebDriverType(WebDriver driver)`

Determines the type of WebDriver instance provided.

**Parameters**:
- `driver` - The WebDriver instance for which to determine the type.

**Return Type**: `Drivers`

### `edgePrintingAndDownloadOptions()`

Returns pre-configured `EdgeOptions` for printing and download settings specific to Microsoft Edge.

**Return Type**: `EdgeOptions`

## Code Example

```java
package org.example.tools;

import org.example.enums.Driver_Mode;
import org.example.enums.Drivers;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.HashMap;

/**
 * DriverManager handles WebDriver instances for parallel execution.
 */
public class DriverManager {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Retrieves the thread-local WebDriver.
     * @return WebDriver the local Thread WebDriver
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    /**
     * Stores the WebDriver in the thread-local reference.
     * @param driver WebDriver to store in the thread
     */
    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    /**
     * Quits the WebDriver and removes it from thread-local storage.
     */
    public static void quitDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }

    /**
     * Initializes a WebDriver instance of the specified type and mode.
     * @param driverName The type of WebDriver to initialize
     * @param mode The mode of the WebDriver (e.g., headless)
     */
    public static void initializeDriver(Drivers driverName, Driver_Mode mode) {
        WebDriver driver;
        switch (driverName) {
            case Chrome -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions op = new ChromeOptions();
                op.addArguments("start-maximized", "--ignore-certificate-errors", "--ignore-urlfetcher-cert-requests");
                if (mode.equals(Driver_Mode.Headless))
                    op.addArguments("headless");
                op.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driver = new ChromeDriver(op);
            }
            case Edge -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions op = new EdgeOptions();
                op.addArguments("start-maximized", "--ignore-certificate-errors", "--ignore-urlfetcher-cert-requests", "--guest");
                if (mode.equals(Driver_Mode.Headless))
                    op.addArguments("headless");
                driver = new EdgeDriver(op);
            }
            case FireFox -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions op = new FirefoxOptions();
                op.addArguments("start-maximized", "--ignore-certificate-errors", "--ignore-urlfetcher-cert-requests", "--guest");
                if (mode.equals(Driver_Mode.Headless))
                    op.addArguments("headless");
                driver = new FirefoxDriver(op);
            }
            case Safari -> {
                WebDriverManager.safaridriver().setup();
                SafariOptions op = new SafariOptions();
                driver = new SafariDriver(op);
            }
            default -> throw new IllegalStateException("Unexpected value: " + driverName);
        }
        driver.manage().window().setSize(new Dimension(1920, 1080));
        setDriver(driver);
    }

    /**
     * Gets the WebDriver type.
     * @param driver The WebDriver instance
     * @return The WebDriver type
     */
    public static Drivers getWebDriverType(WebDriver driver) {
        Drivers driverType = null;

        if (driver instanceof ChromeDriver) {
            driverType = Drivers.Chrome;
        } else if (driver instanceof EdgeDriver) {
            driverType = Drivers.Edge;
        } else if (driver instanceof FirefoxDriver) {
            driverType = Drivers.FireFox;
        } else if (driver instanceof SafariDriver) {
            driverType = Drivers.Safari;
        }

        return driverType;
    }

    /**
     * Returns pre-configured EdgeOptions for printing and download settings.
     * @return EdgeOptions with pre-configured settings
     */
    public static EdgeOptions edgePrintingAndDownloadOptions() {
        EdgeOptions options = new EdgeOptions();
        options.setExperimentalOption("prefs", new String[]{"download.default_directory", "download_path"});

        // Printer config
        options.addArguments("--kiosk-printing");

        // Download config
        HashMap<String, Object> edgePrefs = new HashMap<>();
        edgePrefs.put("download.default_directory", "F:\\java maven projects\\Nazeel-Project\\src\\main\\resources\\downloaded");
        options.setExperimentalOption("prefs", edgePrefs);
        options.addArguments("print.printer_Microsoft_Print_to_PDF.print_to_filename", "F:\\java maven projects\\Nazeel-Project\\src\\main\\resources\\downloaded");
        return options;
    }
}
```

## Reference Update

In the `BasePage` and other relevant classes where `DriverManager` is used, you should ensure that `DriverManager` is properly referenced. 

**For example, in `BasePage` class:**

```java
package org.example.templates;

import org.example.tools.DriverManager; // Add this import
import org.example.tools.CustomAssert;
import org.example.tools.CustomWebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.time.Duration;

public class BasePage
{
   public final WebDriver driver;
   public final CustomWebDriverWait wait;
   public final Actions actions;
   public final JavascriptExecutor js;
   public final CustomAssert asrt;

    /**
     * Initiates the pages using the custom field decorator factory
     * @param driver WebDriver to initialize PageFactory
     */
    public BasePage(WebDriver driver) {
        PageFactory.initElements(new CustomFieldDecorator(new DefaultElementLocatorFactory(driver)), this);
        this.driver = driver;
        wait = new CustomWebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;
        asrt = new CustomAssert();
    }
}
```

Ensure all classes that use `DriverManager` are updated to reflect its use for managing WebDriver instances.