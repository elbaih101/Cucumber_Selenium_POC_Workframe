package org.example.pages;

import org.example.kendoelements.*;
import org.example.templates.BasePage;
import org.example.tools.API;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class P01_TestPage extends BasePage
{
    public P01_TestPage(WebDriver driver)
    {
        super(driver);
    }


    @FindBy(tagName = "kendo-combobox")
    KendoComboBox comboBox;
    @FindBy(tagName = "kendo-switch")
    KendoSwitch kendoSwitch;
    @FindBy(tagName = "kendo-multiselect")
    KendoMultiSelect kendoTapStrip;
    @FindBy(tagName = "kendo-grid")
    KendoGrid kendoGrid;
    @FindBy(tagName = "kendo-buttongroup")
    KendoButtonGroup kendoButtonGroup;
    @FindBy(tagName = "kendo-Date or kendo-time")
    KendoDateTimePicker kendoDateTimePicker;
    @FindBy(tagName = "kendo-dropdown")
    KendoDropDownList kendoDropDownList;

    @FindBy(tagName = "form")
    MultiLangTextField multiLangTextField;

    @FindBy(css = "div.chip-multiselect")
    ChipsMultiselect chipsMultiselect;


    public void sendKeysToTheMultiText()
    {
        multiLangTextField.sendKeys("hello", "مرحبا");
    }

    public void selectCityFromDropList()
    {
        kendoDropDownList.selectByText("cairo");
    }

    public String listenOnAPIAndGetBody()
    {
        API api = new API();
        return api.getResponseBody(driver, "/api/therequest", () -> chipsMultiselect.selectByText("theRequestFiringTrigger"));
    }
}
