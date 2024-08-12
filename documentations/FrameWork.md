# Custom Web Element Framework for Nazeel-Project

This document provides a detailed explanation of the custom web element framework implemented in the `Nazeel-Project`. The framework allows for the creation and management of custom web elements that extend the default Selenium WebElements, enabling enhanced interaction and customization within your automated tests.

## Related Documentation

Refer to the [Maven POM Structure for Nazeel-Project](../readme.md) for an overview of the project's dependencies, plugins, and build management.

## Table of Contents
- [CustomFieldDecorator Class](#Custom-Field-Decorator)
- [CustomWebElement Class](#Custom-Web-Element)
- [BasePage Class](#basepage-class)


## Custom Field Decorator

### Overview

The `CustomFieldDecorator` class extends Selenium's `DefaultFieldDecorator` and is used to create custom elements by decorating fields in page objects. This allows for the creation of custom `FindBys` annotations tailored to your specific needs.

### Code Implementation

```java
package alia.nazeel.templates;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class Extends the custom Field Decorator that can be used inside the BasePom to replace the Default Field decorator.
 * PageFactory.initElements(new CustomFieldDecorator(new DefaultElementLocatorFactory(driver)), this);
 * enabling the use of creating custom FindBys Annotations for The Custom WebElements.
 */
public class CustomFieldDecorator extends DefaultFieldDecorator {
    private static final Logger logger = LoggerFactory.getLogger(CustomFieldDecorator.class);

    public CustomFieldDecorator(ElementLocatorFactory factory) {
        super(factory);
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if (field.isAnnotationPresent(FindBy.class) || field.isAnnotationPresent(FindBys.class) || field.isAnnotationPresent(FindAll.class)) {
            ElementLocator locator = factory.createLocator(field);

            if (locator == null) {
                return null;
            }

            // Handle custom elements
            if (CustomWebElement.class.isAssignableFrom(field.getType())) {
                return CustomProxy(field.getType(), loader, locator);
            }

            // Handle List<CustomWebElement>
            else if (List.class.isAssignableFrom(field.getType())) {
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType parameterizedType) {
                    Type listType = parameterizedType.getActualTypeArguments()[0];
                    Class<?> typeClass = (Class<?>) listType;
                    if (CustomWebElement.class.isAssignableFrom(typeClass)) {
                        return CustomListProxy(typeClass, loader, locator);
                    }
                }
            }
        }

        return super.decorate(loader, field);
    }

    private <T> T CustomProxy(Class<T> clazz, ClassLoader loader, ElementLocator locator) {
        try {
            WebElement proxy = proxyForLocator(loader, locator);
            Constructor<T> constructor = clazz.getConstructor(WebElement.class);
            return constructor.newInstance(proxy);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Failed to create proxy instance", e);
        }
    }

    private <T> List<T> CustomListProxy(Class<T> clazz, ClassLoader loader, ElementLocator locator) {
        try {
            List<WebElement> proxy = proxyForListLocator(loader, locator);
            List<T> customElements = new ArrayList<>();
            Constructor<T> constructor = clazz.getConstructor(WebElement.class);
            for (WebElement element : proxy) {
                customElements.add(constructor.newInstance(element));
            }
            return customElements;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Failed to create proxy instance", e);
        }
    }
}
```

### Key Methods

- **`decorate(ClassLoader loader, Field field)`**: Overrides the default decorator to handle custom elements and lists of custom elements.
- **`CustomProxy(Class<T> clazz, ClassLoader loader, ElementLocator locator)`**: Creates a proxy instance of the specified custom element class.
- **`CustomListProxy(Class<T> clazz, ClassLoader loader, ElementLocator locator)`**: Creates a proxy list of custom elements of type `<T>`.

## Custom Web Element

### Overview

The `CustomWebElement` class is a base class for creating custom web elements. It extends the functionality of Selenium's `WebElement` to include additional methods and utilities that can be used across all custom elements.
many Kendo Ui Elements are extended from this allowing for fine tuning and ease of automation in the Nazeel Project more on this [here](Kendo_Element_Extending_CustomWebElement.md) 

### Code Implementation

```java
package alia.nazeel.templates;

import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Base Custom WebElement class.
 */
public class CustomWebElement {
    public WebElement element;

    public CustomWebElement(WebElement element) {
        this.element = element;
    }

    /**
     * Checks whether the element is enabled.
     * @return boolean - true if enabled, false if disabled.
     */
    public boolean isEnabled() {
        boolean bol = true;
        if (!element.isEnabled())
            return false;
        if (element.getAttribute("aria-disabled") != null && element.getAttribute("aria-disabled").equalsIgnoreCase("true"))
            return false;
        try {
            element.findElement(By.xpath("./ancestor-or-self::*[contains(@class,\"k-state-disabled\")]"));
            bol = false;
        } catch (NoSuchElementException ignored) {
        }
        return bol;
    }

    /**
     * Scrolls the element into view.
     */
    public void scrollIntoView() {
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        new CustomWebDriverWait(Duration.ofMillis(500)).sleep(500);
    }

    /**
     * Finds a child element from the current element.
     * @param by By selector.
     * @return WebElement - the found child element.
     */
    public WebElement findElement(By by) {
        return element.findElement(by);
    }

    /**
     * Clicks the element using JavaScript.
     * @param js JavascriptExecutor.
     */
    public void click(JavascriptExecutor js) {
        js.executeScript("arguments[0].click();", this.element);
    }

    /**
     * Clicks the element using the standard WebElement click method.
     */
    public void click() {
        this.element.click();
    }
}
```

### Key Methods

- **`isEnabled()`**: Checks if the element is enabled, considering various attributes like `aria-disabled` and CSS classes.
- **`scrollIntoView()`**: Scrolls the element into view using JavaScript.
- **`findElement(By by)`**: Finds a child element from the current element.
- **`click(JavascriptExecutor js)`**: Clicks the element using JavaScript.
- **`click()`**: Clicks the element using the standard WebElement click method.


## BasePage Class

### Overview

The `BasePage` class serves as the base class for all page objects in the Nazeel Project. It initializes essential utilities and components necessary for interacting with web elements and performing actions within the browser,
using the [CustomFieldDecorator](#Custom-Field-Decorator) and implementing it in locating the element

### Key Components

- **`WebDriver driver`**: Manages browser interactions.
- **`CustomWebDriverWait wait`**: Provides custom wait functionality for synchronization in tests.
- **`Actions actions`**: Facilitates complex user interactions like drag-and-drop, hover, etc.
- **`JavascriptExecutor js`**: Executes JavaScript within the browser context.
- **`CustomAssert asrt`**: Contains custom assertion methods for validating test conditions.

### Code Example

```java
package alia.nazeel.templates;

import alia.nazeel.tools.CustomAssert;
import alia.nazeel.tools.CustomWebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.time.Duration;

public class BasePage {
    public final WebDriver driver;
    public final CustomWebDriverWait wait;
    public final Actions actions;
    public final JavascriptExecutor js;
    public final CustomAssert asrt;

    /**
     * Initiate the page using the custom field decorator factory.
     * @param driver WebDriver instance to initialize PageFactory.
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

## Notes

For the Maven project setup and dependencies related to the custom web element framework, refer to the [Maven POM Structure for Nazeel-Project](PomSructure.md).

This framework allows for the creation of reusable and maintainable custom elements, enhancing the efficiency and readability of your test automation code.