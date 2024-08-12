package org.example.kendoelements;

import org.example.templates.CustomWebElement;
import org.example.tools.DriverManager;
import org.example.tools.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


public class KendoComboBox extends CustomWebElement {

    Logger logger = LoggerFactory.getLogger(KendoComboBox.class);
    WebElement comboBox;
    final By dropDownButtonBy = By.xpath(".//span[@class=\"k-select\"]");
    final By listItemsBy = By.xpath("//ul[@role=\"listbox\"]//li[@role=\"option\"]");
    final By comboBoxInput = By.xpath(".//input");
    WebElement dropDownButton;
    List<WebElement> listItems;
    WebElement selectedItem;


    public KendoComboBox(WebElement comboBox) {
        super(comboBox);
        this.comboBox = comboBox;
    }

    /**
     * returns the input element of the combobox
     *
     * @return WebElement input element of combo box
     */
    public WebElement getSelectedinput() {
        return comboBox.findElement(comboBoxInput);
    }

    /**
     * finds the drop downlist button
     */
    void getDropDownButton() {
        dropDownButton = comboBox.findElement(dropDownButtonBy);
    }

    /**
     * opens the dropdown list
     */
    void open() {
        if (getSelectedinput().getAttribute("aria-expanded").equals("false")) {
            getDropDownButton();
            dropDownButton.click();
        }
    }

    /**
     * returns the list of items inside the drop down list
     *
     * @return List<>WebElement</> list of all li items inside the list
     */
    public List<WebElement> getListItems() {
        return listItems = comboBox.findElements(listItemsBy);
    }

    /**
     * selects an item by searching for it, it selects the first item found
     *
     * @param text text to search with
     */
    public void selectBySearch(String text) {
        getSelectedinput().sendKeys(text);

        Utils.sleep(300);
        selectedItem = getListItems().stream().filter(i -> i.getText().toLowerCase().contains(text.toLowerCase())).findFirst().orElseThrow();
        selectedItem.click();
    }

    /**
     * selects from the dropped list by text that equals provided text [Case sensitive]
     *
     * @param text text to select with that the selection equals
     */
    public void selectByText(String text) {
        open();
        Utils.sleep(300);
        selectedItem = getListItems().stream().filter(i -> i.getText().equals(text)).findFirst().orElseThrow();
        selectedItem.click();
    }

    /**
     * selects from the dropped list by text that equals provided text [Ignoring case]
     *
     * @param text text to select with that the selection equals
     */
    public void selectByTextIgnoreCase(String text) {

        open();
        Utils.sleep(300);
        selectedItem = getListItems().stream().filter(i -> i.getText().equalsIgnoreCase(text)).findFirst().orElseThrow();
        selectedItem.click();
    }

    /**
     * selects item from the dropped list by text that contains provided text [Ignoring case]
     *
     * @param text text to select with that the selection contains
     */
    public void selectByTextContainsIgnoreCase(String text) {

        open();
        Utils.sleep(300);
        try {
            selectedItem = getListItems().stream().filter(i -> i.getText().toLowerCase().contains(text.toLowerCase())).findFirst().orElseThrow();
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage() + "\n" + comboBox.getTagName() + "[class='" + comboBox.getAttribute("class") + "']" + Arrays.toString(e.getStackTrace()));
            return;
        }
        selectedItem.click();
    }

    /**
     * selects from the dropped list by index
     *
     * @param index index of the item to be selected
     */
    public void selectByIndex(int index) {
        open();
        Utils.sleep(300);
        selectedItem = getListItems().get(index);
        selectedItem.click();
    }

    /**
     * clears the selection of the combo box either by clicking the clear icon
     * or by deleting the selection text from the input field
     */
    public void clearSelection() {
        By clearBy = By.xpath(".//span[contains(@class,\"k-clear-value\")]");
        WebDriver driver = DriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(300));
        if (!Utils.isElementInvisible(clearBy, driver)) {
            WebElement clear = comboBox.findElement(clearBy);
            Actions action = new Actions(driver);
            action.moveToElement(element, 3, 4).perform();
            clear.click();
        } else
            getSelectedinput().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
    }

}


