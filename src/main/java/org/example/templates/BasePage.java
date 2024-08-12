package org.example.templates;


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
     * initiate the pages using the custom field decorator factory
     * @param driver Web Driver to inotiate PageFactory
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
