Here’s the Markdown documentation for the `Nazeel_Calculations` class:

---

# Nazeel_Calculations Class Documentation

## Overview

The `Nazeel_Calculations` class contains methods for performing various tax calculations used in the Nazeel system. These calculations include determining taxes on reservations and outlet orders, handling discounts, and retrieving tax calculation methods.

## Methods

### `public static Double reservationRentTaxes(double rentAmount, double discountAmount, String discountType, List<Tax> appliedTaxes, int calcMethod)`

Calculates the rent taxes based on the provided rent amount, discount amount, discount type, applied taxes, and calculation method.

- **Parameters:**
    - `rentAmount` (double): The total rent amount.
    - `discountAmount` (double): The discount amount to apply.
    - `discountType` (String): Type of discount ("percentage" or "amount").
    - `appliedTaxes` (List<Tax>): List of applied taxes.
    - `calcMethod` (int): Calculation method (used to determine how to handle discounts).

- **Returns:**
    - `Double`: The calculated tax value applied to the rent amount.

### `public static Double outletOrderTaxes(double orderAmount, double discountAmount, boolean inclusive, int calcMethod)`

Calculates the taxes for outlet orders based on the provided order amount, discount amount, inclusivity, and calculation method.

- **Parameters:**
    - `orderAmount` (double): The total order amount.
    - `discountAmount` (double): The discount amount to apply.
    - `inclusive` (boolean): Whether the taxes are inclusive or exclusive.
    - `calcMethod` (int): Calculation method (used to determine how to handle discounts).

- **Returns:**
    - `Double`: The calculated tax value for the order amount.

### `public static Double getDiscountAmount(double Amount, double discountValue, String discountType)`

Calculates the discount amount based on the provided amount, discount value, and discount type.

- **Parameters:**
    - `Amount` (double): The total amount.
    - `discountValue` (double): The discount value.
    - `discountType` (String): The type of discount ("percentage" or "amount").

- **Returns:**
    - `Double`: The calculated discount amount.

### `public static int getTaxCalculationMethod(WebDriver driver, Runnable runnable)`

Retrieves the tax calculation method from a web service API.

- **Parameters:**
    - `driver` (WebDriver): The WebDriver instance used to make the API call.
    - `runnable` (Runnable): A Runnable to trigger the API request.

- **Returns:**
    - `int`: The tax calculation method retrieved from the API.

## Notes

- The `reservationRentTaxes` method handles both inclusive and exclusive tax calculations and is capable of applying various types of taxes.
- The `outletOrderTaxes` method assumes a fixed tax percentage and adjusts for inclusivity.
- The `getDiscountAmount` method determines the discount amount based on whether the discount is a percentage or a fixed amount.
- The `getTaxCalculationMethod` method retrieves the tax calculation method via an API call and parses the response to extract the relevant data.

## Dependencies

Ensure the following dependencies are included in your project for this class to work:

- **Gson Library**: For parsing JSON responses.
- **WebDriver API**: For interacting with web elements and making API requests.[here](ApiLesteningUsingDevTools.md)

---

This documentation provides a detailed overview of each method within the `Nazeel_Calculations` class, including their parameters, return types, and functionality. Adjust as necessary based on your project’s requirements.