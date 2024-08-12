package org.example.stepDefs;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.example.pages.P01_LoginPage;
import org.example.pages.P02_DashBoardPage;
import org.example.pages.reservations.P03_1_ReservationMainDataPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class TestNgTest
{
    WebDriver driver;



    final SoftAssert asrt = new SoftAssert();

    @BeforeSuite
    public void setUp()
    {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(TestData.stageUrl);


    }

    @Test(description = "Making Successful Reservation with Nazeel PMS (web application)")
    public void testSuccsReservation()
    {  //initiating pages and waits
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        P01_LoginPage loginPage = new P01_LoginPage(driver);
        P02_DashBoardPage homePage = new P02_DashBoardPage(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        P03_1_ReservationMainDataPage reservationPage = new P03_1_ReservationMainDataPage(driver);

        //Logging in using The Login TestData username "Mostafa Hamed" password "123456&Mh" ACC "00720"
        loginPage.usernameField.sendKeys(TestData.username);
        loginPage.passwordField.sendKeys(TestData.password);
        loginPage.accField.sendKeys(TestData.accessCode);
        loginPage.loginButton.click();

        //clicking on later to bypass user verification
        wait.until(ExpectedConditions.urlMatches("http://staging.nazeel.net:9002/dashboard"));
        wait.until(ExpectedConditions.elementToBeClickable(loginPage.verificationButton));
        js.executeScript("arguments[0].click();", loginPage.verificationButton);

        //click on Reservation link to open reservation
        homePage.ReservationsLink.click();

        // Click on Add new Reservation
        wait.until(ExpectedConditions.urlContains("/reservations"));
        reservationPage.newReservationButton.click();

        //Select Reservation source
        reservationPage.reservationSourceDropList.click();
        wait.until(ExpectedConditions.visibilityOf(reservationPage.test3ReservationSource));
        reservationPage.test3ReservationSource.click()
        ;
        //Select Visit purpose
        reservationPage.visitPurposeDropList.click();
        wait.until(ExpectedConditions.visibilityOf(reservationPage.familyOrFriendsSelection));
        reservationPage.familyOrFriendsSelection.click();

        //Select Unit Now Span
        reservationPage.selectUnitNowSpan.click();

        //hover on any unit card then click confirm
        wait.until(ExpectedConditions.visibilityOf(reservationPage.panelBar));
        List<WebElement> cards = reservationPage.panelBar.findElements(By.xpath("//div[@class=\"usc-wid unit-card ng-star-inserted\"]"));
        WebElement element = cards.get(new Random().nextInt(cards.size()));
        Actions action = new Actions(driver);
        action.moveToElement(element);
        sleep();
        element.click();
//        WebElement confirmBtn = reservationPage.confirmBtn;
//        confirmBtn.click();

        //click on selectguest now button
        js.executeScript("arguments[0].click();", reservationPage.selectGestButton);

        //select guest dialouge appears click on name to search by name
        WebElement guestFormDialogContainer = reservationPage.guestFormDialogContainer;
        wait.until(ExpectedConditions.visibilityOf(guestFormDialogContainer));
        WebElement nameButton = guestFormDialogContainer.findElement(By.xpath("//button[contains(text(),\"Name\")]"));
        js.executeScript("arguments[0].click();", nameButton);

        //enter the guest name "mostafa" in the Name field
        WebElement nameField = guestFormDialogContainer.findElement(By.xpath("//label[contains(text(),\"Name\")]/following-sibling::input"));
        nameField.sendKeys(TestData.guestName);
        WebElement searchButton = guestFormDialogContainer.findElement(By.xpath("//button[contains(text(),\"Search\")]"));
        searchButton.click();

        //click on Record After the guest Appears
        WebElement guestPanel = reservationPage.guestFormDialogContainer.findElement(By.xpath("//span[contains(text(),\"Mostafa Hamed\")]"));
        wait.until(ExpectedConditions.visibilityOf(guestPanel));
        guestPanel.click();
        WebElement confirmButton = reservationPage.guestFormDialogContainer.findElement(By.xpath("//div[@class=\"u-flex-end\"]//button[contains(text(),\"Confirm\")]"));
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
        js.executeScript("arguments[0].click();", confirmButton);

        //click on check in button
        WebElement saveReservationButton = reservationPage.saveReservationButton;
        wait.until(ExpectedConditions.elementToBeClickable(saveReservationButton));
        saveReservationButton.click();

        //when reservation Summary dialouge appears click on save reservatuon Button
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//kendo-dialog")));
        reservationPage.reservationSummarySaveButton.click();

        //verify toast message appears with text "Saved Successfully" and the reservation status to be "Confirmed"
        WebElement succesMessage = reservationPage.toastMessage;
        String expectedToastMessage = "Saved Successfully";
        String expectedReservationStatus = "Confirmed";
        wait.until(ExpectedConditions.visibilityOf(succesMessage));
        asrt.assertTrue(succesMessage.getText().contains(expectedToastMessage));
        asrt.assertAll();
        WebElement resStatus = reservationPage.reservationStatus;
        Assert.assertTrue(resStatus.getText().contains(expectedReservationStatus));

    }


    @AfterSuite
    public void end()
    {
        try
        {
            Thread.sleep(5000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        driver.quit();
    }

    void sleep()
    {
        try
        {
            Thread.sleep(2000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}


