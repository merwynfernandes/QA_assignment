## Assignment

This contains Manual Test cases in the "Manual_Test_Cases.xlsx" spreadsheet and the TestAutomation repo which contains the automated regression suite to cover the CRUD operations.

### Prerequisites
The view the spreadsheet Microsoft Excel needs to installed. 

To execute the automated suite, latest stable build of chrome browser(Currently v75) and maven should be installed in the system.

## Project overview

testng.xml and data.properties under TestAutomation are used to drive the automation framework.
Each of the individual CRUD operations can either be executed as a whole regression suite or can be run individually. Individual runs are useful to quickly test code changes.
Additionally, smoke tests can be run separately to test application stability before regression.

The default configuration  would run the complete suite together.
The paramters "addComputer",introducedDate","discontinuedDate" and "companyName" from data.properties are used to Add details of a new computer. The same details are used for performing filtering, updation and delete.

Paramteres "newComputerName","newIntroducedDate","newDiscontinuedDate","newCompanyName" would be used to replace the existing values of a computer (computerNametoUpdate)
<br />**Note:** The computer record whose name is assigned to "computerNametoUpdate" should already exist in the database before updation


"deleteComputer" Parameter is used to assign the name of the computer whose record needs to be deleted.<br />**Note:** The computer name assigned to "deleteComputer" should have an existing record before execution of this test case

On default regression, the values used to create the computer record would be used to validate the read operation. 
In case the user wants to verify all values for a specific computer, the "readComputerName" can be used. 
<br />**Note:** 
<br />1) readComputerName should have a computer Name who also has associated Introduced date, Discontinued Date and Company name to ensure complete record can be verified
<br />2)expectedIntroducedDate , expectedDiscontinuedDate and expectedCompanyName are mandatory for test execution and  should be provided to validate the data displayed in the table is indeed correct.


## Execution

### To run the framework perform the below steps:

Note: Regression can be directly run by using the below steps and default configuration

Change the current working directory to the cloned repo and run the below four commands
* cd TestAutomation

* mvn clean

* mvn compile

* mvn test


### To run tests for individual CRUD operation:

Note the below functions and the operations they test

| Function Name  | Operation |
| ------------- | ------------- |
| verifyAddComputerDetails| Create |
| filterComputerName| Read  |
| updateRecord  | Update |
| deleteComputer | Delete  |


Modify the testng.xml as below by uncommenting the "methods" block and add the desired function. For eg, to execute tests for read , modify the testng.xml  as below:

<test name=" CRUD operations Regression Tests ">
	
		<groups>
			<run>
				<include name="regression" />
			</run>
		</groups>
		<classes>
			<class name="testautomation.RegressionTests" >

			<methods>
					<include name= "filterComputerName"/>
					
			</methods>		
			
			</class>

		</classes>
	




<br />**Note:** Please ensure the data.properties has correct values to support the test execution. 
<br />**Note:** Changes need to be reverted to default to run the complete regression suite

### To test build stability modify the below tags in testng.xml and run the tests

	<groups>
			<run>
				<include name="smoke" />
			</run>
		</groups>



## Execution Status:
In addition to the console output refer the below points to view the execution status :

<br /> *Under TestAutomation\logs directory , prints.logs will give you the status of execution 

<br /> *Under TestAutomation\target\surefire-reports, ExtentReportsTestNG.html will give a graphical report of the test execution status 






