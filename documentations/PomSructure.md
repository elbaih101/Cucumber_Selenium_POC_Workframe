# Maven POM Structure for Nazeel-Project

This document provides an overview of the Maven Project Object Model (POM) structure used for the `Nazeel-Project`. The POM file defines the project configurations, dependencies, plugins, and build management for this Java-based project.

## Project Overview

- **Group ID**: `alia.nazeel`
- **Artifact ID**: `Nazeel-Project`
- **Version**: `1.0-SNAPSHOT`
- **Java Version**: 21

## Properties

The following properties are defined in the POM to manage versions of dependencies and plugins:

```xml
<properties>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <cucumber.version>7.18.1</cucumber.version>
    <selenium.version>4.23.0</selenium.version>
    <webdrivermanager.version>5.9.1</webdrivermanager.version>
    <reporting.version>5.8.1</reporting.version>
    <jackson.version>2.17.1</jackson.version>
    <gson.version>2.10.1</gson.version>
    <surefire.version>3.2.5</surefire.version>
    <faker.version>1.0.2</faker.version>
    <snakeyaml.version>2.0</snakeyaml.version>
    <slf4j.version>2.0.13</slf4j.version>
    <annotations.version>23.0.0</annotations.version>
</properties>
```

## Dependencies

The POM file includes several dependencies essential for the project, grouped into categories:

### Cucumber and Selenium

- **Cucumber TestNG**: `io.cucumber:cucumber-testng:${cucumber.version}`
- **Cucumber Java**: `io.cucumber:cucumber-java:${cucumber.version}`
- **Selenium Java**: `org.seleniumhq.selenium:selenium-java:${selenium.version}`
- **WebDriverManager**: `io.github.bonigarcia:webdrivermanager:${webdrivermanager.version}`

### Reporting

- **Maven Cucumber Reporting**: `net.masterthought:maven-cucumber-reporting:${reporting.version}`

### Email Sending

- **Jakarta Mail**: `com.sun.mail:jakarta.mail:1.6.7`

### JSON Manipulation and Extraction

- **Jackson Core**: `com.fasterxml.jackson.core:jackson-core:${jackson.version}`
- **Gson**: `com.google.code.gson:gson:${gson.version}`

### Data Generation

- **Java Faker**: `com.github.javafaker:javafaker:${faker.version}`
- **SnakeYAML**: `org.yaml:snakeyaml:${snakeyaml.version}`

### Logging

- **SLF4J Simple**: `org.slf4j:slf4j-simple:${slf4j.version}`
- **JetBrains Annotations**: `org.jetbrains:annotations:${annotations.version}`

## Build Configuration

### Plugin Management

The POM file defines several plugins for managing the build lifecycle:

- **Maven Surefire Plugin**: Manages the execution of tests.
- **Maven Cucumber Reporting Plugin**: Generates Cucumber reports.
- **Exec Maven Plugin**: Executes custom Java classes, such as sending emails after a successful build.

### Surefire Plugin Configuration

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

### Cucumber Reporting Plugin Configuration

the project uses the cucumber reporting tool for its ease of use and detailed reports 

```xml

<plugin>
    <groupId>net.masterthought</groupId>
    <artifactId>maven-cucumber-reporting</artifactId>
    <version>${reporting.version}</version>
    <executions>
        <execution>
            <id>execution</id>
            <!-- the phase to run the reporting tool  determined as in verify -->
            <phase>verify</phase>
            <!-- verified the maven goal as to generate -->
            <goals>
                <goal>generate</goal>
            </goals>
            <!--the configuration and files to use for report generating-->
            <configuration>
                <projectName>Nazeel-Project</projectName>
                <inputDirectory>${project.build.directory}/cucumber-reports</inputDirectory>
                <skipEmptyJSONFiles>true</skipEmptyJSONFiles>
                <jsonFiles>
                    <param>**/*.json</param>
                </jsonFiles>
                <outputDirectory>${project.build.directory}/cucumber-reports</outputDirectory>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## Profiles

### Cucumber Report Only Profile

A special profile named `cucumber-report-only` is defined to generate Cucumber reports without running tests.

```xml
<profile>
    <id>cucumber-report-only</id>
    <properties>
        <skipTests>true</skipTests>
    </properties>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <skipTests>true</skipTests>
                </configuration>
            </plugin>
            <plugin>
                <groupId>net.masterthought</groupId>
                <artifactId>maven-cucumber-reporting</artifactId>
                <executions>
                    <execution>
                        <id>generate-cucumber-reports</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                        <configuration>
                            <projectName>Nazeel-Project</projectName>
                            <inputDirectory>${project.build.directory}/cucumber-reports</inputDirectory>
                            <skipEmptyJSONFiles>true</skipEmptyJSONFiles>
                            <jsonFiles>
                                <param>**/*.json</param>
                            </jsonFiles>
                            <outputDirectory>${project.build.directory}/cucumber-reports</outputDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</profile>
```



Refer to the [Custom Web Element Framework for Nazeel-Project](FrameWork.md) for an overview of the project's Work Frame.