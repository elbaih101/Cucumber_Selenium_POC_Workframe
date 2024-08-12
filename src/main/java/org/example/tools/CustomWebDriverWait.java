package org.example.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.Duration;

public class CustomWebDriverWait extends WebDriverWait
{
    Logger logger= LoggerFactory.getLogger(this.getClass());

    public CustomWebDriverWait(WebDriver driver, Duration timeout)
    {
        super(driver, timeout);
    }

    public CustomWebDriverWait(WebDriver driver, Duration timeout, Duration sleep)
    {
        super(driver, timeout, sleep);
    }

    public CustomWebDriverWait(WebDriver driver, Duration timeout, Duration sleep, Clock clock, Sleeper sleeper)
    {
        super(driver, timeout, sleep, clock, sleeper);
    }
    public CustomWebDriverWait(Duration timeout){
        super(DriverManager.getDriver(),timeout);
    }

    /**
     * this method uses webdriver wait to wait for the invisibility of all loading elements implimented in nazeel PMS
     */
    public void waitLoading() {
        String loadingBarXpath="//ngx-loading-bar/*";
        String loadingPageXpath="//app-loading-page//div[@class=\"page-loading\"]";
        WebDriver driver =DriverManager.getDriver();
        try {
           driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
            // Wait until the loading animation disappears or becomes stale
            this.withTimeout(Duration.ofSeconds(90))
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
                    .until(ExpectedConditions.invisibilityOfAllElements( driver.findElements(By.xpath(loadingBarXpath+" | "+loadingPageXpath))));
        } catch (NoSuchElementException e) {
            // Handle any exceptions or logging here
            logger.error( "the loading page might not be found "+e.getMessage());
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    /**
     * function to sleep the thread with desired time in millis
     *
     * @param milliSeconds time in millis
     */
    public  void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }
}
