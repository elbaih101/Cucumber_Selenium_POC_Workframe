package org.example.stepDefs;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.P01_TestPage;
import org.example.tools.CustomAssert;
import org.example.tools.CustomWebDriverWait;
import org.example.tools.DriverManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class D01_theSteps
{

    final WebDriver driver = DriverManager.getDriver();
    final CustomAssert asrt = new CustomAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P01_TestPage testPage = new P01_TestPage(driver);

    @Given("choose the test")
    public void chooseTheTest()
    {
        testPage.sendKeysToTheMultiText();
    }

    @Given("now u in the rule")
    public void now_u_in_the_rule()
    {
testPage.selectCityFromDropList();
    }

    @When("you go to school")
    public void youGoToSchool()
    {
      LoggerFactory.getLogger(this.getClass()).error( testPage.listenOnAPIAndGetBody());
    }

    @Then("u learn a lot")
    public void uLearnALot()
    {
        asrt.AssertContains(testPage.listenOnAPIAndGetBody(),"not null");
    }
}
