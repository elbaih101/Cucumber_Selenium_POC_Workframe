package org.example.kendoelements;

import org.example.templates.CustomWebElement;
import org.openqa.selenium.WebElement;

public class KendoSwitch extends CustomWebElement {
    WebElement zwitch;


    public KendoSwitch(WebElement zwitch) {
        super(zwitch);
        this.zwitch = zwitch;
    }

    /**
     * checks if a switch is on or off
     * @return bol true [switched on] false [switched off]
     */
    public boolean isOn(){
        return zwitch.getAttribute("class").contains("k-switch-on");
    }

    /**
     * switches the switch onn if it's switched off
     */
    public void switchOn(){
        if(!isOn())
            zwitch.click();
    }

    /**
     * switches the switch off if it's switched on
     */
    public void switchOf(){
        if(isOn())
            zwitch.click();
    }

    /**
     * clicks the switch regardless of its status
     */
    public void click(){
        zwitch.click();
    }
}
