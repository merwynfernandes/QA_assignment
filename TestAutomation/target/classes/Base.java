package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import testautomation.AddComputerPage;
import testautomation.LandingPage;


public class Base {

	public static WebDriver driver;

	public Properties prop;
	public static String computerName;
	public static String introducedDate;
	public static String discontinuedDate;
	public static String companyName;

	public  LandingPage landingPage;
	public  AddComputerPage addComputerPage;

	public WebDriver initializeDriver() throws IOException
	{

		prop= new Properties();
		FileInputStream fis=new FileInputStream("data.properties");

		prop.load(fis);

		driver=new ChromeDriver();
		driver.get(prop.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
		

	}


}
