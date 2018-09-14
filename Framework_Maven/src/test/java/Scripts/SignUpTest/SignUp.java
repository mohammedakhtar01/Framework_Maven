package Scripts.SignUpTest;

import static Util.GenericOperations.*;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.LoginPage;
import Pages.SignUpPage;
import Util.ExcelRead;

public class SignUp {



	@BeforeMethod
	public void setup() {
		_initializeTest();
	}

	@Test
	public void Test_001() {
		System.out.println("TEST_001");
		
		LoginPage objLoginPage= new LoginPage(driver);
		SignUpPage objSignUpPage= new SignUpPage(driver);
		
		ExcelRead excelRead=new ExcelRead(".//TestData//TestData.xlsx", "SignUp");
		String firstName= excelRead.getCellValue("FirstName", 1);
		objLoginPage.linkSignUp.click();
		_wait(3000);
		
		Select select= new Select(objSignUpPage.paymentPlan);
		select.selectByVisibleText("Free Edition");
		
		objSignUpPage.firstName.sendKeys(firstName);
		
		_wait(3000);

	}

	@Test
	public void Test_002() {
		
		System.out.println("TEST_002");
		
		LoginPage objLoginPage= new LoginPage(driver);
		SignUpPage objSignUpPage= new SignUpPage(driver);
		
		ExcelRead excelRead=new ExcelRead(".//TestData//TestData.xlsx", "SignUp");
		String firstName= excelRead.getCellValue("FirstName", 2);
		objLoginPage.linkSignUp.click();
		_wait(3000);
		
		Select select= new Select(objSignUpPage.paymentPlan);
		select.selectByVisibleText("Free Edition");
		
		objSignUpPage.firstName.sendKeys(firstName);
		_wait(3000);
	}

	@AfterMethod
	public void cleanup(){
		driver.quit();
	}

}
