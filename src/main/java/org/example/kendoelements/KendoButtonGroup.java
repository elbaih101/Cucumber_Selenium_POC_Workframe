package org.example.kendoelements;


import org.example.templates.CustomWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class KendoButtonGroup extends CustomWebElement
{
    WebElement buttonGroup;
    By buttonGroupBy = new By.ByTagName("kendo-buttongroup");
    final By buttonsBy = By.xpath(".//button");

    public KendoButtonGroup(WebElement element) {
        super(element);
        this.buttonGroup =element;
    }

    /**
     * selects a button by its visible text [Case Sensitive]
     * @param name String the visible text on the button
     */
    public void selectButtonByName(String name)
    {
        List<WebElement> buttons = buttonGroup.findElements(buttonsBy);
        for (WebElement button : buttons)
        {
            if (button.getText().equals(name))
            {
                button.click();
                break;
            }
        }
    }

    /**
     * selects a button by its visible text [Ignoring Case]
     * @param name String the visible text on the button
     */
    public void selectButtonByNameIgnoreCase(String name)
    {
        List<WebElement> buttons = buttonGroup.findElements(buttonsBy);
        for (WebElement button : buttons)
        {
            if (button.getText().equalsIgnoreCase(name))
            {
                button.click();
                break;
            }
        }
    }

    /**
     * selects a button by its visible text containing the provided text [Ignoring Case]
     * @param name String the visible text on the button
     */
    public void selectButtonByNameContainsIgnoreCase(String name)
    {
        List<WebElement> buttons = buttonGroup.findElements(buttonsBy);
        for (WebElement button : buttons)
        {
            if (button.getText().toLowerCase().contains(name.toLowerCase()))
            {
                button.click();
                break;
            }
        }
    }

    /**
     * selectes abutton by index
     * @param index index of the button
     */
    public void selectButtonByIndex(int index)
    {
        List<WebElement> buttons = buttonGroup.findElements(buttonsBy);
        if (index < buttons.size())
        {
            buttons.get(index).click();
        }
    }

    /**
     * returns the selected button
     * @return WebElement the selected Button
     */
    public WebElement getActiveButton()
    {
        List<WebElement> buttons = buttonGroup.findElements(buttonsBy);
        for (WebElement button : buttons)
        {
            if (button.getAttribute("class").contains("k-state-active"))
            {
                return button;
            }
        }
        return null;

    }
}
