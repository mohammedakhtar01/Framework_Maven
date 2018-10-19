package Scripts.SummaryPage;

import org.testng.annotations.Test;

import Util.GenericOperations;

public class SummaryGraphs extends GenericOperations{
	
	@Test
	public void TC_5170_01_Check_Total_License_Count_Graph() {
		
		_reportPass("PASS-TC_5170_01-Check_Total_License_Count_Graph");
	}
	
	
	@Test
	public void TC_5170_02_Check_License_Count_by_Type_Graph() {
		
		_reportFail("FAIL-TC_5170_02-Check_License_Count_by_Type_Graph");
		
	}
	
	@Test
	public void TC_5170_03_Check_License_Count_by_Version_Graph() {
		
		_reportFail("FAIL-TC_5170_03-Check_License_Count_by_Version_Graph");
		
	}
	
	
	
	
}
