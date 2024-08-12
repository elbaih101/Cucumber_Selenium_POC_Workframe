
---

# Custom Framework for Kendo UI Elements

This document describes a custom framework for interacting with Kendo UI elements in automated tests. It includes an overview of the `KendoComboBox` class as an example of how to interact with various Kendo UI components.

## Overview

Kendo UI provides a set of rich, interactive widgets that can be challenging to automate. This framework aims to simplify interactions with Kendo UI elements by encapsulating their functionality in custom classes. Each custom class provides methods to interact with specific types of Kendo UI widgets.

## General Structure

The general approach for custom Kendo UI elements involves the following:

1. **Initialization**: Initialize the custom element with a `WebElement` representing the Kendo UI widget.
2. **Interaction Methods**: Define methods to interact with the widget, such as opening dropdowns, selecting items, and retrieving values.
3. **Utility Methods**: Include utility methods to facilitate common operations, such as waiting for elements to be visible or ensuring actions are completed.

## Example: Kendo Combo Box

### `KendoComboBox` Class

The `KendoComboBox` class is a specific implementation for interacting with Kendo UI Combo Boxes. It includes methods to open the dropdown, select items, retrieve the selected value, ext... .

#### Class Definition

```java
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

    // Methods follow
}
```

#### Methods

- **`getSelectedinput()`**: Returns the input element of the ComboBox.

    ```java
    public WebElement getSelectedinput() {
        return comboBox.findElement(comboBoxInput);
    }
    ```

- **`getDropDownButton()`**: Finds the dropdown list button.

    ```java
    void getDropDownButton() {
        dropDownButton = comboBox.findElement(dropDownButtonBy);
    }
    ```

- **`open()`**: Opens the dropdown list if it is not already expanded.

    ```java
    void open() {
        if (getSelectedinput().getAttribute("aria-expanded").equals("false")) {
            getDropDownButton();
            dropDownButton.click();
        }
    }
    ```

- **`getListItems()`**: Returns a list of all items in the dropdown list.

    ```java
    public List<WebElement> getListItems() {
        return listItems = comboBox.findElements(listItemsBy);
    }
    ```

- **`selectBySearch(String text)`**: Selects an item by searching for it. It selects the first item that matches the search text.

    ```java
    public void selectBySearch(String text) {
        getSelectedinput().sendKeys(text);

        Utils.sleep(300);
        selectedItem = getListItems().stream().filter(i -> i.getText().toLowerCase().contains(text.toLowerCase())).findFirst().orElseThrow();
        selectedItem.click();
    }
    ```

### Usage Example
using it with the Custom [Field Decorator](FrameWork.md) facilitates a more robust method of locating and initializing
**formally**:this was the method to initialze the custom web element
```java
// Initialize WebDriver and navigate to the page with the Kendo ComboBox

WebElement comboBoxElement = driver.findElement(By.id("comboBoxId")); // Locate the Kendo ComboBox
KendoComboBox comboBox = new KendoComboBox(comboBoxElement);

// Open the ComboBox dropdown
comboBox.open();

// Select an item by searching for it
comboBox.selectBySearch("Item Name");

// Verify the selected item (add assertions as needed)
String selectedItemText = comboBox.getSelectedinput().getAttribute("value");
assertEquals("Expected Item Name", selectedItemText);
```
***now*** : The optimal method with the pom page

```java
// Initialize WebDriver and navigate to the page with the Kendo ComboBox
import org.openqa.selenium.support.FindBy;

@FindBy(id = "comboBoxId")
KendoComboBox comboBox;

// Open the ComboBox dropdown
comboBox.open();

// Select an item by searching for it
        comboBox.selectBySearch("Item Name");

// Verify the selected item (add assertions as needed)
        String selectedItemText=comboBox.getSelectedinput().getAttribute("value");
        assertEquals("Expected Item Name",selectedItemText);
```
## Custom Classes for Other Kendo UI Elements

In addition to `KendoComboBox`, you can create custom classes for other Kendo UI elements using a similar approach. Here are some examples:

- **KendoDatePicker**: Interact with Kendo DatePicker widgets.
- **KendoGrid**: Interact with Kendo Grid components.
- **KendoDropDownList**: Handle Kendo DropDownList widgets.

Each class should encapsulate the specific interactions needed for the widget and provide utility methods as necessary.

## Conclusion

This framework aims to provide a structured approach to automating interactions with Kendo UI elements. By creating custom classes for each widget, you can streamline test automation and improve maintainability.

---

This document provides a general overview and example for Kendo UI elements. Adjust the details and add more classes as needed to fit the specific requirements of your framework and the Kendo UI components you're working with.