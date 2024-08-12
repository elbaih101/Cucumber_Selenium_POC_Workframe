package org.example.stepDefs;

import alia.nazeel.enums.Driver_Mode;
import alia.nazeel.enums.Drivers;
import alia.nazeel.pages.P01_LoginPage;
import alia.nazeel.pojos.User;
import alia.nazeel.pojos.UserDataReader;
import alia.nazeel.templates.BaseTestNGCucumberRunner;
import alia.nazeel.tools.DriverManager;
import alia.nazeel.tools.NewMan;
import alia.nazeel.tools.Utils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.time.Duration;


public class Hooks {
    public WebDriver driver;
    private Scenario scenario;
    public final static String stageUrl = "https://staging.nazeel.net:9002/";


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
                driver.get(stageUrl);
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

    @Before("@BankTransfer")
    public void createOdoobankTransfer() {
        File file = new File("src/main/resources/postman_collections/odooBankTransfer.json");
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


    public static void endUserLogin(WebDriver driver, String username, String password, String acc) {
        //initiating Waits and Pages
        P01_LoginPage loginPage = new P01_LoginPage(driver);
        //logging in
        loginPage.usernameField.sendKeys(username);
        loginPage.passwordField.sendKeys(password);
        loginPage.accField.sendKeys(acc);
        loginPage.loginButton.click();


    }

    public static void superUserLogin(WebDriver driver) {
        User user = BaseTestNGCucumberRunner.getUSer() == null ? UserDataReader.getNextUser() : BaseTestNGCucumberRunner.getUSer();

        //initiating Waits and Pages
        P01_LoginPage loginPage = new P01_LoginPage(driver);
        if (user != null) {
            //logging in
            loginPage.usernameField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            loginPage.usernameField.sendKeys(user.getUserName());
            loginPage.passwordField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            loginPage.passwordField.sendKeys(user.getPassword());
            loginPage.loginButton.click();
        } else {
            System.out.println("No users available");
        }

    }
}
