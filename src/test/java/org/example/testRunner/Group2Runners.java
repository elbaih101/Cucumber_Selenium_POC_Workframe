package org.example.testRunner;


import io.cucumber.testng.CucumberOptions;
import org.example.templates.BaseTestNGCucumberRunner;
import org.testng.annotations.DataProvider;
@SuppressWarnings("EmptyMethod")
@CucumberOptions
        (


                tags = "@Group2",//"not @Reservation_Rules and not @DigitalPayment and not @Property and not @Blocks_Floors",
                plugin = {
                        "pretty",
                        "html:target/cucumber-reports/cucumber2.html",
                        "json:target/cucumber-reports/cucumber2.json",
                        "junit:target/cucumber-reports/cucumber2.xml",
                        "rerun:target/cucumber-reports/cucumber2.txt",


                }


        )
public class Group2Runners extends BaseTestNGCucumberRunner
{
//   to run in parallel
@SuppressWarnings({"DefaultAnnotationParam", "EmptyMethod"})
@Override
    @DataProvider(parallel = false)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}
