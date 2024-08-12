package org.example.kendoelements;

import org.example.templates.CustomWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class KendoTabStrip extends CustomWebElement {


    WebElement strip;
    List<WebElement> tabs;
    WebElement tabPanel;

    public KendoTabStrip(WebElement strip) {
        super(strip);
        this.strip = strip;

    }

    /**
     * finds the tabs elements and assign them in the tabs List<>Webelement</>
     */
    void initTabs(){
        tabs = strip.findElements(By.cssSelector("li"));
    }

    /**
     * selects a tab by its index
     * @param index int tab index
     */
    public void selectTabByIndex(int index) {
        initTabs();
        tabs.get(index).click();
        initTabPanel();
    }

    /**
     * finds the tab pancel and assigns it to he tabPancel WebElement
     */
    private void initTabPanel() {
        tabPanel = strip.findElement(By.cssSelector("div[role=\"tabpanel\"]"));
    }

    /**
     * selects tab by t's visible text
     * @param text the text displayed on the tab
     */
    public void selectTabByText(String text) {
        initTabs();
        tabs.stream().filter(t -> t.getText().equals(text)).findFirst().orElseThrow().click();
        initTabPanel();
    }

    /**
     * returns the tab pancel of the selected tab
     * @return WebElement tab Panel of the SelectPdf tab
     */
    public WebElement getTabPanel() {
        return tabPanel;
    }

}
