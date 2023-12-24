package driverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript
{
	public static WebDriver driver;
	String inputpath = "./FileInput/Controller.xlsx";
	String outputpath = "./FileOuput/Hybrid_results.xlsx";
	String TestCases = "MasterTestCases";
	public void startTest() throws Throwable
	{
		String Module_status = "";
		//create object for excelfileutil
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		//iterate all rows in Testcases sheet
		for(int i=1;i<=xl.rowCount(TestCases);i++)
		{
			String Execution_status = xl.getCelldata(TestCases, i, 2);
			if(Execution_status.equalsIgnoreCase("Y"))
			{
				//store all sheets into TCModule
				String TCModule = xl.getCelldata(TestCases, i, 1);
				//iterate all rows in TCModule
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					//read cell from TCModule sheet
					String Description = xl.getCelldata(TCModule, j, 0);
					String Object_Type = xl.getCelldata(TCModule, j, 1);
					String Locator_Type = xl.getCelldata(TCModule, j, 2);
					String Locator_Value = xl.getCelldata(TCModule, j, 3);
					String Test_Data = xl.getCelldata(TCModule, j, 4);
					 try {
						 if(Object_Type.equalsIgnoreCase("startBrowser"))
						 {
							driver = FunctionLibrary.startBrowser();
						 }
						 if(Object_Type.equalsIgnoreCase("Openurl"))
						 {
							  FunctionLibrary.openUrl(driver);
						 }
						 if(Object_Type.equalsIgnoreCase("waitForElement")) 
						 {
							 FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
						 }
							
						 
						 if(Object_Type.equalsIgnoreCase("typeAction"))
						 {
							 FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
						 }
						 if(Object_Type.equalsIgnoreCase("clickAction"))
						 {
							 FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value, Test_Data);
						 }
						 if(Object_Type.equalsIgnoreCase("validateTitle"))
						 {
							 FunctionLibrary.validateTitle(driver, Test_Data); 
						 }
						 if(Object_Type.equalsIgnoreCase("closeBrowser"))
						 {
							 FunctionLibrary.closeBrowser(driver);
						 }
						
						 if(Object_Type.equalsIgnoreCase("moseClick"))
						 {
							 FunctionLibrary.moseClick(driver);
						 }
						 if(Object_Type.equalsIgnoreCase("categoryTable"))
						 {
							 FunctionLibrary.categoryTable(driver, Test_Data);
						 }
						 if(Object_Type.equalsIgnoreCase("dropDownAction"))
							{
								FunctionLibrary.dropDownAction(driver, Locator_Type, Locator_Value, Test_Data);
							}
						 if(Object_Type.equalsIgnoreCase("captureStock"))
							{
								FunctionLibrary.captureStock(driver, Locator_Type, Locator_Value);
							}
							if(Object_Type.equalsIgnoreCase("stockTable"))
							{
								FunctionLibrary.stockTable(driver);
							}
							if(Object_Type.equalsIgnoreCase("captureSupplier"))
							{
								FunctionLibrary.captureSupplier(driver, Locator_Value, Locator_Value);
							}
							if(Object_Type.equalsIgnoreCase("suppliertale"))
							{
								FunctionLibrary.suppliertale(driver);
							}
							
						
						 
						 //write as pass into status cell
						 xl.setCelldata(TCModule, j, 5, "pass", outputpath);
						 Module_status="True";
						 
						 
					 }
					 catch(Exception e)
					 {
						 System.out.println(e.getMessage());
						 xl.setCelldata(TCModule, j, 5, "fail", outputpath);
						 Module_status="false";
					 }
					 if(Module_status.equalsIgnoreCase("true"))
					 {
						 xl.setCelldata(TestCases, i, 3, "pass", outputpath);
					 }
					 if(Module_status.equalsIgnoreCase("false"))
					 {
						 xl.setCelldata(TestCases, i, 3, "fail", outputpath);
					 }
				}
				
			}
			else
			{
				xl.setCelldata(TestCases, i, 3, "Blocked", outputpath);
			}
		}
	}
	

}
