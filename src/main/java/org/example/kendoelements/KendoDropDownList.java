package org.example.kendoelements;

import org.example.templates.CustomWebElement;
import org.example.tools.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class KendoDropDownList extends CustomWebElement  {

    WebElement dropDownlist;
    final By dropDownButtonBy = By.xpath(".//span[@class=\"k-select\"]");
    final By listItemsBy = By.xpath("//ul[@role=\"listbox\"]//li[@role=\"option\"]");
    WebElement dropDownButton;
    List<WebElement> listItems;
    WebElement selectedItem;

    public KendoDropDownList(WebElement list) {
        super(list);
        this.dropDownlist = list;
    }

    /**
     * finds the drop downlist button
     */
    public void getDropDownButton() {
        dropDownButton = dropDownlist.findElement(dropDownButtonBy);
    }
    /**
     * opens the dropdown list
     */
    public void open() {
        getDropDownButton();
        dropDownButton.click();
    }
    /**
     * returns the list of items inside the drop down list
     * @return List<>WebElement</> list of all li items inside the list
     */
    public List<WebElement> getListItems() {
        open();
        return  listItems = dropDownlist.findElements(listItemsBy);
    }
    /**
     * selects from the dropped list by text that equals provided text [Case sensitive]
     * @param text text to select with that the selection equals
     */
    public void selectByText(String text) {
         selectedItem = getListItems().stream().filter(i->i.getText().equals(text)).findFirst().orElseThrow();
        Utils.sleep(300);
        selectedItem.click();
    }
    /**
     * selects from the dropped list by text that equals provided text [Ignoring case]
     * @param text text to select with that the selection equals
     */
    public void selectByTextIgnoreCase(String text)
    {
        selectedItem = getListItems().stream().filter(i -> i.getText().equalsIgnoreCase(text)).findFirst().orElseThrow();
        Utils.sleep(300);
        selectedItem.click();
    }
    /**
     * selects item from the dropped list by text that contains provided text [Ignoring case]
     * @param text text to select with that the selection contains
     */
    public void selectByTextContainsIgnoreCase(String text)
    {
        selectedItem = getListItems().stream().filter(i -> i.getText().toLowerCase().contains(text.toLowerCase())).findFirst().orElseThrow();
        Utils.sleep(300);
        selectedItem.click();
    }

    /**
     * selects from the dropped list by index
     * @param index index of the item to be selected
     */
    public void selectByIndex(int index) {
        selectedItem = getListItems().get(index);
        Utils.sleep(300);
        selectedItem.click();
    }
    /**
     * clears the selection of the combo box either by clicking the clear icon
     * or by deleting the selection text from the input field
     */
    public void clearSelection(WebDriver driver) {
        Actions action = (Actions) driver;
        WebElement clear = dropDownlist.findElement(By.xpath(".//span[contains(@class,\"k-clear-value\")]"));
        action.moveToElement(dropDownlist, 3, 4).click().perform();
    }
    }
