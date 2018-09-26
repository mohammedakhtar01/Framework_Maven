package Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateObjectPages {
	
	private ArrayList<String> uniquePageNames;
	public static ArrayList<ArrayList<String[]>> pages;
		
	public CreateObjectPages(String ObjRepPath){
		readORExcel(ObjRepPath);
		createORPages();
	}

	public  void readORExcel(String objRepPath){
		try {
			FileInputStream inputStream =new FileInputStream(new File(objRepPath));
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			//System.out.println("Last row="+sheet.getLastRowNum());
			
			uniquePageNames=new ArrayList<String>();
		
			Row row= sheet.getRow(1);
			//System.out.println("Cell="+sheet.getRow(21).getCell(0));

			String curCell,prevCell;
			prevCell=sheet.getRow(0).getCell(0).getStringCellValue();
			
			for(int i=1;i<=sheet.getLastRowNum();i++){		
				
				//System.out.println(i);
				row= sheet.getRow(i);
				
				if(row!=null){
					
					curCell=sheet.getRow(i).getCell(0).getStringCellValue();
					
					if(!curCell.equals("")){
						if(!curCell.equals(prevCell)){
							uniquePageNames.add(curCell);
							//System.out.println("add");
						}
					}
					prevCell=curCell;
					
				}			
			}

			//System.out.println(uniquePageNames);
		
			int j=0;
			pages=new ArrayList<ArrayList<String[]>>();
			ArrayList<String []> elemList=new ArrayList<String[]>();
			for(int i=1;i<=sheet.getLastRowNum();i++){
				row= sheet.getRow(i);

				if(row!=null){
					
					String p1 = row.getCell(0).getStringCellValue();
					String p2 = row.getCell(1).getStringCellValue();
					String p3 = row.getCell(2).getStringCellValue().replaceAll("[^\\dA-Za-z ]", "_");
					String p4 = row.getCell(3).getStringCellValue();
					String p5 = row.getCell(4).getStringCellValue();
					
					curCell=p1;
					//replaceAll("[^\\dA-Za-z ]", "");

					if(!p1.equals("")){
						if(curCell.equals(uniquePageNames.get(j))){
							//System.out.print(uniquePageNames.get(j));
							String[] prop=new String[]{p1,p2,p3,p4,p5};
							elemList.add(prop);

						}else{
							//
							j=j+1;// increment uniquePageNames
							pages.add(elemList);
							elemList=new ArrayList<String[]>();
							String[] prop=new String[]{p1,p2,p3,p4,p5};
							elemList.add(prop);
						}
					}

				}

			}
			
			//Adding for Last Page
			pages.add(elemList);
			
			
			//Display Elements
			//System.out.println("No of pages="+pages.size());
/*
			for (ArrayList<String[]> page: pages ){
				System.out.println("Elements in this page="+page.size());
				for (String[] elem : page) {
					for(int i=0;i<elem.length;i++){
						System.out.print("-"+elem[i]);
					}
					System.out.println();
				}
			}
*/
			
			workbook.close();
	        inputStream.close();
	        
		} 
		catch (FileNotFoundException e) {
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
	
	public void createORPages() {
        try {
        	
            System.out.println("Total Pages="+pages.size());
            String pageName;
            int flag=0;
            String strFilePath;
            ArrayList<String[]> elemList;
            String pagesPath=System.getProperty("user.dir");
            pagesPath=pagesPath.concat("\\src\\test\\java\\Pages");
            File filePath=new File(pagesPath);
            
            if(!filePath.exists()){
            	filePath.mkdir();
            	System.out.println("CREATED Package- Pages");
            }
            
            for(int i=0;i<pages.size();i++){
        		//System.out.println("PageName="+uniquePageNames.get(i));
        		pageName=uniquePageNames.get(i);
        		elemList=pages.get(i);
        		
        		
        		strFilePath=pagesPath.concat("\\"+pageName+".java");
        		File objfile = new File(strFilePath);
	            
        		FileOutputStream is = new FileOutputStream(objfile);
	            OutputStreamWriter osw = new OutputStreamWriter(is);    
	            Writer w = new BufferedWriter(osw);
	            
	            writeHeader(w);
	            writeContent(w,pageName,elemList);
	            
	            w.close();
	            System.out.println("CREATED PAGE-"+pageName);

        	}	         
            
        	if(flag==0){
        		System.out.println("DONE- All Pages Created Successfully.");
        	}else{
        		System.out.println("FAIL- All Pages NOT  Created Successfully.");
        		throw  new IOException();
        	}
            
            
        } catch (IOException e) {
        	e.printStackTrace();
            System.err.println("EXCEPTION- CREATING OBJECT PAGES");
        }
	}
	
	public void writeHeader(Writer w){
		try {
			w.write("package Pages;");
			w.write("\n \n");

			w.write("import org.openqa.selenium.WebDriver;");
			w.write("\n");
			w.write("import org.openqa.selenium.WebElement;");
			w.write("\n");
			w.write("import org.openqa.selenium.support.FindBy;");
			w.write("\n");
			w.write("import org.openqa.selenium.support.How;");
			w.write("\n");
			w.write("import org.openqa.selenium.support.PageFactory;");
			w.write("\n");			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block\
			System.out.println("EXCEPTION- CREATING HEADER");
			e.printStackTrace();
		}
		
	}
    
	public void writeContent(Writer w,String pageName,ArrayList<String[]> elemList){
    	
    	try{
    		w.write("\n");
    		w.write("public class "+pageName+"{");
    		w.write("\n \n");
    		w.write("\t public "+pageName+"(WebDriver driver) {");
    		w.write("\n");
    		w.write("\t \t PageFactory.initElements(driver, this);");
    		w.write("\n \t }");
    		w.write("\n \n");
    		
    		for (String[] elem : elemList) {
    			
    			if(elem[3].equalsIgnoreCase("linktext")){
    				elem[3]="LINK_TEXT";
    			}
    			else if (elem[3].equalsIgnoreCase("partiallinktext")){
    				elem[3]="PARTIAL_LINK_TEXT";
    			}
    			else if (elem[3].equalsIgnoreCase("className")){
    				elem[3]="CLASS_NAME";
    			}
    			else if (elem[3].equalsIgnoreCase("tagname")){
    				elem[3]="TAG_NAME";
    			}
    			
    			w.write("\t @FindBy(how = How."+elem[3].toUpperCase()+", using = \""+elem[4]+"\")");
        		w.write("\n");
        		
        		if(elem[2].equalsIgnoreCase("Y")){
        			
        			w.write("\t public java.util.List<WebElement> "+elem[1]+";");
        			w.write("\n \n");
        			
        		}else if (elem[2].equalsIgnoreCase("N")) {
        			
        			w.write("\t public WebElement "+elem[1]+";");
        			w.write("\n \n");
        			
				}
			}  		 		
    		
    		w.write("\n }");
    		
    	}
    	catch (IOException e) {
			// TODO Auto-generated catch block\
			System.out.println("EXCEPTION- CREATING CONTENT");
			e.printStackTrace();
		}
    }
	
}
