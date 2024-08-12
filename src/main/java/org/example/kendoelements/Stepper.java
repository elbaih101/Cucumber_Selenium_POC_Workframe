package org.example.kendoelements;

import org.example.templates.CustomWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Stepper extends CustomWebElement {
    WebElement stepper;
    By stepsBy = By.cssSelector("div.n-stepper__item");
    By completedStepsBy = By.cssSelector("div.n-stepper__item--completed");
    By activeStepBy = By.cssSelector("div.n-stepper__item--active");
    List<StepperItem> steps = new ArrayList<>();

    public Stepper(WebElement element) {
        super(element);
        stepper = element;
    }

    public List<StepperItem> getSteps() {

        List<WebElement> s = stepper.findElements(stepsBy);
        for (WebElement e : s) {
            steps.add(new StepperItem(e));
        }
        return steps;
    }public List<StepperItem> getCompletedSteps() {

        List<WebElement> s = stepper.findElements(completedStepsBy);
        for (WebElement e : s) {
            steps.add(new StepperItem(e));
        }
        return steps;
    }

    public StepperItem getActiveStep() {
        return new StepperItem(stepper.findElement(activeStepBy));
    }


    public static class StepperItem extends CustomWebElement {
        WebElement step;
        int stepNo;
        String stepTitle;

        public StepperItem(WebElement element) {
            super(element);
            step = element;
            stepNo = Integer.parseInt(element.findElement(By.cssSelector("div.n-stepper__no")).getText());
            stepTitle = element.findElement(By.cssSelector("div.n-stepper__title")).getText();
        }

        public int getNo() {
            return stepNo;
        }

        public String getTilte() {
            return stepTitle;
        }

        @Override
        public boolean isEnabled() {
            return step.getAttribute("class").contains("enabled") || step.getAttribute("class").contains("active") || step.getAttribute("class").contains("completed");
        }


    }


}
