package Util;

import java.awt.AWTException;
import java.awt.Robot;

import Util.CreateObjectPages;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;


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
			waitFactor=Integer.parseInt(GetProperties.fetchPropertyValue("WaitFactor"));
			
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
			
			
			_wait("small");
			
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
	
	public static void _wait(String w){
		//waitFactor=InitFwk.waitFactor;
		waitFactor=1;
		int wait=waitFactor*1000;
		
		try {
			
			if(w.equalsIgnoreCase("VerySmall")){
				Thread.sleep(wait);
			}else if (w.equalsIgnoreCase("Small")) {
				Thread.sleep(wait*3);
			}else if (w.equalsIgnoreCase("Medium")) {
				Thread.sleep(wait*5);
			}else if (w.equalsIgnoreCase("Long")) {
				Thread.sleep(wait*10);
			}else{
				Thread.sleep(wait*3);
			}
			
		} catch (InterruptedException e) {
			return;
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
/*	
	public static void _reportFail(String TestMethodName,String msg,WebDriver driver){
		System.out.println("FAIL-"+TestMethodName+"-"+msg);
		Reporter.log("FAIL-"+TestMethodName+"-"+msg);
		_takeScreenshot(TestMethodName);	
		Assert.fail();
	}
	
	public static void _reportFail(String TestMethodName,String msg,Throwable e){
		System.out.println("FAIL-"+TestMethodName+"-"+msg);
		Reporter.log("FAIL-"+TestMethodName+"-"+msg);
		_takeScreenshot(TestMethodName);	
		Assert.fail(TestMethodName,e);
	}
	
	public static void _takeScreenshot(String fileName) {
		
		try {
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String reportsPath = InitFwk.reportsPath;
			
			FileUtils.copyFile(scrFile,  new File(reportsPath+"\\Screenshots\\"+fileName+".png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION-TAKE SCREENSHOT");
			e.printStackTrace();
		}

	}
*/	
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
	
	public static void _killChromeDriver(){
		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*	
	public static boolean _uploadFile(String fileName){
		String exeFileName="FileUpload.exe";
//		System.out.println("autoITScriptPath="+InitFwk.autoItScriptPath+"\\"+exeFileName);
//		System.out.println("testFileName="+InitFwk.testFilePath+fileName);
		try {
			Runtime.getRuntime().exec(InitFwk.autoItScriptPath+"\\"+exeFileName+" "+InitFwk.testFilesPath+fileName+" "+"chrome");
			_wait(10000);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_reportFail("EXCEPTION-FILE UPLOAD");
			return false;
		}
		 
	}
*/	
	public static int _generateRandomNum(){
		return (int) (Math.random() * 1000000);
	}

	public static boolean _compareTextEquals(WebElement Elem ,String expString){
		String actString=Elem.getText().trim();
		actString=actString.replace(",", "");
		
		if(expString.equalsIgnoreCase(actString)){
			_reportDone(expString+" is Same");
			return true;
		}else{
			_reportFail(expString+" Not Same. Exp="+expString+"::Actual="+actString);
			return false;
		}
	}
	
	public static boolean _compareTextContains(WebElement elem ,String expString){
		String actString=elem.getText().trim();
		actString=actString.replace(",", "");
		
		if(actString.contains(expString)){
			_reportDone(expString+" is Same");
			return true;
		}else{
			_reportFail(expString+" Not Same. Exp="+expString+"::Actual="+actString);
			return false;
		}
	}

	public static void _moveToElement(WebElement e1) throws AWTException{
		org.openqa.selenium.Point coordinates = e1.getLocation();
		Robot robot = new Robot();
		robot.mouseMove(coordinates.getX()+40,coordinates.getY()+80);
		robot.mouseMove(coordinates.getX()+45,coordinates.getY()+85);
		_wait(1000);
		
//		Actions action=new Actions(driver);
//		action.moveToElement(e1).build().perform();
//		_wait(1000);
		
	}
	
	public static void _moveToElement(WebElement e1,WebElement e2) throws AWTException{
		org.openqa.selenium.Point coordinates = e1.getLocation();
		Robot robot = new Robot();
		robot.mouseMove(coordinates.getX()+40,coordinates.getY()+80);
		robot.mouseMove(coordinates.getX()+45,coordinates.getY()+85);
		_wait(1000);
		
		org.openqa.selenium.Point coordinates2 = e2.getLocation();
		Robot robot2 = new Robot();
		robot2.mouseMove(coordinates2.getX()+40,coordinates2.getY()+80);
		_wait(1000);
//		
//		Actions action=new Actions(driver);
//		action.moveToElement(e1).moveToElement(e2).build().perform();
//		_wait(1000);
	}
	
	
	public static void _pageRefresh(){
		driver.navigate().refresh();
	}
	
	public static String _getTodaysDate(){
		DateFormat df = new SimpleDateFormat("dd/MMM/yy HH:mm:ss");
		Date dateobj = new Date();
		String date=df.format(dateobj).replace("/", "").replace(" ", "_").replace(":", "");
		
		System.out.println(date);
		return date;
		
	}
	
	
	public static void _selectDropdownByText(WebElement dd,String val){
		dd.click();
		_wait(1000);
		driver.findElement(By.xpath("//div[text()='"+val+"']")).click();;
		_wait(2000);
	}
	
	public static void _selectDropdownById(WebElement dd,String val){
		dd.click();
		_wait(1000);
		driver.findElement(By.xpath("//div[@data-item-id='"+val+"']")).click();;
		_wait(2000);
	}

	/****************************************************************/
	public static void _click(WebElement elem){
		elem.click();
	}
	
	public static void _sendKeys(WebElement elem,String txt){
		elem.sendKeys(txt);
	}
	
	public static void _wait_Small() {
		int MilliSec=3;
		if(MilliSec < 1000)
			MilliSec = MilliSec*1000;
		try {
			Thread.sleep(MilliSec);
		} catch (InterruptedException e) {
			return;
		}
	}
	
	public static void _selectTodaysDate(){
		driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']//td[contains(@class,'today')]")).click();
	}
	
	public static void _selectNextMonthDate(){
		driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']//a[@title='Next']")).click();
		_wait(1000);
		driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']//a[text()='20']")).click();
	}
	
	public static void _DoubleClick(WebElement e){
		
		Actions action=new Actions(driver);
		action.doubleClick(e).build().perform();
	}
	
}
