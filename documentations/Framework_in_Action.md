
---

# Framework Overview and Test Case Dependencies

## Framework Structure

The framework is designed to facilitate automated testing with a focus on data-driven tests and parallel execution. It integrates with Cucumber for BDD-style tests and uses various utilities and tools to manage WebDriver instances, perform calculations, and handle API requests.

### Key Components

1. **Cucumber Integration**
    - **Feature Files**: Located in `src/main/resources/features`, these define the test scenarios in Gherkin syntax.
    - **Step Definitions**: Located in `org.example.stepDefs`, these implement the steps defined in the feature files.

2. **Test Execution**
    - **Test Runner**: `BaseTestNGCucumberRunner` sets up the Cucumber test execution with TestNG integration.

3. **Data Management**
    - **POJOs**: Plain Old Java Objects (POJOs) are used to represent various data models.
    - **Data Readers**: Classes like `UserDataReader` and `TaxDataReader` handle reading and providing data from JSON files.

4. **WebDriver Management**
    - **DriverManager**: Manages WebDriver instances and ensures thread safety for parallel execution.
    - **API**: Provides methods to interact with DevTools for API request validation.

5. **Utilities**
    - **Nazeel_Calculations**: Provides methods for tax calculations and handling discounts.
    - **ReportMailer**: Sends test reports via email with attachments.

## Data Storage and Retrieval

### POJOs and JSON Files

POJOs are used to model the data structures for the framework. Data is stored in JSON files and read into these POJOs for use in test cases.

#### Example POJOs

1. **User** (`org.example.pojos.User`):
   ```java
   public class User {
       private String username;
       private String password;
       private String vcc;

       // Getters and Setters
   }
   ```

2. **Tax** (`org.example.pojos.Tax`):
   ```java
   public class Tax {
       private String name;
       private double value;
       private boolean inclusive;
       private String appliedFor;
       private String method;

       // Getters and Setters
   }
   ```

#### Data Reader Classes

- **UserDataReader**:
  Reads user data from `admin-Users.json`.
   ```java
   public class UserDataReader {
       private static final String JSON_FILE = "src/main/resources/testdata/admin-Users.json";
       private static User[] users;

       static {
           users = JsonUtils.readJsonFromFile(JSON_FILE, User[].class);
       }

       public static synchronized User getNextUser() {
           // Implementation
       }
   }
   ```

- **TaxDataReader**:
  Reads tax data from a JSON file (example, not provided in the code above).

### Test Case Dependencies

#### Data-Driven Tests

- **Initialization**: Test data is initialized in `BaseTestNGCucumberRunner` using `UserDataReader.getNextUser()` to provide different users for parallel test execution.

- **Data Utilization**: POJOs are populated with data read from JSON files. This data is used across test cases to validate functionality.

#### Example Test Case Flow

1. **Read Data**:
    - Retrieve test data from JSON files using data readers (e.g., `UserDataReader`).

2. **Setup**:
    - Initialize WebDriver and test environment based on the retrieved data.

3. **Execution**:
    - Run test scenarios defined in feature files using the initialized data.

4. **Validation**:
    - Perform assertions and validations based on the expected outcomes.

5. **Report and Cleanup**:
    - Generate test reports and send them via email using `ReportMailer`.

## Summary

The framework leverages Cucumber for BDD, integrates with TestNG for test execution, and utilizes POJOs and JSON files for data management. By separating test data from test logic and using data-driven approaches, the framework allows for efficient and scalable testing.

---

This document provides a high-level overview of how the framework operates, including how data is managed and used in test cases. Adjust the details as needed to match your specific implementation and requirements.