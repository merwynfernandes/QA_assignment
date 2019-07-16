package testautomation;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;



public class LandingPage {

	WebDriverWait wait;

	WebDriver driver;
	@FindBy(xpath="//a[@href='/']")
	WebElement topBar;

	@FindBy(xpath="//*[@id='main']/h1")
	WebElement numberOfComputersFound;

	@FindBy(id="searchbox")
	WebElement filterTextBox;

	@FindBy(id="searchsubmit")
	WebElement filterButton;

	@FindBy(id="add")
	WebElement addComputerButton;


	@FindBy(xpath="//*[@id='main']/table")
	WebElement mainTable;

	@FindBy(xpath="//*[@id='pagination']/ul")
	WebElement tableNavigation;



	public LandingPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}



	public void clickAddComputer()
	{
		addComputerButton.click();
	}

	
	
	public void searchComputer(String computerName)
	{
		filterTextBox.sendKeys(computerName);
		filterButton.click();
		wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.urlContains(computerName.split(" ")[0]));


	}

	public boolean checkRowCount()
	{
		//To match the number of computers found and the total rows displayed at the bottom of the table 

		return(Integer.parseInt(numberOfComputersFound.getText().split(" ")[0])==Integer.parseInt(tableNavigation.findElement(By.xpath("li[2]")).getText().split(" ")[5]));
	}
	
	public String transform(String tempexpectedResult)
	{
		//Function to convert YYYY-MM-DD format to Date-Mon-Year as displayed in table
		
		String expectedResult="";
		String[] temp=tempexpectedResult.split(",");

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date introDate = sdf.parse(temp[1]);
			Date discontinuedDate=sdf.parse(temp[2]);
			DateFormat out = new SimpleDateFormat("dd MMM yyyy");
			temp[1]=out.format(introDate);
			temp[2]=out.format(discontinuedDate);


		} catch (Exception e) {
			
			System.out.println(e.toString());
		}

		for(int i=0;i<temp.length;i++)
			expectedResult=expectedResult+temp[i]+"," ;

		return expectedResult;

	}

	public boolean checkResultTable(String expectedResult)
	{ 
		//To validate all parameters of the searched computer are displayed correctly in the table
		boolean foundMatch=false;
		String buildRowValues="";
		List <WebElement> rows= mainTable.findElements(By.tagName("tr"));


		for(int i=0;i<rows.size();i++)
		{   WebElement row=rows.get(i);
		List<WebElement> cols = row.findElements(By.tagName("td"));


		for(int j=0;j<cols.size();j++)
		{
			buildRowValues=buildRowValues+cols.get(j).getText()+",";
		}

		if(buildRowValues.length()>0 && buildRowValues.equals(expectedResult))
		{
			foundMatch=true;

			break;

		}
		buildRowValues="";
		}

		return foundMatch;
	}


	public boolean clickComputerName(String computerName)
	{
		boolean clickedComputer=false;

		List <WebElement> rows= mainTable.findElements(By.tagName("tr"));


		for(int i=1;i<rows.size();i++)
		{   WebElement row=rows.get(i);
		List<WebElement> cols = row.findElements(By.tagName("td"));
		if(cols.get(0).getText().equals(computerName))
		{
			cols.get(0).findElement(By.tagName("a")).click();

			clickedComputer=true;
			break;
		}

		}

		return clickedComputer;

	}



}

