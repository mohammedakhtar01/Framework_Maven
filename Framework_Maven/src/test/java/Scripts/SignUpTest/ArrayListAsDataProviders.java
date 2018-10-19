package Scripts.SignUpTest;

import static Util.GenericOperations._initializeTest;
import static Util.GenericOperations._wait;
import static Util.GenericOperations.driver;

import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Pages.LoginPage;
import Pages.SignUpPage;
import Util.ExcelRead;

public class ArrayListAsDataProviders {



	@Test(dataProvider="getData")
	public void Test_001(String s1,String s2,String s3,String s4) {
		
		System.out.println("s1="+s1);
		System.out.println("s2="+s2);
		System.out.println("s3="+s3);
		System.out.println("s4="+s4);
	}


	//Store excel data in Arraylist<Object[]> 
	
	@DataProvider(name="getData")
	public Iterator<Object[]> getData() {
		
		String s1="A";
		String s2="B";
		String s3="C";
		String s4="D";
		
		Object[] obj1=new Object[]{s1,s2,s3,s4};
		ArrayList<Object[]> al= new ArrayList<Object[]>();
		al.add(obj1);
		
		s1="A1";
		s2="B1";
		s3="C1";
		s4="D1";
		
		obj1=new Object[]{s1,s2,s3,s4};
		
		al.add(obj1);
		
		
		return al.iterator();
	}


}	
