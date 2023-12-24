package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary
{
	public static WebDriver driver;
	public static Properties conpro;
	//method for launching browser
	public static WebDriver startBrowser () throws Throwable 
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver =  new ChromeDriver();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("Firefox"))
		{
			driver =  new FirefoxDriver();
		}
		else
		{
			Reporter.log("Browser not matching",true);
		}
		return driver;
	}
	public static void openUrl(WebDriver driver)
	{
		driver.get(conpro.getProperty("Url"));
	}
	
	
	
	//method for waitforelement
	public static void waitForElement(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Test_Data)));
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
		}
		if(Locator_Type.equalsIgnoreCase("name")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));
		}
	}
	//mehod for textboxes
	public static void typeAction(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).clear();
			driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).clear();
			driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).click();
			
		}
	}
	public static void clickAction(WebDriver driver, String Locator_Type,String Locator_Value,String Test_Data) 
	{
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).click();
		}
		if(Locator_Type.equalsIgnoreCase("xpath")) 
		{
			driver.findElement(By.name(Locator_Value)).click();
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).sendKeys(Keys.ENTER);
		}
		
		
	}
	public static void validateTitle(WebDriver driver,String Expected_Title)
	{
		String Actual_Title = driver.getTitle();
		try 
		{
		Assert.assertEquals(Expected_Title, Actual_Title,"title is not matching");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}
	public static void closeBrowser(WebDriver driver)
	{
		driver.quit();
	}
	public static void moseClick(WebDriver driver) throws Throwable
	{
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//a[starts-with(text(),'Stock Items ')]"))).perform();
		Thread.sleep(1000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[starts-with(text(),'Stock Categories')])[2]")));
		ac.pause(3000).click().perform();
	}
	//method for category table
	public static void categoryTable(WebDriver driver,String Exp_Data) throws Throwable
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//search textbox not displayed click search panel
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
	    String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
	    Reporter.log(Exp_Data+"   "+ Act_Data,true);
	    try {
	    Assert.assertEquals(Exp_Data, Act_Data,"Category name is not found in Table");
	       }
	    catch(Throwable t)
	    {
	    	System.out.println(t.getMessage());
	    }
	    }
	public static void dropDownAction(WebDriver driver, String Locator_Type, String Locator_Value , String Test_Data )
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
		int value = Integer.parseInt(Test_Data);
		WebElement element =  driver.findElement(By.xpath(Locator_Value));
		Select select  = new Select(element);
		select.selectByIndex(value);
		
	}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element =  driver.findElement(By.id(Locator_Value));
			Select select  = new Select(element);
			select.selectByIndex(value);
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element =  driver.findElement(By.name(Locator_Value));
			Select select  = new Select(element);
			select.selectByIndex(value);
		}
		
	}
	//method for capturing stock number into note pad
	public static void captureStock(WebDriver driver,String Locator_Type,String Locator_Value)throws Throwable

	{
		String stockNumber ="";
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			stockNumber = driver.findElement(By.id(Locator_Value)).getAttribute("value");
			
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			stockNumber = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			stockNumber = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		//create notepad and write stocknumner into  it
		FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stockNumber);
		bw.flush();
		bw.close();
	}
	//method for stock table
	public static void stockTable(WebDriver driver)throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//if search textbox not displayed clcick search panel button
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
			String Act_data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
			Reporter.log(Exp_Data+"    "+Act_data,true);
			Assert.assertEquals(Exp_Data, Act_data, "Stock Number is Not Found in Table");
			
			
			
		
		
	}
	//method for capture suppplier name into notepad
	public static void captureSupplier(WebDriver driver,String Locator_Type,String Locator_Value) throws Throwable
	{
		String supplierNum  = "";
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			supplierNum = driver.findElement(By.id(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			supplierNum = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			supplierNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/suppliernum.txt");
		BufferedWriter bw =  new BufferedWriter(fw);
		bw.write(supplierNum);
		bw.flush();
		bw.close();
	}
	public static void suppliertale(WebDriver driver) throws Throwable
	{
		FileReader fr = new FileReader("/CaptureData/suppliernum.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_data = br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//if search textbox not displayed clcick search panel button
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Reporter.log(Exp_data+"   "+Act_data,true);
		try
		{
			Assert.assertEquals(Exp_data, Act_data,"supplier number not matching");
			
		}
		catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
		
		
	}
			
		
			
			
		
		
	
	
	    


}
