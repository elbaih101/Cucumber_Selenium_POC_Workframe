package org.example.kendoelements;

import org.example.templates.CustomWebElement;
import org.example.tools.Utils;
import org.openqa.selenium.*;

import java.util.List;


public class KendoMultiSelect extends CustomWebElement
{
    WebElement multiSelect;
    final By listItemsBy = By.xpath("//ul[@role=\"listbox\"]//li[@role=\"option\"]");
    final By checkbox = By.xpath("./input");
    private List<WebElement> listItems;
    List<WebElement> selectedItems;

    public KendoMultiSelect(WebElement multiSelect)
    {
        super(multiSelect);
        this.multiSelect = multiSelect;
    }


    /**
     * open the multi select list by clicking the element
     */
     void open()
    {
        multiSelect.click();
    }

    /**
     * returns the list of items inside the drop down list
     * @return List<>WebElement</> list of all li items inside the list
     */
    public List<WebElement> getListItems()
    {
        open();
        Utils.sleep(300);
        return listItems = multiSelect.findElements(listItemsBy);
    }
    /**
     * selects multi elements from the dropped list by text that equals provided text [Case sensitive]
     * @param text Array [String]  text to select with that the selection equals
     */
    public void selectByText(String ...text)
    {
        getListItems().forEach(listItem -> {

            boolean selected = listItem.getAttribute("class").contains("k-state-selected");
            if (selected &&!Utils.containsString(text,listItem.getText()))
                listItem.click();
            else if (Utils.containsString(text,listItem.getText())&& !selected)
                listItem.click();
        });
    }
    /**
     * selects multi elements from the dropped list by text that equals provided text [Ignoring Case]
     * @param text Array [String]  text to select with that the selection equals
     */
    public void selectByTextIgnoreCase(String ...text)
    {
        getListItems().forEach(listItem -> {

            boolean selected = listItem.getAttribute("class").contains("k-state-selected");
            if (selected && !Utils.containsStringIgnoreCase(text, listItem.getText()))
                listItem.click();
            else if (Utils.containsStringIgnoreCase(text, listItem.getText())&& !selected)
                listItem.click();
        });
    }
    /**
     * selects multi elements from the dropped list by text that contains provided text [Ignoring Case]
     * @param text Array [String]  text to select with that the selection contains
     */
    public void selectByTextContainsIgnoreCase(String ...text)
    {
        getListItems().forEach(listItem -> {


            boolean selected = listItem.getAttribute("class").contains("k-state-selected");
            if (selected && !Utils.containsStringThatContainsIgnoreCase(text, listItem.getText()))
                listItem.click();
            else if (Utils.containsStringThatContainsIgnoreCase(text, listItem.getText())&&!selected)
                listItem.click();
        });
    }

    /**
     * remove any selected element from selection
     */
    public void unselectAll()
    {
        getListItems().forEach(listItem -> {

            if (listItem.getAttribute("class").contains("k-state-selected"))
                listItem.click();
        });

    }

    /**
     * selects all unselected elements
     */
    public void selectAll()
    {
        getListItems().forEach(listItem -> {

            if (!listItem.getAttribute("class").contains("k-state-selected"))
                listItem.click();
        });
    }

    /**
     * unselects multiple elements based on an array of provided text [case sensitive]
     * @param text Array[String] string to match selection with
     */
    public void unselectByText(String ...text)
    {
        getListItems().forEach(listItem -> {

            if (listItem.getAttribute("class").contains("k-state-selected") && Utils.containsStringThatContainsIgnoreCase(text, listItem.getText()))
                listItem.click();
        });

    }

    /**
     * selects multible elements based on array of provided indexes as int
     * @param index Array [int] indexes to match selection with
     */
    public void selectByIndex(int ...index)
    {
        for (int i = 0; i < getListItems().size(); i++)
        {
            boolean selected = listItems.get(i).getAttribute("class").contains("k-state-selected");
            if ((!selected)&& Utils.isIndexInArray(index, i))
                listItems.get(i).click();
            else if (selected &&!Utils.isIndexInArray(index, i))
                listItems.get(i).click();
        }
    }

    /**
     * unselects a any selected element by array of provided indexes
     * @param index Array[int] all indexes of reauired elements
     */
    public void unselectByIndex(int ...index){
        for (int i = 0; i < getListItems().size(); i++)
        {


            boolean selcted = listItems.get(i).getAttribute("class").contains("k-state-selected");
            if (selcted && Utils.isIndexInArray(index, i))
                listItems.get(i).click();
            else if ((!selcted)&&!Utils.isIndexInArray(index, i))
                listItems.get(i).click();
        }
    }

    /**
     * checks if at least 1 item is selecetd in the multi select
     * @return bol true [at least 1 item exists] false [no items exists]
     */
    public boolean selectionExist(){
        boolean bol = multiSelect.findElements(By.xpath(".//li")).isEmpty();
        return !bol;
    }
}
