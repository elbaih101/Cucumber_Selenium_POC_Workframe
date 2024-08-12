Here is a Markdown documentation for the provided classes related to parallel test execution with Cucumber and TestNG, focusing on user management:

---

# Parallel Test Execution Documentation

This documentation outlines the classes and their roles in managing user sessions and executing tests in parallel using Cucumber and TestNG in the Nazeel Project.

### Reference 
-  [parralel web Driver Maager](driverManager.md)


## Table of Contents

- [BaseTestNGCucumberRunner Class](#basetestngcucumberrunner-class)
- [UserDataReader Class](#userdatareader-class)
- [User Class](#user-class)

## BaseTestNGCucumberRunner Class

### Overview

The `BaseTestNGCucumberRunner` class extends `AbstractTestNGCucumberTests` to enable parallel test execution with Cucumber and TestNG. It manages user data for each test run by using `ThreadLocal` to ensure that each thread (test) gets its own user instance.

### Key Methods

- **`setUser(User value)`**: Sets the `User` instance for the current thread.
- **`getUser()`**: Retrieves the `User` instance for the current thread.

### Code Example

```java
package alia.nazeel.templates;

import alia.nazeel.pojos.User;
import alia.nazeel.pojos.UserDataReader;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/main/resources/features",
        glue = {"alia.nazeel.stepDefs"}
)
public class BaseTestNGCucumberRunner extends AbstractTestNGCucumberTests {

    protected static final ThreadLocal<User> user = new ThreadLocal<>();

    static {
        setUser(UserDataReader.getNextUser());
    }

    /**
     * Sets the user for the individual runner class.
     * @param value User the user
     */
    public static void setUser(User value) {
        user.set(value);
    }

    /**
     * Gets the user for the individual runner class.
     * @return User the user for the runner
     */
    public static User getUser() {
        return user.get();
    }
}
```

## UserDataReader Class

### Overview

The `UserDataReader` class is responsible for reading user data from a JSON file and providing the next `User` object for each test run. This class helps in managing user sessions in a parallel test execution environment.

### Key Methods

- **`getNextUser()`**: Retrieves the next `User` object from the list of users in a round-robin fashion.

### Code Example

```java
package alia.nazeel.pojos;

import alia.nazeel.tools.JsonUtils;

/**
 * Responsible for managing user sessions in a parallel test environment.
 */
public class UserDataReader {
    private static final String JSON_FILE = "src/main/resources/testdata/admin-Users.json";
    private static User[] users;
    private static int currentIndex = 0;

    static {
        users = JsonUtils.readJsonFromFile(JSON_FILE, User[].class);
    }

    public static synchronized User getNextUser() {
        if (users != null && users.length > 0) {
            currentIndex = (currentIndex + 1) % users.length;
            return users[currentIndex];
        }

        return null;
    }
}
```

## User Class

### Overview

The `User` class is a POJO used for storing user credentials. It is utilized in test scenarios to perform actions requiring user authentication or session management.

### Key Methods

- **`getUserName()`**: Retrieves the username.
- **`setUserName(String username)`**: Sets the username.
- **`getPassword()`**: Retrieves the password.
- **`setPassword(String password)`**: Sets the password.

### Code Example

```java
package alia.nazeel.pojos;

/**
 * POJO for user data used in session management.
 */
public class User {

    private String username;
    private String password;
    private String vcc;

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

---
more on the parallel webDriver management [parralel web Driver Maager](driverManager.md)


---
This documentation provides a summary of the classes involved in user management and parallel test execution, detailing their responsibilities and key methods.