package testautomation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;



public class AddComputerPage {


	@FindBy(id="add")
	WebElement addComputerButton;

	@FindBy(xpath="//a[@href='/']")
	WebElement topBar;

	@FindBy(xpath="//*[@id='main']/h1")
	WebElement header;



	@FindBy(xpath="//*[@id='main']/form/fieldset/div[1]")
	WebElement computerName;

	@FindBy(xpath="//*[@id='main']/form/fieldset/div[2]")
	WebElement introducedDate;

	@FindBy(xpath="//*[@id='main']/form/fieldset/div[3]")
	WebElement discontinuedDate;

	@FindBy(xpath="//*[@id='main']/form/fieldset/div[4]")
	WebElement company;

	@FindBy(xpath="//*[@id='main']/form/div")
	WebElement actions;

	@FindBy(css="input.btn.primary")
	WebElement saveButton;

	@FindBy(xpath="//*[@id='main']/div[1]")
	WebElement successMessage;

	@FindBy(css="input.btn.danger")
	WebElement deleteButton;

	public AddComputerPage(WebDriver driver)
	{     
		PageFactory.initElements(driver, this);
	}


	public void setComputerName(String name)
	{
		computerName.findElement(By.id("name")).sendKeys(Keys.chord(Keys.CONTROL,"a"),name);
	}


	public void setIntroducedDate(String introDate)
	{
		introducedDate.findElement(By.id("introduced")).sendKeys(Keys.chord(Keys.CONTROL,"a"),introDate);
	}

	public void setDiscontinuedDate(String discontDate)
	{
		discontinuedDate.findElement(By.id("discontinued")).sendKeys(Keys.chord(Keys.CONTROL,"a"),discontDate);
	}


	public void setCompanyName(String companyName)
	{
		Select dropDown=new Select(company.findElement(By.id("company")));
		dropDown.selectByVisibleText(companyName.trim());
	}


	public void clickSaveButton()
	{
		saveButton.click();
	}

	public boolean checkFormElements(WebElement checkElement) 
	{

		return ( checkElement.findElement(By.tagName("label")).isDisplayed() &&
				checkElement.findElement(By.tagName("input")).isEnabled() &&
				checkElement.findElement(By.cssSelector("span.help-inline")).isDisplayed());	
	}


	public boolean checkCompanyElements()
	{
		return (company.findElement(By.tagName("label")).isDisplayed() &&
				company.findElement(By.tagName("select")).isEnabled());
	}

	public boolean checkActionElements()
	{
		return (actions.findElement(By.cssSelector("input.btn.primary")).isEnabled() &&
				actions.findElement(By.cssSelector("a.btn")).isEnabled());
	}

	public void clickCreateComputer()
	{
		actions.findElement(By.cssSelector("input.btn.primary")).click();
	}


	public String getSuccessMessage()
	{
		return successMessage.getText();
	}

	public void deleteRecord()
	{
		deleteButton.click();

	}



}
