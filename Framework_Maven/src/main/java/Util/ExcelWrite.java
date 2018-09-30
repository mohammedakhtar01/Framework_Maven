package Util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWrite {
	
	private String filePath;
	private String sheetName;
	private FileOutputStream outputStream;
	private FileInputStream inputStream;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	

	public ExcelWrite(String strFilePath,String strSheetName){
		
		filePath=strFilePath;
		sheetName=strSheetName;

	}
	
	public  void writeToExistingFile(String colName,int row,String text){
		
		try {
			
			inputStream=new FileInputStream(filePath);	
			workbook = new XSSFWorkbook(inputStream);
			sheet = workbook.getSheet(sheetName);
			
			Row header=sheet.getRow(0);
			int col=0;
			for(Cell c:header){
				if(c.getStringCellValue().equals(colName)){
					col=c.getColumnIndex();
				//	System.out.println("ColIndex="+col);
					break;
				}
			}
			
			Row dataRow=sheet.getRow(row);
			if(col!=0){
//				System.out.println("true");
				dataRow.createCell(col).setCellValue(text);

			}else{
				System.out.println("EXCEPTION-COLUMN NOT FOUND");
				throw new Exception();
			}
			
			outputStream = new FileOutputStream(filePath);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
			
//			System.out.println("Success");

		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION-EXCEL WRITE-FILE NOT FOUND");
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION-EXCEL WRITE-IO EXCEPTION");
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION-EXCEL WRITE-NULL POINTER EXCEPTION-CELL IS BLANK");
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("EXCEPTION-EXCEL WRITE");
			e.printStackTrace();
		}
	}
	
	public  void writeToNewFile(ArrayList<String[]> data){
		
		try {
	
			workbook=new XSSFWorkbook();
			sheet=workbook.createSheet(sheetName);
	
			
			int rowCount=0;
			for(String[] arr:data){
				
				Row row=sheet.createRow(rowCount);
				
				for(int i=0;i<arr.length;i++){
					row.createCell(i).setCellValue(arr[i]);
			//		System.out.println("data="+arr[i]);
				}
				rowCount++;
			}
			
			outputStream=new FileOutputStream(filePath);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();

		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION-FILE NOT FOUND");
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION-IO EXCEPTION");
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION-NULL POINTER EXCEPTION-CELL IS BLANK");
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("EXCEPTION");
			e.printStackTrace();
		}
	}
	
}
