package org.example.stepDefs;


import org.example.pages.P01_TestPage;
import org.example.tools.CustomAssert;
import org.example.tools.CustomWebDriverWait;
import org.example.tools.DriverManager;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class D16_SMS
{

    final WebDriver driver = DriverManager.getDriver();
    final CustomAssert asrt = new CustomAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P01_TestPage testPage=new P01_TestPage(driver);

}
