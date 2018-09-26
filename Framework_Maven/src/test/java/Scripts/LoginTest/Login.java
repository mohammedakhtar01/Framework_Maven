package Scripts.LoginTest;


import static Util.GenericOperations._initializeTest;
import static Util.GenericOperations.driver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.LoginPage;
import Util.ExcelRead;

public class Login {
		
	
	@BeforeMethod
	public void setup() {
		_initializeTest();
	}
	
	@Test
	public void Test_001() {
		System.out.println("TEST_001");
		
		LoginPage objLoginPage= new LoginPage(driver);
		
		ExcelRead excelRead=new ExcelRead(".//TestData//TestData.xlsx");

		String userName=excelRead.getCellData("Login", "UserName", 1);
		String password=excelRead.getCellData("Login", "Password", 1);
		
		
		objLoginPage.userName.sendKeys(userName);
		objLoginPage.password.sendKeys(password);
		objLoginPage.loginBtn.click();
		
		
	}
	
	@Test
	public void Test_002() {
		System.out.println("TEST_002");
		

		LoginPage objLoginPage= new LoginPage(driver);
		ExcelRead excelRead=new ExcelRead(".//TestData//TestData.xlsx");

		String userName=excelRead.getCellData("Login", "UserName", 2);
		String password=excelRead.getCellData("Login", "Password", 2);		
		
		
		objLoginPage.userName.sendKeys(userName);
		objLoginPage.password.sendKeys(password);
		objLoginPage.loginBtn.click();
	}
	
	@AfterMethod
	public void cleanup(){
		driver.quit();
	}
}
