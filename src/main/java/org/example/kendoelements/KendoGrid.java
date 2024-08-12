package org.example.kendoelements;

import org.example.templates.CustomWebElement;
import org.example.tools.DriverManager;
import org.example.tools.Utils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class KendoGrid extends CustomWebElement {
    final WebElement grid;
    WebElement list;
    WebElement header;
    private final By lastCellBy = By.xpath("..//td[last()]");

    public KendoGrid(WebElement grid) {
        super(grid);
        this.grid = grid;

    }


    /**
     * returns the headercell of the given header name
     *
     * @param text enterd column header name
     * @return Webelement the header Cell
     */
    public WebElement getHeaderCellContains(String text) {
        initGrid();
        List<WebElement> columnsHeads = header.findElements(By.tagName("th"));
        for (WebElement head : columnsHeads) {
            if (head.getText().toLowerCase().contains(text.toLowerCase()))
                return head;
        }
        return null;
    }
    public WebElement getHeaderCellWithIndex(int index) {
        initGrid();
        index++;
        return header.findElement(By.cssSelector("th[aria-colindex='"+index+"']"));
    }

    /**
     * initiate the grid by locating the header and the list elements
     */
    public void initGrid() {
        list = grid.findElement(By.xpath(".//tbody"));
        header = grid.findElement(By.xpath(".//thead"));
    }

    /**
     * returns all the rows of the grid neglecting the header
     *
     * @return List<>WebElement</> all the rows of the grid
     */
    public List<WebElement> getGridRows() {
        initGrid();
        return list.findElements(By.cssSelector("tr")); // Assuming each row is a <tr> element
    }

    /**
     * finds the pager element in a grid
     *
     * @return Webelement the paged element
     */
    WebElement getPager() {
        return grid.findElement(By.cssSelector("kendo-pager"));
    }

    /**
     * selects the page size using the pagination select eleemnt
     *
     * @param pageSize int page size may be  100 50 20 10
     */
    public void selectPageSize(int pageSize) {
        Select select = new Select(getPager().findElement(By.cssSelector(".n-pager__sizes kendo-pager-page-sizes select")));
        select.selectByValue(String.valueOf(pageSize));
    }

    /**
     * navigates to the next page of the grid
     */
    public void gotoNextPage() {
        getPager().findElement(By.cssSelector("kendo-pager-next-buttons")).click();
    }

    public void gotoPreviousPage() {
        getPager().findElement(By.cssSelector("kendo-pager-prev-buttons")).click();
    }

    /**
     * navigates to a reuired page number using the grid pagination
     *
     * @param page int number of the page required
     */
    public void gotoPage(int page) {
        for (int i = 0; i < page; i++) {
            gotoNextPage();

        }
    }

    /**
     * returns number of total records from the pagination of the grid
     *
     * @return int number of records
     */
    public int getTotalReocrdsSize() {

        return Integer.parseInt(StringUtils.substringAfter(getPager().findElement(By.cssSelector(".n-pager__info")).getText()," of "));
    }

    /**
     * returns the page size based on the select element of the page value
     *
     * @return int page number
     */
    public int getPageSize() {
        return Integer.parseInt(getPager().findElement(By.cssSelector(".n-pager__sizes kendo-pager-page-sizes select")).getAttribute("value"));
    }

    /**
     * returns the current page number by calculating it based on the number of records visible
     *
     * @return int page number
     */
    public int getCurrentPage() {
        return (int) Math.ceil((double) Integer.parseInt(StringUtils.substringBetween(getPager().findElement(By.cssSelector(".n-pager__info")).getText(),"-"," of ").trim()) / (double) getPageSize());
    }

    /**
     * return the entire columns provided the indexes of the list neglecting the header
     *
     * @param columnIndexes index of clolumns want to retreave cells from
     * @return List<>WebElement</> contains all the cells td of the column
     */
    public List<WebElement> getGridCells(int ...columnIndexes) {
        initGrid();
        List<WebElement> cells=new ArrayList<>();
        for (int columnIndex :columnIndexes) {
            cells.addAll(list.findElements(By.xpath(".//td[@data-kendo-grid-column-index='" + columnIndex + "']")));
        }

        return cells ; // Assuming each column is a <td> element
    }

    /**
     * Uses the column and row index to get a required cell
     *
     * @param rowIndex    index of row
     * @param columnIndex index of column
     * @return returns the required cell
     */
    public WebElement getGridCell(int rowIndex, int columnIndex) {
        List<WebElement> rows = getGridRows();
        if (rowIndex >= rows.size()) {

            throw new IndexOutOfBoundsException("Row index out of bounds");
        }
        // Locate the cell using data-kendo-grid-column-index attribute
        return rows.get(rowIndex).findElement(By.xpath(".//td[@data-kendo-grid-column-index='" + columnIndex + "']"));
    }

    /**
     * in case the element to be used is a table and not a grid (no header)
     *
     * @param rowIndex     index of the row
     * @param columnnumber number of the column
     * @return Webelement the cell required
     */
    public WebElement getTableCell(int rowIndex, int columnnumber) {
        List<WebElement> rows = getGridRows();
        if (rowIndex >= rows.size()) {


            throw new IndexOutOfBoundsException("Row index out of bounds");
        }
        // Locate the cell using data-kendo-grid-column-index attribute
        return rows.get(rowIndex).findElement(By.xpath(".//td[" + columnnumber + "]"));
    }

    /**
     * returns a required grid cell using a base cell in the same record and a column index
     *
     * @param baseCell    Webelement A cell in the same record
     * @param columnIndex Webelement Column index of the required cell
     * @return Webelement the required cell //td
     */
    public WebElement getGridCell(WebElement baseCell, int columnIndex) {


        // Locate the cell using data-kendo-grid-column-index attribute
        return baseCell.findElement(By.xpath("..//td[@data-kendo-grid-column-index='" + columnIndex + "']"));
    }

    /**
     * returns a cell by providing the header name then it gets the column index of the header then using it to get the cell
     *
     * @param headerName the header name of the column of the required cell
     * @param service    Webelement the cell that belongs to the record required
     * @return Webelement the required cell of the grid
     */
    public WebElement getGridCell( WebElement service,String headerName) {
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        try {
            int columnIndex = Integer.parseInt(getHeaderCellContains(headerName).getAttribute("aria-colindex")) - 1;
            DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(10));
            return getGridCell(service, columnIndex);
        } catch (NullPointerException e) {
            LoggerFactory.getLogger(this.getClass()).error("the header doesn't exist ");
            DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(10));
            return null;
        }
    }

    /**
     * clicks a grid cell by its row and column index
     *
     * @param rowIndex    row index
     * @param columnIndex column index
     */
    public void clickGridCell(int rowIndex, int columnIndex) {
        WebElement cell = getGridCell(rowIndex, columnIndex);
        cell.click();
    }

    /**
     * sets the grid cell value by its row and column index
     *
     * @param rowIndex    row index
     * @param columnIndex column index
     * @param value       the value to be set
     */
    public void setGridCellValue(int rowIndex, int columnIndex, String value) {
        WebElement cell = getGridCell(rowIndex, columnIndex);
        cell.clear();
        cell.sendKeys(value);
    }

    /**
     * returns the last grid cell of a record by a given cell
     *
     * @param record the cell in the same record
     * @return Webelement the last cell of the record mainly an action cell
     */
    public WebElement getLastCell(WebElement record) {
        return record.findElement(lastCellBy);
    }

    public void clickRecordButtonWithNumber( WebElement record,Object buttonNumber) {
        WebElement actionsCell = record.findElement(lastCellBy);
        actionsCell.findElement(By.xpath(".//button["+buttonNumber+"]")).click();
    }

    /**
     * clicks the edit button of a given record provided a base cell in the same record
     *
     * @param record WebElement base cell in the same record
     */
    public void clickEditRecordButton(WebElement record) {
        WebElement actionsCell = record.findElement(lastCellBy);
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"icon-edit\")]]")).click();
    }

    /**
     * clicks the view button of a given record provided a base cell in the same record
     *
     * @param record WebElement base cell in the same record
     */
    public void clickViewRecordButton(WebElement record) {
        WebElement actionsCell = record.findElement(lastCellBy);
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"icon-eye\")]]")).click();
    }

    /**
     * clicks the print button of a given record provided a base cell in the same record
     *
     * @param record WebElement base cell in the same record
     */
    public void clickPrintRecordButton(WebElement record) {
        clickRecordButtonWithIcon(record, "icon-print");
    }

    /**
     * clicks the button with icon of a given record provided a base cell in the same record
     *
     * @param record WebElement base cell in the same record
     */
    public void clickRecordButtonWithIcon(WebElement record, String icon) {
        WebElement actionsCell = record.findElement(lastCellBy);
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"" + icon + "\")]]")).click();
    }

    /**
     * clicks the change button of a given record provided a base cell in the same record
     *
     * @param record WebElement base cell in the same record
     */
    public void clickChangeRecordButton(WebElement record) {
        WebElement actionsCell = record.findElement(lastCellBy);
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"icon-change\")]]")).click();
    }

    /**
     * clicks the delete button of a given record provided a base cell in the same record
     *
     * @param record WebElement base cell in the same record
     */
    public void clickDeleteRecordButton(WebElement record) {
        WebElement actionsCell = record.findElement(lastCellBy);
        actionsCell.findElement(By.xpath(".//button[.//*[name()=\"use\" and contains(@*,\"icon-delete\")]]")).click();
    }

    /**
     * clicks the more menu button of a given record provided a base cell in the same record
     *
     * @param record WebElement base cell in the same record
     */
    public WebElement clickRecordMoreMenuButton(WebElement record) {
        WebElement actionsCell = record.findElement(lastCellBy);

        WebElement moreActionButton = actionsCell.findElement(By.xpath(".//div[.//*[name()=\"use\" and contains(@*,\"icon-more\")]]"));
        moreActionButton.click();
        return moreActionButton;
    }

    /**
     * selects an action of a record by clicking the more action menu then choosing the action by text
     * {@code @clickRecordMoreMenuButton(WebElement record)}
     *
     * @param record WebElement base cell in the same record
     * @param text   the action text
     */
    public void selectRecordActionByText(WebElement record, String text) {
        List<WebElement> actions = clickRecordMoreMenuButton(record).findElements(By.xpath("//ul[@class=\"n-table__more-actions-menu\"]//li"));
        WebElement selectedAction = actions.stream().filter(i -> i.getText().toLowerCase().contains(text.toLowerCase())).findFirst().orElseThrow();
        Utils.sleep(300);
        selectedAction.click();

    }
}