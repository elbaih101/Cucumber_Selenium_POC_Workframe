package org.example.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * <h>CustomAssert</h>
 * <p>this class is a custom assert class fitted to the project it allows for custom assertion methods to be applied on the project with ease</p>
 */
public class CustomAssert extends SoftAssert {

    CustomWebDriverWait wait = new CustomWebDriverWait(Duration.ofSeconds(10));

    /**
     * checks if a string contains another string
     *
     * @param a Actal String
     * @param b Expected String to be contained in a
     */
    public void AssertContains(String a, String b) {
        assertTrue(a.contains(b), "Expected: " + b + "\nActual: " + a);
    }

    /**
     * checks if a string contains another string
     *
     * @param a Actal String
     * @param b Expected String to be contained in a
     */
    public void AssertContains(String a, String b, String msg) {

        assertTrue(a.contains(b), msg + " Expected: " + b + "\nActual: " + a);
    }

    /**
     * Check Each string of
     *
     * @param a Ist of Strins to combare
     * @param b the string to compare with
     */
    public void AssertContains(List<String> a, String b) {
        int matches = 0;
        String currentItem = null;
        for (String s : a) {
            currentItem = s;
            if (s.contains(b))
                matches++;
        }
        if (matches == 0)
            //noinspection DataFlowIssue
            assertTrue(false, "Expected: " + b + "\nActual: " + currentItem);
    }

    public void AssertEqualsIgnoreCase(String actual, String expected) {
        if (actual == null && expected == null) {
            assertTrue(true);
            return;
        }
        if (actual == null || expected == null) {
            //noinspection DataFlowIssue
            assertFalse(true);

        } else
            assertTrue(actual.equalsIgnoreCase(expected), "Expected :" + expected + "\nActual: " + actual);
    }

    public void AssertEqualsIgnoreCase(String actual, String expected, String expectedName) {
        assertTrue(actual.equalsIgnoreCase(expected), "Expected " + expectedName + ":" + expected + "\nActual: " + actual);
    }

    public void AssertAnyMatch(List<WebElement> a, Predicate<WebElement> condition) {

        assertTrue(a.stream().anyMatch(condition));
    }


    public void AssertAnyMatch(List<WebElement> a, Predicate<WebElement> condition, String msg) {

        assertTrue(a.stream().anyMatch(condition), msg);
    }

    public void AssertNonMatch(List<WebElement> a, Predicate<WebElement> condition) {

        assertTrue(a.stream().noneMatch(condition));
    }

    public void AssertNonMatch(List<WebElement> a, Predicate<WebElement> condition, String msg) {

        assertTrue(a.stream().noneMatch(condition), msg);
    }

    public void AssertToastMessagesContains(String mesage) {
        List<WebElement> toastMsgs = DriverManager.getDriver().findElements(By.className("toast-message"));
        wait.until(ExpectedConditions.visibilityOfAllElements(toastMsgs));
        List<String> mesagesContents = new ArrayList<>();
        for (WebElement toast : toastMsgs) {

            if (toast.getText().trim().toLowerCase().contains(mesage.toLowerCase())) {
                this.assertTrue(true);
                return;
            } else {
                mesagesContents.add(toast.getText());
            }

        }
        if (!mesagesContents.isEmpty())
            //noinspection DataFlowIssue
            this.assertFalse(true, "actual : " + Arrays.toString(mesagesContents.toArray()) + "\nExpected : " + mesage.toLowerCase() + "\n");

    }

}
