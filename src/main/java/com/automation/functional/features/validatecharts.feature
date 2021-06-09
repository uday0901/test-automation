Feature: All test cases

@SmokeTest
Scenario Outline: Chart data validations
Given launch browser "<browser>"
When user navigates to the URL "<URL>"
Then validate the chart data

Examples:
|browser|URL|
|chrome|https://spark.adobe.com/express-apps/chart/|