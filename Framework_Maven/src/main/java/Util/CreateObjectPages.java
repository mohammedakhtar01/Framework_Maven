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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateObjectPages {


	LinkedHashMap<String,ArrayList<String[]>> perPageElements=new LinkedHashMap<String,ArrayList<String[]>>();

	public CreateObjectPages(String ObjRepPath){
		readORExcel(ObjRepPath);
		createORPages();
	}

	public  void readORExcel(String objRepPath){
		try {
			FileInputStream inputStream =new FileInputStream(new File(objRepPath));
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);

			int numOfRows= sheet.getPhysicalNumberOfRows();
			//	System.out.println("numOfRows="+numOfRows);

			String pageCell;

			String pageName,objectName,isMultiple,how,using; 
			String[] elements;
			ArrayList<String[]> elementsList;

			for(int i=1;i<numOfRows;i++) {
				pageCell=sheet.getRow(i).getCell(0).toString();
				//System.out.println(pageCell);
				if(pageCell!="") {

					pageName=sheet.getRow(i).getCell(0).toString();
					objectName=sheet.getRow(i).getCell(1).toString();
					isMultiple=sheet.getRow(i).getCell(2).toString();
					how=sheet.getRow(i).getCell(3).toString();
					using=sheet.getRow(i).getCell(4).toString();


					if(perPageElements.containsKey(pageName)) {
						elements=new String[] {pageName,objectName,isMultiple,how,using};//Add all properties to String[]
						elementsList=perPageElements.get(pageName);// Get Old Arraylist of elements for that page
						elementsList.add(elements);//Add All propertiesList to ElementList
						perPageElements.put(pageName, elementsList);

					}else {

						elements=new String[] {pageName,objectName,isMultiple,how,using};//Add all properties to String[]
						elementsList=new ArrayList<String[]>(); //Create new ArrayList for Elements of that page
						elementsList.add(elements);//Add All propertiesList to ElementList 
						perPageElements.put(pageName, elementsList);
					}

				}
			}

			System.out.println("UniquePages="+perPageElements.keySet());
			/*			
			for(String key: perPageElements.keySet()) {
				ArrayList<String[]> k= perPageElements.get(key);
				for(String[] s: k) {
					System.out.print(s[0]+"-");
					System.out.print(s[1]+"-");
					System.out.print(s[2]+"-");
					System.out.print(s[3]+"-");
					System.out.println(s[4]);
				}
			}
			 */		



		}catch (FileNotFoundException e) {
			// TODO: handle exception
		}
		catch (IOException e) {
			// TODO: handle exception
		}		

	}

	public void createORPages() {
		try {
			System.out.println("Total Pages="+perPageElements.size());

			int flag=0;
			String strFilePath;
			ArrayList<String[]> elementsList;
			String pagesPath=System.getProperty("user.dir");
			pagesPath=pagesPath.concat("\\src\\test\\java\\Pages");
			File filePath=new File(pagesPath);


			if(!filePath.exists()){
				filePath.mkdir();
				System.out.println("CREATED Package- Pages");
			}

			for(String pageName: perPageElements.keySet()) {

				//System.out.println("PageName="+uniquePageNames.get(i));

				elementsList=perPageElements.get(pageName);


				strFilePath=pagesPath.concat("\\"+pageName+".java");
				File objfile = new File(strFilePath);

				FileOutputStream is = new FileOutputStream(objfile);
				OutputStreamWriter osw = new OutputStreamWriter(is);    
				Writer w = new BufferedWriter(osw);

				writeHeader(w);
				writeContent(w,pageName,elementsList);

				w.close();
				System.out.println("CREATED PAGE-"+pageName);

			}	         

			if(flag==0){
				System.out.println("DONE- All Pages Created Successfully.");
			}else{
				System.out.println("FAIL- All Pages NOT  Created Successfully.");
				throw  new IOException();
			}

		}catch (FileNotFoundException e) {
			System.out.println("EXCEPTION- FILE NOT FOUND");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("EXCEPTION- IO EXCEPTION");
			e.printStackTrace();
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
