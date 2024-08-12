package org.example.stepDefs;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.example.enums.Driver_Mode;
import org.example.enums.Drivers;
import org.example.tools.DriverManager;
import org.example.tools.NewMan;
import org.example.tools.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.time.Duration;


public class Hooks {
    public WebDriver driver;
    private Scenario scenario;
    public final static String BaseUrl = "the requiredUrl";


    @Before
    public void start(Scenario scenario) {
        this.scenario = scenario;
        DriverManager.initializeDriver(Drivers.Chrome, Driver_Mode.UI);
        this.driver = DriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        getUrl();

    }

    private void getUrl() {
        int count = 3;
        while (count != 0) {
            try {
                driver.get(BaseUrl);
                break;
            } catch (WebDriverException e) {
                if (e.getMessage().contains("ERR_CONNECTION_TIMED_OUT")) {
                    getUrl();
                    count--;
                } else {
                    throw new WebDriverException(e);
                }
            }
        }
    }

    @Before("@Group1")
    public void runPostManCollection() {
        File file = new File("src/main/resources/postman_collections/somePostManCollection.json");
        NewMan.runPostmanTestCase(file.getAbsolutePath());

    }

    @After
    public void end() throws Exception {
        Utils.screenShotOnFailure(scenario, driver);
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }






    }

