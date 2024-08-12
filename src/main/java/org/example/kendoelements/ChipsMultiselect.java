package org.example.kendoelements;


import org.example.templates.CustomWebElement;
import org.example.tools.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ChipsMultiselect extends CustomWebElement
{

    WebElement chipsMultiSelect;
    List<WebElement> chips;

    public ChipsMultiselect(WebElement chipsMultiSelect) {
        super(chipsMultiSelect);
        this.chipsMultiSelect = super.element;
    }

    /**
     * locates the chips of the chip multi select element
     * @return List<>WebElement</>  the chips of the chip multi select element
     */
    public List<WebElement> getChips() {
        return chips = chipsMultiSelect.findElements(By.xpath(".//div[contains(@class,\"multiselect__item\")]"));
    }

    /**
     * select chips based of a given array of string that each match the strings [Case sensitive]
     * @param text Array[String] texts of the required to select chips
     */
    public void selectByText(String... text) {
        getChips().forEach(chip -> {
            boolean selected = chip.getAttribute("class").contains("selected");
            if (selected && !Utils.containsString(text, chip.getText()))
                chip.click();
            else if (Utils.containsString(text, chip.getText()) && !selected)
                chip.click();
        });
    }

    /**
     * select chips based of a given array of string that each match the strings [Ignoring case]
     * @param text Array[String] texts of the required to select chips
     */
    public void selectByTextIgnoreCase(String... text) {
        getChips().forEach(chip -> {
            boolean selected = chip.getAttribute("class").contains("selected");
            if (selected && !Utils.containsStringIgnoreCase(text, chip.getText()))
                chip.click();
            else if (Utils.containsStringIgnoreCase(text, chip.getText()) && !selected)
                chip.click();
        });
    }

    /**
     * select chips based of a given array of string that each contains the strings [Ignoring case]
     * @param text Array[String] texts of the required to select chips
     */
    public void selectByTextContainsIgnoreCase(String... text) {
        getChips().forEach(chip -> {
            boolean selected = chip.getAttribute("class").contains("selected");
            if (selected && !Utils.containsStringThatContainsIgnoreCase(text, chip.getText()))
                chip.click();
            else if (Utils.containsStringThatContainsIgnoreCase(text, chip.getText()) && !selected)
                chip.click();
        });
    }

    /**
     * selects a single chip by index
     * @param index int the index of the required chip
     */
    public void selectByIndex(int index) {
        WebElement chip = getChips().get(index);
        boolean selected = chip.getAttribute("class").contains("selected");
        if (!selected)
            chip.click();
    }

}
