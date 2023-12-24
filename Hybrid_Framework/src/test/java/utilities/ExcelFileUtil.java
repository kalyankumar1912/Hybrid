package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
	Workbook wb;
	public ExcelFileUtil(String Excelpath)throws Throwable
	{
		FileInputStream fi = new FileInputStream(Excelpath);
		wb = WorkbookFactory.create(fi);
		
	}
	public int rowCount(String Sheetname)
	{
		return wb.getSheet(Sheetname).getLastRowNum();
	}
	public String getCelldata(String Sheetname,int row,int column )
	{
		String data = "";
		if(wb.getSheet(Sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		{
			int celldata = (int)wb.getSheet(Sheetname).getRow(row).getCell(column).getNumericCellValue();
			data = String.valueOf(celldata);
		}
		else
		{
			data = wb.getSheet(Sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	}
	//method for writing cell data 
	public void setCelldata(String Sheetname ,int row,int column,String status,String WriteExcel) throws Throwable
	{
	   Sheet ws = wb.getSheet(Sheetname);
	   Row rowNum = ws.getRow(row);
	   //create cell in a row
	   Cell sell = rowNum.createCell(column);
	   sell.setCellValue("status");
	   if(status.equalsIgnoreCase("pass"))
	   {
		   CellStyle style = wb.createCellStyle();
		   Font font = wb.createFont();
		   font.setColor(IndexedColors.GREEN.getIndex());
		   font.setBold(true);
		   style.setFont(font);
		   rowNum.getCell(column).setCellStyle(style);
		   
	   }
	   else if(status.equalsIgnoreCase("Fail"))
	   {
		   CellStyle style = wb.createCellStyle();
		   Font font = wb.createFont();
		   font.setColor(IndexedColors.RED.getIndex());
		   font.setBold(true);
		   style.setFont(font);
		   rowNum.getCell(column).setCellStyle(style);  
	   }
	   else if(status.equalsIgnoreCase("blocked"))
	   {
		   CellStyle style = wb.createCellStyle();
		   Font font = wb.createFont();
		   font.setColor(IndexedColors.BLUE.getIndex());
		   font.setBold(true);
		   style.setFont(font);
		   rowNum.getCell(column).setCellStyle(style);
	   }
	   FileOutputStream fo = new FileOutputStream(WriteExcel);
	   wb.write(fo);
	}
	public static void main(String[] args) throws Throwable {
		ExcelFileUtil xl = new ExcelFileUtil("D:/Sample_log.xlsx");
		//count rows in a sheet
		int rc = xl.rowCount("Emp");
		System.out.println("no of rows are"+ rc);
		for(int i=1;i<rc;i++)
		{
			String fname = xl.getCelldata("Emp", i, 0);
			String mname = xl.getCelldata("Emp", i, 1);
			String lname = xl.getCelldata("Emp", i, 2);
			String eid = xl.getCelldata("Emp", i, 3);
			System.out.println(fname+"  "+ mname+"   "+lname+"  "+eid);
			xl.setCelldata("Emp", i, 4, "pass", "D:/datadrivenResults.xlsx");
			
		}
		
	}

}
