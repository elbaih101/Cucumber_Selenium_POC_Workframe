package org.example.kendoelements;

import org.example.templates.CustomWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class MultiLangTextField extends CustomWebElement {

    String firstLangText;
    String secondLangText;

    WebElement multiLangTextField;
    By menuBy = By.xpath("./../../following-sibling::div");
    By secondInput = By.xpath("./../../following-sibling::div/input[2]");
    By secondLangInputButtonBy = By.xpath("./following-sibling::button");

    public MultiLangTextField(WebElement multiLangTextField) {
        super(multiLangTextField);
        this.multiLangTextField = multiLangTextField;
    }

    /**
     * sets the both language fields of the field by clearing its content then sting it  with he provided list[String]
     * if the string list is only 1 element then only the visible text field will be set
     * @param text array [String] text to be set in the multi language fields
     */
    public void sendKeys(String... text) {
        sendKeysFirstLangField(text[0]);
        if (text.length > 1) {
            sendKEysSecondLangField(text[1]);
        }
    }

    /**
     * clears both language fields by clearing first field then oppening the second fieldand clearing it
     */
    public void clear() {
        clearFirstLangField();
        clearSecondLangField();

    }

    /**
     * opens the second field and returns it by finding it using the provided By
     * @return WebElement Second Language field
     */
    WebElement secondLangField() {
        if (!multiLangTextField.findElement(menuBy).getAttribute("class").contains("show")) {
            clicktheMultiTextButton();
        }
        return multiLangTextField.findElement(secondInput);
    }

    /**
     * clicks the multi language text to open the seci=ond language field
     */
    private void clicktheMultiTextButton() {
        multiLangTextField.findElement(secondLangInputButtonBy).click();
    }

    /**
     * clears the base text field
     */
    public void clearFirstLangField() {
        multiLangTextField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
    }

    /**
     * clears the additional language field by oppening it and clearing its value
     */
    public void clearSecondLangField() {
        secondLangField().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        clicktheMultiTextButton();
    }

    /**
     * send keys to the base language field
     * @param keys String the keys to be sent
     */
    public void sendKeysFirstLangField(String keys) {
        firstLangText = keys;
        multiLangTextField.sendKeys(keys);
    }

    /**
     * send keys to the additional language field
     * @param keys String the keys to be sent
     */
    public void sendKEysSecondLangField(String keys) {
        secondLangText = keys;
        secondLangField().sendKeys(keys);
        clicktheMultiTextButton();
    }

    /**
     * gets the text present in the base language field
     * @return String base language field text
     */
    public String getFirstLangText() {
        return firstLangText = multiLangTextField.getAttribute("value");
    }

    /**
     * gets the text present in the additional language field
     * @return String additional language field text
     */
    public String getSecondLangText() {
        secondLangText = secondLangField().getAttribute("value");
        clicktheMultiTextButton();
        return secondLangText;
    }
}
