
---

# API Class Documentation

## Overview

The `API` class provides functionality to handle and validate API requests and responses using Selenium's DevTools. It allows you to listen to network responses, retrieve response bodies, and perform actions based on network traffic during automated testing.

## Key Components

- **DevTools**: Selenium's DevTools interface used to interact with browser's DevTools protocol.
- **Network**: DevTools protocol methods related to network activities.

## Class Fields

- `final AtomicReference<Network.GetResponseBodyResponse> asyncResponse`: Holds the response body of asynchronous requests.
- `DevTools devTools`: Manages the DevTools session for the WebDriver.

## Methods

### `asyncRequestListener(EdgeDriver driver, String requestUrl)`

Starts a DevTools session to listen for network responses for a given request URL.

- **Parameters:**
    - `driver` - The `EdgeDriver` instance.
    - `requestUrl` - The URL to listen for in the network responses.

### `await()`

Waits for and retrieves the response body after an asynchronous request.

- **Returns:** `Network.GetResponseBodyResponse` - The response body of the request.
- **Throws:** `RuntimeException` if no response body is found within the specified timeout.

### `getResponseBody(WebDriver driver, String requestUrl, Runnable requestTrigger)`

Retrieves the response body of a network request triggered by a provided `Runnable` function.

- **Parameters:**
    - `driver` - The WebDriver instance (must be a `ChromiumDriver`).
    - `requestUrl` - The URL to listen for in the network responses.
    - `requestTrigger` - A `Runnable` function to trigger the network request.
- **Returns:** `String` - The response body of the request.

### `getResponseBody(WebDriver driver, List<String> requestUrls, Runnable requestTrigger)`

Retrieves the response body for one of several possible network requests triggered by a provided `Runnable` function.

- **Parameters:**
    - `driver` - The WebDriver instance (must be a `ChromiumDriver`).
    - `requestUrls` - A list of URLs to listen for in the network responses.
    - `requestTrigger` - A `Runnable` function to trigger the network request.
- **Returns:** `String` - The response body of the request.

### `getResponseBody(ChromeDriver driver, String requestUrl, Runnable requestTrigger)`

Retrieves the response body of a network request in Chrome, triggered by a provided `Runnable` function.

- **Parameters:**
    - `driver` - The `ChromeDriver` instance.
    - `requestUrl` - The URL to listen for in the network responses.
    - `requestTrigger` - A `Runnable` function to trigger the network request.
- **Returns:** `String` - The response body of the request.
- **Throws:** `RuntimeException` if no response body is found within the specified timeout.

## Example Usage

### Initialize and Use API with EdgeDriver

```java
API api = new API();
api.asyncRequestListener(edgeDriver, "https://example.com/api");
String responseBody = api.await().getBody();
```

### Get Response Body with ChromiumDriver

```java
API api = new API();
String responseBody = api.getResponseBody(chromiumDriver, "https://example.com/api", () -> {
    // Trigger network request here
});
```

### Get Response Body with List of URLs

```java
API api = new API();
List<String> urls = Arrays.asList("https://example.com/api1", "https://example.com/api2");
String responseBody = api.getResponseBody(chromiumDriver, urls, () -> {
    // Trigger network request here
});
```

### Get Response Body with ChromeDriver

```java
API api = new API();
String responseBody = api.getResponseBody(chromeDriver, "https://example.com/api", () -> {
    // Trigger network request here
});
```

## Dependencies

Ensure that the following dependencies are included in your project:

- Selenium WebDriver
- Selenium DevTools Protocol (for network-related functionalities)

---

This documentation provides a clear overview of the `API` class, detailing its methods and usage for handling network requests and responses during Selenium tests. Adjust the examples as needed for your specific use case.

the `API` is used through the project on various occasions check [NazeelCalculatons](nazeel calculations.md)