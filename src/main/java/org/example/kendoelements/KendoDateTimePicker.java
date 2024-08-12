package org.example.kendoelements;

import org.example.templates.CustomWebElement;
import org.example.tools.CustomWebDriverWait;
import org.example.tools.DriverManager;
import org.example.tools.Utils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class KendoDateTimePicker extends CustomWebElement {


    private final WebElement kendoDateTimePicker;
    private WebElement calender;

    CustomWebDriverWait wait = new CustomWebDriverWait(Duration.ofSeconds(5));
    final private By inputBy = By.xpath(".//input");
    final private By dateTimePickerButton = By.xpath(".//span[@class=\"k-select\"]");
    final private By calendarBy = By.xpath("//kendo-calendar");
    final private By timeSelector = By.cssSelector("kendo-timeselector");
    final private By setButton = By.cssSelector("button.k-time-accept");
    final private By cancelButton = By.cssSelector("button.k-time-cancel");

    final private By dateTimeContainorBy = By.xpath("//div[contains(@class,\"k-datetime-container\")]");
    String dateTimeFormat = "dd/MM/yyyy HH:mm a";
    String dateFormat = "dd/MM/yyyy";
    KendoButtonGroup buttonGroup;
   private WebElement container;
    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateTimeFormat);
    DateTimeFormatter dateFormatter = DateTimeFormat.forPattern(dateFormat);

    public KendoDateTimePicker(WebElement kendoDateTimePicker) {
        super(kendoDateTimePicker);
        this.kendoDateTimePicker = kendoDateTimePicker;
    }

    /**
     * sets the date and time using the input element
     *
     * @param dateTime String of the date and time on the form of dd/MM/yyyy HH:mm a
     */

    public void setDateTime(String dateTime) {
        WebElement dateTimeInput = getDateTimeInput();
        dateTimeInput.click();
        dateTime = dateTime.replaceAll("[/\\-: ]", "");
        dateTimeInput.sendKeys(Keys.HOME);
        dateTimeInput.sendKeys(dateTime);
    }

    /**
     * selects the date time from the date time picker popup
     *
     * @param dateTime String of the date and time on the form of dd/MM/yyyy HH:mm a
     */
    public void selectDateTime(String dateTime) {
        DateTime datetime1 = DateTime.parse(dateTime, dateTimeFormatter);
        getDateTimeContainer();
        buttonGroup = new KendoButtonGroup(container.findElement(By.cssSelector("div.k-datetime-buttongroup")));
        selectYear(datetime1.toString(DateTimeFormat.forPattern("YYYY")));
        selectMonth(datetime1.toString(DateTimeFormat.forPattern("MMM")));
        selectDay(datetime1.toString(DateTimeFormat.forPattern("MMMM dd, YYYY")));
        buttonGroup.selectButtonByName("Time");
        selectTime(datetime1.toString(DateTimeFormat.forPattern("HH")), datetime1.toString(DateTimeFormat.forPattern("mm")), datetime1.toString(DateTimeFormat.forPattern("a")));
        clickSetButton();
    }

    /**
     * selects the date time from the date time picker popup
     *
     * @param date String of the date and on the form of dd/MM/yyyy
     */
    public void selectDate(String date) {
        DateTime datetime1 = DateTime.parse(date, dateFormatter);
        getDateTimeContainer();
        selectYear(datetime1.toString(DateTimeFormat.forPattern("yyyy")));
        selectMonth(datetime1.toString(DateTimeFormat.forPattern("yyyy MMM")));
        selectDay(datetime1.toString(DateTimeFormat.forPattern("MMMM dd, yyyy")));
    }

    /**
     * finds the date time container element
     */
    void getDateTimeContainer() {
        getKendoDateTimePickerButton().click();
        wait.waitLoading();
        container=kendoDateTimePicker.findElement(dateTimeContainorBy);
        calender = kendoDateTimePicker.findElement(calendarBy);
    }

    public void clear() {
        getDateTimeInput().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
    }

    /**
     * finds the datetime pick button
     *
     * @return Webelement button for the picker pop up
     */
    private WebElement getKendoDateTimePickerButton() {
        return kendoDateTimePicker.findElement(dateTimePickerButton);
    }

    /**
     * finds the input field of the date time
     *
     * @return Webelement the date time input element
     */
    public WebElement getDateTimeInput() {
        return kendoDateTimePicker.findElement(inputBy);
    }

    /**
     * selects a year based on the string of the year from the date time picked
     *
     * @param year String the year string on form of  yyyy
     */
    void selectYear(String year) {

        WebElement yearButton = calender.findElement(By.cssSelector("span.k-button"));
        int thisYear = Integer.parseInt(yearButton.getText().replaceAll("\\D", ""));
        int targetYear = Integer.parseInt(year);
        yearButton.click();
        String yearXPath = String.format("//td[@role='gridcell' and contains(@title, '%s')]", year);
        String targetYearXPath = String.format("//span[@class='k-button k-bare k-title' and contains(text(),'%s')]", year);
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        do {
            switch (Integer.compare(targetYear, thisYear)) {
                case -1 -> Utils.scroll("up", calender);
                case 1 -> Utils.scroll("down", calender);
            }

        } while (Utils.isElementInvisible(By.xpath(targetYearXPath), DriverManager.getDriver()));
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement yearElement = kendoDateTimePicker.findElement(By.xpath(targetYearXPath));
        // yearElement.click();

    }


    /**
     * selects a month based on the string of the month from the date time picked
     *
     * @param month String the month string on form of  MM
     */
    void selectMonth(String month) {
        String monthXPath = String.format("//td[@role='gridcell' and contains(@title, '%s')]/span", month);
        WebElement monthElement = kendoDateTimePicker.findElement(By.xpath(monthXPath));
        wait.sleep(200);
        monthElement.click();
    }

    /**
     * selects a month based on the string of the day from the date time picked
     *
     * @param date String the month string on form of  dd
     */
    void selectDay(String date) {
        String dateXPath = String.format("//td[@role='gridcell' and contains(@title, '%s')]", date);
        WebElement dateElement = calender.findElement(By.xpath(dateXPath));
        dateElement.click();
    }

    /**
     * selects the time from the date time picker
     *
     * @param hour   String hours on form of hh [12]
     * @param minute String minutes on the form of mm [55]
     * @param period String period on form of  AM or PM
     */
    void selectTime(String hour, String minute, String period) {
        // hour, minute should be in 'hh', 'mm' format, e.g., '12', '00'
        // period should be 'AM' or 'PM'
        String hourXPath = String.format("//kendo-timelist[@data-timelist-index='0']//li/span[text()='%s']", hour);
        String minuteXPath = String.format("//kendo-timelist[@data-timelist-index='2']//li/span[text()='%s']", minute);
        String periodXPath = String.format("//kendo-timelist[@data-timelist-index='4']//li/span[text()='%s']", period);

        WebElement hourElement = calender.findElement(By.xpath(hourXPath));
        WebElement minuteElement = calender.findElement(By.xpath(minuteXPath));
        WebElement periodElement = calender.findElement(By.xpath(periodXPath));

        hourElement.click();
        minuteElement.click();
        periodElement.click();
    }

    /**
     * sets the time selection
     */
    void clickSetButton() {
        container.findElement(setButton).click();
    }

    /**
     * cancel the time selection
     */
    void clickCancelButton() {
        container.findElement(cancelButton).click();
    }
     public String getCssValue(String att){
       return kendoDateTimePicker.getCssValue(att);
     }
}
