package Pages;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage{
 
	 public LoginPage(WebDriver driver) {
	 	 PageFactory.initElements(driver, this);
 	 }
 
	 @FindBy(how = How.NAME, using = "username")
	 public WebElement userName;
 
	 @FindBy(how = How.NAME, using = "password")
	 public WebElement password;
 
	 @FindBy(how = How.XPATH, using = "//input[@value='Login']")
	 public WebElement loginBtn;
 
	 @FindBy(how = How.LINK_TEXT, using = "Features")
	 public WebElement linkFeatures;
 
	 @FindBy(how = How.LINK_TEXT, using = "Sign Up")
	 public WebElement linkSignUp;
 
	 @FindBy(how = How.LINK_TEXT, using = "Pricing")
	 public WebElement linkPricing;
 

 }