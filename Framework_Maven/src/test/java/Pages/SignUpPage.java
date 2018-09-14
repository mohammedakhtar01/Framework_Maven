package Pages;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage{
 
	 public SignUpPage(WebDriver driver) {
	 	 PageFactory.initElements(driver, this);
 	 }
 
	 @FindBy(how = How.ID, using = "payment_plan_id")
	 public WebElement paymentPlan;
 
	 @FindBy(how = How.NAME, using = "first_name")
	 public WebElement firstName;
 

 }