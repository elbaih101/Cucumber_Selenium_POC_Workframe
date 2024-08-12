Here’s the Markdown documentation for the `ReportMailer` class, detailing its functionality, methods, and usage:

---

# ReportMailer Class Documentation

## Overview

The `ReportMailer` class is designed to send email reports with attached files. It uses JavaMail API to connect to an SMTP server, authenticate, and send emails with attachments. The class includes features for setting up the email content, adding recipients, and handling attachments.

## Configuration

- **SMTP Server:** `smtp server address`
- **Username:** `server user name`
- **Password:** `smtp password` (This should be replaced with a secure method for storing credentials)
- **From Email:** `from email`
- **To Email:** `to email`
- **CC Emails:** `List<Strings> the recevieng mails`
- **Subject:** `Automation Testing Report`
- **Report Directory:** `target/cucumber-reports`
- **Zip File Name:** `Cucumber_Reports.zip`

## Main Method

### `public static void main(String[] args)`

The entry point for the application. Configures SMTP properties, creates a session, and sends an email with the specified attachments.

- **Steps:**
    1. Set up SMTP properties and session.
    2. Create and configure the email message.
    3. Attach files from the report directory.
    4. Send the email.

### Exception Handling

- **Exceptions:**
    - `MessagingException` - If there is an error with sending the email.
    - `IOException` - If there is an error reading the files for attachment.

## Methods

### `static Multipart multiPartAttachments()`

Creates a `Multipart` object containing email attachments from the report directory.

- **Returns:** `Multipart` - The multipart object with file attachments.
- **Throws:** `MessagingException`, `IOException` - If there are issues with creating or adding attachments.

### Example Usage

The `ReportMailer` class is intended to be executed from the command line or as part of a build process. Here’s how you can use it in a Maven build:

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.0.0</version>
    <executions>
        <execution>
            <id>send-email</id>
            <phase>install</phase>
            <goals>
                <goal>java</goal>
            </goals>
            <configuration>
                <mainClass>alia.nazeel.tools.ReportMailer</mainClass>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## Dependencies

Ensure that the following dependency is included in your Maven `pom.xml`:

```xml
<dependency>
    <groupId>com.sun.mail</groupId>
    <artifactId>jakarta.mail</artifactId>
    <version>${jakartamail.version}</version>
</dependency>
```

## Notes

- The password in the code is currently in plain text. For security reasons, consider using environment variables or a secrets manager.
- The `zipDirectory` method is commented out but can be used to zip the report directory before attaching it to the email.

---

This documentation provides an overview of the `ReportMailer` class, its purpose, configuration, and methods. Adjust any sensitive information and paths as needed for your specific use case.