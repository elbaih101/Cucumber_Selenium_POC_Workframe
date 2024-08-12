

---

# Nazeel-Project Documentation

## Table of Contents

1. [Maven POM Structure](#maven-pom-structure)
2. [Custom Web Element Framework](#custom-web-element-framework)
3. [Parallel Excution](#Parallel-Excution)
4. [external dependencies](#External-Depencencies)
5. [Mailing Reports](#E-mailing-Reports)

---

## Maven POM Structure

The `Nazeel-Project` uses a Maven POM file to manage its dependencies, plugins, and build configurations. Key
dependencies include Cucumber, Selenium, and Jackson for JSON processing. The project also uses plugins for test
management and reporting.

For more details, see the full POM structure [here](documentations/PomSructure.md).

---

## Custom Web Element Framework

This project includes a custom framework for managing web elements using Selenium. The `CustomFieldDecorator` class
allows for advanced handling of `WebElement` annotations, while the `CustomWebElement` class provides additional
utilities for interacting with web elements.

For a detailed explanation, see the full documentation [here](documentations/FrameWork.md).

---

## Parallel Excution

Parallel execution allows multiple tests to run simultaneously, which can significantly speed up the test execution
process. This guide covers the setup and management of parallel execution in Selenium tests using  `maven-surefire-plugin`.

### Overview

Parallel execution in Selenium with TestNG and  `maven-surefire-plugin` involves configuring TestNG to run tests in multiple threads. This is often
achieved by using a `ThreadLocal` variable to store WebDriver instances, ensuring that each thread operates with its own
WebDriver instance.

### Key Components

1. **DriverManager**: Manages WebDriver instances for each thread, ensuring thread safety.
2. **BaseTestNGCucumberRunner**: Configures Cucumber tests to run with TestNG, providing thread-local storage for user
   data.

## DriverManager Class

The `DriverManager` class handles WebDriver instances and ensures that each thread has its own WebDriver.

##### references

- [Parallel Driver Manager](documentations/driverManager.md)

### Key Methods

- **`initializeDriver(Drivers driverName, Driver_Mode mode)`**: Initializes and sets a WebDriver instance for the
  current thread.
- **`getDriver()`**: Retrieves the WebDriver instance for the current thread.
- **`quitDriver()`**: Quits the WebDriver instance and removes it from thread-local storage.
- **`getWebDriverType(WebDriver driver)`**: Determines the type of WebDriver instance.
- **`edgePrintingAndDownloadOptions()`**: Configures EdgeOptions for printing and download settings.

### Example Usage

```java
DriverManager.initializeDriver(Drivers.Chrome, Driver_Mode.Normal);

WebDriver driver = DriverManager.getDriver();
```

## BaseTestNGCucumberRunner Class

The `BaseTestNGCucumberRunner` class sets up parallel execution for Cucumber tests with TestNG.

### references

- [parallel execution](documentations/parralelExcution.md)

### Key Methods

- **`setUser(User value)`**: Sets the user for the individual test runner class.
- **`getUser()`**: Retrieves the user for the individual test runner class.

### Example Usage

```java

@CucumberOptions(features = "src/main/resources/features", glue = {"alia.nazeel.stepDefs"})
public class BaseTestNGCucumberRunner extends AbstractTestNGCucumberTests {

    protected static final ThreadLocal<User> user = new ThreadLocal<>();

    static {
        setUser(UserDataReader.getNextUser());
    }

    public static void setUser(User value) {
        user.set(value);
    }

    public static User getUser() {
        return user.get();
    }
}
```

## Configuration in `pom.xml`

using the `maven-surefire-plugin` in the  `pom.xml` file
to configure the amount of threads and the method of parallelism:

### references

- [POM Structure](documentations/PomSructure.md)

```xml

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>${surefire.version}</version>
    <configuration>
        <!--this to control the parallelism over the classes not the methods-->
        <parallel>classes</parallel>
        <!--the maximum count of threads -->
        <threadCount>4</threadCount>
        <!--ignoring test failure to skip  -->
        <testFailureIgnore>true</testFailureIgnore>
        <!--order of running the classes -->
        <runOrder>Alphabetical</runOrder>
        <!--the target classes to be run -->
        <includes>
            <include>**/*Runners.java</include>
        </includes>
    </configuration>
    <!--handling the execution of the test classes -->
    <executions>
        <execution>
            <id>surefire-test</id>
            <!--the phase of the test to run the test classes-->
            <phase>test</phase>
            <!--set the goal as the test project -->
            <goals>
                <goal>test</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Conclusion

Parallel execution helps in reducing the total test execution time and ensures efficient use of resources. By
using `DriverManager` for WebDriver management and `BaseTestNGCucumberRunner` for setting up Cucumber tests, you can
effectively run tests in parallel.

---

## External-Depencencies

The project depends on the Newman CLI tool for running Postman collections. To set up Newman, follow these steps:

1. Install Node.js using Chocolatey:

   ```sh
   choco install nodejs.install
   ```

2. Install the latest version of npm globally:

   ```sh
   npm install -g npm@10.8.1
   ```

3. Install Newman globally:

   ```sh
   npm install -g newman
   ```

These commands should be run separately in PowerShell or Windows Terminal.
--- 

### the newMan Runner Class

this class manages the running of `postman` collection files with the `.json` extension given its path

```java
public class NewMan {

    static String userDirectory = System.getProperty("user.home");

    public static void runPostmanTestCase(String postman_collection_Path) {
        try {
            // Define the command to run Newman
            String[] command = {
                    userDirectory + "\\AppData\\Roaming\\npm\\newman.cmd",
                    "run",
                    postman_collection_Path, // Replace with the path to your Postman collection file
                    "--reporters",
                    "cli"
            };

            // Start the process
            Process process = new ProcessBuilder(command).start();

            // Read the output
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Check if the process completed successfully
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException(postman_collection_Path + " failed with exit code: " + exitCode);
            }

        } catch (Exception e) {
            LoggerFactory.getLogger(NewMan.class).error("Failed to execute Postman test case", e);
        }
    }
}

```

---

## E-mailing Reports

### References

- [mailing reports using `jakarta-mail` dependency and `maven-execute` plugin](documentations/ReportsMailing.md)

using the `jakarta-mail` dependency to create a body of the mail and attatching emailabe cucumber reports
then executing the class on the `install` phase of maven after the reports werer generated in the `verify` phase

---