package Scripts.SignUpTest;

import static Util.GenericOperations.*;

import java.util.ArrayList;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.reporters.jq.ReporterPanel;

import Pages.LoginPage;
import Pages.SignUpPage;
import Util.ExcelRead;

public class SignUp {



	@BeforeMethod
	public void setup() {
		_initializeTest();
	}

	@Test
	public void Test_003() {
		System.out.println("TEST_003");
		
		LoginPage objLoginPage= new LoginPage(driver);
		SignUpPage objSignUpPage= new SignUpPage(driver);
		
		ExcelRead excelRead=new ExcelRead(".//TestData//TestData.xlsx");
		
		String firstName= excelRead.getCellData("SignUp", "FirstName", 1);
		objLoginPage.linkSignUp.click();
		_wait(3000);
		
		_reportDone("Done-Test_001");
		_reportPass("PASS-Test_001");
		_reportPass("TEST_001", "Passed");
		_reportFail("Fail-Test_001");
		_reportFail("TEST_001", "FAIL", driver);
		_reportFail("Message-", "expected value-", "Actual Val");
		
		
		
		Select select= new Select(objSignUpPage.paymentPlan);
		select.selectByVisibleText("Free Edition");
		
		objSignUpPage.firstName.sendKeys(firstName);
		
		
		_wait(3000);
		
	}

	@Test
	public void Test_004() {
		
		System.out.println("TEST_004");
		
		LoginPage objLoginPage= new LoginPage(driver);
		SignUpPage objSignUpPage= new SignUpPage(driver);
		
		ExcelRead excelRead=new ExcelRead(".//TestData//TestData.xlsx");
		String firstName= excelRead.getCellData("SignUp", "FirstName", 2);;
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
