package Scripts.SummaryPage;

import org.testng.annotations.Test;

import Util.GenericOperations;

public class LicenseDetails extends GenericOperations{
	
	@Test
	public void TC_5171_01_Summary_Page_Validate_RPA_License_Details() {
		
		_reportPass("PASS-TC_5171_01-Summary Page-Validate RPA License Details");
	}
	
	
	@Test
	public void TC_5171_02_SummaryPage_Validate_SE_Named_Users_License_Details() {
		
		_reportFail("FAIL-TC_5171_02-Summary Page- Validate SE-Named Users License Details");
		
	}
	
	@Test
	public void TC_5171_03_Summary_Page_Validate_SE_Concurrent_Users_License_Details() {
		
		_reportFail("FAIL-TC_5171_03-Summary Page- Validate SE-Concurrent Users License Details");
		
	}
}
