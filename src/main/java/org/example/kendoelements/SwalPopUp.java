package org.example.kendoelements;

import org.example.templates.CustomWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SwalPopUp extends CustomWebElement {

    WebElement popup;
    By contentBy = By.id("swal2-content");
    By confirmBy=By.xpath(".//button[contains(@class,\"swal2-confirm\")]");
    By cancelBy=By.xpath(".//button[contains(@class,\"swal2-cancel\")]");
    By denyBy =By.xpath(".//button[contains(@class,\"swal2-deny\")]");
    WebElement content;

    public SwalPopUp(WebElement popup) {
        super(popup);
        this.popup = popup;
    }

    public WebElement getContent(){
        return content= popup.findElement(contentBy);
    }

    /**
     * confirms the pop up
     */
    public void confirm(){
        popup.findElement(confirmBy).click();
    }

    /**
     * cancels the popup
     */
    public void cancel(){
        popup.findElement(cancelBy).click();
    }

    /**
     * denys the popup
     */
    public void deny(){
        popup.findElement(denyBy).click();
    }


}
