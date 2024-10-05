package CommonFunctions;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Media;

public class Reports {
		private static ThreadLocal extentTest = new ThreadLocal();
		 
		public static ExtentTest getTest() {
		return (ExtentTest)extentTest.get();
		}
		 
		public static void setTest(ExtentTest test) {
			extentTest.set(test);
		}
		 
		public static void log(Status status, Media message) {
			getTest().log(status, message);
		}
		
}