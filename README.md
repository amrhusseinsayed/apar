# jawwy

jawwy is a Java testing framework built with BDD concept using cucumber.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for testing purposes.

## Concepts Included

* Testing Framework design
* Page Object pattern
* Page
* BDD
* Cucumber
* JUnit
* Gherkin Language
* Common web page interaction methods
* Extracting test data from json file
* Html reports for testing outputs
* Screenshots for the testing outputs

## Tools

* Maven
* Selenium
* Cucumber
* JUnit

## Requirements

In order to utilize this project, you need to have the following installed locally:

* Maven
* Chrome
* Java Compiler 11
* Cucumber

## Installing

* Install Java
* Set the System Environment variables with JDK and JRE paths
* Install Eclipse or IntelliJ
* Install Cucumber inside the IDE if not configured by default
* Install Maven inside the IDE if not configured by default
* Install Git
* Clone the project from `https://github.com/amrhusseinsayed/apar.git`

## Framework structure

* `flows.feature` in the directory `src/test/resources/features` holds the steps of the scenarios related Validating the
  subscriptions plans in two different countries
* `TestRunner.java` in the directory `src/test/java/runner` holds the one method to run before all the scenarios and
  another method to run after all the scenarios
* `Hooks.java` in the directory `src/test/java/stepDefinition/core` holds the hooks that will be executed either after
  or before each scenario
* `HomepageDef.java` in the directory `src/test/java/stepDef/ui` holds the methods of the steps related to the Homepage
  scenario
* `HomepageImpl.java` in the directory `src/test/java/stepDefImpl/ui` holds the implementation of the methods linked
  with the step related to the Homepage
* `ActionsUtil.java` in the directory `src/test/java/utils` holds the helper methods that can be used during the
  execution
* `ExtentReportUtil.java` in the directory `src/test/java/utils` holds the methods used to initialize the Html report
  that will be used to collect the executions results
* `JsonFileUtil.java` in the directory `src/test/java/utils` holds the methods of handling the json files
* `PropertiesFileUtil.java` in the directory `src/test/java/utils` holds a method to read any properties file
* `ScreenshotUtil.java` in the directory `src/test/java/utils` holds a method to take a screenshot from the current view
* `DriverUtil.java` in the directory `src/test/java/utils` holds the methods of initializing the web driver and its wait
  also a method to close the driver after execution
* `subscriptions.json` in the directory `src/test/resources/test-data` is the test data used during the execution
* `config.properties` in the directory `src/test/resources` holds the properties that will be used during the execution
* `log4j2.xml` in the directory `src/test/resources` holds the configurations of the logger that will be used during the
  execution
* `extent-config.xml` in the directory `src/test/resources` holds the configurations of the html report that will hold
  all the results of the executed scenarios
* `src/test/java/testOutputs` will hold the html report with the status of the executed scenarios also a folder for the
  screenshots taken during the execution

## Running the tests

* You can run all the scenarios either by building the project as Maven project and the scenario will be automatically
  executed after the compilation process, or you can run the `TestRunner.java` as a JUnit class

## Running workflow description

* The execution begins from the method under the annotation `@BeforeClass` inside `TestRunner.java` and it does the
  following
    * Extract the properties from the `config.properties` that will be used during the execution
    * Initialize the HTML Extent Report using its `extent-config.xml` file
* The method under the annotation `@Before(order = 0)` inside `Hooks.java` is called to add an entry for the scenario to
  be executed inside the html report
* The method under the annotation `@Before(order = 1)` inside `Hooks.java` is called to initialize a browser instance
  based on the extracted properties
* Then the first scenario is executed and each executed step is logged inside both the html report and the log file
* After executing all the scenario steps, the method under the annotation `@After(order = 1)` inside `Hooks.java` is
  called to log the final results of the scenario inside both the log file and the html report also to take screenshot
  if the scenario fails
* The method under the annotation `@After(order = 0)` inside `Hooks.java` is called to close the running browser
  instance
* Finally, the method under the annotation `@AfterClass` inside `TestRunner.java` is called to flush the data of the
  executed scenario(s) inside the html report

## Reporting

You can see a sample of the testing report, logs files and screenshots after the execution in the
directory `target/cucumber-reports`which contains

* `screenshots folder which contains a screenshot for a failed scenario that has a name matches the failed scenario name
* `CucumberTestReport.json` a JSON report for the execution details
* `test.log` a logs file for the execution details
* `test-report.html` a html file for the execution details
