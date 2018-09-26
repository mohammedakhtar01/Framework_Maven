package Util;



import Util.CreateObjectPages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.google.common.io.Files;


public class GenericOperations {
	public static WebDriver driver;
	public static String browser;
	public static String Url;
	public static int waitFactor;
	
	static {
		new CreateObjectPages(".//ObjectRep//ObjectRep.xlsx");
	}
	
	public static void _initializeTest(){
		try{
			
			browser=GetProperties.fetchPropertyValue("Browser");
			Url=GetProperties.fetchPropertyValue("Url");
			
			if(browser.equalsIgnoreCase("CHROME")){
				System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
			    options.addArguments("test-type");
				try{
					driver=new ChromeDriver(options);
				}catch(org.openqa.selenium.remote.UnreachableBrowserException e){
					System.out.println("UNREACHABLE BROWSER EXCEPTION- org.openqa.selenium.remote.UnreachableBrowserException ");
					//e.printStackTrace();
					driver=new ChromeDriver(options);
				}
			    	
			}
			else if (browser.equalsIgnoreCase("FIREFOX")) {
				System.setProperty("webdriver.gecko.driver", ".\\Driver\\geckodriver.exe");
				driver=new FirefoxDriver();
			}
			else if (browser.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver", ".\\Driver\\IEDriverServer.exe");
				driver=new InternetExplorerDriver();
			}
			else{
				System.out.println("Browser value not matched.Please enter valid value.");
			}

			driver.manage().window().maximize();
			driver.get(Url);
			
			
			_wait(3000);
			
		}catch(Exception e){
			System.out.println("EXCEPTION-INITIALIZE TEST FAILED");
			e.printStackTrace();
		}

	}
		
	public static boolean _waitForElement(int waitTime,By b ){
		try{
			
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.visibilityOfElementLocated(b));
			
			_reportDone("Element is Displayed");
			return true;
			
		}catch(Exception e){
			System.out.println("EXCEPTION-WAIT FOR ELEMENT");
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static void _wait(int MilliSec) {
		
		if(MilliSec < 1000)
			MilliSec = MilliSec*1000;
		try {
			Thread.sleep(MilliSec);
		} catch (InterruptedException e) {
			return;
		}
	}
	
	public static void _reportDone(String msg){
		System.out.println("DONE-"+msg);
		Reporter.log("DONE-"+msg);
	}

	public static void _reportPass(String msg){
		System.out.println("PASS-"+msg);
		Reporter.log("PASS-"+msg);
	}
	
	public static void _reportPass(String testCaseName,String msg){
		System.out.println("PASS-"+testCaseName+"-"+msg);
		Reporter.log("PASS-"+testCaseName+"-"+msg);
	}
	
	public static void _reportFail(String msg){
		System.out.println("FAIL-"+msg);
		Reporter.log("FAIL-"+msg);
	}
	
	public static void _reportFail(String msg,String expValue,String ActVal){
		System.out.println("FAIL-"+msg+"-Exp-"+expValue+"::Act-"+ActVal);
		Reporter.log("FAIL-"+msg+"-Exp-"+expValue+"::Act-"+ActVal);
	}
	
	public static void _reportFail(String TestMethodName,String msg,WebDriver driver){
		System.out.println("FAIL-"+TestMethodName+"-"+msg);
		Reporter.log("FAIL-"+TestMethodName+"-"+msg);
		_takeScreenshot(TestMethodName);	
		Assert.fail();
	}

	public static void _takeScreenshot(String fileName) {
		
		try {
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String reportsPath = ".\\Reports";
			
			//FileUtils.copyFile(scrFile,  new File(reportsPath+"\\Screenshots\\"+fileName+".png"));
			Files.copy(scrFile, new File(reportsPath+"\\Screenshots\\"+fileName+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION-TAKE SCREENSHOT");
			e.printStackTrace();
		}

	}
	
	public static By _getObjectProperties(String pageName,String objectName){

		boolean flag1=false,flag2=false;
		By b=null;

		for (ArrayList<String[]> page: CreateObjectPages.pages ){
			//System.out.println("I");
			//System.out.println("Elements in this page="+page.size());

			if(page.get(0)[0].equalsIgnoreCase(pageName)){
				//System.out.println("pageName="+page.get(0)[0]);

				for (String[] elem : page) {
					//System.out.println("J");
					if(elem[1].equalsIgnoreCase(objectName)){
						//System.out.println("TRUE-2");
						//System.out.println("objectName="+elem[2]);
						
						if(elem[3].equalsIgnoreCase("linkText")) {
							//elem[2]="linkText";
							b= By.linkText(elem[4]);
							flag1=true;
							break;
						}else if(elem[3].equalsIgnoreCase("partialLinkText")){
							//elem[2]="partialLinkText";
							b=By.partialLinkText(elem[4]);
							flag1=true;
							break;
						}else if(elem[3].equalsIgnoreCase("className")){
							//elem[2]="className";
							b=By.className(elem[4]);
							flag1=true;
							break;
						}else if(elem[3].equalsIgnoreCase("id")){
							//elem[2]="id";
							b=By.id(elem[4]);
							flag1=true;
							break;
						}else if(elem[3].equalsIgnoreCase("name")){
							//elem[2]="name";
							b=By.name(elem[4]);
							flag1=true;
							break;
						}else if(elem[3].equalsIgnoreCase("xpath")){
							//elem[2]="xpath";
							//System.out.println("XPATH");
							b=By.xpath(elem[4]);
							flag1=true;
							break;
						}else if(elem[3].equalsIgnoreCase("tagName")){
							//elem[2]="tagName";
							b=By.tagName(elem[4]);
							flag1=true;
							break;
						}else if(elem[3].equalsIgnoreCase("cssSelector")){
							//elem[2]="tagName";
							b=By.cssSelector(elem[4]);
							break;	
						}else {
							System.out.println("By=NULL");
							flag1=true;
							break;
						}	

					}	
					if(flag1){
						flag2=true;
						break;
					}
				}
				
			}
			if(flag2){
				break;
			}
		}	

		return b;
	}

	

}
