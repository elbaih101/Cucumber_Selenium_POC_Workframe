package org.example.templates;


import org.example.tools.CustomWebDriverWait;
import org.example.tools.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Base Custom WebElement for
 */
public class CustomWebElement
{
    public WebElement element;

    public CustomWebElement(WebElement element)
    {
        this.element = element;
        // make sure all the child classes doesnt have any operations inside them only initialization
    }

    /**
     * Checks whether the element is enabled of not
     *
     * @return bol true [enabled] false [disabled]
     */
    public boolean isEnabled()
    {
        boolean bol = true;
        if (!element.isEnabled())
            return false;
        if (element.getAttribute("aria-disabled") != null && element.getAttribute("aria-disabled").equalsIgnoreCase("true"))
            return false;
        try
        {
            element.findElement(By.xpath("./ancestor-or-self::*[contains(@class,\"k-state-disabled\")]"));
            bol = false;

        } catch (NoSuchElementException ignored)
        {
        }
        return bol;
    }

    public void scrollIntoView()
    {
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        new CustomWebDriverWait(Duration.ofMillis(500)).sleep(500);
    }

    /**
     * finds element from the element
     *
     * @param by By selectors
     * @return WebElement the found element
     */
    public WebElement findElement(By by)
    {
        return element.findElement(by);
    }

    public void click(JavascriptExecutor js)
    {
        js.executeScript("arguments[0].click();", this.element);
    }

    public void click()
    {
        this.element.click();
    }

    public String getAttribute(String attributeName)
    {
        return element.getAttribute(attributeName);
    }

}
