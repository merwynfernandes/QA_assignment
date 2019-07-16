package testautomation;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import resources.Base;

public class RegressionTests extends Base{

	public static Logger log=LogManager.getLogger(RegressionTests.class.getName());
	SoftAssert softAssert;

	
	@BeforeSuite(alwaysRun = true)
	public void initialize() throws IOException
	{
		log.info("=====STARTING TEST EXECUTION======");
		driver=initializeDriver();
		addComputerPage= new AddComputerPage(driver);
		landingPage= new LandingPage(driver);
		softAssert=new SoftAssert();
		

	}


	@BeforeTest(description="Verifies build/application stability before triggering regression",groups = { "smoke", "regression" })
	public void checkLandingPageElements()
	{ 
		log.info("Verifying page elements on Landing page");
		softAssert.assertEquals(landingPage.topBar.getText(),"Play sample application — Computer database");
		softAssert.assertTrue(landingPage.numberOfComputersFound.isDisplayed());
		softAssert.assertTrue(landingPage.filterTextBox.isDisplayed());
		softAssert.assertTrue(landingPage.filterButton.isEnabled());
		softAssert.assertTrue(landingPage.mainTable.isDisplayed());
		softAssert.assertTrue(landingPage.filterButton.isEnabled());
		softAssert.assertTrue(landingPage.checkRowCount());
		softAssert.assertTrue(landingPage.tableNavigation.isDisplayed());
		softAssert.assertAll();

	}


	@Test (description="Verifies the Add Computer Page availability",groups = { "smoke", "regression" },priority=1)
	public void checkAddComputerPageLoaded()
	{
		log.info("Verifying Add Computer Page accessibility");
		addComputerPage.addComputerButton.click();
		Assert.assertEquals(driver.getCurrentUrl(),"http://computer-database.herokuapp.com/computers/new");

	}

	@Test (description="Verifies WebElements of Add Computer page",groups = { "smoke", "regression" },priority=2)
	public void checkAddComputerPageElements()
	{
		log.info("Verifying Web Elements on Add Computer Page ");
		softAssert.assertEquals(addComputerPage.topBar.getText(),"Play sample application — Computer database");
		softAssert.assertTrue(addComputerPage.checkFormElements(addComputerPage.computerName));
		softAssert.assertTrue(addComputerPage.checkFormElements(addComputerPage.introducedDate));
		softAssert.assertTrue(addComputerPage.checkFormElements(addComputerPage.discontinuedDate));
		softAssert.assertTrue(addComputerPage.checkCompanyElements());
		softAssert.assertTrue(addComputerPage.checkActionElements());
		softAssert.assertAll();
	}


	@Test(description="Verifies the addition of new computer",groups = {"regression"},priority=3)
	public void verifyAddComputerDetails()
	{
		log.info("Verifying the addition of new computer");
		driver.get(prop.getProperty("url"));
		addComputerPage.addComputerButton.click();
		computerName= prop.getProperty("addComputer");
		introducedDate=prop.getProperty("introducedDate");
		discontinuedDate=prop.getProperty("discontinuedDate");
		companyName=prop.getProperty("companyName");
		log.info("New computer details fetched from properties file successfully");
		
		addComputerPage.setComputerName(computerName);
		addComputerPage.setIntroducedDate(introducedDate);
		addComputerPage.setDiscontinuedDate(discontinuedDate);
		addComputerPage.setCompanyName(companyName);

		addComputerPage.clickCreateComputer();

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		Assert.assertEquals(addComputerPage.getSuccessMessage(),"Done! Computer "+computerName+" has been created");
		
		log.info("Computer details for "+computerName+" added to the database successfully");
	}


	@Test (description="Verifies the search/filter functionality",groups = {"regression"},priority=4)
	public void filterComputerName()
	{
		log.info("Verifying the search/filter functionality");
		//Assign appropriate values from properties file
		computerName= prop.getProperty("readComputerName").isEmpty()==true ? prop.getProperty("addComputer"): prop.getProperty("readComputerName");
		introducedDate= prop.getProperty("expectedIntroducedDate").isEmpty()==true ? prop.getProperty("introducedDate"): prop.getProperty("expectedIntroducedDate");
		discontinuedDate= prop.getProperty("expectedDiscontinuedDate").isEmpty()==true ? prop.getProperty("discontinuedDate"): prop.getProperty("expectedDiscontinuedDate");
		companyName= prop.getProperty("expectedCompanyName").isEmpty()==true ? prop.getProperty("companyName"): prop.getProperty("expectedCompanyName");
			
		log.info("Reading details of "+computerName);
		landingPage.searchComputer(computerName); 

		//Generating expected output after Date format transformation
		String tempexpectedResult=computerName+","+introducedDate+","+discontinuedDate+","+companyName;
		String expectedResult=landingPage.transform(tempexpectedResult);

		Assert.assertTrue(landingPage.checkResultTable(expectedResult));
		log.info("Details of "+computerName+ " have been read successfully");
	}




	@Test(description="Verifies the update functionality",groups = {"regression"},priority=5)
	public void updateRecord()
	{   
		log.info("Verifying the update functionality");
		
		driver.get(prop.getProperty("url"));
		String fromComputerName=prop.getProperty("computerNametoUpdate");
		String toComputerName=prop.getProperty("newComputerName");
		String newIntroducedDate=prop.getProperty("newIntroducedDate");
		String newDiscontinuedDate=prop.getProperty("newDiscontinuedDate");
		String newCompanyName=prop.getProperty("newCompanyName");
		log.info("New Values of "+fromComputerName+ " for updation are successfully fetched from the properties file");

		landingPage.searchComputer(fromComputerName);

		if(landingPage.clickComputerName(fromComputerName))
		{
			addComputerPage.setComputerName(toComputerName);
			addComputerPage.setIntroducedDate(newIntroducedDate);
			addComputerPage.setDiscontinuedDate(newDiscontinuedDate);
			addComputerPage.setCompanyName(newCompanyName);
			addComputerPage.clickSaveButton();
		}

		Assert.assertEquals(addComputerPage.getSuccessMessage(),"Done! Computer "+toComputerName+" has been updated");
		log.info("Update verified successfully");
	}



	@Test(description="Verifies the delete functionality",groups = {"regression"},priority=6)
	public void deleteComputer()
	{
		log.info("Verifying delete functionality");
		landingPage.searchComputer(prop.getProperty("deleteComputer"));
		landingPage.clickComputerName(prop.getProperty("deleteComputer"));
		addComputerPage.deleteRecord();
		Assert.assertEquals(addComputerPage.getSuccessMessage(),"Done! Computer has been deleted");
		
		log.info(prop.getProperty("deleteComputer")+" has been deleted successfully");
	}



	@AfterSuite(alwaysRun = true)
	public void tearDown()
	{ 
		log.info("=====TEST EXECUTION COMPLETED======");
		driver.quit();
	}

}
