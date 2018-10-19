package Scripts.UploadLicenses;

import org.testng.Assert;
import org.testng.annotations.Test;

import Util.GenericOperations;

public class UploadLicenseFunctionality extends GenericOperations{
	
	
	@Test
	public void TC_5169_01_Upload_A_Valid_RPA_license() {
		
		_reportPass("PASS-TC_5169_01_Upload_A_Valid_RPA_license");
	}
	
	
	@Test
	public void TC_5169_02_Upload_A_Valid_SE_license_Named_Users() {
		
		_reportFail("FAIL-TC_5169_02_Upload_A_Valid_SE_license_Named_Users");
		
	}
	
	@Test
	public void TC_5169_03_Upload_A_Valid_SE_license_Concurrent_Users() {
		
		_reportFail("FAIL-TC_5169_03_Upload_A_Valid_SE_license_Concurrent_Users");
		
	}
	
	@Test
	public void TC_5176_02_Upload_Expired_License() {
		
		_reportPass("PASS-TC_5176_02_Upload_Expired_License");
	}
	
	@Test
	public void TC_5176_03_Upload_Invalid_License_File() {
		_reportFail("FAIL-TC_5176_03_Upload_Invalid_License_File");
	}
	
}
