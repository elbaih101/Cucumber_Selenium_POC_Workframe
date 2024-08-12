package org.example.testRunner;

import io.cucumber.testng.CucumberOptions;
import org.example.templates.BaseTestNGCucumberRunner;
import org.testng.annotations.DataProvider;
@CucumberOptions
        (
                tags = "@Group1"/* and not@Property"*/,//"not @Reservation_Rules and not @DigitalPayment and not @Property and not @Blocks_Floors",
                plugin = {
                        "pretty",
                        "html:target/cucumber-reports/cucumber1.html",
                        "json:target/cucumber-reports/cucumber1.json",
                        "junit:target/cucumber-reports/cucumber1.xml",
                        "rerun:target/cucumber-reports/cucumber1.txt",


                }


        )
public class Group1Runners extends BaseTestNGCucumberRunner
{

//   to run in parallel
    @SuppressWarnings({"DefaultAnnotationParam", "EmptyMethod"})
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}