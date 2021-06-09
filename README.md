# test-automation
Automation Framework

Framework Structure:

src/main/java/com/automation/functional has pom classes, test classes and feature files
src/main/java/com/automation/utilities has all helper classes
src/main/java/com/automation/testdata has all test data files required for autoamtion scripts


src/main/resources has property files, grid config files, driver executables etc.


Execution Steps:

1. Download project code from https://github.com/uday0901/test-automation repo
2. Once project is configured in intellij/eclipse run below command fron terminal from project root directory
$mvn clean install

or 
$mvn install
$mvn test
