package Util;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;
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
		_takeScreenshot();	
		Assert.fail();
	}

	public static String _takeScreenshot() {
		
		try {
			Date date= new Date();
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String reportsPath = System.getProperty("user.dir")+"//target//surefire-reports";
			
			String fileName="Screenshot_"+date.toString().replace(" ", "_").replace(":","_")+".jpg";
			String filePath=reportsPath+"//"+fileName;
			
			FileUtils.copyFile(scrFile,  new File(filePath));
			
			
			return filePath;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION-TAKE SCREENSHOT");
			e.printStackTrace();
			return null;
		}

	}

}
