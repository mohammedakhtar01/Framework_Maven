package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelRead {
	String strExcelPath;
	String strSheetName;
	ArrayList<ArrayList<String>> Data=new ArrayList<ArrayList<String>>();
	
	public ExcelRead(String FileName,String SheetName){
		
		this.strExcelPath=FileName;
		this.strSheetName=SheetName;
		Data=loadTestData();
	}
	
	
	@SuppressWarnings({ "finally", "deprecation" })
	public ArrayList<ArrayList<String>> loadTestData(){
		try {
			FileInputStream inputStream =new FileInputStream(new File(strExcelPath));
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheet(strSheetName);
			Row header=sheet.getRow(0);
			ArrayList<String> strheader;
			for(Cell c:header){	
				strheader=new ArrayList<String>();
				strheader.add(c.getStringCellValue());
				Data.add(strheader);
			}
					
	//		System.out.println(header.getLastCellNum());
	//		System.out.println(sheet.getLastRowNum());
			
			int lastColHeader=header.getLastCellNum();
			int lastRow=sheet.getLastRowNum();
	
			Row r;
			String temp;
			for(int i=1;i<=lastRow;i++){
				r=sheet.getRow(i);
				for(int j=0;j<lastColHeader;j++){
					try{
						r.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
						 
						switch (r.getCell(j).getCellType()) {
		                    case Cell.CELL_TYPE_STRING:
		                   //    System.out.println("S="+r.getCell(j).getStringCellValue());
		                        temp=r.getCell(j).getStringCellValue();
		                        Data.get(j).add(temp);
		                        break;
		                    case Cell.CELL_TYPE_NUMERIC:
		                    //    System.out.println("N="+r.getCell(j).getNumericCellValue());
		                        temp=r.getCell(j).getNumericCellValue()+"";
		                        Data.get(j).add(temp);
		                        break;
		                }
					}catch(NullPointerException n){
					//	System.out.println("Cell is blank-1");
						 Data.get(j).add("");
						continue;
					}
				}
			}
			
			workbook.close();
	        inputStream.close();
//			for(ArrayList<String> k:Data){
//				System.out.println("Data="+k);
//			}
			
//			System.out.println("TestData="+Data);
	        
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION-EXCEL READ-FILE NOT FOUND");
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION-EXCEL READ-IO EXCEPTION");
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION-EXCEL READ-NULL POINTER EXCEPTION-CELL IS BLANK");
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("EXCEPTION-EXCEL READ");
			e.printStackTrace();
		}	
		finally{
			return Data;
		}
	}

	public String getCellValue(String colName,int row){	
		try{
			
			ArrayList<String> arrCol=getColumn(colName);
			
			if(arrCol.size()!=0){
				return arrCol.get(row);
			}
			return null;
			
		}catch(Exception e){
			return null;
		}
		//System.out.println("EMPTY ARRAY");
		
	}
	
	public ArrayList<String> getColumn(String columnName){
				
		for(ArrayList<String> arr:Data){
			if(arr.get(0).equalsIgnoreCase(columnName)){
				return arr;
			}
		}
		return new ArrayList<String>();
	}
	
	public ArrayList<String> removeDuplicates(ArrayList<String> arr){
		
		for(int i=0;i<arr.size();i++){
	        for(int j=i+1;j<arr.size();j++){
	            if(arr.get(i).equals(arr.get(j))){
	                arr.remove(j);
	                j--;
	            }
	        }
	    }
		
		return arr;
	}
	
	public ArrayList<String> removeEmptyStrings(ArrayList<String> arr){
		
		Iterator<String> itr = arr.iterator();
		while (itr.hasNext())
		{
		    String s = itr.next();
		    if (s == null || s.isEmpty())
		    {
		        itr.remove();
		    }
		}
		
		return arr;
	}
	
}
